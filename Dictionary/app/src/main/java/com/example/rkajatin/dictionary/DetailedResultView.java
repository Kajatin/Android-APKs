package com.example.rkajatin.dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailedResultView extends Activity {

    private TextView mTextViewWord;
    private TextView mTextViewTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_result_view);

        Bundle bundle = getIntent().getExtras();
        ResponseDataHolder rdh = bundle.getParcelable(Strings.PACKAGE+".parcel");

        mTextViewWord = findViewById(R.id.tv_item_word);
        mTextViewTest = findViewById(R.id.test_intent);

        mTextViewWord.setText("Word");















        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;*/

        //this.setFinishOnTouchOutside(true);

        /*final Transition t = getWindow().getSharedElementEnterTransition();
        if(t!=null) {
            t.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    t.removeListener(this);
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    t.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }*/

    }
}
