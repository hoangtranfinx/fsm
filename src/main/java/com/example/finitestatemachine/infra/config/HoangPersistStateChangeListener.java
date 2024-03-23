package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HoangPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {
    private final OriginationStateMachineDao dao;
    @Override
    public void onPersist(State<String, String> state, Message<String> message,
                          Transition<String, String> transition, StateMachine<String, String> stateMachine) {
        if (message != null && message.getHeaders().containsKey("order")) {
            log.info("hoang here");
            Integer order = message.getHeaders().get("order", Integer.class);
//                OriginationStateMachineEntity entity = new OriginationStateMachineEntity(order, state.getId());
            OriginationStateMachineEntity entity = dao.getReferenceById(order);
            entity.setState(state.getId());
            dao.save(entity);
        }
    }
}
