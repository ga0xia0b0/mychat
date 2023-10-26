package com.example.mychat.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mychat.MyApplication;
import com.example.mychat.R;
import com.example.mychat.activity.ChatActivity;
import com.example.mychat.activity.NewFriendActivity;
import com.example.mychat.object.Friend;
import com.example.mychat.object.Request;
import com.example.mychat.object.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author gxb
 * Get the user and friend objects from web server. Then use logical statements to decide which users are friends and add them to list.
 */

public class ContactFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private TextView textViewAddPrompt;
    private TextView textViewContactNumber;
    private ConstraintLayout layoutNewFriend;
    private List<User> users = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView();
        setEvent();
        return view;
    }

    private void initView() {
        textViewAddPrompt = view.findViewById(R.id.textViewAddPrompt);
        layoutNewFriend = view.findViewById(R.id.layoutNewFriend);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewContact);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ContactAdapter contactAdapter = new ContactAdapter(users);
        recyclerView.setAdapter(contactAdapter);
    }

    private void setEvent() {
        layoutNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateContactPrompt();
        updateContactUser();
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

        List<User> list;

        public ContactAdapter(List<User> list) {
            this.list = list;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewContact;
            TextView textViewContact;
            TextView textViewBlank;
            View viewContact;
            View viewContactLeft;

            public ViewHolder(View view) {
                super(view);
                imageViewContact = (ImageView) view.findViewById(R.id.imageViewContact);
                textViewContact = (TextView) view.findViewById(R.id.textViewContact);
                textViewBlank = (TextView) view.findViewById(R.id.textViewBlank);
                viewContact = (View) view.findViewById(R.id.viewContact);
                viewContactLeft = (View) view.findViewById(R.id.viewContactLeft);
            }
        }

        @NonNull
        @Override
        public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
            ContactAdapter.ViewHolder viewHolder = new ContactAdapter.ViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = list.get(viewHolder.getAdapterPosition());
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("avatar", user.getAvatar());
                    startActivity(intent);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
            User user = list.get(position);
            String username = user.getUsername();
            holder.textViewContact.setText(username);
            loadImage(holder.imageViewContact, user.getAvatar());

            if (position >= 1) {
                if (list.get(position - 1).getUsername().toLowerCase().charAt(0) == username.toLowerCase().charAt(0)) {
                    holder.textViewBlank.setVisibility(View.GONE);
                } else {
                    holder.textViewBlank.setText(username.toUpperCase().substring(0, 1));
                }
            }
            if (position == 0) {
                holder.textViewBlank.setText(username.toUpperCase().substring(0, 1));
            }

            if (position != getItemCount() - 1) {
                if (list.get(position + 1).getUsername().toLowerCase().charAt(0) != username.toLowerCase().charAt(0)) {
                    holder.viewContact.setVisibility(View.GONE);
                }
            } else {
                holder.viewContactLeft.setVisibility(View.VISIBLE);
                holder.viewContact.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    private void loadImage(ImageView imageView, String avatar) {
        RequestOptions options = new RequestOptions().bitmapTransform(new RoundedCorners(20));
        if (avatar.contentEquals("123")) {
            Glide.with(getContext())
                    .load(R.drawable.user)
                    .apply(options)
                    .into(imageView);
        } else {
            Glide.with(getContext())
                    .load(avatar)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .apply(options)
                    .into(imageView);
        }
    }

    private void updateContactPrompt() {
        BmobQuery<Request> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Request>() {
            @Override
            public void done(List<Request> list, BmobException e) {
                String userId = MyApplication.getUser().getObjectId();
                if (list != null) {
                    int count = 0;
                    for (Request request : list) {
                        if (request.getReceiverId().contentEquals(userId)) {
                            String requesterId = request.getRequesterId();
                            String receiverId = request.getReceiverId();
                            String requesterName = request.getRequesterName();
                            String requesterAvatar = request.getRequesterAvatar();
                            count++;
                        }
                    }
                    if (count == 0) {
                        textViewAddPrompt.setVisibility(View.INVISIBLE);
                    } else {
                        textViewAddPrompt.setVisibility(View.VISIBLE);
                        textViewAddPrompt.setText(count + "");
                    }
                }
            }
        });
    }
//更新联系人
    private void updateContactUser() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        int previousSize = users.size();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                BmobQuery<Friend> bmobQuery1 = new BmobQuery<>();
                bmobQuery1.findObjects(new FindListener<Friend>() {
                    @Override
                    public void done(List<Friend> list1, BmobException e) {
                        List<User> temp = new ArrayList<>();
                        User me = MyApplication.getUser();
                        if (list != null && list1 != null) {
                            for (User user : list) {
                                for (Friend friend : list1) {
                                    if (user.getObjectId().contentEquals(friend.getUser1()) && me.getObjectId().contentEquals(friend.getUser2())) {
                                        temp.add(user);
                                    }
                                    if (user.getObjectId().contentEquals(friend.getUser2()) && me.getObjectId().contentEquals(friend.getUser1())) {
                                        temp.add(user);
                                    }
                                }
                            }
                        }
                        users = temp;

                        Collections.sort(users, new Comparator<User>() {
                            @Override
                            public int compare(User o1, User o2) {
                                String s1 = o1.getUsername().toLowerCase();
                                String s2 = o2.getUsername().toLowerCase();
                                return s1.compareTo(s2);
                            }
                        });

                        if (users.size() != previousSize)
                            recyclerView.setAdapter(new ContactAdapter(users));
                    }
                });
            }
        });
    }
}