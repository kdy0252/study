package designpattern.statepattern.impl;

public interface TrafficLightState {

    void changeState(TrafficLight trafficLight);

    void print();
}
