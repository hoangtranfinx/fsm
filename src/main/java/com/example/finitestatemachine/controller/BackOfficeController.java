package com.example.finitestatemachine.controller;

import com.example.finitestatemachine.core.FSMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/los")
@RequiredArgsConstructor
public class BackOfficeController {
    private final FSMService fsmService;

    @PostMapping("/continue")
    public Mono<String> continueOldFlow(String stateId) {
        fsmService.continueOldFlow(stateId);
        return  Mono.just("Success");
    }
}
