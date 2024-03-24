package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import reactor.core.publisher.Mono;

@Slf4j
public class Persist {
    private final PersistStateMachineHandler handler;
    private final OriginationStateMachineDao dao;

	public Persist(PersistStateMachineHandler handler, PersistStateMachineHandler.PersistStateChangeListener listener
                , OriginationStateMachineDao dao) {
		this.handler = handler;
		this.handler.addPersistStateChangeListener(listener);
        this.dao = dao;
	}

    @Transactional
    public void change(int order, String event) {
        OriginationStateMachineEntity origination = dao.findById(order)
                .orElseThrow(()->new RuntimeException("hehe"));

        handler.handleEventWithStateReactively(MessageBuilder
                        .withPayload(event).setHeader("order", order).build(), origination.getState())
                .subscribe();
    }

}
