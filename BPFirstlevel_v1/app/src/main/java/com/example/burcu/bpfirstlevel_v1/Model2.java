package com.example.burcu.bpfirstlevel_v1;

public class Model2 {

    private int id;
    private String array;
    private String hedefKonum;


    public Model2(int id, String array, String hedefKonum){
        this.id = id;
        this.array = array;
        this.hedefKonum = hedefKonum;

    }

    public Model2(String array, String hedefKonum){
        this.array = array;
        this.hedefKonum = hedefKonum;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }

    public String getHedefKonum() {
        return hedefKonum;
    }

    public void setHedefKonum(String hedefKonum) {
        this.hedefKonum = hedefKonum;
    }
}
