package com.example.rkajatin.dictionary;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseDataHolder implements Parcelable {

    public enum LEXICAL_CATEGORY {
        ADJECTIVE,
        ADVERB,
        COMBINING_FORM,
        CONJUNCTION,
        CONTRACTION,
        DETERMINER,
        IDIOMATIC,
        INTERJECTION,
        NOUN,
        NUMERAL,
        OTHER,
        PARTICLE,
        PREDETERMINER,
        PREFIX,PREPOSITION,
        RESIDUAL,
        SUFFIX,
        VERB
    }

    private JSONObject jsonCompleteResponse;

    public ResponseDataHolder(JSONObject jsonData) {
        jsonCompleteResponse = jsonData;
    }

    public void updateDataHolder(JSONObject jsonData) {
        jsonCompleteResponse = jsonData;
    }

    private String getCategoryString(LEXICAL_CATEGORY category) {
        switch (category) {
            case ADJECTIVE: return "Adjective";
            case ADVERB: return "Adverb";
            case COMBINING_FORM: return "Combining Form";
            case CONJUNCTION: return "Conjunction";
            case CONTRACTION: return "Contraction";
            case DETERMINER: return "Determiner";
            case IDIOMATIC: return "Idiomatic";
            case INTERJECTION: return "Interjection";
            case NOUN: return "Noun";
            case NUMERAL: return "Numeral";
            case OTHER: return "Other";
            case PARTICLE: return "Particle";
            case PREDETERMINER: return "Predeterminer";
            case PREFIX: return "Prefix";
            case PREPOSITION: return "Preposition";
            case RESIDUAL: return "Residual";
            case SUFFIX: return "Suffix";
            case VERB: return "Verb";
            default: return null;
        }
    }

    public JSONArray getEveryEntry() {
        JSONArray ret = new JSONArray();

        JSONObject jsonEntryObject = (jsonCompleteResponse != null) ?
                jsonCompleteResponse.optJSONObject(Strings.mFieldDictionary) : null;

        if (jsonEntryObject != null) {
            JSONObject jsonEntryResult = jsonEntryObject.optJSONArray(Strings.mFieldResult).optJSONObject(0);
            JSONArray jsonLexicalEntries = jsonEntryResult.optJSONArray(Strings.mFieldLexicalEntries);

            String id = jsonEntryResult.optString(Strings.mFieldID);
            String language = jsonEntryResult.optString(Strings.mFieldLanguage);
            String type = jsonEntryResult.optString(Strings.mFieldType);
            String word = jsonEntryResult.optString(Strings.mFieldWord);

            for (int i = 0; i < jsonLexicalEntries.length(); ++i) {
                JSONObject jsonLexicalEntry = jsonLexicalEntries.optJSONObject(i);
                String lexicalCategory = jsonLexicalEntry.optString(Strings.mFieldLexicalCategory);
                JSONArray jsonEntries = jsonLexicalEntry.optJSONArray(Strings.mFieldEntry);
                JSONArray jsonPronunciations = jsonLexicalEntry.optJSONArray(Strings.mFieldPronunciations);
                JSONArray jsonDerivativeOf = jsonLexicalEntry.optJSONArray(Strings.mFieldDerivativeOf);
                JSONArray jsonDerivatives = jsonLexicalEntry.optJSONArray(Strings.mFieldDerivatives);

                for (int j = 0; j < jsonEntries.length(); ++j) {
                    JSONObject jsonEntry = jsonEntries.optJSONObject(j);
                    String etymology = jsonEntry.has(Strings.mFieldEtymologies) ? jsonEntry.optJSONArray(Strings.mFieldEtymologies).optString(0) : null;
                    JSONArray jsonSenses = jsonEntry.optJSONArray(Strings.mFieldSenses);

                    for (int k = 0; k < jsonSenses.length(); ++k) {
                        JSONObject jsonSense = jsonSenses.optJSONObject(k);
                        String definition = jsonSense.has(Strings.mFieldDefinitions) ? jsonSense.optJSONArray(Strings.mFieldDefinitions).optString(0) : null;
                        String domains = jsonSense.has(Strings.mFieldDomains) ? jsonSense.optJSONArray(Strings.mFieldDomains).optString(0) : null;
                        JSONArray jsonExamples = jsonSense.optJSONArray(Strings.mFieldExamples);
                        JSONArray jsonRegisters = jsonSense.optJSONArray(Strings.mFieldRegisters);
                        JSONArray jsonSubSenses = jsonSense.optJSONArray(Strings.mFieldSubSenses);

                        try {
                            JSONObject obj = new JSONObject();
                            obj
                                    .putOpt(Strings.mFieldID, id)
                                    .putOpt(Strings.mFieldLanguage, language)
                                    .putOpt(Strings.mFieldType, type)
                                    .putOpt(Strings.mFieldWord, word)
                                    .putOpt(Strings.mFieldLexicalCategory, lexicalCategory)
                                    .putOpt(Strings.mFieldPronunciations, jsonPronunciations)
                                    .putOpt(Strings.mFieldDerivativeOf, jsonDerivativeOf)
                                    .putOpt(Strings.mFieldDerivatives, jsonDerivatives)
                                    .putOpt(Strings.mFieldEtymologies, etymology)
                                    .putOpt(Strings.mFieldDefinitions, definition)
                                    .putOpt(Strings.mFieldDomains, domains)
                                    .putOpt(Strings.mFieldExamples, jsonExamples)
                                    .putOpt(Strings.mFieldRegisters, jsonRegisters)
                                    .putOpt(Strings.mFieldSubSenses, jsonSubSenses);

                            ret.put(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return ret;
    }

    public JSONArray getThesaurus() {
        JSONArray ret = new JSONArray();

        JSONObject jsonThesaurusObject = (jsonCompleteResponse != null) ?
                jsonCompleteResponse.optJSONObject(Strings.mFieldThesaurus) : null;

        if (jsonThesaurusObject != null) {
            JSONObject jsonThesaurusResult = jsonThesaurusObject.optJSONArray(Strings.mFieldResult).optJSONObject(0);
            JSONArray jsonLexicalEntries = jsonThesaurusResult.optJSONArray(Strings.mFieldLexicalEntries);

            String id       = jsonThesaurusResult.optString(Strings.mFieldID);
            String language = jsonThesaurusResult.optString(Strings.mFieldLanguage);
            String type     = jsonThesaurusResult.optString(Strings.mFieldType);
            String word     = jsonThesaurusResult.optString(Strings.mFieldWord);

            for (int i = 0; i < jsonLexicalEntries.length(); ++i) {
                JSONObject jsonLexicalEntry = jsonLexicalEntries.optJSONObject(i);
                String lexicalCategory = jsonLexicalEntry.optString(Strings.mFieldLexicalCategory);
                JSONArray jsonEntries = jsonLexicalEntry.optJSONArray(Strings.mFieldEntry);

                for (int j = 0; j < jsonEntries.length(); ++j) {
                    JSONObject jsonEntry = jsonEntries.optJSONObject(j);
                    JSONArray jsonSenses = jsonEntry.optJSONArray(Strings.mFieldSenses);



                    for (int k = 0; k < jsonSenses.length(); ++k) {
                        JSONObject jsonSense = jsonSenses.optJSONObject(k);

                        String domains = jsonSense.has(Strings.mFieldDomains) ? jsonSense.optJSONArray(Strings.mFieldDomains).optString(0) : null;
                        JSONArray jsonExamples = jsonSense.optJSONArray(Strings.mFieldExamples);
                        JSONArray jsonRegisters = jsonSense.optJSONArray(Strings.mFieldRegisters);
                        JSONArray jsonSubSenses = jsonSense.optJSONArray(Strings.mFieldSubSenses);
                        JSONArray jsonSynonyms = jsonSense.optJSONArray(Strings.mFieldSynonyms);
                        JSONArray jsonAntonyms = jsonSense.optJSONArray(Strings.mFieldAntonyms);


                        try {
                            JSONObject obj = new JSONObject();
                            obj
                                    .putOpt(Strings.mFieldID, id)
                                    .putOpt(Strings.mFieldLanguage, language)
                                    .putOpt(Strings.mFieldType, type)
                                    .putOpt(Strings.mFieldWord, word)
                                    .putOpt(Strings.mFieldLexicalCategory, lexicalCategory)
                                    .putOpt(Strings.mFieldDomains, domains)
                                    .putOpt(Strings.mFieldExamples, jsonExamples)
                                    .putOpt(Strings.mFieldRegisters, jsonRegisters)
                                    .putOpt(Strings.mFieldSubSenses, jsonSubSenses)
                                    .putOpt(Strings.mFieldSynonyms, jsonSynonyms)
                                    .putOpt(Strings.mFieldAntonyms, jsonAntonyms);

                            ret.put(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return ret;
    }

    public JSONArray getTranslations() {
        JSONArray ret = new JSONArray();

        JSONObject jsonTranslateObject = (jsonCompleteResponse != null) ?
                jsonCompleteResponse.optJSONObject(Strings.mFieldTranslate) : null;

        if (jsonTranslateObject != null) {
            JSONObject jsonTranslateResult = jsonTranslateObject.optJSONArray(Strings.mFieldResult).optJSONObject(0);
            JSONArray jsonLexicalEntries = jsonTranslateResult.optJSONArray(Strings.mFieldLexicalEntries);

            String id       = jsonTranslateResult.optString(Strings.mFieldID);
            String language = jsonTranslateResult.optString(Strings.mFieldLanguage);
            String type     = jsonTranslateResult.optString(Strings.mFieldType);
            String word     = jsonTranslateResult.optString(Strings.mFieldWord);

            for (int i = 0; i < jsonLexicalEntries.length(); ++i) {
                JSONObject jsonLexicalEntry = jsonLexicalEntries.optJSONObject(i);
                String lexicalCategory = jsonLexicalEntry.optString(Strings.mFieldLexicalCategory);
                JSONArray jsonEntries = jsonLexicalEntry.optJSONArray(Strings.mFieldEntry);
                JSONArray jsonDerivativeOf = jsonLexicalEntry.optJSONArray(Strings.mFieldDerivativeOf);
                JSONArray jsonDerivatives = jsonLexicalEntry.optJSONArray(Strings.mFieldDerivatives);

                for (int j = 0; j < jsonEntries.length(); ++j) {
                    JSONObject jsonEntry = jsonEntries.optJSONObject(j);
                    String etymology = jsonEntry.has(Strings.mFieldEtymologies) ? jsonEntry.optJSONArray(Strings.mFieldEtymologies).optString(0) : null;
                    JSONArray jsonSenses = jsonEntry.optJSONArray(Strings.mFieldSenses);

                    for (int k = 0; k < jsonSenses.length(); ++k) {
                        JSONObject jsonSense = jsonSenses.optJSONObject(k);
                        String definition = jsonSense.has(Strings.mFieldDefinitions) ? jsonSense.optJSONArray(Strings.mFieldDefinitions).optString(0) : null;
                        String domains = jsonSense.has(Strings.mFieldDomains) ? jsonSense.optJSONArray(Strings.mFieldDomains).optString(0) : null;
                        JSONArray jsonExamples = jsonSense.optJSONArray(Strings.mFieldExamples);
                        JSONArray jsonRegisters = jsonSense.optJSONArray(Strings.mFieldRegisters);
                        JSONArray jsonSubSenses = jsonSense.optJSONArray(Strings.mFieldSubSenses);
                        JSONArray jsonPronunciations = jsonSense.optJSONArray(Strings.mFieldPronunciations);
                        JSONArray jsonTranslations = jsonSense.optJSONArray(Strings.mFieldTranslations);

                        try {
                            JSONObject obj = new JSONObject();
                            obj
                                    .putOpt(Strings.mFieldID, id)
                                    .putOpt(Strings.mFieldLanguage, language)
                                    .putOpt(Strings.mFieldType, type)
                                    .putOpt(Strings.mFieldWord, word)
                                    .putOpt(Strings.mFieldLexicalCategory, lexicalCategory)
                                    .putOpt(Strings.mFieldPronunciations, jsonPronunciations)
                                    .putOpt(Strings.mFieldDerivativeOf, jsonDerivativeOf)
                                    .putOpt(Strings.mFieldDerivatives, jsonDerivatives)
                                    .putOpt(Strings.mFieldEtymologies, etymology)
                                    .putOpt(Strings.mFieldDefinitions, definition)
                                    .putOpt(Strings.mFieldDomains, domains)
                                    .putOpt(Strings.mFieldExamples, jsonExamples)
                                    .putOpt(Strings.mFieldRegisters, jsonRegisters)
                                    .putOpt(Strings.mFieldSubSenses, jsonSubSenses)
                                    .putOpt(Strings.mFieldTranslations, jsonTranslations);

                            ret.put(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return ret;
    }









    /*
    Below piece of code implements the Parcelable functionality.
     */

    public static final Parcelable.Creator<ResponseDataHolder> CREATOR = new Parcelable.Creator<ResponseDataHolder>() {

        public ResponseDataHolder createFromParcel(Parcel in) {
            return new ResponseDataHolder(in);
        }

        public ResponseDataHolder[] newArray(int size) {
            return new ResponseDataHolder[size];
        }
    };

    private ResponseDataHolder(Parcel in) {
        try {
            jsonCompleteResponse = new JSONObject(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jsonCompleteResponse.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
