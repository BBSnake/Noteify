package com.snaykmob.noteify.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.snaykmob.noteify.database.SQLiteHelper;
import com.snaykmob.noteify.dto.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String columns[] = {SQLiteHelper.ITEM_COLUMN_ID,
            SQLiteHelper.ITEM_COLUMN_CATEGORY_ID,
            SQLiteHelper.ITEM_COLUMN_TEXT,
            SQLiteHelper.ITEM_COLUMN_COMPLETED};

    public ItemDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ItemDTO createItem(String name, long categoryId, long completed) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.ITEM_COLUMN_CATEGORY_ID, categoryId);
        values.put(SQLiteHelper.ITEM_COLUMN_TEXT, name);
        values.put(SQLiteHelper.ITEM_COLUMN_COMPLETED, completed);
        long insertId = database.insert(SQLiteHelper.TABLE_ITEM, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_ITEM,
                columns, SQLiteHelper.ITEM_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ItemDTO newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    public void deleteItem(ItemDTO item) {
        long id = item.getId();
        database.delete(SQLiteHelper.TABLE_ITEM, SQLiteHelper.ITEM_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    public void deleteAllItems(long categoryId) {
        database.delete(SQLiteHelper.TABLE_ITEM, SQLiteHelper.ITEM_COLUMN_CATEGORY_ID + "=?", new String[]{String.valueOf(categoryId)});
    }

    public void deleteAllMarkedItems(long categoryId) {
        database.delete(SQLiteHelper.TABLE_ITEM, SQLiteHelper.ITEM_COLUMN_CATEGORY_ID + "=? and " + SQLiteHelper.ITEM_COLUMN_COMPLETED + "=?",
                new String[]{String.valueOf(categoryId), String.valueOf(1)});
    }

    public void updateItem(ItemDTO item) {
        long id = item.getId();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.ITEM_COLUMN_COMPLETED, item.getCompleted());
        database.update(SQLiteHelper.TABLE_ITEM, values, SQLiteHelper.ITEM_COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<ItemDTO> getItems(long categoryId) {
        List<ItemDTO> items = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_ITEM,
                columns, SQLiteHelper.ITEM_COLUMN_CATEGORY_ID + "=?", new String[]{String.valueOf(categoryId)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ItemDTO item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return items;
    }

    private ItemDTO cursorToItem(Cursor cursor) {
        ItemDTO item = new ItemDTO();
        item.setId(cursor.getLong(0));
        item.setCategoryId(cursor.getLong(1));
        item.setText(cursor.getString(2));
        item.setCompleted(cursor.getLong(3));
        return item;
    }
}
