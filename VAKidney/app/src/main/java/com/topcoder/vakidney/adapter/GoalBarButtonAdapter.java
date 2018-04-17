package com.topcoder.vakidney.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.model.Goal;

import java.util.List;

/**
 * Created by afrisalyp on 12/04/2018.
 */

public class GoalBarButtonAdapter extends RecyclerView.Adapter
    implements View.OnClickListener {

    private Context mContext;
    private List<Goal> mGoals;
    private Goal mSelectedGoal;
    private OnSeekBarButtonChangeListener mListener;

    public GoalBarButtonAdapter(Context context, List<Goal> goals) {
        mContext = context;
        mGoals = goals;
        mSelectedGoal = mGoals.get(0);
    }

    public void setOnSeekBarButtonChangeListener(OnSeekBarButtonChangeListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Button button = (Button) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_seek_bar_button, null);
        button.setOnClickListener(this);
        return new Holder(button);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Goal goal = mGoals.get(position);

        Button button = (Button) holder.itemView;
        button.setTag(goal);
        button.setText(goal.getTitleStr());

        if (goal == mSelectedGoal) {
            button.setBackgroundResource(R.drawable.bg_seekbar_selected);
            button.setTextColor(mContext.getColor(R.color.colorWhite));
        }
        else {
            button.setBackgroundResource(android.R.color.transparent);
            button.setTextColor(mContext.getColor(R.color.colorLightDarkGray));
        }
    }

    @Override
    public int getItemCount() {
        return mGoals.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() != null && view.getTag() instanceof Goal) {
            mSelectedGoal = (Goal) view.getTag();
            notifyDataSetChanged();
            if (mListener != null) mListener.onGoalSelected(mSelectedGoal);
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        private Holder(Button itemView) {
            super(itemView);
        }

    }

    public Goal getSelectedGoal() {
        return mSelectedGoal;
    }

    public void setSelectedGoal(Goal mSelectedGoal) {
        this.mSelectedGoal = mSelectedGoal;
        notifyDataSetChanged();
    }

    public static interface OnSeekBarButtonChangeListener {
        void onGoalSelected(Goal goal);
    }
}
