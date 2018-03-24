package com.topcoder.vakidney.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topcoder.vakidney.ChartActivity;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.constant.ChartType;

import java.util.List;

public class ChartMenuAdapter extends Adapter implements View.OnClickListener {

    private final Context mContext;
    private final RecyclerView mRecycleView;
    private final List<Integer> mChartTypes;

    public ChartMenuAdapter(RecyclerView parent, List<Integer> chartTypes) {
        mRecycleView = parent;
        mChartTypes = chartTypes;
        mContext = parent.getContext();
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
        int chartType = mChartTypes.get(position);

        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setTag(chartType);

        viewHolder.textLabel.setText(ChartType.getChartLabel(chartType));
    }

    @Override
    public int getItemCount() {
        return mChartTypes.size();
    }

    @Override
    public void onClick(View view) {
        if(view.getTag() != null && view.getTag() instanceof Integer) {
            Integer chartType = (Integer) view.getTag();
            Intent intent = new Intent(mContext, ChartActivity.class);
            intent.putExtra("chartType", chartType);
            mContext.startActivity(intent);
        }
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textLabel;

        private NoteViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.text_label);
        }

    }

}
