package com.example.mychat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychat.EmojiAdapter;
import com.example.mychat.EmojiDataSource;
import com.example.mychat.R;

import java.util.List;

public class EmojiFragment extends Fragment implements EmojiAdapter.OnEmojiClickListener {
    private List<String> emojiList;
    private EmojiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);

        // 初始化数据,从 SQLite 中获取表情图数据
        EmojiDataSource emojiDataSource = new EmojiDataSource(requireContext());
        emojiDataSource.open();

        // 将表情图片地址信息插入数据库
        for (int i = 1; i <= 1001; i++) {
            String imagePath = "file:///android_asset/emoji (" + i + ").png";
            emojiDataSource.insertEmoji(imagePath);
        }

        emojiList = emojiDataSource.getAllEmojiPaths();
        emojiDataSource.close();

        // 初始化 RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.emoji_grid_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        // 初始化适配器
        adapter = new EmojiAdapter(emojiList, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onEmojiClick(String emojiUrl) {
        // 处理点击事件，emojiUrl 是点击的表情图对应的地址字符串
    }
}
