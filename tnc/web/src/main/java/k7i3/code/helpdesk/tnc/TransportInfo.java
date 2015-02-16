package k7i3.code.helpdesk.tnc;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private TransportEquipment transportEquipment;

    public TransportInfo() {
    }

    public TransportInfo(LifeCycleInfo modification, String project, String stateNumber) {
        this.modification = modification;
        this.project = project;
        this.stateNumber = stateNumber;
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
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getGarageNumber() {
        return garageNumber;
    }

    public void setGarageNumber(String garageNumber) {
        this.garageNumber = garageNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public TransportEquipment getTransportEquipment() {
        return transportEquipment;
    }

    public void setTransportEquipment(TransportEquipment transportEquipment) {
        this.transportEquipment = transportEquipment;
    }
}
