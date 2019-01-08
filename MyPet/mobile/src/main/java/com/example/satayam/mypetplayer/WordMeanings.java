package com.example.satayam.mypetplayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;


public class WordMeanings {
    private String word;
    private String phonetic;
    private HashMap<String, Vector<String>> meanings;
    private String firstMeaning;

    WordMeanings(){
        word = phonetic = firstMeaning = null;
        meanings = new HashMap<String, Vector<String>>();
    }
    public void setWord(String word){
        this.word = word;
    }
    public  void setPhonetic(String phonetic){
        this.phonetic = phonetic;
    }
    public void setMeaning(String pos, String meaning){
        if(meanings.containsKey(pos)==false){
            Vector<String> stringVec = new Vector<String>();
            meanings.put(pos,stringVec);
        }
        if(firstMeaning == null)
            firstMeaning = meaning;
        meanings.get(pos).add(meaning);
    }
    public String getWord(){
        return word;
    }
    public String getPhonetic(){
        return phonetic;
    }
    public String getFirstMeaning(){
        return firstMeaning;
    }
}
