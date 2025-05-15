package designpattern.observerpattern.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverOne implements Observer {
    @Override
    public void update() {
        log.info("update complete_1");
    }
}
