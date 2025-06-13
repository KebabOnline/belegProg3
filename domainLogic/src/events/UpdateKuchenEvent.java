package events;

import java.util.EventObject;

public class UpdateKuchenEvent extends EventObject {
    private final int fachnummer;

    public UpdateKuchenEvent(Object source, int fachnummer) {
        super(source);
        this.fachnummer = fachnummer;
    }

    public int getFachnummer() {
        return fachnummer;
    }
}
