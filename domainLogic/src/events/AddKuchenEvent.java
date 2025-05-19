package events;

import domainLogic.ObstkuchenImpl;

public class AddKuchenEvent implements Event {
    private final ObstkuchenImpl kuchen;

    public AddKuchenEvent(ObstkuchenImpl kuchen) {
        this.kuchen = kuchen;
    }

    public ObstkuchenImpl getKuchen() {
        return kuchen;
    }
}
