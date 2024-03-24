package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.exception.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
@Slf4j
public class RetryPersistStateMachineHandler extends PersistStateMachineHandler {
    @Override
    public Mono<Void> handleEventWithStateReactively(Message<String> event, String state) {
        return Mono.defer(() -> {
            StateMachine<String, String> stateMachine = getInitStateMachine();
            // TODO: REACTOR add docs and revisit this function concept
            return Mono.from(stateMachine.stopReactively())
                    .thenEmpty(
                            Flux.fromIterable(stateMachine.getStateMachineAccessor().withAllRegions())
                                    .flatMap(region -> region.resetStateMachineReactively(new DefaultStateMachineContext<String, String>(state, null, null, null)))
                    )
                    .then(stateMachine.startReactively())
                    .thenMany(stateMachine.sendEvent(Mono.just(event))
                            .doOnNext(
                                    stateMachineEventResult -> {
                                        if (!stateMachineEventResult
                                                .getResultType()
                                                .equals(StateMachineEventResult.ResultType.ACCEPTED)) {
                                            throw new RetryableException("Event was not accept by state machine", null);
                                        }
                                    })
                            .doOnError(
                                    e->
                                            log.error("Error when processing event", e)
                            ).retryWhen(
                                    Retry.backoff(2, Duration.ofSeconds(1))
                                            .filter(throwable -> throwable instanceof RetryableException))
                    )
                    .then();
        });
    }

    public RetryPersistStateMachineHandler(StateMachine<String, String> stateMachine) {
        super(stateMachine);
    }
}
