package com.example.finitestatemachine.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Origination {
    int id;
    String name;
    String leadId;

    private static int counter = 0;

    public Origination(String customer, String leadId) {
        this.id = counter++;
        this.name = customer;
        this.leadId = leadId;
    }
}
