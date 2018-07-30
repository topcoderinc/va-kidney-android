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

    static class ViewHolder {
        TextView tvName;
        TextView tvCurrentValue;
        TextView tvUnit;
        TextView tvAddData;
        TextView tvSuggestion;
        ImageView imgStanding;
        ImageView imgTrend;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = activity.getLayoutInflater().inflate(R.layout.item_grid_labdata, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tvName);

            viewHolder.tvCurrentValue = view.findViewById(R.id.tvCurrentvalue);

            viewHolder.tvUnit = view.findViewById(R.id.tvUnit);

            viewHolder.tvSuggestion = view.findViewById(R.id.tvSuggestion);

            viewHolder.tvAddData = view.findViewById(R.id.tvData);

            viewHolder.imgStanding = view.findViewById(R.id.imgStanding);

            viewHolder.imgTrend = view.findViewById(R.id.imgTrend);

            viewHolder.tvSuggestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.showOkDialog(activity, activity.getString(R.string.feature_not_implemented), null);
                }
            });

            viewHolder.tvAddData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.showOkDialog(activity, activity.getString(R.string.feature_not_implemented), null);
                }
            });
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LabData labData = labDataArrayList.get(i);
        viewHolder.tvName.setText(labData.getName());
        viewHolder.tvCurrentValue.setText(labData.getCurrentValue());
        viewHolder.tvUnit.setText(labData.getUnit());

        switch (labData.getStanding()) {
            case 0:
                viewHolder.imgStanding.setImageResource(R.drawable.ic_bad);
                break;
            case 1:
                viewHolder.imgStanding.setImageResource(R.drawable.ic_normal);
                break;
            case 2:
                viewHolder.imgStanding.setImageResource(R.drawable.ic_good);
                break;
        }

        switch (labData.getTrend()) {
            case 0:
                viewHolder.imgTrend.setVisibility(View.VISIBLE);
                viewHolder.imgTrend.setImageResource(R.drawable.ic_arrow_down);
                break;
            case 1:
                viewHolder.imgTrend.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.imgTrend.setVisibility(View.VISIBLE);
                viewHolder.imgTrend.setImageResource(R.drawable.ic_arrow_up);
                break;
        }
        return view;
    }
}
