package events;

public interface Subjekt {
    void addListener(Beobachter beobachter);
    void removeListener(Beobachter listener);
    void benachrichtige(Event event);
}
