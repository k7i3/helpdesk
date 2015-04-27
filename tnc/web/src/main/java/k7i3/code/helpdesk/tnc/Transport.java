package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 27.01.15.
 */
//@NamedQuery(name = "findAllAccessibleTransport", query = "SELECT b FROM Transport b WHERE b.transportInfo.project IN :projects AND b.transportInfo.branch IN :branches ORDER BY b.creation.date DESC")
//@NamedQuery(name = "findAllAccessibleTransport", query = "SELECT b FROM Transport b WHERE b.transportInfo.project IN ('БАТ', 'БАД') AND b.transportInfo.branch IN ('Уфа', 'Стерлитамак', 'Нефтекамск') ORDER BY b.creation.date DESC")
@Entity
@NamedQueries({
        @NamedQuery(name = "findAllTransport", query = "SELECT b FROM Transport b ORDER BY b.creation.date DESC"),
        @NamedQuery(name = "findAllProjects", query = "SELECT DISTINCT b.transportInfo.project FROM Transport b ORDER BY b.transportInfo.project DESC"),
        @NamedQuery(name = "findAllBranches", query = "SELECT DISTINCT b.transportInfo.branch FROM Transport b ORDER BY b.transportInfo.branch DESC"),
        @NamedQuery(name = "findAllTransportModels", query = "SELECT DISTINCT b.transportInfo.model FROM Transport b ORDER BY b.transportInfo.model DESC"),
        @NamedQuery(name = "findAllTerminalModels", query = "SELECT DISTINCT b.terminal.terminalInfo.model FROM Transport b ORDER BY b.terminal.terminalInfo.model DESC"),
        @NamedQuery(name = "findAllFirmware", query = "SELECT DISTINCT b.terminal.terminalInfo.firmware FROM Transport b ORDER BY b.terminal.terminalInfo.firmware DESC"),
        @NamedQuery(name = "findAllRoutes", query = "SELECT DISTINCT b.transportInfo.route FROM Transport b ORDER BY b.transportInfo.route DESC"),
        @NamedQuery(name = "findAllAccessibleTransportByProjectsAndBranches", query = "SELECT b FROM Transport b WHERE b.transportInfo.project IN :projects AND b.transportInfo.branch IN :branches ORDER BY b.creation.date DESC"),
        @NamedQuery(name = "findAllAccessibleTransportByProjects", query = "SELECT b FROM Transport b WHERE b.transportInfo.project IN :projects ORDER BY b.creation.date DESC"),
        @NamedQuery(name = "findAllAccessibleTransportByBranches", query = "SELECT b FROM Transport b WHERE b.transportInfo.branch IN :branches ORDER BY b.creation.date DESC")
})
public class Transport implements Serializable{
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
            @AttributeOverride(name="date", column= @Column(name="deletion_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="deletion_DidBy"))
    })
    @Embedded
    private LifeCycleInfo deletion;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private TransportInfo transportInfo = new TransportInfo();
    @OneToMany (cascade = CascadeType.ALL)
    private List<TransportInfo> transportInfoHistory = new ArrayList<>();
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Terminal terminal = new Terminal();
    @OneToOne(cascade = CascadeType.ALL)
    private Point point = new Point();
    @OneToMany (cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    @OneToMany (cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private  String currentTicketHeader = "нет"; //duplication for filtering at transportTable
    private  String currentTicketStatus = "нет"; //duplication for filtering at transportTable

    public Transport() {
    }

    public Transport(TransportInfo transportInfo, LifeCycleInfo creation, Terminal terminal) {
        this.transportInfo = transportInfo;
        this.creation = creation;
        this.terminal = terminal;
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

    public LifeCycleInfo getDeletion() {
        return deletion;
    }

    public void setDeletion(LifeCycleInfo deletion) {
        this.deletion = deletion;
    }

    public TransportInfo getTransportInfo() {
        return transportInfo;
    }

    public void setTransportInfo(TransportInfo transportInfo) {
        this.transportInfo = transportInfo;
    }

    public List<TransportInfo> getTransportInfoHistory() {
        return transportInfoHistory;
    }

    public void setTransportInfoHistory(List<TransportInfo> transportInfoHistory) {
        this.transportInfoHistory = transportInfoHistory;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCurrentTicketHeader() {
        return currentTicketHeader;
    }

    public void setCurrentTicketHeader(String currentTicketHeader) {
        this.currentTicketHeader = currentTicketHeader;
    }

    public String getCurrentTicketStatus() {
        return currentTicketStatus;
    }

    public void setCurrentTicketStatus(String currentTicketStatus) {
        this.currentTicketStatus = currentTicketStatus;
    }
}
