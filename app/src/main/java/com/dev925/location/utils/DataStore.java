package com.dev925.location.utils;

import android.content.Context;

import com.dev925.location.models.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static Gson gson = new Gson();
    private static DataStore instance;
    private TrieNode<Location> rootnode;

    public static DataStore create(Context context, int resource) {
        if (instance == null) {
            InputStream is = context.getResources().openRawResource(resource);
            String json = readInputStream(is);
            List<Location> list = createLocationList(json);
            instance.rootnode = createTrie(list);
        }

        return instance;
    }

    public static String readInputStream(InputStream is) {
        int size = 0;
        try {
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static List<Location> createLocationList(String json) {
        Type listType = new TypeToken<ArrayList<Location>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static TrieNode<Location> createTrie(List<Location> locations) {
        TrieNode<Location> locationTrieNode = new TrieNode<>();
        for (Location location: locations) {
            locationTrieNode.insert(location.toString(), location);
        }
        return locationTrieNode;
    }

    public static List<Location> search(String query) {
        return instance.rootnode.query(query);
    }


}
