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
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="acceptance_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="acceptance_DidBy"))
    })
    @Embedded
    private LifeCycleInfo acceptance;
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="closingn_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="closing_DidBy"))
    })
    @Embedded
    private LifeCycleInfo closing;
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="deletion_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="deletion_DidBy"))
    })
    @Embedded
    private LifeCycleInfo deletion;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private TicketInfo ticketInfo = new TicketInfo();
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

    public LifeCycleInfo getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(LifeCycleInfo acceptance) {
        this.acceptance = acceptance;
    }

    public LifeCycleInfo getClosing() {
        return closing;
    }

    public void setClosing(LifeCycleInfo closing) {
        this.closing = closing;
    }

    public LifeCycleInfo getDeletion() {
        return deletion;
    }

    public void setDeletion(LifeCycleInfo deletion) {
        this.deletion = deletion;
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
