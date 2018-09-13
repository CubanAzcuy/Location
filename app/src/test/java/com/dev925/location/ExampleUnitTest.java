package com.dev925.location;

import com.dev925.location.models.Location;
import com.dev925.location.utils.TrieNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void attemptToBuildTrie() {
        TrieNode<String> root = new TrieNode<String>();
        root.insert("car", "car");
        root.insert("cat", "cat");
        List<String> carWords = root.query("car");
        Assert.assertEquals(carWords.size(), 1);
    }
    @Test
    public void attemptToQueryTrie() {
        TrieNode<String> root = new TrieNode<String>();
        root.insert("car", "car");
        root.insert("cat", "cat");
        List<String> carWords = root.query("car");
        Assert.assertEquals(carWords.size(), 1);

        List<String> caWords = root.query("ca");
        Assert.assertEquals(caWords.size(), 2);
    }

    private String readFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("cities.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

    @Test
    public void attemptToReadFile() throws IOException {
        String jsonAsString = readFile();
        Assert.assertTrue(jsonAsString != null && jsonAsString.length() > 0);
    }

    private List createLocationList() throws IOException {
        String jsonAsString = readFile();
        Type listType = new TypeToken<ArrayList<Location>>(){}.getType();
        return gson.fromJson(jsonAsString, listType);
    }

    @Test
    public void attemptToCreateJSONList() throws IOException {
        List<Location> locationList = createLocationList();
        Assert.assertTrue(locationList.size() > 0);
    }

    private TrieNode createLocationTrieFromList() throws IOException {
        List<Location> locationList = createLocationList();
        TrieNode root = new TrieNode();
        for (Location location: locationList) {
            root.insert(location.toString(), location);
        }
        return root;
    }

    @Test
    public void attemptToCreateTrieFromList() throws IOException {
        TrieNode root = createLocationTrieFromList();
        Assert.assertTrue(root.query("").size() > 0);
    }

    @Test
    public void queryForSpecificCity() throws IOException {
        TrieNode root = createLocationTrieFromList();
        long oldTime = new Date().getTime();
        List<Location> locations = root.query("A");
        long newTime = new Date().getTime();
        System.out.println("This query took around: " + (newTime - oldTime));
        Assert.assertTrue(root.query("Atlanta").size() == 1);
    }

    @Test
    public void queryForNull() throws IOException {
        TrieNode root = createLocationTrieFromList();
        long oldTime = new Date().getTime();
        List<Location> locations = root.query("Aasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf");
        long newTime = new Date().getTime();
        System.out.println("This query took around: " + (newTime - oldTime));
        Assert.assertTrue(locations.size() == 0);
    }
}