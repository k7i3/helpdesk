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

    public TicketInfo(TicketInfo ticketInfo) {
        this.ticketStatus = ticketInfo.getTicketStatus();
        this.ticketHeader = ticketInfo.getTicketHeader();
        this.modification = ticketInfo.getModification();
        this.ticketDetails = new TicketDetails(ticketInfo.getTicketDetails());
        this.transportInfo = ticketInfo.getTransportInfo();
        this.terminalInfo = ticketInfo.getTerminalInfo();
        this.pointInfo = ticketInfo.getPointInfo();
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
