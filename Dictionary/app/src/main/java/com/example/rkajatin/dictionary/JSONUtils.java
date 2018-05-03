package com.example.rkajatin.dictionary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

    public static String fetchLemma(JSONArray from) {
        try {
            return from
                    .getJSONObject(0)
                    .getJSONArray("inflectionOf")
                    .getJSONObject(0)
                    .getString("id")
                    .toLowerCase();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fetchSynonyms(JSONObject from) {
        String ret = "";

        JSONArray jsonSynonyms = from.optJSONArray(Strings.mFieldSynonyms);
        if (jsonSynonyms != null) {
            for (int i = 0; i < jsonSynonyms.length(); ++i) {
                JSONObject jsonSynonym = jsonSynonyms.optJSONObject(i);
                String synonym = jsonSynonym.optString("text");
                if (synonym != null) {
                    ret = ret.concat(synonym+", ");
                }
            }
        }

        return ret;
    }

    public static String fetchAntonyms(JSONObject from) {
        String ret = "";

        JSONArray jsonAntoynms = from.optJSONArray(Strings.mFieldAntonyms);
        if (jsonAntoynms != null) {
            for (int i = 0; i < jsonAntoynms.length(); ++i) {
                JSONObject jsonAntonym = jsonAntoynms.optJSONObject(i);
                String synonym = jsonAntonym.optString("text");
                if (synonym != null) {
                    ret = ret.concat(synonym+", ");
                }
            }
        }

        return ret;
    }

    public static String fetchAnExample(JSONObject from) {
        String example = null;

        JSONArray examples = from.optJSONArray(Strings.mFieldExamples);
        if (examples != null) {
            example = examples.optJSONObject(0).optString("text");
        }

        return example;
    }

    public static String fetchTranslations(JSONObject from) {
        String ret = "";

        JSONArray jsonTranslations = from.optJSONArray(Strings.mFieldTranslations);
        if (jsonTranslations != null) {
            for (int i = 0; i < jsonTranslations.length(); ++i) {
                JSONObject jsonTranslation = jsonTranslations.optJSONObject(i);
                String translation = jsonTranslation.optString("text");
                if (translation != null) {
                    ret = ret.concat(translation+", ");
                }
            }
        }

        return ret;
    }

    public static String fetchPhoneticSpelling(JSONObject from) {
        String ret = null;

        JSONArray jsonPronunciation = from.optJSONArray(Strings.mFieldPronunciations);
        if (jsonPronunciation != null) {
            JSONObject jsonPhoneticSpelling = jsonPronunciation.optJSONObject(0);
            ret = "/"+jsonPhoneticSpelling.optString(Strings.mFieldPhoneticSpelling)+"/";
        }

        return ret;
    }
}
