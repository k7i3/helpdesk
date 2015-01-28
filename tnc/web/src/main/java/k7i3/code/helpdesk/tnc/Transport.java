package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 27.01.15.
 */
@Entity
public class Transport {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String project;
    @NotNull
    private String branch;
    @NotNull
    private String stateNumber;
    @NotNull
    private String garageNumber;
    @NotNull
    private String model;
    @NotNull
    @OneToOne
    private Terminal terminal;
    @ElementCollection
    private List<String> comments = new ArrayList<>();

    public Transport() {
    }

    public Transport(String project, String branch, String stateNumber, String garageNumber, String model, Terminal terminal, List<String> comments) {
        this.project = project;
        this.branch = branch;
        this.stateNumber = stateNumber;
        this.garageNumber = garageNumber;
        this.model = model;
        this.terminal = terminal;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
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
}
