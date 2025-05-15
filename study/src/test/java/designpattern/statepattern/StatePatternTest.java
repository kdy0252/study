package designpattern.statepattern;

import designpattern.statepattern.impl.RedLight;
import designpattern.statepattern.impl.TrafficLight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StatePatternTest {

    @Test
    @DisplayName("State Pattern test")
    void statePatternTest() {
        TrafficLight trafficLight = new TrafficLight();

        trafficLight.setTrafficLightState(new RedLight());

        trafficLight.print();
        trafficLight.changeState();
        trafficLight.print();
        trafficLight.changeState();
        trafficLight.print();
    }
}
