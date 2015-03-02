package k7i3.code.helpdesk.tnc;

import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@RequestScoped
public class TransportController {
    @Inject
    private TransportEJB transportEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private List<Transport> transport; // transport table
    private List<String> projects;
    private List<String> branches;
    private List<String> transportModels;
    private List<String> terminalModels;
    private List<String> firmware;
    private List<String> routes;

    private List<Transport> filteredTransport; // transport table
    private List<Transport> checkboxSelectedTransport; // transport table

    private Transport unitOfTransport;

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

    //Do FIND

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

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        //TODO save previous info
        transportEJB.updateTransport((Transport) event.getObject());
        FacesMessage msg = new FacesMessage("Информация сохранена", ((Transport) event.getObject()).getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Информация не сохранена", ((Transport) event.getObject()).getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    public void onCellEdit(CellEditEvent event) {
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }















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

    public Transport getUnitOfTransport() {
        return unitOfTransport;
    }

    public void setUnitOfTransport(Transport unitOfTransport) {
        this.unitOfTransport = unitOfTransport;
    }

    public List<Transport> getCheckboxSelectedTransport() {
        return checkboxSelectedTransport;
    }

    public void setCheckboxSelectedTransport(List<Transport> checkboxSelectedTransport) {
        this.checkboxSelectedTransport = checkboxSelectedTransport;
    }
}
