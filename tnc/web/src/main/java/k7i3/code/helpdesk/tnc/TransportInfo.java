package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 12.02.15.
 */
@Entity
public class TransportInfo {
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
    private String project;
    private String branch;
    @NotNull
    private String stateNumber;
    private String garageNumber;
    private String model;
    private String route;
    @Embedded
    private TransportEquipment transportEquipment = new TransportEquipment();

    public TransportInfo() {
    }

    public TransportInfo(LifeCycleInfo modification, String project, String stateNumber) {
        this.modification = modification;
        this.project = project.trim().toUpperCase();
        this.stateNumber = stateNumber.trim().toUpperCase();
    }

    public TransportInfo(TransportInfo transportInfo) {
        this.modification = transportInfo.getModification();
        this.project = transportInfo.getProject();
        this.branch = transportInfo.getBranch();
        this.stateNumber = transportInfo.getStateNumber();
        this.garageNumber = transportInfo.getGarageNumber();
        this.model = transportInfo.getModel();
        this.route = transportInfo.getRoute();
        this.transportEquipment = transportInfo.getTransportEquipment();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportInfo)) return false;

        TransportInfo that = (TransportInfo) o;

        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        if (garageNumber != null ? !garageNumber.equals(that.garageNumber) : that.garageNumber != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (modification != null ? !modification.equals(that.modification) : that.modification != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (route != null ? !route.equals(that.route) : that.route != null) return false;
        if (stateNumber != null ? !stateNumber.equals(that.stateNumber) : that.stateNumber != null) return false;
        if (transportEquipment != null ? !transportEquipment.equals(that.transportEquipment) : that.transportEquipment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modification != null ? modification.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (stateNumber != null ? stateNumber.hashCode() : 0);
        result = 31 * result + (garageNumber != null ? garageNumber.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (route != null ? route.hashCode() : 0);
        result = 31 * result + (transportEquipment != null ? transportEquipment.hashCode() : 0);
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project.trim().toUpperCase();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch.trim().toUpperCase();
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber.trim().toUpperCase();
    }

    public String getGarageNumber() {
        return garageNumber;
    }

    public void setGarageNumber(String garageNumber) {
        this.garageNumber = garageNumber.trim().toUpperCase();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model.trim().toUpperCase();
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route.trim().toLowerCase();
    }

    public TransportEquipment getTransportEquipment() {
        return transportEquipment;
    }

    public void setTransportEquipment(TransportEquipment transportEquipment) {
        this.transportEquipment = transportEquipment;
    }
}
