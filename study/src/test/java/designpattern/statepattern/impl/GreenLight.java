package designpattern.statepattern.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreenLight implements TrafficLightState {
    @Override
    public void changeState(TrafficLight trafficLight) {
        trafficLight.setTrafficLightState(new RedLight());
    }

    @Override
    public void print() {
        log.info("Green light");
    }
}
