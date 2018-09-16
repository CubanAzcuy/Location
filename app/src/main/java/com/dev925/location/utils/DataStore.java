package com.dev925.location.utils;

import android.content.Context;

import com.dev925.location.models.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static Gson gson = new Gson();
    private static DataStore instance;
    private TrieNode<Location> rootnode;

    public static DataStore create(Context context, int resource) {
        if (instance == null) {
            instance = new DataStore();
            InputStream is = context.getResources().openRawResource(resource);
            try {
                instance.rootnode = readFile(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static TrieNode<Location> readFile(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        TrieNode<Location> locationTrieNode = new TrieNode<>();

        while(reader.ready()) {
            String line = reader.readLine();
            //ignore array start
            if(!(line.equals("[") || line.equals("]"))) {
                if(line.substring(line.length()-1, line.length()).equals(",")) {
                    line = line.substring(0, line.length()-1);
                }
                Location location = gson.fromJson(line, Location.class);
                locationTrieNode.insert(location.toString(), location);
            }
        }

        return locationTrieNode;
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
        if(instance == null) return new ArrayList<>();
        return instance.rootnode.query(query);
    }


}
