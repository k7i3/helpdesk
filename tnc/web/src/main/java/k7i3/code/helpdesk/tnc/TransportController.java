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

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@RequestScoped
public class TransportController {
    @Inject
    private TransportEJB transportEJB;

    private List<Transport> transport; // transport table
    private List<String> projects;
    private List<String> branches;
    private List<String> transportModels;
    private List<String> terminalModels;
    private List<String> firmware;
    private List<String> routes;

    private List<Transport> filteredTransport; // transport table
    private List<Transport> checkboxSelectedTransport; // transport table

    private List<Comment> filteredTransportComments; // in table at comments dialog (will be deleted)

    private Transport dialogSelectedTransport; // comments dialog + add comment (unitOfTransport )
    private Point dialogSelectedPoint; // point dialog (point)
    private String commentContent;

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

    public void doAddTransportComment(Transport transport, String commentDidBy) {
        Comment comment = new Comment(new LifeCycleInfo(new Date(), commentDidBy), new CommentInfo(new LifeCycleInfo(new Date(), commentDidBy), commentContent));
        transport.getComments().add(comment);
        transportEJB.updateTransport(transport);
        commentContent = null;

        FacesMessage msg = new FacesMessage("сохранено", transport.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    public void doAddTransportComment(ActionEvent event) {
//        FacesMessage msg = new FacesMessage("сохранено", event.getSource().getClass().getSimpleName()); // component from xhtml (commandButton)
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }

    //On

    public void onRowEdit(RowEditEvent event) {
        transportEJB.updateTransport((Transport) event.getObject());
        FacesMessage msg = new FacesMessage("сохранено", ((Transport) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("отменено", ((Transport) event.getObject()).getId().toString());
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








    //Get & Set

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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
