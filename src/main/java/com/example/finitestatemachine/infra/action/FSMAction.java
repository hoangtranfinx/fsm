package com.example.finitestatemachine.infra.action;

import com.example.finitestatemachine.core.LosHandlerService;
import com.example.finitestatemachine.infra.config.StateMachineConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FSMAction {
    private final LosHandlerService service;

    public Action<String, String> genId() {
        return context -> {
            String customer = context.getMessageHeaders().get("name", String.class);
            log.warn("ACTION {}", context.getEvent());
            service.createLos(customer);
//            context.getExtendedState()
//                    .getVariables()
//                    .put("deployed", true);
        };
    }

    public Action<String, String> esignHDB() {
        return context -> {
            log.warn("ACTION {}", context.getEvent());
            service.esignHDB(context.getMessage().getPayload());
//            context.getExtendedState()
//                    .getVariables()
//                    .put("deployed", true);
        };
    }
}
