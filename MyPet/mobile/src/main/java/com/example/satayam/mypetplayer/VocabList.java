/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.satayam.mypetplayer;

/**
 * {@link VocabList} represents a single Android platform release.
 * Each object has 3 properties: name, version number, and image resource ID.
 */
public class VocabList {

    // Name of the Android version (e.g. Gingerbread, Honeycomb, Ice Cream Sandwich)
    private String mVocabName;

    // Android version number (e.g. 2.3-2.7, 3.0-3.2.6, 4.0-4.0.4)
    private String mVocabMeaning;

    // Drawable resource ID
    private int mVocabResourcePath;

    /*
     * Create a new VocabList object.
     *
     * @param vName is the name of the vocab (e.g. distend)
     * @param vNumber is the corresponding meaning of the vocab (e.g. swell)
     * @param image is the contextual reference that corresponds to the vocab
     * */
    public VocabList(String vName, String vMeaning, int vResourcePath)
    {
        mVocabName = vName;
        mVocabMeaning = vMeaning;
        mVocabResourcePath = vResourcePath;
    }

    /**
     * Get the vocab name
     */
    public String getVocabName() {
        return mVocabName;
    }

    /**
     * Get the vocab meaning
     */
    public String getVocabMeaning() {
        return mVocabMeaning;
    }

    /**
     * Get the vocab resource path
     */
    public int getVocabResourcePath() {
        return mVocabResourcePath;
    }

}
