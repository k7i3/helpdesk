package k7i3.code.helpdesk.tnc;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@RequestScoped
public class TransportController {
    @Inject
    private TransportEJB transportEJB;
    private List<Transport> transport;
    private List<Transport> filteredTransport;
    private List<String> projects;
    private List<String> branches;

    @PostConstruct
    public void doFindAllTransport() {
        transport = transportEJB.findAllTransport();
        doFindAllProjects();
        doFindAllBranches();
    }

    public void doFindAllProjects() {
        projects = transportEJB.findAllProjects();
    }

    public void doFindAllBranches() {
        branches = transportEJB.findAllBranches();
    }

    public List<Transport> getTransport() {
        return transport;
    }

    public void setTransport(List<Transport> transport) {
        this.transport = transport;
    }

    public List<Transport> getFilteredTransport() {
        return filteredTransport;
    }

    public void setFilteredTransport(List<Transport> filteredTransport) {
        this.filteredTransport = filteredTransport;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }
}
