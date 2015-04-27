package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Terminal {
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
    private TerminalInfo terminalInfo = new TerminalInfo();
    @OneToMany(cascade = CascadeType.ALL)
    private List<TerminalInfo> terminalInfoHistory = new ArrayList<>();

    public Terminal() {
    }

    public Terminal(LifeCycleInfo creation, TerminalInfo terminalInfo) {
        this.creation = creation;
        this.terminalInfo = terminalInfo;
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

    public TerminalInfo getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(TerminalInfo terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public List<TerminalInfo> getTerminalInfoHistory() {
        return terminalInfoHistory;
    }

    public void setTerminalInfoHistory(List<TerminalInfo> terminalInfoHistory) {
        this.terminalInfoHistory = terminalInfoHistory;
    }
}
