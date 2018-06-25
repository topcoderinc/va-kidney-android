package com.topcoder.vakidney.fragments.charts;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.topcoder.vakidney.databinding.FragmentPotassiumBinding;
import com.topcoder.vakidney.model.ChartData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.JsondataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This fragment is used to show the charts of potassium using chart Data. This fragment uses LineChart for data representation
 */
public class PotassiumFragment extends Fragment {

    private ArrayList<ChartData> chartDataArrayListActual2016;
    private ArrayList<ChartData> chartDataArrayListGoals2016;

    private ArrayList<ChartData> chartDataArrayListActual2017;
    private ArrayList<ChartData> chartDataArrayListGoals2017;

    private LineChart lineChart;
    private LineChart lineChart2;

    private LineData lineData;


    private final List<Entry> entriesActual2016 = new ArrayList<>();
    private final List<Entry> entriesGoals2016 = new ArrayList<>();

    private final List<Entry> entriesActual2017 = new ArrayList<>();
    private final List<Entry> entriesGoals2017 = new ArrayList<>();
    private FragmentPotassiumBinding binder;

    public PotassiumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_potassium, container, false);
        final View view = binder.getRoot();

        chartDataArrayListActual2016 = JsondataUtil.getPotassiumDataActual(2016, getActivity());
        chartDataArrayListGoals2016 = JsondataUtil.getPotassiumDataGoal(2016, getActivity());

        chartDataArrayListActual2017 = JsondataUtil.getPotassiumDataActual(2017, getActivity());
        chartDataArrayListGoals2017 = JsondataUtil.getPotassiumDataGoal(2017, getActivity());

        PopulateData(binder.lineChart, chartDataArrayListActual2016, chartDataArrayListGoals2016, entriesActual2016, entriesGoals2016);
        PopulateData(binder.lineChart2, chartDataArrayListActual2017, chartDataArrayListGoals2017, entriesActual2017, entriesGoals2017);
        return view;
    }

    /**
     * Used to populate and modify the chartView with corresponding data
     *
     * @param lineChart                    is just a chartView
     * @param chartDataArrayListActual2016 chart data for year 2016
     * @param chartDataArrayListGoals2016  chart data for year 2017
     * @param entries                      chart data entries for 2016
     * @param entries2                     chart data entries for 2017
     */
    private void PopulateData(LineChart lineChart, ArrayList<ChartData> chartDataArrayListActual2016, ArrayList<ChartData> chartDataArrayListGoals2016, List<Entry> entries, List<Entry> entries2) {
        int i = 1;
        for (ChartData chartData : chartDataArrayListActual2016) {
//            entries.add(new Entry(i, Float.parseFloat(chartData.getValue()+"f"), chartData.getMonth()));
            i++;
        }
        i = 1;
        for (ChartData chartData : chartDataArrayListGoals2016) {
//            entries2.add(new Entry(i, Float.parseFloat(chartData.getValue()+"f"), chartData.getMonth()));
            i++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Actual");
        LineDataSet dataSet2 = new LineDataSet(entries2, "Goal");
        dataSet.setColor(getContext().getColor(R.color.colorAccent));
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(3.0f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        dataSet2.setColor(Color.parseColor("#557630"));
        dataSet2.setDrawCircles(false);
        dataSet2.setLineWidth(3.0f);
        dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineData = new LineData(dataSet);
        lineData.addDataSet(dataSet2);
        lineChart.setData(lineData);
        lineChart.invalidate();

        lineChart.getDescription().setEnabled(false);


        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        lineChart.getLineData().setDrawValues(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextSize(0.0f);
        xAxis.setEnabled(false);
        xAxis.setGranularityEnabled(false);
        xAxis.setGranularity(1f); // one hour

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setGridColor(Color.RED);
        leftAxis.setDrawGridLines(true);
        leftAxis.setInverted(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(14.0f);
        leftAxis.setValueFormatter(new MyValueFormatter());

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private class MyValueFormatter implements IAxisValueFormatter {

        public MyValueFormatter() {
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return String.format("%.1f", value) + " mEq/L";
        }
    }

}
