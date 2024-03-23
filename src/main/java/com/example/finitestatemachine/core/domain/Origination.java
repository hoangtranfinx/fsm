package com.example.finitestatemachine.core.domain;

import com.example.finitestatemachine.infra.StateMachineConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Origination {
    int id;
    String name;
    String leadId;
    Map<Object,Object> context;

    private static int counter = 0;

    public Origination(String customer, String leadId) {
        this.id = counter++;
        this.name = customer;
        this.leadId = leadId;
    }
}
