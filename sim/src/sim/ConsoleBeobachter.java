package sim;

import events.AddKuchenEvent;
import events.Beobachter;
import events.Event;
import events.RemoveKuchenEvent;

public class ConsoleBeobachter implements Beobachter {

    @Override
    public void aktualisiere(Event event) {
        String timestamp = java.time.LocalTime.now().toString();

        if (event instanceof AddKuchenEvent) {
            AddKuchenEvent addEvent = (AddKuchenEvent) event;
            System.out.printf("[%s] AUTOMAT: Kuchen hinzugefügt (Fachnummer: %d, %s, %s)%n",
                    timestamp, addEvent.getKuchen().getFachnummer(), addEvent.getKuchen().getObstsorte(), addEvent.getKuchen().getHersteller().getName());
        } else if (event instanceof RemoveKuchenEvent) {
            RemoveKuchenEvent removeEvent = (RemoveKuchenEvent) event;
            System.out.printf("[%s] AUTOMAT: Kuchen entfernt (Fachnummer: %d)%n",
                    timestamp, removeEvent.getFachnummer());
        }
    }
}
