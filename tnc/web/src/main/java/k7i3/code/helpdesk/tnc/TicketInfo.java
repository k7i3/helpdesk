package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketHeader ticketHeader;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    @NotNull
    private String updatedBy;
    private String closedBy;
    private String deletedBy;

    //Current Info
    @NotNull
    @OneToOne (cascade = CascadeType.ALL)
    private TransportInfo transportInfo;
    @NotNull
    @OneToOne (cascade = CascadeType.ALL)
    private TerminalInfo terminalInfo;
    @NotNull
    @OneToOne (cascade = CascadeType.ALL)
    private  PointInfo pointInfo;

    //Details
    @NotNull
    private Boolean isTherePossibilityToGetDefaultPlace;
    @NotNull
    private Boolean isInspected;
    @NotNull
    private String place;
    @NotNull
    private String mobileNumberOfDriver;
    @NotNull
    private String mobileNumberOfAgent;

    public TicketInfo() {
    }

    public TicketInfo(TicketStatus ticketStatus, TicketHeader ticketHeader, String updatedBy, TransportInfo transportInfo, TerminalInfo terminalInfo, PointInfo pointInfo, Boolean isTherePossibilityToGetDefaultPlace, Boolean isInspected, String place, String mobileNumberOfDriver, String mobileNumberOfAgent) {
        this.ticketStatus = ticketStatus;
        this.ticketHeader = ticketHeader;
        this.updateDate = new Date();
        this.updatedBy = updatedBy;
        this.transportInfo = transportInfo;
        this.terminalInfo = terminalInfo;
        this.pointInfo = pointInfo;
        this.isTherePossibilityToGetDefaultPlace = isTherePossibilityToGetDefaultPlace;
        this.isInspected = isInspected;
        this.place = place;
        this.mobileNumberOfDriver = mobileNumberOfDriver;
        this.mobileNumberOfAgent = mobileNumberOfAgent;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
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

    public Boolean getIsTherePossibilityToGetDefaultPlace() {
        return isTherePossibilityToGetDefaultPlace;
    }

    public void setIsTherePossibilityToGetDefaultPlace(Boolean isTherePossibilityToGetDefaultPlace) {
        this.isTherePossibilityToGetDefaultPlace = isTherePossibilityToGetDefaultPlace;
    }

    public Boolean getIsInspected() {
        return isInspected;
    }

    public void setIsInspected(Boolean isInspected) {
        this.isInspected = isInspected;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMobileNumberOfDriver() {
        return mobileNumberOfDriver;
    }

    public void setMobileNumberOfDriver(String mobileNumberOfDriver) {
        this.mobileNumberOfDriver = mobileNumberOfDriver;
    }

    public String getMobileNumberOfAgent() {
        return mobileNumberOfAgent;
    }

    public void setMobileNumberOfAgent(String mobileNumberOfAgent) {
        this.mobileNumberOfAgent = mobileNumberOfAgent;
    }
}
