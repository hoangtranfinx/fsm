package com.example.finitestatemachine.infra.action;

import com.example.finitestatemachine.core.LosHandlerService;
import com.example.finitestatemachine.infra.StateMachineConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FSMAction {
    private final LosHandlerService service;

    public Action<StateMachineConfig.States, StateMachineConfig.Events> genId() {
        return context -> {
            log.warn("do something {}", context.getEvent());
            service.createLos(context.getMessage().getPayload().toString());
//            context.getExtendedState()
//                    .getVariables()
//                    .put("deployed", true);
        };
    }

    public Action<StateMachineConfig.States, StateMachineConfig.Events> esignHDB() {
        return context -> {
            log.warn("do something {}", context.getEvent());
            service.esignHDB(context.getMessage().getPayload().toString());
//            context.getExtendedState()
//                    .getVariables()
//                    .put("deployed", true);
        };
    }
}
