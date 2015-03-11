package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 12.02.15.
 */
@Entity
public class TerminalInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="modification_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="modification_DidBy"))
    })
    @Embedded
    private LifeCycleInfo modification;
    @NotNull
    private Integer number;
    private String firmware;
    private String mobileNumber;
    private String serialNumber;
    private String model;

    public TerminalInfo() {
    }

    public TerminalInfo(LifeCycleInfo modification, Integer number) {
        this.modification = modification;
        this.number = number;
    }

    public TerminalInfo(TerminalInfo terminalInfo) {
        this.modification = terminalInfo.getModification();
        this.number = terminalInfo.getNumber();
        this.firmware = terminalInfo.getFirmware();
        this.mobileNumber = terminalInfo.getMobileNumber();
        this.serialNumber = terminalInfo.getSerialNumber();
        this.model = terminalInfo.getModel();
    }

    public Long getId() {
        return id;
    }

    public LifeCycleInfo getModification() {
        return modification;
    }

    public void setModification(LifeCycleInfo modification) {
        this.modification = modification;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
