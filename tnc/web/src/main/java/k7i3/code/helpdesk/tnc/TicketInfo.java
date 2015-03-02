package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 12.02.15.
 */
@Entity
public class TicketInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
//    @NotNull //- it is attempt to fix problem with ajax rowEdit event (when event is executed, validation ticketHeader at TicketController execute to, and broke everything)
    @Enumerated(EnumType.STRING)
    private TicketHeader ticketHeader;
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="modification_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="modification_DidBy"))
    })
    @Embedded
    private LifeCycleInfo modification;
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
    @OneToOne (cascade = CascadeType.ALL)
    private TicketDetails ticketDetails = new TicketDetails();

    //Current Info for history
    @NotNull
    @OneToOne (cascade = CascadeType.ALL)
    private TransportInfo transportInfo;
    @NotNull
    @OneToOne (cascade = CascadeType.ALL)
    private TerminalInfo terminalInfo;
    @NotNull
    @OneToOne (cascade = CascadeType.ALL)
    private  PointInfo pointInfo;

    public TicketInfo() {
    }

    public TicketInfo(TicketStatus ticketStatus, TicketHeader ticketHeader, LifeCycleInfo modification, TicketDetails ticketDetails, TransportInfo transportInfo, TerminalInfo terminalInfo, PointInfo pointInfo) {
        this.ticketStatus = ticketStatus;
        this.ticketHeader = ticketHeader;
        this.modification = modification;
        this.ticketDetails = ticketDetails;
        this.transportInfo = transportInfo;
        this.terminalInfo = terminalInfo;
        this.pointInfo = pointInfo;
    }

    public Long getId() {
        return id;
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

    public LifeCycleInfo getModification() {
        return modification;
    }

    public void setModification(LifeCycleInfo modification) {
        this.modification = modification;
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

    public TicketDetails getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(TicketDetails ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    public TransportInfo getTransportInfo() {
        return transportInfo;
    }

    public void setTransportInfo(TransportInfo transportInfo) {
        this.transportInfo = transportInfo;
    }

    public TerminalInfo getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(TerminalInfo terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public PointInfo getPointInfo() {
        return pointInfo;
    }

    public void setPointInfo(PointInfo pointInfo) {
        this.pointInfo = pointInfo;
    }
}
