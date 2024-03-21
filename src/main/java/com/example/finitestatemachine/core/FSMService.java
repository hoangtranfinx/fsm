package com.example.finitestatemachine.core;

import com.example.finitestatemachine.infra.StateMachineConfig;
import com.example.finitestatemachine.infra.exception.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.example.finitestatemachine.infra.StateMachineConfig.Events.GenID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FSMService {
    private final StateMachineFactory<StateMachineConfig.States, StateMachineConfig.Events> stateMachineFactory;

//    void method() {
//        StateMachine<StateMachineConfig.States, StateMachineConfig.Events> stateMachine = stateMachineFactory.getStateMachine();
//        stateMachine.startReactively().subscribe();
//    }

    public Mono<Void> genID(String data) {
        // TODO, put data to context ?
        Map<String, Object> headers = new HashMap<>();
        headers.put("name", data);

        MessageHeaders messageHeaders = new MessageHeaders(headers);

//        stateMachineFactory.getStateMachine()
//                .startReactively().subscribe();
//        stateMachine.startReactively().subscribe();

        return stateMachineFactory.getStateMachine()
                .sendEvent(Mono.just(MessageBuilder.createMessage(GenID, messageHeaders)))
                .doOnNext(
                        stateMachineEventResult -> {
                            if (!stateMachineEventResult
                                    .getResultType()
                                    .equals(StateMachineEventResult.ResultType.ACCEPTED)) {
                                throw new RuntimeException("Event was not accept by state machine");
                            }
                        })
                .doOnComplete(
                        () -> {
                            log.info("Send event {} successfully", GenID);
                        })
                .doOnError(error -> log.error("Error when processing genID", error))
                .retryWhen(
                        Retry.backoff(2, Duration.ofSeconds(1))
                                .filter(throwable -> throwable instanceof RetryableException))
                .then();
//                .subscribe();
    }

    public synchronized StateMachine<StateMachineConfig.States, StateMachineConfig.Events> getStateMachine() {
        return stateMachineFactory.getStateMachine();
    }

    public void continueOldFlow(String stateId) {
        // TODO, get old data from database, put into context or something
        Map<String, Object> headers = new HashMap<>();
        headers.put("customer", "customer");

        MessageHeaders messageHeaders = new MessageHeaders(headers);

        var stateMachine = getStateMachine();

        stateMachine
                .sendEvent(Mono.just(MessageBuilder.createMessage(GenID, messageHeaders)))
                .doOnNext(
                        stateMachineEventResult -> {
                            if (!stateMachineEventResult
                                    .getResultType()
                                    .equals(StateMachineEventResult.ResultType.ACCEPTED)) {
                                throw new RuntimeException("Event was not accept by state machine");
                            }
                        })
                .doOnComplete(
                        () -> {
                            log.debug("Send event {} successfully", GenID);
                        })
                .doOnError(error -> log.error("Error when processing genID", error))
                .retryWhen(
                        Retry.backoff(2, Duration.ofSeconds(1))
                                .filter(throwable -> throwable instanceof RetryableException))
                .subscribe();
    }
}
