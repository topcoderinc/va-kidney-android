package com.topcoder.vakidney.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topcoder.vakidney.model.DrugInteraction;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.ResourcesDetailActivity;
import com.topcoder.vakidney.util.TextUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Abinash Neupane on 2/8/2018.
 */

/**
 * Used to populate view with medication resources data read from MedicationResources.json file
 */
public class MedicationAdapter extends BaseAdapter {

    private final List<DrugInteraction> drugInteractions;
    private final List<DrugInteraction> drugConsumptions;
    private final Activity activity;
    private final String[] sectionHeaders = new String[] {
            "Drug Consumption",
            "Drug Interaction Warning"
    };

    public MedicationAdapter(List<DrugInteraction> drugConsumptions,
                             List<DrugInteraction> drugInteractions,
                             Activity activity) {
        this.drugConsumptions = drugConsumptions;
        this.drugInteractions = drugInteractions;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        List<DrugInteraction> drugs = null;

        switch (i) {
            case 0: {
                drugs = drugConsumptions;
                break;
            }
            case 1: {
                drugs = drugInteractions;
                break;
            }
        }
        view = activity.getLayoutInflater().inflate(R.layout.item_medication_list, viewGroup, false);
        LinearLayout mainView = view.findViewById(R.id.mainView);

        View viewMainTitle = activity.getLayoutInflater().inflate(R.layout.item_medicationresource_maintitle, viewGroup, false);
        TextView tvMainTitle = viewMainTitle.findViewById(R.id.mainTitle);
        tvMainTitle.setText(sectionHeaders[i]);
        mainView.addView(viewMainTitle);

        for (int k = 0; k < drugs.size(); k++) {
            final DrugInteraction drugInteraction = drugs.get(k);
            View viewTitleDesc = activity.getLayoutInflater().inflate(
                    R.layout.item_medicationresource_titledesc,
                    viewGroup,
                    false);
            TextView tvTitle = viewTitleDesc.findViewById(R.id.title);
            TextView tvDesc = viewTitleDesc.findViewById(R.id.desc);
            LinearLayout divider = viewTitleDesc.findViewById(R.id.divider);
            tvTitle.setText(TextUtil.capitalizeFirstLetter(drugInteraction.getName()));
            tvDesc.setText(
                    "Report: #" + drugInteraction.getReportId() +
                    "\n" +
                    "Date: " + new SimpleDateFormat("MMM dd yyyy", Locale.US).format(drugInteraction.getDate()));

            viewTitleDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                    Intent intent = new Intent(activity, ResourcesDetailActivity.class);
                    intent.putExtra("title", drugInteraction.getName());
                    intent.putExtra("actionbartitle", "Drug Interaction Details");
                    String descrtiption = "";
                    descrtiption = "Report: #" + drugInteraction.getReportId() + "\n";
                    descrtiption =
                            descrtiption +
                            "Date: " +
                            new SimpleDateFormat(
                                    "MMM dd yyyy",
                                    Locale.US
                            ).format(drugInteraction.getDate()) +
                            "\n\n";

                    try {
                        JSONArray drugsArray = new JSONArray(drugInteraction.getDrugsArray());
                        for (int i = 0; i < drugsArray.length(); i++) {
                            descrtiption =
                                    descrtiption +
                                    drugsArray
                                            .getJSONObject(i)
                                            .getString("medicinalproduct") +
                                    "\n";
                        }
                        descrtiption = descrtiption + "\n";

                        JSONArray reactionsArray = new JSONArray(drugInteraction.getReactionsArray());
                        for (int i = 0; i < reactionsArray.length(); i++) {
                            descrtiption =
                                    descrtiption +
                                    reactionsArray
                                            .getJSONObject(i)
                                            .getString("reactionmeddrapt") +
                                    "\n";
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    intent.putExtra("desc", descrtiption);
                    activity.startActivity(intent);
                }
            });
            if(k == drugInteractions.size() - 1){
                divider.setVisibility(View.GONE);
            }
            mainView.addView(viewTitleDesc);

        }
        return view;
    }

}
