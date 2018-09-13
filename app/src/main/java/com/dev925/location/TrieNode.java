package com.dev925.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode {
    private HashMap<Character, TrieNode> children = new HashMap<>();
    private String content;
    private boolean isWord;

    //Some Assumption Here that Words are case sensitive this can be changed in the future
    public void insert(String word) {
        if(word != null && word.trim().length() > 0) {
            insert(word, word);
        }
    }

    private void insert(String orginalWord, String turncatedWord) {
        Character searchChar = turncatedWord.substring(0, 1).toCharArray()[0];

        TrieNode node = children.get(searchChar);

        if(node == null) {
            node = new TrieNode();
            children.put(searchChar, node);
            node.isWord = false;
        }

        if(turncatedWord.length() != 1) {
            String newTurncatedWord = turncatedWord.substring(1, turncatedWord.length());
            node.insert(orginalWord, newTurncatedWord);
        } else {
            node.isWord = true;
            node.content = orginalWord;
        }
    }

    public List<String> query(String externalQuery) {

        if(externalQuery == null) {
            return new ArrayList<>();
        }

        if(externalQuery.length() == 0) {
            return getWords();
        } else {
            Character searchChar = externalQuery.substring(0, 1).toCharArray()[0];
            String internalQuery = externalQuery.substring(1, externalQuery.length());
            TrieNode node = children.get(searchChar);

            if(node == null) {
                return new ArrayList<>();
            } else {
                return node.query(internalQuery);
            }
        }
    }

    private List<String> getWords() {
        List<String> words = new ArrayList<>();

        if(isWord) {
            words.add(content);
        }

        if(!children.isEmpty()) {
            for (Character key : children.keySet()) {
                words.addAll(children.get(key).getWords());
            }
        }

        return words;
    }
}

