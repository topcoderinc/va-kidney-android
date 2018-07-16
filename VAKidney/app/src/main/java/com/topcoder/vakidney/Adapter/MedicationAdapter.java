package com.topcoder.vakidney.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.ResourcesDetailActivity;
import com.topcoder.vakidney.model.DrugInteraction;
import com.topcoder.vakidney.util.TextUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This is adapter class to show food recommendation list.
 */

public class MedicationAdapter extends Adapter {

    private final Context mContext;
    private final RecyclerView mRecycleView;
    private final List<DrugInteraction> mDrugInteractionList;
    private final Activity activity;
    public static final int Medication = 0;
    public static final int DrugInteractionWarning = 1;
    private int mMode;

    public MedicationAdapter(RecyclerView parent, List<DrugInteraction> mDrugInteractionList, Activity activity, int mode) {
        mRecycleView = parent;
        this.mDrugInteractionList = mDrugInteractionList;
        this.activity = activity;
        mContext = parent.getContext();
        mMode = mode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommendations, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DrugInteraction drugInteraction = mDrugInteractionList.get(position);

        viewHolder.itemView.setTag(drugInteraction);

        viewHolder.textTitle.setText(TextUtil.capitalizeFirstLetter(drugInteraction.getName()));
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.nexa_bold);
        viewHolder.textTitle.setTypeface(typeface);

        viewHolder.textDesc.setText(
                "Report: #" + drugInteraction.getReportId() +
                        "\n" +
                        "Date: " + new SimpleDateFormat("MMM dd yyyy", Locale.US).format(drugInteraction.getDate()));
        viewHolder.textDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                Intent intent = new Intent(activity, ResourcesDetailActivity.class);
                intent.putExtra("title", drugInteraction.getName());
                intent.putExtra("actionbartitle", "Drug Interaction Details");
                String descrtiption = "";
                descrtiption = "Report: #" + drugInteraction.getReportId() + "\n";
                descrtiption =
                        descrtiption +
                                "Date: " +
                                new SimpleDateFormat(
                                        "MMM dd yyyy",
                                        Locale.US
                                ).format(drugInteraction.getDate()) +
                                "\n\n";

                try {
                    if (mMode == Medication) {
                        descrtiption = descrtiption + "Medications:\n\n";
                        JSONArray drugsArray = new JSONArray(drugInteraction.getDrugsArray());
                        for (int i = 0; i < drugsArray.length(); i++) {
                            descrtiption =
                                    descrtiption +
                                            drugsArray
                                                    .getJSONObject(i)
                                                    .getString("medicinalproduct") +
                                            "\n";
                        }
                        descrtiption = descrtiption + "\n";
                    }
                    if (mMode == DrugInteractionWarning) {
                        descrtiption = descrtiption + "Drug Interaction Warning:\n\n";
                        JSONArray reactionsArray = new JSONArray(drugInteraction.getReactionsArray());
                        for (int i = 0; i < reactionsArray.length(); i++) {
                            descrtiption =
                                    descrtiption +
                                            reactionsArray
                                                    .getJSONObject(i)
                                                    .getString("reactionmeddrapt") +
                                            "\n";
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra("desc", descrtiption);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDrugInteractionList.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesc;

        private ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textDesc = itemView.findViewById(R.id.desc);
        }
    }

}
