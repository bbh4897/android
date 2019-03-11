package com.example.burcu.bpfirstlevel_v1;

public class Model2 {

    private int id;
    private String level;
    private String frekans;

    public Model2(int id, String level, String frekans){
        this.id = id;
        this.level = level;
        this.frekans = frekans;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFrekans() {
        return frekans;
    }

    public void setFrekans(String frekans) {
        this.frekans = frekans;
    }
}
