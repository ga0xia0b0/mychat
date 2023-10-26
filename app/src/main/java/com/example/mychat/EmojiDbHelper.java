package com.example.mychat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmojiDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "emoji.db";
    private static final int DATABASE_VERSION = 1;

    // 表名和列名
    public static final String TABLE_EMOJI = "emoji";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    // 创建表的 SQL 语句
    private static final String DATABASE_CREATE = "create table "
            + TABLE_EMOJI + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_IMAGE_PATH
            + " text not null);";

    public EmojiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOJI);
        onCreate(db);
    }
}
