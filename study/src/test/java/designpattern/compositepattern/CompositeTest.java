package designpattern.compositepattern;

import designpattern.compositepattern.impl.Composite;
import designpattern.compositepattern.impl.Leaf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompositeTest {

    @Test
    @DisplayName("Composite Pattern 테스트")
    void compositeTest() {
        Composite composite_1 = new Composite();
        Composite composite_0 = new Composite();

        composite_1.add(new Leaf());
        composite_1.add(new Leaf());

        composite_0.add(new Leaf());
        composite_0.add(new Leaf());
        composite_0.add(composite_1);

        composite_0.execute();
    }
}
