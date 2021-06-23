package com.worklation.covid19.model;

public class Collector {
    int _id;
    String _heart_rate;
    String _resp_rate;
    String fever;
    String nausea;
    String headache;
    String diarrhea;
    String soar_throat;
    String muscle_ache;
    String loss_of_smell;
    String cough;
    String shortness_of_breath;
    String feeling_tired;
    public Collector(){   }

    public Collector(
                     String fever, String nausea,String headache,
                     String diarrhea, String soar_throat,
                     String muscle_ache, String loss_of_smell, String cough,
                     String shortness_of_breath, String feeling_tired){
        this.fever = fever;
        this.nausea = nausea;
        this.headache = headache;
        this.diarrhea = diarrhea;
        this.soar_throat = soar_throat;
        this.muscle_ache = muscle_ache;
        this.loss_of_smell = loss_of_smell;
        this.cough = cough;
        this.shortness_of_breath = shortness_of_breath;
        this.feeling_tired = feeling_tired;
    }
    public Collector(int id, String _heart_rate, String _resp_rate){
        this._id = id;
        this._heart_rate = _heart_rate;
        this._resp_rate = _resp_rate;
    }


    public Collector(String heart_rate,String test){
        this._heart_rate = heart_rate;
    }
    public Collector( String _resp_rate){
        this._resp_rate = _resp_rate;
    }


    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getHeartRate(){
        return this._heart_rate;
    }

    public void setHeartRate(String name){
        this._heart_rate = name;
    }

    public String getRespRate(){
        return this._resp_rate;
    }

    public void setRespRate(String phone_number){
        this._resp_rate = phone_number;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getNausea() {
        return nausea;
    }

    public void setNausea(String nausea) {
        this.nausea = nausea;
    }

    public String getHeadache() {
        return headache;
    }

    public void setHeadache(String headache) {
        this.headache = headache;
    }

    public String getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(String diarrhea) {
        this.diarrhea = diarrhea;
    }

    public String getSoarThroat() {
        return soar_throat;
    }

    public void setSoar_throat(String soar_throat) {
        this.soar_throat = soar_throat;
    }

    public String getMuscleAche() {
        return muscle_ache;
    }

    public void setMuscle_ache(String muscle_ache) {
        this.muscle_ache = muscle_ache;
    }

    public String getLossOfSmell() {
        return loss_of_smell;
    }

    public void setLoss_of_smell(String loss_of_smell) {
        this.loss_of_smell = loss_of_smell;
    }

    public String getCough() {
        return cough;
    }

    public void setCough(String cough) {
        this.cough = cough;
    }

    public String getShortnessOfBreath() {
        return shortness_of_breath;
    }

    public void setShortness_of_breath(String shortness_of_breath) {
        this.shortness_of_breath = shortness_of_breath;
    }

    public String getFeelingTired() {
        return feeling_tired;
    }

    public void setFeeling_tired(String feeling_tired) {
        this.feeling_tired = feeling_tired;
    }
}