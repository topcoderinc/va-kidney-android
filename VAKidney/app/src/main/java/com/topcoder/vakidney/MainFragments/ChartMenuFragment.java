package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.Adapter.ChartMenuAdapter;
import com.topcoder.vakidney.Adapter.FoodAdapter;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.constant.ChartType;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This class is used to show all the chart type related to the disease category
 */
public class ChartMenuFragment extends Fragment {

    public ChartMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_menu, container, false);

        UserData userData = UserData.get();

        RecyclerView recyclerView = view.findViewById(R.id.rv_chart_menu);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));

        List<Integer> chartTypes = ChartType.getChartsByCategory(
                userData.getDiseaseCategory(),
                userData.isDialysis()
        );

        ChartMenuAdapter adapter = new ChartMenuAdapter(recyclerView, chartTypes);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
