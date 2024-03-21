//package com.example.finitestatemachine;
//
//import com.example.finitestatemachine.infra.StateMachineConfig;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.statemachine.StateMachine;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import static com.example.finitestatemachine.infra.StateMachineConfig.Events.COIN;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class Test {
//    private final StateMachine<StateMachineConfig.States, StateMachineConfig.Events> stateMachine;
//
//    @Bean
//    public void events() {
//        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(COIN).build()))
//                .doOnNext(data->
//                        log.info(data.getMessage().getPayload().name()))
//                .subscribe();
//
//        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(StateMachineConfig.Events.PUSH).build()))
//                .doOnNext(data->
//                        log.info(data.getMessage().getPayload().name()))
//                .subscribe();
//    }
//}
