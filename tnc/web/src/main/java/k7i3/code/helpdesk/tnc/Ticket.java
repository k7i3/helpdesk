package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @NotNull
    private String createdBy;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private TicketInfo ticketInfo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TicketInfo> ticketInfoHistory = new ArrayList<>();
    @OneToMany (cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Ticket() {
    }

    public Ticket(String createdBy, TicketInfo ticketInfo) {
        this.createDate = new Date();
        this.createdBy = createdBy;
        this.ticketInfo = ticketInfo;
    }

    public Long getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
