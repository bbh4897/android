package com.example.burcu.d4;

public class ListItem {


    private String head;
    private String desc;



    public ListItem(String head, String desc){
        this.head = head;
        this.desc = desc;
    }

    public String getHead(){
        return head;
    }

    public String getDesc(){
        return desc;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
