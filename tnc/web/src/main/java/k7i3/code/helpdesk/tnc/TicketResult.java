package k7i3.code.helpdesk.tnc;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by k7i3 on 16.03.15.
 */
//public enum TicketResult {
//    REMOTELY("удаленно"),
//    IN_PLACE_FIRMWARE("на месте (программно)"),
//    IN_PLACE_HARDWARE("на месте (железо)"),
//    OTHER("прочее");
//
//    private final String description;
//
//    TicketResult(String description) {
//        this.description = description;
//    }
//
//    @Override
//    public String toString() {
//        return description;
//    }
//}
@Entity
@NamedQueries({
        @NamedQuery(name = "findAllTicketResults", query = "SELECT t FROM TicketResult t"),
        @NamedQuery(name = "findAllActiveTicketResults", query = "SELECT t FROM TicketResult t WHERE t.isActive = true")
})
public class TicketResult {
    @Id
    private String description;
    private Boolean isActive;

    public TicketResult() {
    }

    public TicketResult(String description) {
        this.description = description.toLowerCase();
    }

    public TicketResult(String description, Boolean isActive) {
        this.description = description.toLowerCase();
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketResult)) return false;

        TicketResult that = (TicketResult) o;

        if (!description.equals(that.description)) return false;
        if (!isActive.equals(that.isActive)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + isActive.hashCode();
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}