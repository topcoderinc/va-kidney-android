package com.topcoder.vakidney.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.topcoder.vakidney.model.Resources;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.ResourcesDetailActivity;

import java.util.ArrayList;

/**
 * Created by Abinash Neupane on 2/8/2018.
 */

/**
 * Used to Populate view with resources data read from Resources.json file
 */

public class ResourcesAdapter extends BaseAdapter {
    private final ArrayList<Resources> resourcesArrayList;
    private final Activity activity;

    public ResourcesAdapter(ArrayList<Resources> resourcesArrayList, Activity activity) {
        this.resourcesArrayList = resourcesArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return resourcesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return resourcesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=activity.getLayoutInflater().inflate(R.layout.item_resources, viewGroup, false);
        TextView tvTitle=view.findViewById(R.id.title);
        TextView tvDesc=view.findViewById(R.id.desc);
        final Resources resources=resourcesArrayList.get(i);
        tvTitle.setText(resources.getTitle());
        tvDesc.setText(resources.getDesc().replace("\n\n", ""));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                Intent intent=new Intent(activity, ResourcesDetailActivity.class);
                intent.putExtra("title", resources.getTitle());
                intent.putExtra("actionbartitle", "Resource Details");
                intent.putExtra("desc", resources.getDesc());
                activity.startActivity(intent);
            }
        });
        return view;
    }
}
