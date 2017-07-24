package com.esp.sharing.Helper;

import android.content.Context;

import com.esp.sharing.Model.Seller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hoa's PC on 7/19/2017.
 */

public class HistoryManager {
    private final String NAME = "note.txt";
    private Context context;
    private ArrayList<Seller> sellers;
    private final String defaultJson = "histories";
    private String json;

    public HistoryManager(Context context) throws IOException, JSONException {
        this.context = context;
        if (!checkExits()){
            createJson();
            sellers = new ArrayList<>();
        } else {
            sellers = getSellers();
        }
    }

    public boolean checkExits(){
        File file = context.getFileStreamPath(NAME);
        return file.exists();
    }

    public void readData(){
        this.json = null;
        try {
            InputStream is = context.openFileInput(NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            this.json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void WriteData(String json) throws IOException {
        FileOutputStream out = context.openFileOutput(NAME, MODE_PRIVATE);
        out.write(json.getBytes());
        out.close();
    }

    public void createJson() throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject histories = new JSONObject();
        histories.put(defaultJson, jsonArray);
        String json = histories.toString();
        WriteData(json);
    }

    public void updateJson() throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        for (Seller seller : sellers ){
            JSONObject object = new JSONObject();
            object.put("id",seller.getId());
            object.put("name",seller.getName());
            object.put("address",seller.getLocation());
            object.put("phone",seller.getPhone());
            object.put("info",seller.getDescription());
            jsonArray.put(object);
        }
        JSONObject histories = new JSONObject();
        histories.put(defaultJson, jsonArray);
        String json = histories.toString();
        WriteData(json);
    }

    public void addHistory(Seller seller) throws IOException, JSONException {
        if (sellers.contains(seller)){
            sellers.remove(seller);
        }
        this.sellers.add(0,seller);
        if (this.sellers.size() > 10){
            this.sellers.remove(sellers.size());
        }
        updateJson();
    }

    public ArrayList<Seller> getSellers() throws JSONException {
        readData();
        JSONObject obj = new JSONObject(json);
        JSONArray m_jArry = obj.getJSONArray(defaultJson);
        ArrayList<Seller> sellerArrayList = new ArrayList<>();

        for (int i = 0; i < m_jArry.length(); i++) {
            JSONObject object = m_jArry.getJSONObject(i);
            Seller seller = new Seller();
            seller.setId(object.getInt("id"));
            seller.setName(object.getString("name"));
            seller.setLocation(object.getString("address"));
            seller.setPhone(object.getString("phone"));
            seller.setDescription(object.getString("info"));
            sellerArrayList.add(seller);
        }
        return sellerArrayList;
    }

}
