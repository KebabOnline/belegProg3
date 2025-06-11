package events;

import java.util.EventObject;

public class RemoveKuchenEvent extends EventObject {
    private final int fachnummer;

    public RemoveKuchenEvent(Object source, int fachnummer) {
        super(source);
        this.fachnummer = fachnummer;
    }

    public int getFachnummer() {
        return fachnummer;
    }
}
