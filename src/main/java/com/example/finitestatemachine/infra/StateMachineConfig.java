package com.example.finitestatemachine.infra;

import com.example.finitestatemachine.infra.config.LocalPersistStateChangeListener;
import com.example.finitestatemachine.infra.config.Persist;
import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

@Configuration
@EnableStateMachine
@Slf4j
@RequiredArgsConstructor
public class StateMachineConfig
        extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("PLACED")
                .state("PROCESSING")
                .state("SENT")
                .state("DELIVERED");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("PLACED").target("PROCESSING")
                .event("PROCESS")
                .and()
                .withExternal()
                .source("PROCESSING").target("SENT")
                .event("SEND")
                .and()
                .withExternal()
                .source("SENT").target("DELIVERED")
                .event("DELIVER");
    }

    @Configuration
    static class PersistHandlerConfig {

        @Autowired
        private StateMachine<String, String> stateMachine;

        @Autowired
        private LocalPersistStateChangeListener listener;

        @Bean
        public Persist persist() {
            return new Persist(persistStateMachineHandler(), listener);
        }

        @Bean
        public PersistStateMachineHandler persistStateMachineHandler() {
            return new PersistStateMachineHandler(stateMachine);
        }

    }
}

