package com.example.Springbatchmultithreading.dto;

public class demoDTO {

    private String value;

    public demoDTO (String value){
        this.value = value;
    }

    public String getValue ( ){
        return value;
    }

    public void setValue (String value){
        this.value = value;
    }
}
