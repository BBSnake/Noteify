package com.snaykmob.noteify.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CATEGORY = "Category";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_TEXT = "text";
    public static final String CATEGORY_COLUMN_COMPLETED = "completed";

    public static final String TABLE_ITEM = "Item";
    public static final String ITEM_COLUMN_ID = "id";
    public static final String ITEM_COLUMN_CATEGORY_ID = "category_id";
    public static final String ITEM_COLUMN_TEXT = "text";
    public static final String ITEM_COLUMN_COMPLETED = "completed";

    private static final String DATABASE_NAME = "noteify.db";
    private static final int DATABASE_VERSION = 3;

    private static final String CATEGORY_CREATE =
            "create table " + TABLE_CATEGORY + "( " + CATEGORY_COLUMN_ID
                    + " integer primary key autoincrement, " + CATEGORY_COLUMN_TEXT
                    + " text not null, " + CATEGORY_COLUMN_COMPLETED + " integer);";

    private static final String ITEM_CREATE =
            "create table " + TABLE_ITEM + "( " + ITEM_COLUMN_ID
                    + " integer primary key autoincrement, " + ITEM_COLUMN_CATEGORY_ID
                    + " integer references " + TABLE_CATEGORY + "(" + CATEGORY_COLUMN_ID + ") on delete cascade, "
                    + ITEM_COLUMN_TEXT + " text not null, " + ITEM_COLUMN_COMPLETED + " integer);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CATEGORY_CREATE);
        database.execSQL(ITEM_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }
}
