package designpattern.observerpattern.impl;

import java.util.ArrayList;
import java.util.List;

public class Event {
    List<Observer> observerList = new ArrayList<>();

    public void register(Observer observer) {
        observerList.add(observer);
    }

    public void notifyToObserver() {
        observerList.forEach(Observer::update);
    }
}
