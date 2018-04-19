package com.topcoder.vakidney.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
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
    private final List<Long> mChartTypes;
    private List<Goal> mGoals = new ArrayList<>();
    private final UserData mUserData;

    public ChartMenuAdapter(RecyclerView parent, List<Long> chartTypes) {
        mRecycleView = parent;
        mChartTypes = chartTypes;
        mContext = parent.getContext();

        mUserData = UserData.get();
        if (mUserData != null) {
            mGoals = Goal.get(mUserData.getDiseaseCategory(), mUserData.isDialysis());
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

        if (position == 0) {
            viewHolder.viewHeader.setVisibility(View.VISIBLE);
            viewHolder.textHeader.setText("Major");
        }
        else if (position - mGoals.size() == 0) {
            viewHolder.viewHeader.setVisibility(View.VISIBLE);
            viewHolder.textHeader.setText("Other (Labs)");
        }
        else {
            viewHolder.viewHeader.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setTag(chartType);

        viewHolder.textLabel.setText(ChartType.getChartLabel(chartType));

        if (ChartType.isChartFilled(mContext, chartType)) {
            viewHolder.textLabel.setTypeface(null, Typeface.BOLD);
        }
        else {
            viewHolder.textLabel.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return mGoals.size() + mChartTypes.size();
    }

    @Override
    public void onClick(View view) {
        if(view.getTag() != null && view.getTag() instanceof Long) {
            Long chartType = (Long) view.getTag();
            Intent intent = new Intent(mContext, ChartActivity.class);
            intent.putExtra("chartType", chartType);
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
