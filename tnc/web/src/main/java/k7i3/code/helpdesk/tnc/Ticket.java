package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="creation_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="creation_DidBy"))
    })
    @Embedded
    private LifeCycleInfo creation;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private TicketInfo ticketInfo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TicketInfo> ticketInfoHistory = new ArrayList<>();
    @OneToMany (cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Ticket() {
    }

    public Ticket(TicketInfo ticketInfo, LifeCycleInfo creation) {
        this.ticketInfo = ticketInfo;
        this.creation = creation;
    }

    public Long getId() {
        return id;
    }

    public LifeCycleInfo getCreation() {
        return creation;
    }

    public void setCreation(LifeCycleInfo creation) {
        this.creation = creation;
    }

    public TicketInfo getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(TicketInfo ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public List<TicketInfo> getTicketInfoHistory() {
        return ticketInfoHistory;
    }

    public void setTicketInfoHistory(List<TicketInfo> ticketInfoHistory) {
        this.ticketInfoHistory = ticketInfoHistory;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
