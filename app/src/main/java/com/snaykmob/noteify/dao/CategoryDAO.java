package com.snaykmob.noteify.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.snaykmob.noteify.database.SQLiteHelper;
import com.snaykmob.noteify.dto.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String columns[] = {SQLiteHelper.CATEGORY_COLUMN_ID, SQLiteHelper.CATEGORY_COLUMN_TEXT};

    public CategoryDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public CategoryDTO createCategory(String text) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.CATEGORY_COLUMN_TEXT, text);
        long insertId = database.insert(SQLiteHelper.TABLE_CATEGORY, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_CATEGORY,
                columns, SQLiteHelper.CATEGORY_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        CategoryDTO newCategory = cursorToCategory(cursor);
        cursor.close();
        return newCategory;
    }

    public void deleteCategory(CategoryDTO category) {
        long id = category.getId();
        database.delete(SQLiteHelper.TABLE_ITEM, SQLiteHelper.ITEM_COLUMN_CATEGORY_ID + "=?",
                new String[] {String.valueOf(id)});
        database.delete(SQLiteHelper.TABLE_CATEGORY, SQLiteHelper.CATEGORY_COLUMN_ID + "=?",
                new String[] {String.valueOf(id)});
    }

    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = new ArrayList<CategoryDTO>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_CATEGORY,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CategoryDTO category = cursorToCategory(cursor);
            categories.add(category);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return categories;
    }

    private CategoryDTO cursorToCategory(Cursor cursor) {
        CategoryDTO category = new CategoryDTO();
        category.setId(cursor.getLong(0));
        category.setText(cursor.getString(1));
        return category;
    }
}
