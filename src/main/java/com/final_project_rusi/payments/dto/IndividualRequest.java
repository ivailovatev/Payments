package com.final_project_rusi.payments.dto;

import javax.validation.constraints.Size;

import static com.final_project_rusi.payments.messages.ExceptionMessages.*;

public class IndividualRequest {
    @Size(min = 3, message = NAME_SHOULD_BE_MIN_3_SYMBOLS)
    @Size(max = 30, message = NAME_SHOULD_BE_MAX_30_SYMBOLS)
    private String name;
    private String address;

    public IndividualRequest() {
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
        return "IndividualView{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
