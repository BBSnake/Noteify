package com.snaykmob.noteify.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.snaykmob.noteify.database.SQLiteHelper;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.dto.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String columns[] = {SQLiteHelper.ITEM_COLUMN_ID, SQLiteHelper.ITEM_COLUMN_CATEGORY_ID, SQLiteHelper.ITEM_COLUMN_TEXT};

    public ItemDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ItemDTO createItem(String name, long categoryId) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.ITEM_COLUMN_CATEGORY_ID, categoryId);
        values.put(SQLiteHelper.ITEM_COLUMN_TEXT, name);
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

    public void deleteComment(ItemDTO item) {
        long id = item.getId();
        System.out.println("Item deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_ITEM, SQLiteHelper.ITEM_COLUMN_ID + "=?",
                new String[] {String.valueOf(id)});
    }

    public List<ItemDTO> getItems(long categoryId) {
        List<ItemDTO> items = new ArrayList<ItemDTO>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_ITEM,
                columns, "id=?", new String[] {String.valueOf(categoryId)}, null, null, null);

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
        return item;
    }
}
