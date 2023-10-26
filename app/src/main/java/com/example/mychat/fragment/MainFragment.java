package com.example.mychat.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mychat.MyApplication;
import com.example.mychat.R;
import com.example.mychat.object.User;
import com.example.mychat.activity.AddFriendActivity;
import com.google.android.material.tabs.TabLayout;

/**
 * @author gxb
 * place different fragments, change the displaying fragment according to click events
 */

public class MainFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private TextView textViewTitle;
    private ImageView imageViewPlus;
    private Fragment messageFragment;
    private Fragment contactFragment;
    private Fragment meFragment;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_main, container, false);
        initFragment();
        replaceFragment(messageFragment);
        index = 0;
        initView(view);
        setEvent();
        setTabLayout();
        initData();
        return view;
    }

    private void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        imageViewPlus = (ImageView) view.findViewById(R.id.imageViewPlus);
    }

    private void setEvent() {
        imageViewPlus.setOnClickListener(new View.OnClickListener() {
            PopupWindow popupWindow = null;
            boolean windowExist = false;

            @Override
            public void onClick(View view) {
                switch (index) {
                    case 1:
                        Intent intent = new Intent(getContext(), AddFriendActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setTabLayout() {

        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }

        for (int i = 0; i < 3; i++) {
            tabLayout.getTabAt(i).setCustomView(makeTabView(i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView imageView;
                switch (tab.getPosition()) {
                    case 0:
                        imageView = tab.getCustomView().findViewById(R.id.imageViewIcon);
                        imageView.setImageResource(R.drawable.message2);
                        textViewTitle.setText("chat");
                        replaceFragment(messageFragment);
                        index = 0;
                        imageViewPlus.setVisibility(View.INVISIBLE);
//                        imageViewPlus.setVisibility(View.VISIBLE);
//                        imageViewPlus.setImageResource(R.drawable.plus);
                        break;
                    case 1:
                        imageView = tab.getCustomView().findViewById(R.id.imageViewIcon);
                        imageView.setImageResource(R.drawable.contact2);
                        textViewTitle.setText("friends");
                        replaceFragment(contactFragment);
                        index = 1;
                        imageViewPlus.setVisibility(View.VISIBLE);
                        imageViewPlus.setImageResource(R.drawable.add_friend);
                        break;
                    case 2:
                        imageView = tab.getCustomView().findViewById(R.id.imageViewIcon);
                        imageView.setImageResource(R.drawable.my2);
                        textViewTitle.setText("profile");
                        replaceFragment(meFragment);
                        index = 3;
                        imageViewPlus.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView;
                switch (tab.getPosition()) {
                    case 0:
                        imageView = tab.getCustomView().findViewById(R.id.imageViewIcon);
                        imageView.setImageResource(R.drawable.message1);
                        break;
                    case 1:
                        imageView = tab.getCustomView().findViewById(R.id.imageViewIcon);
                        imageView.setImageResource(R.drawable.contact1);
                        break;
                    case 2:
                        imageView = tab.getCustomView().findViewById(R.id.imageViewIcon);
                        imageView.setImageResource(R.drawable.my1);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.center_layout, fragment);
        transaction.commit();
    }

    private View makeTabView(int position) {
        View tabView = LayoutInflater.from(this.getContext()).inflate(R.layout.tab_view, null);
        ImageView imageView = tabView.findViewById(R.id.imageViewIcon);
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.message2);
                break;
            case 1:
                imageView.setImageResource(R.drawable.contact1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.my1);
                break;
        }
        return tabView;
    }

    private void initFragment() {
        messageFragment = new MessageFragment();
        contactFragment = new ContactFragment();
        meFragment = new MeFragment();
    }

    private void initData() {
        Bundle bundle = MainFragment.this.getArguments();
        User user = (User) bundle.getSerializable("user");
        MyApplication.setUser(user);
    }
}