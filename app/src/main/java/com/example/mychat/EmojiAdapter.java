package com.example.mychat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {
    private List<String> emojiList;
    private OnEmojiClickListener clickListener;

    // 构造函数
    public EmojiAdapter(List<String> emojiList, OnEmojiClickListener clickListener) {
        this.emojiList = emojiList;
        this.clickListener = clickListener;
    }

    @Override
    public EmojiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emoji_item, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmojiViewHolder holder, int position) {
        final String emojiUrl = emojiList.get(position);
        // 使用 Glide 加载图片
        Glide.with(holder.imageView)
                .load(emojiUrl)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    static class EmojiViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        EmojiViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.emoji_image);
        }
    }

    public interface OnEmojiClickListener {
        void onEmojiClick(String emojiUrl);
    }
}

