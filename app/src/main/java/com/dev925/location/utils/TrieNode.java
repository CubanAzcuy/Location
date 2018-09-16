package com.dev925.location.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode<T> {
    private HashMap<Character, TrieNode> children = new HashMap<>();
    private T content;
    private boolean isWord;

    //Some Assumption Here that Words are case sensitive this can be changed in the future
    public void insert(String word, T data) {
        if(word != null && word.trim().length() > 0) {
            word = cleanQuery(word);
            insertInToTrie(word, data);
        }
    }

    private void insertInToTrie(String turncatedWord, T data) {
        Character searchChar = turncatedWord.substring(0, 1).toCharArray()[0];

        TrieNode node = children.get(searchChar);

        if(node == null) {
            node = new TrieNode();
            children.put(searchChar, node);
            node.isWord = false;
        }

        if(turncatedWord.length() != 1) {
            String newTurncatedWord = turncatedWord.substring(1, turncatedWord.length());
            node.insertInToTrie(newTurncatedWord, data);
        } else {
            node.isWord = true;
            node.content = data;
        }
    }

    public List<T> query(String externalQuery) {
        if(externalQuery == null) {
            return new ArrayList<>();
        }

        return internalQuery(cleanQuery(externalQuery));
    }

    public List<T>  internalQuery(String externalQuery) {
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

    private List<T> getWords() {
        List<T> words = new ArrayList<>();

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

    private String cleanQuery(String term) {
        try {
            return term.trim().toLowerCase().replaceAll("[^A-Za-z0-9]", "");
        } catch (Exception e) {
            return term.trim().toLowerCase();
        }
    }
}

