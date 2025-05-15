package designpattern.compositepattern.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Leaf implements Component {
    @Override
    public void execute() {
        log.info("Leaf");
    }
}
