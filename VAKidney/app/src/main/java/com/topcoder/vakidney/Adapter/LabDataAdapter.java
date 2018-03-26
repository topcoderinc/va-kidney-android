package com.topcoder.vakidney.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.topcoder.vakidney.model.LabData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.DialogManager;

import java.util.ArrayList;

/**
 * Created by Abinash Neupane on 2/8/2018.
 */

/**
 * Used to populate view with data from Labdata.json file
 */
public class LabDataAdapter extends BaseAdapter {

    private final ArrayList<LabData> labDataArrayList;
    private final Activity activity;

    public LabDataAdapter(ArrayList<LabData> labDataArrayList, Activity activity) {
        this.labDataArrayList = labDataArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return labDataArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return labDataArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=activity.getLayoutInflater().inflate(R.layout.item_grid_labdata, viewGroup, false);
        TextView tvName=view.findViewById(R.id.tvName);
        TextView tvCurrentValue=view.findViewById(R.id.tvCurrentvalue);
        TextView tvUnit=view.findViewById(R.id.tvUnit);
        TextView tvSuggestion=view.findViewById(R.id.tvSuggestion);
        TextView tvAddData=view.findViewById(R.id.tvData);
        ImageView imgStanding=view.findViewById(R.id.imgStanding);
        ImageView imgTrend=view.findViewById(R.id.imgTrend);

        tvSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showOkDialog(activity, activity.getString(R.string.feature_not_implemented), null);
            }
        });


        tvAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showOkDialog(activity, activity.getString(R.string.feature_not_implemented), null);
            }
        });


        LabData labData=labDataArrayList.get(i);
        tvName.setText(labData.getName());
        tvCurrentValue.setText(labData.getCurrentValue());
        tvUnit.setText(labData.getUnit());

        switch (labData.getStanding()){
            case 0:
                imgStanding.setImageResource(R.drawable.ic_bad);
                break;
            case 1:
                imgStanding.setImageResource(R.drawable.ic_normal);
                break;
            case 2:
                imgStanding.setImageResource(R.drawable.ic_good);
                break;
        }

        switch (labData.getTrend()){
            case 0:
                imgTrend.setVisibility(View.VISIBLE);
                imgTrend.setImageResource(R.drawable.ic_arrow_down);
                break;
            case 1:
                imgTrend.setVisibility(View.GONE);
                break;
            case 2:
                imgTrend.setVisibility(View.VISIBLE);
                imgTrend.setImageResource(R.drawable.ic_arrow_up);
                break;
        }
        return view;
    }
}
