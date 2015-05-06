package k7i3.code.helpdesk.tnc;

/**
 * Created by k7i3 on 28.01.15.
 */
public enum TicketStatus {
    OPENED("открыта"),
    ACCEPTED("принята"),
    ON_SERVICE("на выезд"),
    CLOSED("закрыта"),
    ARCHIVED("в архиве"),
    INCORRECT("невалидная"),
    CANCELED("отменена"),
    REPEATED_ON_SERVICE("повторно на выезд"),
    REPEATED_CLOSED("повторно закрыта");

    private final String description;

    TicketStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
