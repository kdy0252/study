package designpattern.statepattern.impl;

import lombok.Setter;

@Setter
public class TrafficLight {
    private TrafficLightState trafficLightState;

    public void print() {
        trafficLightState.print();
    }

    public void changeState() {
        trafficLightState.changeState(this);
    }
}
