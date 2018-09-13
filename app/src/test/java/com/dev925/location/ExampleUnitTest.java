package com.dev925.location;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void attemptToBuildTrie() {
        TrieNode root = new TrieNode();
        root.insert("car");
        root.insert("cat");
        List<String> carWords = root.query("car");
        Assert.assertEquals(carWords.size(), 1);
    }
    @Test
    public void attemptToQueryTrie() {
        TrieNode root = new TrieNode();
        root.insert("car");
        root.insert("cat");
        List<String> carWords = root.query("car");
        Assert.assertEquals(carWords.size(), 1);

        List<String> caWords = root.query("ca");
        Assert.assertEquals(caWords.size(), 2);
    }


    @Test
    public void attemptToReadFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("cities.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String jsonAsString = new String(buffer, "UTF-8");
        Assert.assertTrue(jsonAsString != null && jsonAsString.length() > 0);
    }


}