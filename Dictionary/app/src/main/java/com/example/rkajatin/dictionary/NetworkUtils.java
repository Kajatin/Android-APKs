package com.example.rkajatin.dictionary;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    private final static String DICT_ID = "ebb555bd";
    private final static String DICT_KEY = "0f3c4bf1bf14b249af6273c24a631246";
    private final static String DICT_BASE_URL = "https://od-api.oxforddictionaries.com/api/v1";
    private final static String DICT_LANG = "en";
    private final static String DICT_INFL = "inflections";
    private final static String DICT_ENTRY = "entries";
    private final static String DICT_THESAURUS = "synonyms;antonyms";
    private final static String DICT_TRANSLATE = "translations=";

    enum DICT {
        LEMMA,
        ENTRY,
        THESAURUS,
        TRANSLATE
    }

    public static URL buildURL(String wordToLookup, DICT type) {
        Uri dictionaryQueryURI = null;

        switch (type) {
            case LEMMA:
                dictionaryQueryURI = Uri.parse(DICT_BASE_URL).buildUpon()
                        .appendPath(DICT_INFL)
                        .appendPath(DICT_LANG)
                        .appendPath(wordToLookup)
                        .build();
                break;
            case ENTRY:
                dictionaryQueryURI = Uri.parse(DICT_BASE_URL).buildUpon()
                        .appendPath(DICT_ENTRY)
                        .appendPath(DICT_LANG)
                        .appendPath(wordToLookup)
                        .build();
                break;
            case THESAURUS:
                dictionaryQueryURI = Uri.parse(DICT_BASE_URL).buildUpon()
                        .appendPath(DICT_ENTRY)
                        .appendPath(DICT_LANG)
                        .appendPath(wordToLookup)
                        .appendPath(DICT_THESAURUS)
                        .build();
                break;
            case TRANSLATE:
                dictionaryQueryURI = Uri.parse(DICT_BASE_URL).buildUpon()
                        .appendPath(DICT_ENTRY)
                        .appendPath(DICT_LANG)
                        .appendPath(wordToLookup)
                        .appendPath(DICT_TRANSLATE+"de")
                        .build();
                break;
        }

        URL dictionaryQueryURL = null;
        try {
            dictionaryQueryURL = new URL(dictionaryQueryURI.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return dictionaryQueryURL;
    }

    public static String fetchResponseFromURL(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("app_id", DICT_ID);
        urlConnection.setRequestProperty("app_key", DICT_KEY);
        try {
            InputStream ins = urlConnection.getInputStream();
            Scanner scanner = new Scanner(ins);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
