package com.esp.sharing.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {

    private static final String DB_LOCAL = "local.sqlite";
    private final static String HISTORY_TABLE_NAME = "History";
    private final static String HISTORY_ID = "HistoryId";
    private final static String SELLER_ID = "SellerId";
    private final static String SELLER_NAME = "SellerName";
    private final static String SELLER_PHONE = "SellerPhone";
    private final static String SELLER_LOCATION = "SellerLocation";
    private final static String SELLER_DESCRIPTION = "SellerDescription";
    private final static String PRODUCT_ID = "ProductId";
    private final static String PRODUCT_NAME = "ProductName";
    private final static String FARVORITE_TABLE_NAME = "Favorite";

    private Context context;


    public LocalDatabase(Context context) {
        super(context, DB_LOCAL, null, 1);
        createTable();
    }

    public void createTable() {
        String createHistoryTable = "CREATE TABLE IF NOT EXISTS " + HISTORY_TABLE_NAME + " (" +
                HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SELLER_ID + " INTEGER NOT NULL, " +
                SELLER_NAME + " TEXT NOT NULL, " +
                SELLER_LOCATION + " TEXT NOT NULL)";
        String createFavoriteTable = "CREATE TABLE IF NOT EXISTS " + FARVORITE_TABLE_NAME + " (" +
                SELLER_ID + " INTEGER PRIMARY KEY, " +
                SELLER_NAME + " TEXT NOT NULL, " +
                SELLER_LOCATION + " TEXT NOT NULL)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(createHistoryTable);
        database.execSQL(createFavoriteTable);

    }

    public boolean insertHistory(int sellerId, String sellerName, String sellerLocation) {
        ContentValues values = new ContentValues();
        values.put(SELLER_ID, sellerId);
        values.put(SELLER_NAME, sellerName);
        values.put(SELLER_LOCATION, sellerLocation);
        SQLiteDatabase database = getWritableDatabase();
        long index = database.insert(HISTORY_TABLE_NAME, null, values);
        return index > 0;
    }

    public boolean insertFavorite(int sellerId, String sellerName, String sellerLocation) {
        ContentValues values = new ContentValues();
        values.put(SELLER_ID, sellerId);
        values.put(SELLER_NAME, sellerName);
        values.put(SELLER_LOCATION, sellerLocation);
        SQLiteDatabase database = getWritableDatabase();
        long index = database.insert(FARVORITE_TABLE_NAME, null, values);
        return index > 0;
    }

//    public boolean updateRecord(String id, String title, String content, int textSize, int textColor, int bgResId) {
//        SQLiteDatabase database = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(TITLE_NOTE, title);
//        values.put(CONTENT_NOTE, content);
//        values.put(TEXT_SIZE, textSize);
//        values.put(TEXT_COLOR, textColor);
//        values.put(BACKGROUND_ID, bgResId);
//        long index = database.update(TABLE_NAME, values, ID_NOTE + " = " + id, null);
//        return index > 0;
//    }

    public boolean deleteFavoriteRecord(String sellerId) {
        SQLiteDatabase database = getWritableDatabase();
        long index = database.delete(FARVORITE_TABLE_NAME, SELLER_ID + " = " + sellerId, null);
        return index > 0;
    }

    public Cursor queryInHistoryTable() {
        String sql = "SELECT * FROM " + HISTORY_TABLE_NAME;
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor queryInFavoriteTable() {
        String sql = "SELECT * FROM " + FARVORITE_TABLE_NAME;
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
