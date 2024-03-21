package com.example.finitestatemachine.controller;

import com.example.finitestatemachine.controller.req.Request;
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
public class LosController {
    private final FSMService fsmService;

    @PostMapping("/upload")
    public Mono<String> uploadFromCsv(Request request) {
        fsmService.genID(request.getName());
        return  Mono.just("Success");
    }
}