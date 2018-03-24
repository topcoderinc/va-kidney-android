package com.topcoder.vakidney.Model;

import android.text.format.DateUtils;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store chart data that can be used in three CHart Fragments
 */

public class ChartData extends SugarRecord<ChartData> {

    private double value;
    private long date;
    private int type;

    public ChartData() {}

    public ChartData(double value, int type, long date) {
        this.value = value;
        this.type = type;
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType() {
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

    public static List<ChartData> getLastYear(int chartType) {
        long now = System.currentTimeMillis();
        long lastYear = now - DateUtils.YEAR_IN_MILLIS;
        Select.from(ChartData.class);

        return ChartData.findWithQuery(
                ChartData.class,
                "Select * from CHART_DATA where type = ? AND date > ? ORDER BY date ASC",
                String.valueOf(chartType),
                String.valueOf(lastYear));
    }
}
