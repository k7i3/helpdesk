package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
@NamedQueries({
//        @NamedQuery(name = "countTicketsByResults", query = "SELECT t.ticketInfo.ticketResults, COUNT (t) FROM Ticket t GROUP BY t.ticketInfo.ticketResults"),
        @NamedQuery(name = "countAllTickets", query = "SELECT COUNT(t) FROM Ticket t"),
        @NamedQuery(name = "countTicketsByStatus", query = "SELECT COUNT(t) FROM Ticket t WHERE t.ticketInfo.ticketStatus = :status"),
        @NamedQuery(name = "countTicketsByResult", query = "SELECT COUNT(t) FROM Ticket t WHERE :result MEMBER OF t.ticketInfo.ticketResults"),
        @NamedQuery(name = "countTicketsByStatuses", query = "SELECT t.ticketInfo.ticketStatus, COUNT(t) FROM Ticket t GROUP BY t.ticketInfo.ticketStatus ORDER BY COUNT(t) DESC"),
        @NamedQuery(name = "countTicketsByStatusesBetweenDates", query = "SELECT t.ticketInfo.ticketStatus, COUNT(t) FROM Ticket t WHERE t.creation.date BETWEEN :startDate AND :endDate GROUP BY t.ticketInfo.ticketStatus ORDER BY COUNT(t) DESC"),
        @NamedQuery(name = "countTicketsByHeaders", query = "SELECT t.ticketInfo.ticketHeader, COUNT(t) FROM Ticket t GROUP BY t.ticketInfo.ticketHeader ORDER BY COUNT(t) DESC"),
        @NamedQuery(name = "countTicketsByProjects", query = "SELECT t.ticketInfo.transportInfo.project, COUNT(t) FROM Ticket t GROUP BY t.ticketInfo.transportInfo.project ORDER BY COUNT(t) DESC"),
        @NamedQuery(name = "countTicketsByBranches", query = "SELECT t.ticketInfo.transportInfo.branch, COUNT(t) FROM Ticket t GROUP BY t.ticketInfo.transportInfo.branch ORDER BY COUNT(t) DESC"),


//        @NamedQuery(name = "findAllBranches", query = "SELECT DISTINCT b.transportInfo.branch FROM Transport b ORDER BY b.transportInfo.branch DESC"),
//        @NamedQuery(name = "findAllTransportModels", query = "SELECT DISTINCT b.transportInfo.model FROM Transport b ORDER BY b.transportInfo.model DESC"),
//        @NamedQuery(name = "findAllTerminalModels", query = "SELECT DISTINCT b.terminal.terminalInfo.model FROM Transport b ORDER BY b.terminal.terminalInfo.model DESC"),
//        @NamedQuery(name = "findAllFirmware", query = "SELECT DISTINCT b.terminal.terminalInfo.firmware FROM Transport b ORDER BY b.terminal.terminalInfo.firmware DESC"),
//        @NamedQuery(name = "findAllRoutes", query = "SELECT DISTINCT b.transportInfo.route FROM Transport b ORDER BY b.transportInfo.route DESC"),
//        @NamedQuery(name = "findAllAccessibleTransportByProjectsAndBranches", query = "SELECT b FROM Transport b WHERE b.transportInfo.project IN :projects AND b.transportInfo.branch IN :branches ORDER BY b.creation.date DESC"),
//        @NamedQuery(name = "findAllAccessibleTransportByProjects", query = "SELECT b FROM Transport b WHERE b.transportInfo.project IN :projects ORDER BY b.creation.date DESC"),
//        @NamedQuery(name = "findAllAccessibleTransportByBranches", query = "SELECT b FROM Transport b WHERE b.transportInfo.branch IN :branches ORDER BY b.creation.date DESC")
})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            @AttributeOverride(name="date", column= @Column(name="service_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="service_DidBy"))
    })
    @Embedded
    private LifeCycleInfo service;

    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="closing_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="closing_DidBy"))
    })
    @Embedded
    private LifeCycleInfo closing;

    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="archiving_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="archiving_DidBy"))
    })
    @Embedded
    private LifeCycleInfo archiving;

    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="incorrectness_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="incorrectness_DidBy"))
    })
    @Embedded
    private LifeCycleInfo incorrectness;

    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="cancellation_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="cancellation_DidBy"))
    })
    @Embedded
    private LifeCycleInfo cancellation;

    @ElementCollection
    private List<LifeCycleInfo> repeatedService = new ArrayList<>();

    @ElementCollection
    private List<LifeCycleInfo> repeatedClosing = new ArrayList<>();

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

    public LifeCycleInfo getService() {
        return service;
    }

    public void setService(LifeCycleInfo service) {
        this.service = service;
    }

    public LifeCycleInfo getClosing() {
        return closing;
    }

    public void setClosing(LifeCycleInfo closing) {
        this.closing = closing;
    }

    public LifeCycleInfo getArchiving() {
        return archiving;
    }

    public void setArchiving(LifeCycleInfo archiving) {
        this.archiving = archiving;
    }

    public LifeCycleInfo getIncorrectness() {
        return incorrectness;
    }

    public void setIncorrectness(LifeCycleInfo incorrectness) {
        this.incorrectness = incorrectness;
    }

    public LifeCycleInfo getCancellation() {
        return cancellation;
    }

    public void setCancellation(LifeCycleInfo cancellation) {
        this.cancellation = cancellation;
    }

    public List<LifeCycleInfo> getRepeatedService() {
        return repeatedService;
    }

    public void setRepeatedService(List<LifeCycleInfo> repeatedService) {
        this.repeatedService = repeatedService;
    }

    public List<LifeCycleInfo> getRepeatedClosing() {
        return repeatedClosing;
    }

    public void setRepeatedClosing(List<LifeCycleInfo> repeatedClosing) {
        this.repeatedClosing = repeatedClosing;
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
