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
    private List<String> models;
    private List<String> firmware;
    private Transport selectedTransport;
    private List<Comment> filteredTransportComments;

    @PostConstruct
    public void doFindAllTransport() {
        transport = transportEJB.findAllTransport();
//        selectedTransport = transport.get(0);
        doFindAllProjects();
        doFindAllBranches();
        doFindAllModels();
        doFindAllFirmware();
    }

    public void doFindAllProjects() {
        projects = transportEJB.findAllProjects();
    }

    public void doFindAllBranches() {
        branches = transportEJB.findAllBranches();
    }

    public void doFindAllModels() {
        models = transportEJB.findAllModels();
    }

    public void doFindAllFirmware() {
        firmware = transportEJB.findAllFirmware();
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

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public List<String> getFirmware() {
        return firmware;
    }

    public void setFirmware(List<String> firmware) {
        this.firmware = firmware;
    }

    public Transport getSelectedTransport() {
        return selectedTransport;
    }

    public void setSelectedTransport(Transport selectedTransport) {
        this.selectedTransport = selectedTransport;
    }

    public List<Comment> getFilteredTransportComments() {
        return filteredTransportComments;
    }

    public void setFilteredTransportComments(List<Comment> filteredTransportComments) {
        this.filteredTransportComments = filteredTransportComments;
    }
}
