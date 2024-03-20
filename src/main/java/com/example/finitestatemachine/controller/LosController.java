package com.example.finitestatemachine.controller;

import com.example.finitestatemachine.infra.StateMachineConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.example.finitestatemachine.infra.StateMachineConfig.Events.COIN;

@RestController
@Slf4j
@RequestMapping("/los")
@RequiredArgsConstructor
public class LosController {
    private final StateMachine<StateMachineConfig.States, StateMachineConfig.Events> stateMachine;
    public Mono<String> getData(){
        return stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(COIN).build()))
                .doOnNext(data->
                        log.info(data.getMessage().getPayload().name()))
                .map(data->data.getMessage().getPayload().name());
    }
}
