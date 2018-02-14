package com.topcoder.vakidney.Model;

/**
 * Created by Abinash Neupane on 2/9/2018.
 * It contains the overall data for a particular user using the app.
 */

public class UserData {

    private String username;
    private String password;
    private String fullname;
    private int points;
    private int age;
    private int height;
    private int weight;
    private boolean dialysis;
    private String diseaseCategory;
    private boolean setupgoals;
    private boolean avatar;
    private boolean biometric;
    private double runningcurrent;
    private double runninggoal;
    private int stepcurrent;
    private int stepgoal;
    private int jumpcurrent;
    private int jumpgoal;
    private int swimmingcurrent;
    private int swimminggoal;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isDialysis() {
        return dialysis;
    }

    public void setDialysis(boolean dialysis) {
        this.dialysis = dialysis;
    }

    public String getDiseaseCategory() {
        return diseaseCategory;
    }

    public void setDiseaseCategory(String diseaseCategory) {
        this.diseaseCategory = diseaseCategory;
    }

    public boolean isSetupgoals() {
        return setupgoals;
    }

    public void setSetupgoals(boolean setupgoals) {
        this.setupgoals = setupgoals;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public boolean isBiometric() {
        return biometric;
    }

    public void setBiometric(boolean biometric) {
        this.biometric = biometric;
    }

    public double getRunningcurrent() {
        return runningcurrent;
    }

    public void setRunningcurrent(double runningcurrent) {
        this.runningcurrent = runningcurrent;
    }

    public double getRunninggoal() {
        return runninggoal;
    }

    public void setRunninggoal(double runninggoal) {
        this.runninggoal = runninggoal;
    }

    public int getStepcurrent() {
        return stepcurrent;
    }

    public void setStepcurrent(int stepcurrent) {
        this.stepcurrent = stepcurrent;
    }

    public int getStepgoal() {
        return stepgoal;
    }

    public void setStepgoal(int stepgoal) {
        this.stepgoal = stepgoal;
    }

    public int getJumpcurrent() {
        return jumpcurrent;
    }

    public void setJumpcurrent(int jumpcurrent) {
        this.jumpcurrent = jumpcurrent;
    }

    public int getJumpgoal() {
        return jumpgoal;
    }

    public void setJumpgoal(int jumpgoal) {
        this.jumpgoal = jumpgoal;
    }

    public int getSwimmingcurrent() {
        return swimmingcurrent;
    }

    public void setSwimmingcurrent(int swimmingcurrent) {
        this.swimmingcurrent = swimmingcurrent;
    }

    public int getSwimminggoal() {
        return swimminggoal;
    }

    public void setSwimminggoal(int swimminggoal) {
        this.swimminggoal = swimminggoal;
    }
}
