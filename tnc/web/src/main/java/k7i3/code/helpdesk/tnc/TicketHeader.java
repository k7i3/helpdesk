package k7i3.code.helpdesk.tnc;

/**
 * Created by k7i3 on 29.01.15.
 */
public enum TicketHeader {
    NOT_ONLINE("не на связи"),
    BAD_TRACK("некорректный трек"),
    OTHER("прочее");

    private final String description;

    TicketHeader(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
