package designpattern.observerpattern.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverTwo implements Observer {
    @Override
    public void update() {
        log.info("update complete_2");
    }
}
