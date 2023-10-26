package com.example.mychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmojiDataSource {
    // 数据库字段
    private String[] allColumns = { EmojiDbHelper.COLUMN_ID,
            EmojiDbHelper.COLUMN_IMAGE_PATH };

    private SQLiteDatabase database;
    private EmojiDbHelper dbHelper;

    public EmojiDataSource(Context context) {
        dbHelper = new EmojiDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertEmoji(String imagePath) {
        ContentValues values = new ContentValues();
        values.put(EmojiDbHelper.COLUMN_IMAGE_PATH, imagePath);
        database.insert(EmojiDbHelper.TABLE_EMOJI, null, values);
    }

    public List<String> getAllEmojiPaths() {
        List<String> emojiPaths = new ArrayList<>();

        Cursor cursor = database.query(EmojiDbHelper.TABLE_EMOJI,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String imagePath = cursor.getString(cursor.getColumnIndex(EmojiDbHelper.COLUMN_IMAGE_PATH));
            emojiPaths.add(imagePath);
            cursor.moveToNext();
        }
        // 确保关闭cursor
        cursor.close();
        return emojiPaths;
    }
}

