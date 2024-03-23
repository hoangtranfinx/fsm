package com.example.finitestatemachine.core;

import com.example.finitestatemachine.infra.config.Persist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class FSMService {

    @Autowired
    private Persist persist;

    public Mono<Void> genID(String data) {
        persist.change(4,"GenID");
        return Mono.empty();
    }

    @Bean
    public void test(){
        persist.change(4,"GenID");
    }

}
