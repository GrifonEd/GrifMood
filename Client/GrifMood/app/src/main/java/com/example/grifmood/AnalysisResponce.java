package com.example.grifmood;

public class AnalysisResponce {
    String znachimost;
    Float coef;

    public AnalysisResponce(String znachimost, Float coef) {
        this.znachimost = znachimost;
        this.coef = coef;
    }

    @Override
    public String toString() {
        return "AnalysisResponce{" +
                "znachimost='" + znachimost + '\'' +
                ", coef=" + coef +
                '}';
    }

    public String getZnachimost() {
        return znachimost;
    }

    public void setZnachimost(String znachimost) {
        this.znachimost = znachimost;
    }

    public Float getCoef() {
        return coef;
    }

    public void setCoef(Float coef) {
        this.coef = coef;
    }
}
