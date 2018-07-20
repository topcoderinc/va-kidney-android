package com.topcoder.vakidney.model;

import android.text.format.DateUtils;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store chart data that can be used in three CHart Fragments
 */

public class ChartData extends SugarRecord<ChartData> {

    private String value;
    private long date;
    private long type;

    public ChartData() {
    }

    public ChartData(String value, long type, long date) {
        this.value = value;
        this.type = type;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public void save() {
        List<ChartData> olds = ChartData.find(
                ChartData.class,
                "date = ? AND type = ?",
                String.valueOf(this.date),
                String.valueOf(this.type));
        if (olds.size() > 0) {
            for (ChartData old : olds) {
                old.delete();
            }
        }
        super.save();
    }

    public static List<ChartData> getLastYear(long chartType) {
        long now = System.currentTimeMillis();
        long lastYear = now - DateUtils.YEAR_IN_MILLIS;
        Select.from(ChartData.class);

        return ChartData.findWithQuery(
                ChartData.class,
                "Select * from CHART_DATA where type = ? AND date > ? ORDER BY date ASC",
                String.valueOf(chartType),
                String.valueOf(lastYear));
    }

    public static List<ChartData> getChartData(long chartType) {
        return ChartData.findWithQuery(
                ChartData.class,
                "Select * from CHART_DATA where type = ?  ORDER BY date DESC",
                String.valueOf(chartType));
    }
}
