package com.example.finitestatemachine.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LosHandlerService {

    public void createLos(String name) {
        if(name.equals("hoang")){
            log.error("throw error with context: {}", name);
            throw new RuntimeException("hehe");
        }
        log.info("do something with context: {}", name);
    }

}
