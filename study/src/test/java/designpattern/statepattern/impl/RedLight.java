package designpattern.statepattern.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedLight implements TrafficLightState {
    @Override
    public void changeState(TrafficLight trafficLight) {
        trafficLight.setTrafficLightState(new GreenLight());
    }

    @Override
    public void print() {
        log.info("Red light");
    }
}
