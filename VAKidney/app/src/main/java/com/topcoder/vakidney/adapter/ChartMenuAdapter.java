package com.topcoder.vakidney.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topcoder.vakidney.ChartActivity;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.constant.ChartType;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;

import java.util.ArrayList;
import java.util.List;


/**
 * This is adapter class to show the menu contains chart type.
 */

public class ChartMenuAdapter extends Adapter implements View.OnClickListener {

    private final Context mContext;
    private final RecyclerView mRecycleView;
    private final List<Long> mChartTypes = new ArrayList<>();
    private List<Goal> mGoals = new ArrayList<>();
    private final UserData mUserData;

    public ChartMenuAdapter(RecyclerView parent, List<Long> chartTypes) {
        mRecycleView = parent;
        if (chartTypes != null) {
            mChartTypes.addAll(chartTypes);
        }
        mContext = parent.getContext();

        mUserData = UserData.get();
        if (mUserData != null) {
            mGoals = Goal.getWithoutComorbidities(mUserData.getDiseaseCategory(), mUserData.isDialysis());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chart_menu, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteViewHolder viewHolder = (NoteViewHolder) holder;
        long chartType =
                position >= mGoals.size() ?
                        mChartTypes.get(position - mGoals.size()) :
                        mGoals.get(position).getGoalId();

        Typeface boldTypeface = ResourcesCompat.getFont(mContext, R.font.nexa_bold);
        Typeface lightTypeface = ResourcesCompat.getFont(mContext, R.font.nexa_light);
        viewHolder.textHeader.setTypeface(boldTypeface);


        if (position == 0) {
            viewHolder.viewHeader.setVisibility(View.VISIBLE);
            viewHolder.textHeader.setText("Major");
        } else if (position - mGoals.size() == 0) {
            viewHolder.viewHeader.setVisibility(View.VISIBLE);
            viewHolder.textHeader.setText("Other (Labs)");
        } else {
            viewHolder.viewHeader.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setTag(chartType);

        viewHolder.textLabel.setText(ChartType.getChartLabel(chartType));

        if (ChartType.isChartFilled(mContext, chartType)) {
            viewHolder.textLabel.setTypeface(boldTypeface);
            viewHolder.textLabel.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            viewHolder.textLabel.setTypeface(lightTypeface);
            viewHolder.textLabel.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
        }
    }

    @Override
    public int getItemCount() {
        return mGoals.size() + mChartTypes.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() != null && view.getTag() instanceof Long) {
            Long chartType = (Long) view.getTag();
            Intent intent = new Intent(mContext, ChartActivity.class);
            intent.putExtra("chartType", chartType);
            Log.d("Chart type", chartType + "");
            mContext.startActivity(intent);
        }
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textLabel;
        private TextView textHeader;
        private View viewHeader;

        private NoteViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.text_label);
            textHeader = itemView.findViewById(R.id.text_header);
            viewHeader = itemView.findViewById(R.id.layout_header);
        }

    }

}
