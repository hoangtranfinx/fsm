package com.example.finitestatemachine.infra.persistant;

import com.example.finitestatemachine.core.domain.Origination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class OriginationPersistant {
    private final List<Origination> originationList = new ArrayList<>();

    public void addOrigination(Origination origination) {
        originationList.add(origination);
        log.info("save database {}", origination.getId());
    }

    public Origination getOrigination(int id) {
        return originationList.get(id);
    }
}
