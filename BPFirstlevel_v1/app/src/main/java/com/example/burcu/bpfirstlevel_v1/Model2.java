package com.example.burcu.bpfirstlevel_v1;

public class Model2 {

    private int id;
    private String level;

    public Model2(int id, String level){
        this.id = id;
        this.level = level;
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
}
