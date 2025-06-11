package events;

import domainLogic.ObstkuchenImpl;

import java.util.EventObject;

public class AddKuchenEvent extends EventObject {
    private final ObstkuchenImpl kuchen;

    public AddKuchenEvent(Object source, ObstkuchenImpl kuchen) {
        super(source);
        this.kuchen = kuchen;
    }

    public ObstkuchenImpl getKuchen() {
        return kuchen;
    }
}
