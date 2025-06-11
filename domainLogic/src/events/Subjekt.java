package events;

import java.util.EventObject;

public interface Subjekt {
    void addListener(Beobachter beobachter);
    void removeListener(Beobachter listener);
    void benachrichtige(EventObject event);
}
