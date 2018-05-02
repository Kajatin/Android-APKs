package com.example.rkajatin.dictionary;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.EntryViewHolder> {

    private int mExpandedPosition = -1;
    private ResponseDataHolder mDataHolder;
    private JSONArray mData;

    public DictionaryAdapter(JSONObject jsonData) {
        this.mDataHolder = new ResponseDataHolder(jsonData);
        this.mData = this.mDataHolder.getEveryEntry();
    }

    public void updateData(JSONObject jsonData) {
        mDataHolder.updateDataHolder(jsonData);
        this.mData = this.mDataHolder.getEveryEntry();
        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull EntryViewHolder holder) {
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.card_create_anim);
        holder.itemView.startAnimation(anim);
        super.onViewAttachedToWindow(holder);
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.entry_result_card, parent, false);
        return new EntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntryViewHolder holder, final int position) {
        try {
            final JSONObject entry = mData.getJSONObject(position);

            try {
                String ex = JSONUtils.fetchAnExample(entry);

                if (ex == null) {
                    holder.tvExample.setVisibility(View.GONE);
                }

                holder.tvWord.setText(entry.optString(Strings.mFieldID));
                holder.tvLexCategory.setText(entry.optString(Strings.mFieldLexicalCategory));
                holder.tvDefinition.setText(entry.optString(Strings.mFieldDefinitions));
                holder.tvExample.setText(ex);
            } catch (Exception e) {
                e.printStackTrace();
            }



            final boolean isExpanded = position == mExpandedPosition;
            //holder.tvExample.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    //TransitionManager.beginDelayedTransition((ViewGroup) v.findViewById(R.id.entry_card_root));
                    //notifyDataSetChanged();

                    Intent subActivity = new Intent(v.getContext(), DetailedResultView.class);
                    subActivity.putExtra(Strings.PACKAGE + ".parcel", mDataHolder);

                    String tr = v.getContext().getString(R.string.cardview_transition_name);
                    String ttr = v.getContext().getString(R.string.tv_word_transition_name);
                    ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),
                            Pair.create(v.findViewById(R.id.entry_card_root), tr),
                            Pair.create(v.findViewById(R.id.tv_item_word), ttr)
                    );

                    ((Activity) v.getContext()).getWindow().setExitTransition(null);
                    v.getContext().startActivity(subActivity, ao.toBundle());
                    ((Activity) v.getContext()).overridePendingTransition(0, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.length() : 0;
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvLexCategory;
        TextView tvDefinition;
        TextView tvExample;

        public EntryViewHolder(View v) {
            super(v);

            tvWord = (TextView) v.findViewById(R.id.tv_item_word);
            tvLexCategory = (TextView) v.findViewById(R.id.tv_item_lexcategory);
            tvDefinition = (TextView) v.findViewById(R.id.tv_item_definition);
            tvExample = (TextView) v.findViewById(R.id.tv_item_example);
        }
    }
}
