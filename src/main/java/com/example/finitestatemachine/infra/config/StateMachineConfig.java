package com.example.finitestatemachine.infra.config;

import com.example.finitestatemachine.infra.action.FSMAction;
import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;


@Configuration
@EnableStateMachine
@Slf4j
@RequiredArgsConstructor
public class StateMachineConfig
        extends StateMachineConfigurerAdapter<String, String> {

    public enum States {
        INIT, S1, S2, END
    }

    public enum Events {
        GenID, ESIGN_HDB, CBS
    }

    private final FSMAction action;

//    @Override
//    public void configure(StateMachineConfigurationConfigurer<String, String> config)
//            throws Exception {
//        config
//                .withConfiguration()
////                .listener(new WorkflowStateListener())
//                .autoStartup(true);
//    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> String)
            throws Exception {
        String
                .withStates()
                .initial("INIT")
                .state("S1", testConfig())
                .state("S1")
                .state("S2")
                .state("END")
                .end("END");
    }

    public Action<String, String> testConfig() {
        return context -> log.info("testConfig");
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("INIT")
                .target("S1")
                .event("GenID")
                .actionFunction(action::genId)
//                .action(action.genId())

                .and()
                .withExternal()
                .source("S1")
                .target("S2")
                .event("ESIGN_HDB")
//                .action(action.esignHDB())

                .and()
                .withExternal()
                .source("S2")
                .target("END")
                .event("CBS")
                ;

    }

    private Guard<String,String> checkDeploy(){
        return context -> {
            Boolean flag = (Boolean) context.getExtendedState()
                    .getVariables()
                    .get("deployed");
            return flag == null ? false : flag;
        };
    }

    private StateMachineListener<String, String> listener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void transition(Transition<String, String> transition) {
                log.warn("move from:{} to:{}",
                        ofNullableState(transition.getSource()),
                        ofNullableState(transition.getTarget()));
            }

            @Override
            public void eventNotAccepted(Message<String> event) {
                log.error("event not accepted: {}", event);
            }

            private Object ofNullableState(State<String,String> s) {
                return Optional.ofNullable(s)
                        .map(State::getId)
                        .orElse(null);
            }
        };
    }

    @Configuration
    static class PersistHandlerConfig {

        @Autowired
        private StateMachine<String, String> stateMachine;

        @Autowired
        private LocalPersistStateChangeListener listener;

        @Autowired
        private OriginationStateMachineDao dao;

        @Bean
        public Persist persist() {
            return new Persist(persistStateMachineHandler(), listener, dao);
        }

        @Bean
        public PersistStateMachineHandler persistStateMachineHandler() {
            return new PersistStateMachineHandler(stateMachine);
        }

    }
}

