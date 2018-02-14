package com.topcoder.vakidney.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topcoder.vakidney.Model.MedicationResources;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.ResourcesDetailActivity;

import java.util.ArrayList;

/**
 * Created by Abinash Neupane on 2/8/2018.
 */

/**
 * Used to populate view with medication resources data read from MedicationResources.json file
 */
public class MedicationAdapter extends BaseAdapter {

    private final ArrayList<MedicationResources> medicationResourcesArrayList;
    private final Activity activity;

    public MedicationAdapter(ArrayList<MedicationResources> medicationResourcesArrayList, Activity activity) {
        this.medicationResourcesArrayList = medicationResourcesArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return medicationResourcesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return medicationResourcesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = activity.getLayoutInflater().inflate(R.layout.item_medication_list, viewGroup, false);
        LinearLayout mainView = view.findViewById(R.id.mainView);

        MedicationResources medicationResources = medicationResourcesArrayList.get(i);

        View viewMainTitle = activity.getLayoutInflater().inflate(R.layout.item_medicationresource_maintitle, viewGroup, false);
        TextView tvMainTitle = viewMainTitle.findViewById(R.id.mainTitle);
        tvMainTitle.setText(medicationResources.getMainTitle());
        mainView.addView(viewMainTitle);

        ArrayList<MedicationResources.MedicationTitleDesc> titleDescArrayList = medicationResources.getMedicationTitleDescs();
        for (int k = 0; k < titleDescArrayList.size(); k++) {
            final MedicationResources.MedicationTitleDesc medicationTitleDesc = titleDescArrayList.get(k);
            View viewTitleDesc = activity.getLayoutInflater().inflate(R.layout.item_medicationresource_titledesc, viewGroup, false);
            TextView tvTitle = viewTitleDesc.findViewById(R.id.title);
            TextView tvDesc = viewTitleDesc.findViewById(R.id.desc);
            LinearLayout divider = viewTitleDesc.findViewById(R.id.divider);
            tvTitle.setText(medicationTitleDesc.getTitle());
            tvDesc.setText(medicationTitleDesc.getDesc().replace("\n\n", ""));

            viewTitleDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.finish();
                    Intent intent=new Intent(activity, ResourcesDetailActivity.class);
                    intent.putExtra("title", medicationTitleDesc.getTitle());
                    intent.putExtra("actionbartitle", "Nutrition Details");
                    intent.putExtra("desc", medicationTitleDesc.getDesc());
                    activity.startActivity(intent);
                }
            });
            if(k==titleDescArrayList.size()-1){
                divider.setVisibility(View.GONE);
            }
            mainView.addView(viewTitleDesc);


        }
        return view;
    }

}
