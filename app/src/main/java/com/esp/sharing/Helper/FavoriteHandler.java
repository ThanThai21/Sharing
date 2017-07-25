package com.esp.sharing.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Hoa's PC on 7/19/2017.
 */

public class FavoriteHandler extends SQLiteOpenHelper {

    private Context context;
    private static final int version = 1;
    private static final String name = "local.db";

    private final String TABLE_NAME = "favorite";
    private final String ID_SELLER = "ID";
    private final String STT = "stt";

    public FavoriteHandler(Context context) {
        super(context,name,null,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                STT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                ID_SELLER + " TEXT" +
                ")";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFav_Seller(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_SELLER, id); // Contact Name

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void deleteFav_Seller(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_SELLER + " = ?",
                new String[] { id });
        db.close();
    }

    public ArrayList<String> favSellers(){
        ArrayList<String> sellers = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String st = cursor.getString(cursor.getColumnIndex(ID_SELLER));
                sellers.add(st);
            } while (cursor.moveToNext());
        }
        return sellers;
    }
}
