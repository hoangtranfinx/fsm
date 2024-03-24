package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.exception.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class CustomPersistStateMachineHandler extends PersistStateMachineHandler {
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
                    .thenMany(stateMachine.sendEvent(Mono.just(event)))
                    .then();
        });
    }

    public CustomPersistStateMachineHandler(StateMachine<String, String> stateMachine) {
        super(stateMachine);
    }
}
