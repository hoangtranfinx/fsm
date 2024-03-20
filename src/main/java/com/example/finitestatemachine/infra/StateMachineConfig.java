package com.example.finitestatemachine.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;
import java.util.Optional;

@Configuration
@EnableStateMachine
@Slf4j
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<StateMachineConfig.States, StateMachineConfig.Events> {

    public enum States {
        INIT, S1, S2, S3, END
    }

    public enum Events {
        GenID, ESIGN_FPT, ESIGN_HDB, CBS
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .listener(new WorkflowStateListener())
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.INIT)
                .states(EnumSet.allOf(States.class))
                .end(States.END);
    }

    private Action<States, Events> doSomething() {
        return context -> {
            log.warn("do something {}", context.getEvent());
//            context.getExtendedState()
//                    .getVariables()
//                    .put("deployed", true);
        };
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.INIT)
                .target(States.S1)
                .event(Events.GenID)
                .action(doSomething())

                .and()
                .withExternal()
                .source(States.S1)
                .target(States.S2)
                .event(Events.ESIGN_FPT)

                .and()
                .withExternal()
                .source(States.S2)
                .target(States.S3)
                .event(Events.ESIGN_HDB)

                .and()
                .withExternal()
                .source(States.S3)
                .target(States.END)
                .event(Events.CBS)

    }

    private Guard<States,Events> checkDeploy(){
        return context -> {
            Boolean flag = (Boolean) context.getExtendedState()
                    .getVariables()
                    .get("deployed");
            return flag == null ? false : flag;
        };
    }

//    private StateMachineListener<States, Events> listener() {
//        return new StateMachineListenerAdapter<States, Events>() {
//            @Override
//            public void transition(Transition<States, Events> transition) {
//                log.warn("move from:{} to:{}",
//                        ofNullableState(transition.getSource()),
//                        ofNullableState(transition.getTarget()));
//            }
//
//            @Override
//            public void eventNotAccepted(Message<Events> event) {
//                log.error("event not accepted: {}", event);
//            }
//
//            private Object ofNullableState(State s) {
//                return Optional.ofNullable(s)
//                        .map(State::getId)
//                        .orElse(null);
//            }
//        };
//    }
}

