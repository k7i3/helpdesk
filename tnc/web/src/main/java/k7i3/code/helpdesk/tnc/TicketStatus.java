package k7i3.code.helpdesk.tnc;

/**
 * Created by k7i3 on 28.01.15.
 */
public enum TicketStatus {
    OPENED("открыта"),
    ACCEPTED("принята"),
    SERVICING("выезд"),
    CLOSED("закрыта"),
    ARCHIVED("в архиве"),
    INCORRECT("невалидная"),
    CANCELED("отменена");

    private final String description;

    TicketStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
