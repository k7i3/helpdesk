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
    private List<Comment> filteredTransportComments;

    private Transport dialogSelectedTransport;
    private Point dialogSelectedPoint;
    private List<Transport> checkboxSelectedTransport;

    private List<String> projects;
    private List<String> branches;
    private List<String> transportModels;
    private List<String> terminalModels;
    private List<String> firmware;
    private List<String> routes;

    @PostConstruct
    public void doFindAllTransport() {
        transport = transportEJB.findAllTransport();
        doFindAllProjects();
        doFindAllBranches();
        doFindAllTransportModels();
        doFindAllTerminalModels();
        doFindAllFirmware();
        doFindAllRoutes();
    }

    public void doFindAllProjects() {
        projects = transportEJB.findAllProjects();
    }

    public void doFindAllBranches() {
        branches = transportEJB.findAllBranches();
    }

    public void doFindAllTransportModels() {
        transportModels = transportEJB.findAllTransportModels();
    }

    public void doFindAllTerminalModels() {
        terminalModels = transportEJB.findAllTerminalModels();
    }

    public void doFindAllFirmware() {
        firmware = transportEJB.findAllFirmware();
    }

    public void doFindAllRoutes() {
        routes = transportEJB.findAllRoutes();
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

    public List<String> getTransportModels() {
        return transportModels;
    }

    public void setTransportModels(List<String> transportModels) {
        this.transportModels = transportModels;
    }

    public List<String> getTerminalModels() {
        return terminalModels;
    }

    public void setTerminalModels(List<String> terminalModels) {
        this.terminalModels = terminalModels;
    }

    public List<String> getFirmware() {
        return firmware;
    }

    public void setFirmware(List<String> firmware) {
        this.firmware = firmware;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    public Transport getDialogSelectedTransport() {
        return dialogSelectedTransport;
    }

    public void setDialogSelectedTransport(Transport dialogSelectedTransport) {
        this.dialogSelectedTransport = dialogSelectedTransport;
    }

    public List<Comment> getFilteredTransportComments() {
        return filteredTransportComments;
    }

    public void setFilteredTransportComments(List<Comment> filteredTransportComments) {
        this.filteredTransportComments = filteredTransportComments;
    }

    public List<Transport> getCheckboxSelectedTransport() {
        return checkboxSelectedTransport;
    }

    public void setCheckboxSelectedTransport(List<Transport> checkboxSelectedTransport) {
        this.checkboxSelectedTransport = checkboxSelectedTransport;
    }

    public Point getDialogSelectedPoint() {
        return dialogSelectedPoint;
    }

    public void setDialogSelectedPoint(Point dialogSelectedPoint) {
        this.dialogSelectedPoint = dialogSelectedPoint;
    }
}
