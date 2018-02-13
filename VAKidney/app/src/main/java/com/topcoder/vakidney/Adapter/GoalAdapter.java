package com.topcoder.vakidney.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.topcoder.vakidney.AddNewGoalActivity;
import com.topcoder.vakidney.AddNewMealActivity;
import com.topcoder.vakidney.CustomView.ArcProgress;
import com.topcoder.vakidney.Model.Goal;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

import java.util.ArrayList;

/**
 * Created by abina on 2/8/2018.
 */


/**
 * Goal Adapter s used to populate the corresponding view with goal data from Goals.json file
 */
public class GoalAdapter extends BaseAdapter{


    private ArrayList<Goal> goalArrayList;
    private Activity activity;

    public GoalAdapter(ArrayList<Goal> goalArrayList, Activity activity) {
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
            view=activity.getLayoutInflater().inflate(R.layout.item_grid_goal_addnew, viewGroup, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                    activity.startActivity(new Intent(activity, AddNewGoalActivity.class));
                }
            });
        }else{
            view=activity.getLayoutInflater().inflate(R.layout.item_grid_goal, viewGroup, false);
            ArcProgress goalProgress=view.findViewById(R.id.goalProgress);
            TextView tvCurrentGoals=view.findViewById(R.id.tvCurrentGoals);
            TextView tvGoalUnit=view.findViewById(R.id.tvGoalUnit);
            TextView tvAddGoalString=view.findViewById(R.id.tvAddGoalString);
            final Goal goal=goalArrayList.get(i-1);
            goalProgress.setMax((int)goal.getGoal());
            goalProgress.setArcAngle(250.0f);
            goalProgress.setIcon(goal.getIcon());
            goalProgress.setIconColor(Color.parseColor("#"+goal.getColorCode()));
            goalProgress.setFinishedStrokeColor(Color.parseColor("#"+goal.getColorCode()));
            goalProgress.setProgress((int)goal.getCurrentLevel());
            goalProgress.setBottomText(JsondataUtil.getGoalTitleById(activity, goal.getTitle()));
            tvCurrentGoals.setText(goal.getCurrentLevel()+"/"+goal.getGoal());
            tvGoalUnit.setText(JsondataUtil.getGoalUnitById(activity,goal.getUnit()));
            tvAddGoalString.setText(goal.getAddString());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                    Intent intent=new Intent(activity, AddNewGoalActivity.class);
                    intent.putExtra("id", goal.getId());
                    activity.startActivity(intent);
                }
            });
        }
        return view;
    }
}
