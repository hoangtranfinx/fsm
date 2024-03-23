package com.example.finitestatemachine.core;

import com.example.finitestatemachine.infra.config.Persist;
import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class FSMService {
    private final Persist persist;

    private final OriginationStateMachineDao dao;

    public Mono<Void> genID(String data, int id) {
        OriginationStateMachineEntity ori =new OriginationStateMachineEntity(id, "INIT");
        dao.save(ori);

        persist.change(id,"GenID");
        return Mono.empty();
    }

    @Bean
    public int test(){
        persist.change(4,"GenID");
        return 1;
    }

    public void continueOldFlow(String stateId) {
//        // TODO, get old data from database, put into context or something
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("customer", "customer");
//
//        MessageHeaders messageHeaders = new MessageHeaders(headers);
//
//        stateMachine
//                .sendEvent(Mono.just(MessageBuilder.createMessage("GenID", messageHeaders)))
//                .doOnNext(
//                        stateMachineEventResult -> {
//                            if (!stateMachineEventResult
//                                    .getResultType()
//                                    .equals(StateMachineEventResult.ResultType.ACCEPTED)) {
//                                throw new RuntimeException("Event was not accept by state machine");
//                            }
//                        })
//                .doOnComplete(
//                        () -> {
//                            log.debug("Send event {} successfully", "GenID");
//                        })
//                .doOnError(error -> log.error("Error when processing genID", error))
//                .retryWhen(
//                        Retry.backoff(2, Duration.ofSeconds(1))
//                                .filter(throwable -> throwable instanceof RetryableException))
//                .subscribe();
    }
}
