package k7i3.code.helpdesk.tnc;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * Created by k7i3 on 29.01.15.
 */
//public enum TicketHeader {
//    NOT_ONLINE("не на связи"),
//    BAD_TRACK("некорректный трек"),
//    OTHER("прочее");
//
//    private final String description;
//
//    TicketHeader(String description) {
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
        @NamedQuery(name = "findAllTicketHeaders", query = "SELECT t FROM TicketHeader t"),
        @NamedQuery(name = "findAllActiveTicketHeaders", query = "SELECT t FROM TicketHeader t WHERE t.isActive = true")
})
public class TicketHeader implements Serializable{
    @Id
    private String description;
    private Boolean isActive;

    public TicketHeader() {
    }

    public TicketHeader(String description) {
        this.description = description.toLowerCase();
    }

    public TicketHeader(String description, Boolean isActive) {
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
        if (!(o instanceof TicketHeader)) return false;

        TicketHeader that = (TicketHeader) o;

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
