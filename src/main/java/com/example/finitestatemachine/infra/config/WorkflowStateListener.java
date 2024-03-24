package com.example.finitestatemachine.infra.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Slf4j
public final class WorkflowStateListener extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateChanged(final State<String, String> from, final State<String, String> to) {
        log.info("stateChanged from:{}, to:{}", from, to);
    }

    @Override
    public void stateEntered(final State<String, String> state) {
        log.info("stateEntered state:{}", state);
    }

    @Override
    public void stateExited(final State<String, String> state) {
        log.info("stateExited state:{}", state);
    }

    @Override
    public void transition(final Transition<String, String> transition) {
        log.info("transition transition:{}", transition);
    }

    @Override
    public void transitionStarted(final Transition<String, String> transition) {
        log.info("transitionStarted transition:{}", transition);
    }

    @Override
    public void transitionEnded(final Transition<String, String> transition) {
        log.info("transitionEnded transition:{}", transition);
    }

    @Override
    public void stateMachineStarted(final StateMachine<String, String> stateMachine) {
        log.info("stateMachineStarted stateMachine:{}", stateMachine);
    }

    @Override
    public void stateMachineStopped(final StateMachine<String, String> stateMachine) {
        log.info("stateMachineStopped stateMachine:{}", stateMachine);
    }

    @Override
    public void extendedStateChanged(final Object key, final Object value) {
        log.info("extendedStateChanged key:{}, value={}", key, value);
    }

    @Override
    public void stateMachineError(
            final StateMachine<String, String> stateMachine, final Exception exception) {
        log.info("stateMachineError stateMachine:{}, exception:{}", stateMachine, exception);
    }

    @Override
    public void stateContext(final StateContext<String, String> stateContext) {
        log.info("stateContext stateContext:{}", stateContext);
    }

    @Override
    public void eventNotAccepted(Message<String> event) {
        log.info("event not accepted:{}", event);
        super.eventNotAccepted(event);
    }
}
