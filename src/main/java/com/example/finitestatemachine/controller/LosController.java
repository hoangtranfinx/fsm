package com.example.finitestatemachine.controller;

import com.example.finitestatemachine.controller.req.Request;
import com.example.finitestatemachine.core.FSMService;
import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/los")
@RequiredArgsConstructor
public class LosController {
    private final FSMService fsmService;

    private final OriginationStateMachineDao dao;

    @PostMapping("/upload")
    public Mono<String> uploadFromCsv(@RequestBody Request request) {
        fsmService.genID(request.getName(), request.getId());
        return Mono.just("Success");
    }

    @PostMapping("/upload-retry")
    public Mono<String> genIdRetry(@RequestBody Request request) {
        fsmService.genIDRetry(request.getName(), request.getId());
        return Mono.just("Success");
    }

    @PostMapping("/insert")
    public Mono<String> insert(@RequestBody Request request) {
        dao.save(new OriginationStateMachineEntity(request.getId(), request.getName()));
        return Mono.just("Success");
    }
}
