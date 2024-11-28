package com.example.stringapp;

public class DataClass {
    private String dataTittle;
    private String dataDesc;
    private String dataTimeDate;
    private String dataImage;

    public String getDataTittle() {
        return dataTittle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataTimeDate() {
        return dataTimeDate;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClass(String dataTittle, String dataDesc, String dataTimeDate, String dataImage) {
        this.dataTittle = dataTittle;
        this.dataDesc = dataDesc;
        this.dataTimeDate = dataTimeDate;
        this.dataImage = dataImage;
    }

    public DataClass(){

    }
}
