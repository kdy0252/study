package designpattern.compositepattern.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Composite implements Component {
    private final List<Component> componentList = new ArrayList<>();

    public void add(Component component) {
        componentList.add(component);
    }

    @Override
    public void execute() {
        log.info("Composite");
        componentList.forEach(Component::execute);
    }
}
