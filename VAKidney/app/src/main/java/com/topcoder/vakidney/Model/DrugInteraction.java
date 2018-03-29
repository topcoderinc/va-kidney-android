package com.topcoder.vakidney.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by afrisalyp on 14/03/2018.
 */

public class DrugInteraction extends SugarRecord<DrugInteraction> implements Serializable {

    String name;
    String query;
    String reportId;
    Date date;
    String drugsArray;
    String reactionsArray;

    public DrugInteraction() {}

    public DrugInteraction(
            String name,
            String query,
            String reportId,
            Date date,
            String drugsArray,
            String reactionsArray) {
        this.name = name;
        this.query = query;
        this.reportId = reportId;
        this.date = date;
        this.drugsArray = drugsArray;
        this.reactionsArray = reactionsArray;
    }

    public static DrugInteraction findByDrugs(String... drugs) {
        String name = drugs[0];
        for (int i = 1; i < drugs.length; i++) {
            name = " + " + drugs[i];
        }

        List<DrugInteraction> drugInteractions = DrugInteraction.find(
                DrugInteraction.class,
                "name = ?", name);
        return drugInteractions.size() > 0 ? drugInteractions.get(0) : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrugs(String... drugs) {
        String name = drugs[0];
        for (int i = 1; i < drugs.length; i++) {
            name = name + " + " + drugs[i];
        }
        String query = drugs[0];
        for (int i = 1; i < drugs.length; i++) {
            query = query + " " + drugs[i];
        }
        this.name = name;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDrugsArray() {
        return drugsArray;
    }

    public void setDrugsArray(String drugsArray) {
        this.drugsArray = drugsArray;
    }

    public String getReactionsArray() {
        return reactionsArray;
    }

    public void setReactionsArray(String reactionsArray) {
        this.reactionsArray = reactionsArray;
    }
}
