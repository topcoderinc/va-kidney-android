package com.topcoder.vakidney;

import android.graphics.Color;
import android.os.Bundle;
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

    private int mChartType;
    private LineChart mLineChart;
    private List<ChartData> mChartData;

    private TextView mTextStartDate;
    private TextView mTextEndDate;
    private DateFormat mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        AppCompatImageView backBtn;
        AppCompatImageView addBtn;
        backBtn = findViewById(R.id.backBtn);
        addBtn = findViewById(R.id.addBtn);
        backBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        if (getIntent().hasExtra("chartType")) {
            mChartType = getIntent().getIntExtra("chartType", ChartType.TYPE_E_GFR);
        }
        else {
            mChartType = ChartType.TYPE_E_GFR;
        }

        TextView titleText = findViewById(R.id.actionBarTitle);
        TextView unitText = findViewById(R.id.textUnit);
        titleText.setText(ChartType.getChartLabel(mChartType));
        unitText.setText(ChartType.getChartUnit(mChartType));

        mDateFormat = new SimpleDateFormat("MMM dd", Locale.US);
        mTextStartDate = findViewById(R.id.dateStart);
        mTextEndDate = findViewById(R.id.dateEnd);

        mLineChart = findViewById(R.id.chart);
        mChartData = ChartData.getLastYear(mChartType);
        if (mChartData != null && mChartData.size() > 0) {
            populateData(mLineChart, mChartData);
        }
    }


    /**
     * Used to populate and modify the chartView with corresponding data
     * @param lineChart is just a chratView
     * @param chartDataArray chart data
     */
    private void populateData(
            LineChart lineChart,
            List<ChartData> chartDataArray) {

        List<Entry> entries = new ArrayList<>();

        for(ChartData chartData : chartDataArray){
            entries.add(new Entry(chartData.getDate(), Float.parseFloat(chartData.getValue()+"f"), chartData));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Actual");
        dataSet.setColor(getColor(R.color.colorAccent));
        dataSet.setDrawCircles(true);
        dataSet.setLineWidth(3.0f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData;
        lineData = new LineData(dataSet);

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
        leftAxis.setGridColor(Color.RED);
        leftAxis.setDrawGridLines(true);
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
        mTextStartDate.setText(startDate);
        mTextEndDate.setText(endDate);
        mTextStartDate.setVisibility(View.VISIBLE);
        mTextEndDate.setVisibility(View.VISIBLE);
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
        populateData(mLineChart, mChartData);
    }

    @Override
    public void onCanceled() {

    }
}
