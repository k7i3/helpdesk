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
    @GeneratedValue
    private Long id;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private TerminalInfo terminalInfo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TerminalInfo> terminalInfoHistory = new ArrayList<>();

    public Terminal() {
    }

    public Terminal(TerminalInfo terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public Long getId() {
        return id;
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
