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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TerminalInfo)) return false;

        TerminalInfo that = (TerminalInfo) o;

        if (firmware != null ? !firmware.equals(that.firmware) : that.firmware != null) return false;
        if (mobileNumber != null ? !mobileNumber.equals(that.mobileNumber) : that.mobileNumber != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (modification != null ? !modification.equals(that.modification) : that.modification != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modification != null ? modification.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (firmware != null ? firmware.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
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
