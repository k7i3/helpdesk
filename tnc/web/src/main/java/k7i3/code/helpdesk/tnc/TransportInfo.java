package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by k7i3 on 12.02.15.
 */
@Entity
public class TransportInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @NotNull
    private String project;
    private String branch;
    @NotNull
    private String stateNumber;
    private String garageNumber;
    private String model;
    private String route;

    //Equipment
    private Boolean isThereSpeaker;
    private Boolean isTherePtt;
    private Boolean isThereDvr;
    private Boolean isThereInformer;

    public TransportInfo() {
    }

    public TransportInfo(String project, String stateNumber) {
        this.updateDate = new Date();
        this.project = project;
        this.stateNumber = stateNumber;
    }

    public Long getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public Boolean getIsThereSpeaker() {
        return isThereSpeaker;
    }

    public void setIsThereSpeaker(Boolean isThereSpeaker) {
        this.isThereSpeaker = isThereSpeaker;
    }

    public Boolean getIsTherePtt() {
        return isTherePtt;
    }

    public void setIsTherePtt(Boolean isTherePtt) {
        this.isTherePtt = isTherePtt;
    }

    public Boolean getIsThereDvr() {
        return isThereDvr;
    }

    public void setIsThereDvr(Boolean isThereDvr) {
        this.isThereDvr = isThereDvr;
    }

    public Boolean getIsThereInformer() {
        return isThereInformer;
    }

    public void setIsThereInformer(Boolean isThereInformer) {
        this.isThereInformer = isThereInformer;
    }
}
