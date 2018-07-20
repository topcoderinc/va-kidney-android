package com.topcoder.vakidney.model;

import java.util.ArrayList;

/**
 * Created by Abinash Neupane on 2/8/2018.
 * It is used to store data for various Resource Articles used in Medication Fragments
 */

public class MedicationResources {

    private String mainTitle;
    private ArrayList<MedicationTitleDesc> medicationTitleDescs;

    public MedicationResources() {
        medicationTitleDescs = new ArrayList<>();
    }

    public void addMedicationTitleDesc(String title, String desc) {
        MedicationTitleDesc medicationTitleDesc = new MedicationTitleDesc();
        medicationTitleDesc.setTitle(title);
        medicationTitleDesc.setDesc(desc);
        medicationTitleDescs.add(medicationTitleDesc);
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public ArrayList<MedicationTitleDesc> getMedicationTitleDescs() {
        return medicationTitleDescs;
    }

    public void setMedicationTitleDescs(ArrayList<MedicationTitleDesc> medicationTitleDescs) {
        this.medicationTitleDescs = medicationTitleDescs;
    }

    public class MedicationTitleDesc {
        private String title;
        private String desc;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
