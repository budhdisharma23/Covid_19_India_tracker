package com.example.covid_19indiatracker;

public class DistrictModel {

    private String district,cases,tCase,deaths,tDeath,recovered,tRecover,active;

    public DistrictModel() {
    }

    public DistrictModel(String district, String cases, String tCase, String deaths, String tDeath,
                      String recovered, String tRecover, String active) {
        this.district = district;
        this.cases = cases;
        this.tCase = tCase;
        this.deaths = deaths;
        this.tDeath = tDeath;
        this.recovered = recovered;
        this.tRecover = tRecover;
        this.active = active;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String gettCase() {
        return tCase;
    }

    public void settCase(String tCase) {
        this.tCase = tCase;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String gettDeath() {
        return tDeath;
    }

    public void settDeath(String tDeath) {
        this.tDeath = tDeath;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String gettRecover() {
        return tRecover;
    }

    public void settRecover(String tRecover) {
        this.tRecover = tRecover;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
