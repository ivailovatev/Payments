package com.final_project_rusi.payments.models;

public class Individual {
    private int indivId;
    private String name;
    private String address;

    public Individual() {
    }

    public Individual(int indivId, String name, String address) {
        this.indivId = indivId;
        this.name = name;
        this.address = address;
    }

    public int getIndivId() {
        return indivId;
    }

    public void setIndivId(int indivId) {
        this.indivId = indivId;
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
        return "Individual{" +
                "indivId=" + indivId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
