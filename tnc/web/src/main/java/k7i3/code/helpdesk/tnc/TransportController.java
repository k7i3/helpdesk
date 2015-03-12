package k7i3.code.helpdesk.tnc;

import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
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

    private Transport unitOfTransport = new Transport();
    private String didBy;

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

    //Do ADD

    public void doAddTransport() {
        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        //TODO to helper method
        unitOfTransport.setCreation(lifeCycleInfo);
        unitOfTransport.getTransportInfo().setModification(lifeCycleInfo);
        unitOfTransport.getTerminal().setCreation(lifeCycleInfo);
        unitOfTransport.getTerminal().getTerminalInfo().setModification(lifeCycleInfo);
        unitOfTransport.getPoint().getPointInfo().setModification(lifeCycleInfo);
        transportEJB.createTransport(unitOfTransport);

        FacesMessage msg = new FacesMessage("Сохранено (транспорт)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do UPDATE

    public void doUpdateTransportInfo() { // is used when updating transportEquipment only (as long as)
        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TransportInfo newTransportInfo = new TransportInfo(unitOfTransport.getTransportInfo());
        prepareNewTransportInfo(newTransportInfo, lifeCycleInfo);
        setNewTransportInfo(newTransportInfo, transportForUpdates);

        transportEJB.updateTransport(transportForUpdates);

        FacesMessage msg = new FacesMessage("Обновлено (инфорамция о транспорте)", transportForUpdates.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        unitOfTransport = (Transport) event.getObject();
        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        didBy = "Администратор"; // TODO fix it!

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TransportInfo newTransportInfo = new TransportInfo(unitOfTransport.getTransportInfo()); // TODO make if (equals)
        prepareNewTransportInfo(newTransportInfo, lifeCycleInfo);
        setNewTransportInfo(newTransportInfo, transportForUpdates);
        TerminalInfo newTerminalInfo = new TerminalInfo(unitOfTransport.getTerminal().getTerminalInfo()); // TODO make if (equals)
        prepareNewTerminalInfo(newTerminalInfo, lifeCycleInfo);
        setNewTerminalInfo(newTerminalInfo, transportForUpdates);

        transportEJB.updateTransport(transportForUpdates);

        FacesMessage msg = new FacesMessage("Обновлено (информация о транспорте и о терминале)", (transportForUpdates.getTransportInfo().getStateNumber()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Не обновлено (транспорт)", ((Transport) event.getObject()).getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //HELPER METHODS

    private void prepareNewTransportInfo(TransportInfo newTransportInfo, LifeCycleInfo lifeCycleInfo) {
        newTransportInfo.setModification(lifeCycleInfo);
    }

    private void setNewTransportInfo(TransportInfo newTransportInfo, Transport transport) {
        TransportInfo oldTransportInfo = transport.getTransportInfo(); // = new TicketInfo(transport.getTransportInfo());
        transport.getTransportInfoHistory().add(oldTransportInfo);
        transport.setTransportInfo(newTransportInfo);
    }

    private void prepareNewTerminalInfo(TerminalInfo newTerminalInfo, LifeCycleInfo lifeCycleInfo) {
        newTerminalInfo.setModification(lifeCycleInfo);
    }

    private void setNewTerminalInfo(TerminalInfo newTerminalInfo, Transport transport) {
        TerminalInfo oldTerminalInfo = transport.getTerminal().getTerminalInfo(); // = new TicketInfo(transport.getTerminal().getTerminalInfo());
        transport.getTerminal().getTerminalInfoHistory().add(oldTerminalInfo);
        transport.getTerminal().setTerminalInfo(newTerminalInfo);
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

    public String getDidBy() {
        return didBy;
    }

    public void setDidBy(String didBy) {
        this.didBy = didBy;
    }
}
