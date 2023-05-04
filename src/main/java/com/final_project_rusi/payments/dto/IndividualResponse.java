package com.final_project_rusi.payments.dto;

public class IndividualResponse {
    private String name;
    private String address;

    public IndividualResponse() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "IndividualResponse{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
