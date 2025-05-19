package events;

public class RemoveKuchenEvent implements Event {
    private final int fachnummer;

    public RemoveKuchenEvent(int fachnummer) {
        this.fachnummer = fachnummer;
    }

    public int getFachnummer() {
        return fachnummer;
    }
}
