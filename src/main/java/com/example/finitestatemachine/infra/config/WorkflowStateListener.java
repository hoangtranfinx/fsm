package com.example.finitestatemachine.infra.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Slf4j
public final class WorkflowStateListener extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateChanged(final State<String, String> from, final State<String, String> to) {
        log.debug("stateChanged from:{}, to:{}", from, to);
    }

    @Override
    public void stateEntered(final State<String, String> state) {
        log.debug("stateEntered state:{}", state);
    }

    @Override
    public void stateExited(final State<String, String> state) {
        log.debug("stateExited state:{}", state);
    }

    @Override
    public void transition(final Transition<String, String> transition) {
        log.debug("transition transition:{}", transition);
    }

    @Override
    public void transitionStarted(final Transition<String, String> transition) {
        log.debug("transitionStarted transition:{}", transition);
    }

    @Override
    public void transitionEnded(final Transition<String, String> transition) {
        log.debug("transitionEnded transition:{}", transition);
    }

    @Override
    public void stateMachineStarted(final StateMachine<String, String> stateMachine) {
        log.debug("stateMachineStarted stateMachine:{}", stateMachine);
    }

    @Override
    public void stateMachineStopped(final StateMachine<String, String> stateMachine) {
        log.debug("stateMachineStopped stateMachine:{}", stateMachine);
    }

    @Override
    public void extendedStateChanged(final Object key, final Object value) {
        log.debug("extendedStateChanged key:{}, value={}", key, value);
    }

    @Override
    public void stateMachineError(
            final StateMachine<String, String> stateMachine, final Exception exception) {
        log.debug("stateMachineError stateMachine:{}, exception:{}", stateMachine, exception);
    }

    @Override
    public void stateContext(final StateContext<String, String> stateContext) {
        log.debug("stateContext stateContext:{}", stateContext);
    }
}
