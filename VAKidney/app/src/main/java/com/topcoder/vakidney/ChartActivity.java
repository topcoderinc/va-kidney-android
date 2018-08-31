package com.topcoder.vakidney;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.topcoder.vakidney.databinding.ActivityChartBinding;
import com.topcoder.vakidney.model.ChartData;
import com.topcoder.vakidney.constant.ChartType;
import com.topcoder.vakidney.popup.AddChartPopup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This is activity class is used to show charts
 */
public class ChartActivity extends AppCompatActivity implements
        View.OnClickListener,
        AddChartPopup.AddChartPopupListener {

    private final static long ONE_HOUR = 1000 * 60 * 60;
    private long mChartType;
    private List<ChartData> mChartData;


    private DateFormat mDateFormat;
    ActivityChartBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_chart);


        binder.backBtn.setOnClickListener(this);
        binder.addBtn.setOnClickListener(this);

        if (getIntent().hasExtra("chartType")) {
            mChartType = getIntent().getLongExtra("chartType", ChartType.TYPE_E_GFR);
        } else {
            mChartType = ChartType.TYPE_E_GFR;
        }
        if (mChartType == ChartType.TYPE_BODYWEIGHT)
            binder.tvHeading.setVisibility(View.VISIBLE);
        binder.actionBarTitle.setText(ChartType.getChartLabel(mChartType));
        binder.textUnit.setText(ChartType.getChartUnit(mChartType));

        mDateFormat = new SimpleDateFormat("MMM dd", Locale.US);

        mChartData = ChartData.getLastYear(mChartType);
        if (mChartData != null && mChartData.size() > 0) {
            populateData(binder.chart, mChartData);
        }

        Typeface typeface = ResourcesCompat.getFont(this, R.font.nexa_bold);
        binder.actionBarTitle.setTypeface(typeface);
    }


    /**
     * Used to populate and modify the chartView with corresponding data
     *
     * @param lineChart      is just a chratView
     * @param chartDataArray chart data
     */
    private void populateData(
            LineChart lineChart,
            List<ChartData> chartDataArray) {

        if (chartDataArray.size() == 1) {
            ChartData fakeChartData = new ChartData(
                    chartDataArray.get(0).getValue(),
                    chartDataArray.get(0).getType(),
                    chartDataArray.get(0).getDate() + ONE_HOUR
            );
            chartDataArray.add(fakeChartData);
        }

        List<Entry> entries = new ArrayList<>();

        for (ChartData chartData : chartDataArray) {
            entries.add(new Entry(chartData.getDate(), Float.parseFloat(chartData.getValue() + "f"), chartData));
        }

        ChartData firstChartData = chartDataArray.get(0);
        ChartData lastChartData = chartDataArray.get(chartDataArray.size() - 1);

        LineDataSet dataSet = new LineDataSet(entries, "Actual");
        dataSet.setColor(getColor(R.color.colorPrimaryDark));
        dataSet.setDrawCircles(true);
        dataSet.setLineWidth(3.0f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        ChartType.ChartThreshold threshold = ChartType.getChartThreshold(mChartType);

        // Build maximum goal chart
        List<Entry> maxGoalEntries = new ArrayList<>();
        maxGoalEntries.add(new Entry(firstChartData.getDate(), threshold.getMax()));
        maxGoalEntries.add(new Entry(lastChartData.getDate(), threshold.getMax()));

        LineDataSet maxGoalDataSet = new LineDataSet(maxGoalEntries, "Goal");
        maxGoalDataSet.setColor(getColor(android.R.color.holo_red_dark));
        maxGoalDataSet.setDrawCircles(false);
        maxGoalDataSet.setLineWidth(1.0f);
        maxGoalDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Build minimum goal chart
        List<Entry> minGoalEntries = new ArrayList<>();
        minGoalEntries.add(new Entry(firstChartData.getDate(), threshold.getMin()));
        minGoalEntries.add(new Entry(lastChartData.getDate(), threshold.getMin()));

        LineDataSet minGoalDataSet = new LineDataSet(minGoalEntries, "Goal");
        minGoalDataSet.setColor(getColor(android.R.color.holo_green_dark));
        minGoalDataSet.setDrawCircles(false);
        minGoalDataSet.setLineWidth(1.0f);
        minGoalDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData;
        lineData = new LineData();
        lineData.addDataSet(minGoalDataSet);
        lineData.addDataSet(maxGoalDataSet);
        lineData.addDataSet(dataSet);

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
        xAxis.setYOffset(-1.0f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setGridColor(Color.RED);
        leftAxis.setDrawGridLines(false);
        leftAxis.setInverted(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setTextColor(getColor(R.color.text_black));
        leftAxis.setTextSize(14.0f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        String startDate = mDateFormat.format(new Date(chartDataArray.get(0).getDate()));
        String endDate = mDateFormat.format(new Date(chartDataArray.get(chartDataArray.size() - 1).getDate()));
        binder.dateStart.setText(startDate);
        binder.dateEnd.setText(endDate);
        binder.dateStart.setVisibility(View.VISIBLE);
        binder.dateEnd.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backBtn: {
                finish();
            }
            break;
            case R.id.addBtn: {
                AddChartPopup popup = new AddChartPopup(this, mChartType);
                popup.setListener(this);
                popup.showAt(view);
            }
            break;
        }
    }

    @Override
    public void onAdded(ChartData chartData) {
        mChartData = ChartData.getLastYear(mChartType);
        populateData(binder.chart, mChartData);
    }

    @Override
    public void onCanceled() {

    }
}
