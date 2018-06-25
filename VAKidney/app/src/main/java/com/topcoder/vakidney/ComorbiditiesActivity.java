package com.topcoder.vakidney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.topcoder.vakidney.constant.ChartType;
import com.topcoder.vakidney.databinding.ComorditiesChartBinding;
import com.topcoder.vakidney.model.ChartData;
import com.topcoder.vakidney.popup.AddChartPopup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This is activity class is used to show charts
 */
public class ComorbiditiesActivity extends AppCompatActivity implements View.OnClickListener, AddChartPopup.AddChartPopupListener {

    private long mChartType;
    private List<ChartData> mChartData;

    private static final DateFormat CHART_DATE_FORMAT = new SimpleDateFormat("MMM dd", Locale.US);

    ComorditiesChartBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_comorbidities_chart);

        mBinding.backBtn.setOnClickListener(this);
        mBinding.addBtn.setOnClickListener(this);

        mChartType = getIntent().getLongExtra("chartType", ChartType.TYPE_BLOODPRESSURE);

        mBinding.actionBarTitle.setText(ChartType.getChartLabel(mChartType));
        mBinding.textUnit.setText(ChartType.getChartUnit(mChartType));
        mBinding.bodyWeightDescription.setVisibility(mChartType == ChartType.TYPE_BODYWEIGHT ? View.VISIBLE : View.GONE);


        mChartData = ChartData.getLastYear(mChartType);
        if (mChartData != null && mChartData.size() > 0) {
            populateScatterData(mBinding.chart, mChartData);
        }
    }

    public static Intent getIntent(Context context, long chartType) {
        final Intent intent = new Intent(context, ComorbiditiesActivity.class);
        intent.putExtra("chartType", chartType);
        return intent;
    }

    ArrayList<IScatterDataSet> generateData(List<ChartData> chartDataArray, long chartType) {
        ArrayList<IScatterDataSet> sets = new ArrayList<>();
        mBinding.headers.setVisibility(View.VISIBLE);
        if (chartType == ChartType.TYPE_BLOODPRESSURE) {
            mBinding.legendA.setVisibility(View.VISIBLE);
            mBinding.legendA.setText(R.string.systolic);
            mBinding.legendB.setVisibility(View.VISIBLE);
            mBinding.legendB.setText(R.string.diastolic);

            List<Entry> systolicEntries = new ArrayList<>();
            List<Entry> diastolicEntries = new ArrayList<>();

            for (ChartData chartData : chartDataArray) {
                String value = chartData.getValue();
                double systolic = Integer.parseInt(String.valueOf(value).split("/")[0]);
                double diastolic = Integer.parseInt(String.valueOf(value).split("/")[1]);
                systolicEntries.add(new Entry(chartData.getDate(), Float.parseFloat(systolic + "f"), chartData));
                diastolicEntries.add(new Entry(chartData.getDate(), Float.parseFloat(diastolic + "f"), chartData));
            }

            ScatterDataSet systolicDataSet = new ScatterDataSet(systolicEntries, "Systolic");
            systolicDataSet.setScatterShapeSize(25f);
            systolicDataSet.setScatterShape(ScatterShape.CIRCLE);
            systolicDataSet.setColors(ColorTemplate.rgb("#003f72"));
            sets.add(systolicDataSet);

            ScatterDataSet diastolicDataSet = new ScatterDataSet(diastolicEntries, "Diastolic");
            diastolicDataSet.setScatterShapeSize(25f);
            diastolicDataSet.setScatterShape(ScatterShape.CIRCLE);
            diastolicDataSet.setColors(ColorTemplate.rgb("#ff669900"));
            sets.add(diastolicDataSet);
        } else if (chartType == ChartType.TYPE_BLOODGLUCOSE) {
            List<Entry> glucoseEntries = new ArrayList<>();
            for (ChartData chartData : chartDataArray) {
                String value = chartData.getValue();
                glucoseEntries.add(new Entry(chartData.getDate(), Float.parseFloat(value + "f"), chartData));
            }

            ScatterDataSet glucoseScatterDataSet = new ScatterDataSet(glucoseEntries, "Glucose");
            glucoseScatterDataSet.setScatterShapeSize(25f);
            glucoseScatterDataSet.setScatterShape(ScatterShape.CIRCLE);
            glucoseScatterDataSet.setColors(ColorTemplate.rgb("#003f72"));
            sets.add(glucoseScatterDataSet);
        } else if (chartType == ChartType.TYPE_BODYWEIGHT) {
            List<Entry> weightEntries = new ArrayList<>();
            for (ChartData chartData : chartDataArray) {
                String value = chartData.getValue();
                weightEntries.add(new Entry(chartData.getDate(), Float.parseFloat(value + "f"), chartData));
            }

            ScatterDataSet weightScatterDataSet = new ScatterDataSet(weightEntries, "Weight");
            weightScatterDataSet.setScatterShapeSize(25f);
            weightScatterDataSet.setScatterShape(ScatterShape.CIRCLE);
            weightScatterDataSet.setColors(ColorTemplate.rgb("#003f72"));
            sets.add(weightScatterDataSet);
        }

        return sets;

    }

    float getMinVisibleX(List<ChartData> chartDataArray) {
        float min = Long.MAX_VALUE;
        for (ChartData chartData : chartDataArray) {
            min = Math.min(min, chartData.getDate());
        }
        return min - 86400000f;
    }

    float getMaxVisibleX(List<ChartData> chartDataArray) {
        float max = Long.MIN_VALUE;
        for (ChartData chartData : chartDataArray) {
            max = Math.max(max, chartData.getDate());
        }
        return max + 86400000f;
    }


    private void populateScatterData(ScatterChart scatterChart, List<ChartData> chartDataArray) {
        ArrayList<IScatterDataSet> sets = generateData(chartDataArray, mChartType);

        ScatterData data = new ScatterData(sets);
        scatterChart.setData(data);
        scatterChart.invalidate();
        scatterChart.getDescription().setEnabled(false);


        Legend l = scatterChart.getLegend();
        l.setEnabled(false);
        scatterChart.getScatterData().setDrawValues(false);

        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTextColor(getColor(R.color.text_black));
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.rgb(0, 0, 0));
        xAxis.setTextSize(12.0f);
        xAxis.setEnabled(true);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(86400000); // one day
        xAxis.setAxisMinimum(getMinVisibleX(chartDataArray));
        xAxis.setAxisMaximum(getMaxVisibleX(chartDataArray));
        xAxis.setCenterAxisLabels(false);


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("chartdate", "chartdate: " + CHART_DATE_FORMAT.format(value));
                return CHART_DATE_FORMAT.format(value);
            }
        });

        YAxis leftAxis = scatterChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setGridColor(Color.RED);
        leftAxis.setDrawGridLines(false);
        leftAxis.setInverted(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setTextColor(getColor(R.color.text_black));
        leftAxis.setTextSize(14.0f);

        YAxis rightAxis = scatterChart.getAxisRight();
        rightAxis.setEnabled(false);

/*        String startDate = CHART_DATE_FORMAT.format(new Date(chartDataArray.get(0).getDate()));
        String endDate = CHART_DATE_FORMAT.format(new Date(chartDataArray.get(chartDataArray.size() - 1).getDate()));
        mTextStartDate.setText(startDate);
        mTextEndDate.setText(endDate);
        mTextStartDate.setVisibility(View.VISIBLE);
        mTextEndDate.setVisibility(View.VISIBLE);*/
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
        populateScatterData(mBinding.chart, mChartData);
    }

    @Override
    public void onCanceled() {

    }
}
