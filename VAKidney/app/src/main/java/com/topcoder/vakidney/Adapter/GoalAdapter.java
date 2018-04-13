package com.topcoder.vakidney.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topcoder.vakidney.AddNewGoalActivity;
import com.topcoder.vakidney.customview.ArcProgress;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.constant.GoalType;

import java.util.List;

/**
 * Created by Abinash Neupane on 2/8/2018.
 */


/**
 * Goal adapter s used to populate the corresponding view with goal data from Goals.json file
 */
public class GoalAdapter extends BaseAdapter{


    private final List<Goal> goalArrayList;
    private final Activity activity;

    public GoalAdapter(List<Goal> goalArrayList, Activity activity) {
        this.goalArrayList = goalArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return goalArrayList.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return goalArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i==0){
            view = activity.getLayoutInflater().inflate(R.layout.item_grid_goal_addnew, viewGroup, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, AddNewGoalActivity.class));
                }
            });
        }else{
            view = activity.getLayoutInflater().inflate(R.layout.item_grid_goal, viewGroup, false);
            ArcProgress goalProgress = view.findViewById(R.id.goalProgress);
            LinearLayout layout = view.findViewById(R.id.layout);
            TextView tvCurrentGoals = view.findViewById(R.id.tvCurrentGoals);
            TextView tvGoalUnit = view.findViewById(R.id.tvGoalUnit);
            TextView tvAddGoalString = view.findViewById(R.id.tvAddGoalString);
            final Goal goal = goalArrayList.get( i - 1 );
            goalProgress.setMax((int)goal.getGoal());
            goalProgress.setArcAngle(250.0f);
            goalProgress.setIcon(GoalType.getIcon(goal.getType()));
            goalProgress.setIconColor(Color.parseColor("#"+goal.getColorCode()));
            goalProgress.setFinishedStrokeColor(Color.parseColor("#"+goal.getColorCode()));
            goalProgress.setProgress((int)goal.getCurrentLevel());
            goalProgress.setBottomTextSize(30);
            goalProgress.setBottomText(goal.getTitleStr());
            if ((goal.getCurrentLevel() == Math.floor(goal.getCurrentLevel())) && !Double.isInfinite(goal.getCurrentLevel())) {
                tvCurrentGoals.setText((int)goal.getCurrentLevel()+"/"+(int)goal.getGoal());
            }else{
                tvCurrentGoals.setText(goal.getCurrentLevel()+"/"+goal.getGoal());
            }
            tvGoalUnit.setText(goal.getUnitStr());
            tvAddGoalString.setText(GoalType.getAddTitle(goal.getType()));
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddNewGoalActivity.class);
                    intent.putExtra("goal", goal);
                    activity.startActivity(intent);
                }
            });
        }
        return view;
    }
}
