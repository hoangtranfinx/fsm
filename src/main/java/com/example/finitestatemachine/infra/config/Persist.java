package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class Persist {
    private final PersistStateMachineHandler handler;
    private final OriginationStateMachineDao dao;

    private final PersistStateMachineHandler.PersistStateChangeListener listener;

    public Persist(OriginationStateMachineDao dao,
                   StateMachine<String, String> stateMachine,
                   PersistStateMachineHandler.PersistStateChangeListener listener) {

        this.handler = new PersistStateMachineHandler(stateMachine);
        this.dao = dao;
        this.listener = listener;
        this.handler.addPersistStateChangeListener(listener);
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
