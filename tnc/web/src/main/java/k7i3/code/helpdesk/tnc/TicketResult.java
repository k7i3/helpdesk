package k7i3.code.helpdesk.tnc;

/**
 * Created by k7i3 on 16.03.15.
 */
public enum TicketResult {
    REMOTELY("удаленно"),
    IN_PLACE_FIRMWARE("на месте (программно)"),
    IN_PLACE_HARDWARE("на месте (железо)"),
    OTHER("прочее");

    private final String description;

    TicketResult(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
