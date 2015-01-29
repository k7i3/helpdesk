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
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    @Enumerated(EnumType.STRING)
    private TicketHeader ticketHeader;
    @NotNull
    private String openedBy;
    private String closedBy;
    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public Ticket() {
    }

    public Ticket(Date createDate, Date updateDate, Date closeDate, Date deleteDate, TicketStatus ticketStatus, TicketHeader ticketHeader, String openedBy, String closedBy, List<Comment> comments) {
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.closeDate = closeDate;
        this.deleteDate = deleteDate;
        this.ticketStatus = ticketStatus;
        this.ticketHeader = ticketHeader;
        this.openedBy = openedBy;
        this.closedBy = closedBy;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }

    public String getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
