package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

import java.util.HashMap;
import java.util.Map;

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
    public void change(int originationId, String event, String context) {
        OriginationStateMachineEntity origination = dao.findById(originationId)
                .orElseThrow(() -> new RuntimeException("can not find origination by origination id"));

        String oldState = origination.getState();
        //update context

        Map<String, Object> headers = new HashMap<>();
        headers.put("originId", originationId);
        headers.put("context", context);

        MessageHeaders messageHeaders = new MessageHeaders(headers);

        handler.handleEventWithStateReactively(MessageBuilder
                        .createMessage(event, messageHeaders), oldState)
                .subscribe();
    }

}
