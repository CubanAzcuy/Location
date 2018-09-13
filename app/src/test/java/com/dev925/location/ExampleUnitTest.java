package com.dev925.location;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void canBuildTry() {
        TrieNode root = new TrieNode();
        root.insert("car");
        List<String> carWords = root.query("car");
        Assert.assertEquals(carWords.size(), 1);
    }
}