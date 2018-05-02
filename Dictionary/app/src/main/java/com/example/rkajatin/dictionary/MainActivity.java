package com.example.rkajatin.dictionary;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends Activity {

    private EditText etToLookup;
    private ImageButton ibLookup;
    private ProgressBar pbLookup;
    private BottomNavigationView navigation;
    private RecyclerView rvResults;
    private DictionaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        etToLookup = findViewById(R.id.et_word_to_lookup);
        ibLookup   = findViewById(R.id.bt_lookup);
        pbLookup   = findViewById(R.id.pb_lookup);
        navigation = findViewById(R.id.navigation);
        rvResults  = findViewById(R.id.rv_results);

        etToLookup.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    PerformSearch(v);
                    return true;
                }
                return false;
            }
        });

        ibLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformSearch(v);
            }
        });

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_dictionary:
                        PerformSearch(navigation.getRootView());
                        return true;
                    case R.id.navigation_thesaurus:
                        PerformSearch(navigation.getRootView());
                        return true;
                    case R.id.navigation_translate:
                        PerformSearch(navigation.getRootView());
                        return true;
                }
                return false;
            }
        });

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
            }
        });

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DictionaryAdapter(null);
        rvResults.setAdapter(adapter);
    }

    private void PerformSearch(View v) {
        etToLookup.clearFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        URL url = NetworkUtils.buildURL(etToLookup.getText().toString().trim().toLowerCase(), NetworkUtils.DICT.LEMMA);
        new FetchLemma().execute(url);
    }

    public class FetchLemma extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ibLookup.setVisibility(View.INVISIBLE);
            pbLookup.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            try {
                return NetworkUtils.fetchResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    JSONObject jsonLemmaObj = new JSONObject(s);
                    JSONArray jsonLexicalEntries = jsonLemmaObj
                            .getJSONArray(Strings.mFieldResult)
                            .getJSONObject(0)
                            .getJSONArray(Strings.mFieldLexicalEntries);


                    new FetchDictionaryResponse().execute(jsonLexicalEntries);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                super.onPostExecute(s);
            }
        }
    }

    public class FetchDictionaryResponse extends AsyncTask<JSONArray, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(JSONArray... jsonLexicalEntries) {
            JSONObject jsonResponse = new JSONObject();

            try {
                String lemma = JSONUtils.fetchLemma(jsonLexicalEntries[0]);

                URL urlEntry     = NetworkUtils.buildURL(lemma, NetworkUtils.DICT.ENTRY);
                URL urlThesaurus = NetworkUtils.buildURL(lemma, NetworkUtils.DICT.THESAURUS);
                URL urlTranslate = NetworkUtils.buildURL(lemma, NetworkUtils.DICT.TRANSLATE);

                String entry = null;
                String thesaurus = null;
                String translate = null;

                try {
                    entry = NetworkUtils.fetchResponseFromURL(urlEntry);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    thesaurus = NetworkUtils.fetchResponseFromURL(urlThesaurus);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    translate = NetworkUtils.fetchResponseFromURL(urlTranslate);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject jsonEntry     = (entry != null)     ? new JSONObject(entry)     : null;
                JSONObject jsonThesaurus = (thesaurus != null) ? new JSONObject(thesaurus) : null;
                JSONObject jsonTranslate = (translate != null) ? new JSONObject(translate) : null;

                jsonResponse
                        .putOpt(Strings.mFieldLemma     , jsonLexicalEntries[0])
                        .putOpt(Strings.mFieldDictionary, jsonEntry)
                        .putOpt(Strings.mFieldThesaurus , jsonThesaurus)
                        .putOpt(Strings.mFieldTranslate , jsonTranslate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject jsonResponse) {
            adapter.updateData(jsonResponse);

            ibLookup.setVisibility(View.VISIBLE);
            pbLookup.setVisibility(View.INVISIBLE);

            super.onPostExecute(jsonResponse);
        }
    }
}
