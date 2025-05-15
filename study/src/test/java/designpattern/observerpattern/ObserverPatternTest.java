package designpattern.observerpattern;

import designpattern.observerpattern.impl.Event;
import designpattern.observerpattern.impl.ReceiverOne;
import designpattern.observerpattern.impl.ReceiverTwo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ObserverPatternTest {

    @Test
    @DisplayName("Observer Pattern Test")
    void observerPatternTest() {
        // 이 함수가 invoker
        Event notifier = new Event();
        notifier.register(new ReceiverOne());
        notifier.register(new ReceiverTwo());

        notifier.notifyToObserver();
    }
}
