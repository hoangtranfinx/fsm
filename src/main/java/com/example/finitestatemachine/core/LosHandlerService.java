package com.example.finitestatemachine.core;

import com.example.finitestatemachine.core.domain.Origination;
import com.example.finitestatemachine.infra.persistant.OriginationPersistant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LosHandlerService {
    private final OriginationPersistant persistant;

    public void createLos(String name) {
        if(name.equals("hoang")){
            throw new RuntimeException("hehe");
        }
        persistant.addOrigination(new Origination(name, "leadId"));
    }

    public void esignHDB(String id) {
        log.info("esignHDB {}", persistant.getOrigination(Integer.parseInt(id)));
    }

}
