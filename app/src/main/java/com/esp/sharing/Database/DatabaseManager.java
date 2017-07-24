package com.esp.sharing.Database;

import android.content.Context;
import android.database.Cursor;

import com.esp.sharing.Model.Seller;

import java.util.ArrayList;

public class DatabaseManager {

    public static Database database;
    public static LocalDatabase localDatabase;

    public static ArrayList<Seller> getAllSeller(String name) {
        database.opendatabase();
        ArrayList<Seller> sellerArrayList = new ArrayList<Seller>();
        Cursor cursor = database.myDataBase.rawQuery("SELECT * FROM Sellers WHERE Name LIKE '%" + name + "%'", null);
        if (cursor == null || cursor.getCount() <= 0) {
            return sellerArrayList;
        }
        cursor.moveToFirst();
        do {
            int sId = cursor.getInt(0);
            String sName = cursor.getString(1);
            String sPhone = cursor.getString(2);
            String sLocation = cursor.getString(3);
            String sDescription = cursor.getString(4);
            if (sDescription == null) {
                sDescription = "Không có mô tả";
            }
            sellerArrayList.add(new Seller(sId, sName, sPhone, sLocation, "", sDescription));
        } while (cursor.moveToNext());
        database.close();
        return sellerArrayList;
    }

    public static ArrayList<Seller> getHistory() {
        ArrayList<Seller> sellerArrayList = new ArrayList<Seller>();
        Cursor cursor = localDatabase.queryInHistoryTable();
        if (cursor == null || cursor.getCount() <= 0) {
            return sellerArrayList;
        }
        cursor.moveToFirst();
        do {
            int historyId = cursor.getInt(0);
            int sellerId = cursor.getInt(1);
            String sellerName = cursor.getString(2);
            String sellerLocation = cursor.getString(3);
            sellerArrayList.add(new Seller(sellerId, sellerName, "", sellerLocation, "", ""));
        } while (cursor.moveToNext());
        cursor.close();
        return sellerArrayList;
    }

    public static ArrayList<Seller> getFavorite() {
        ArrayList<Seller> sellerArrayList = new ArrayList<Seller>();
        Cursor cursor = localDatabase.queryInFavoriteTable();
        if (cursor == null || cursor.getCount() <= 0) {
            return sellerArrayList;
        }
        cursor.moveToFirst();
        do {
            int sellerId = cursor.getInt(0);
            String sellerName = cursor.getString(1);
            String sellerLocation = cursor.getString(2);
            sellerArrayList.add(new Seller(sellerId, sellerName, "", sellerLocation, "", ""));
        } while (cursor.moveToNext());
        cursor.close();
        return sellerArrayList;
    }
}
