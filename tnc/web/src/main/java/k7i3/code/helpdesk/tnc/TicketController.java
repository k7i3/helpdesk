package k7i3.code.helpdesk.tnc;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 24.02.15.
 */
@Named
@RequestScoped
public class TicketController {
    @Inject
    private TransportEJB transportEJB;
    @Inject
    private TicketEJB ticketEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private Transport unitOfTransport;
    private Ticket ticket = new Ticket();
    private List<TicketHeader> ticketHeaders = Arrays.asList(TicketHeader.values());
    private String didBy;
    private String commentContent;

    //Do ADD

    public void doAddTransportComment(Transport transport, String didBy) {
        Comment comment = new Comment(new LifeCycleInfo(new Date(), didBy), new CommentInfo(new LifeCycleInfo(new Date(), didBy), commentContent));
        transport.getComments().add(comment);
        transportEJB.updateTransport(transport);
        commentContent = null;

        FacesMessage msg = new FacesMessage("Сохранено (комментарий к транспорту)", transport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAddTicketComment(Ticket ticket, String didBy) {
        Comment comment = new Comment(new LifeCycleInfo(new Date(), didBy), new CommentInfo(new LifeCycleInfo(new Date(), didBy), commentContent));
        ticket.getComments().add(comment);
        ticketEJB.updateTicket(ticket);
        commentContent = null;

        FacesMessage msg = new FacesMessage("Сохранено (комментарий к заявке)", ticket.getTicketInfo().getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAddTicket() {
        logger.info("=>=>=>=>=> TicketController.doAddTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToAddTicket(unitOfTransport)) { // update unitOfTransport from the database is going on here
            FacesMessage msg = new FacesMessage("Уже есть активная заявка", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        prepareNewTicketInfo(ticket.getTicketInfo(), lifeCycleInfo, TicketStatus.OPENED);

        ticket.setCreation(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, commentContent))); // difference
        unitOfTransport.getTickets().add(ticket); // difference

        transportEJB.updateTransport(unitOfTransport);

        FacesMessage msg = new FacesMessage("Сохранено (заявка)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
//        doReset(); for PF('addTicketDialog').show() via JS (@SessionScoped) and for Primefaces dialog framework (@SessionScoped)
//        RequestContext.getCurrentInstance().closeDialog(null); for Primefaces dialog framework (@SessionScoped)
    }

    //Do UPDATE

    public void doUpdateTicketInfo() {
        logger.info("=>=>=>=>=> TicketController.doUpdateTicketInfo()");
        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());

        Ticket ticketForUpdates = ticketEJB.findTicketById(ticket.getId()); // difference

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, ticketForUpdates.getTicketInfo().getTicketStatus());
        setNewTicketInfo(newTicketInfo, ticketForUpdates);

        ticketEJB.updateTicket(ticketForUpdates);

        FacesMessage msg = new FacesMessage("Обновлено (параметры заявки)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do CHANGE STATUS

    public void doAcceptTicket() {
        logger.info("=>=>=>=>=> TicketController.doAcceptTicket()");
        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        ticket = ticketEJB.findTicketById(ticket.getId());

        if (ticket.getAcceptance() != null) {
            FacesMessage msg = new FacesMessage("Заявка уже принята", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        if (ticket.getClosing() != null || ticket.getDeletion() != null) { // difference
            FacesMessage msg = new FacesMessage("Заявка уже неактуальна (закрыта или удалена)", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.ACCEPTED);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setAcceptance(lifeCycleInfo); // difference

        ticketEJB.updateTicket(ticket);

        FacesMessage msg = new FacesMessage("Заявка принята", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doCloseTicket() {
        logger.info("=>=>=>=>=> TicketController.doCloseTicket()");
        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        ticket = ticketEJB.findTicketById(ticket.getId());

        if (ticket.getClosing() != null || ticket.getDeletion() != null) {
            FacesMessage msg = new FacesMessage("Заявка уже неактуальна (закрыта или удалена)", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.CLOSED);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setClosing(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, commentContent))); // difference

        ticketEJB.updateTicket(ticket);

        FacesMessage msg = new FacesMessage("Заявка закрыта", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doDeleteTicket() {
        logger.info("=>=>=>=>=> TicketController.doDeleteTicket()");
        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        ticket = ticketEJB.findTicketById(ticket.getId());

        if (ticket.getDeletion() != null) {
            FacesMessage msg = new FacesMessage("Заявка уже удалена", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.DELETED);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setDeletion(lifeCycleInfo); // difference

        ticketEJB.updateTicket(ticket);

        FacesMessage msg = new FacesMessage("Заявка удалена", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do CHECK

    public Boolean doCheckForPossibilityToAddTicket(Transport transport) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToAddTicket()");
        unitOfTransport = transportEJB.findTransportById(transport.getId());
        List<Ticket> tickets = unitOfTransport.getTickets();
        return tickets.isEmpty() || !(tickets.get(tickets.size() - 1).getClosing() == null && tickets.get(tickets.size() - 1).getDeletion() == null);
    }

    //HELPER METHODS

    private void prepareNewTicketInfo(TicketInfo newTicketInfo, LifeCycleInfo lifeCycleInfo, TicketStatus ticketStatus) {
        logger.info("=>=>=>=>=> TicketController.prepareNewTicketInfo()");
        newTicketInfo.setModification(lifeCycleInfo);
        newTicketInfo.setTransportInfo(new TransportInfo(unitOfTransport.getTransportInfo()));
        newTicketInfo.setTerminalInfo(new TerminalInfo(unitOfTransport.getTerminal().getTerminalInfo()));
        newTicketInfo.setPointInfo(new PointInfo(unitOfTransport.getPoint().getPointInfo()));
        newTicketInfo.setTicketStatus(ticketStatus);
    }

    private void setNewTicketInfo(TicketInfo newTicketInfo, Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.setNewTicketInfo()");
        TicketInfo oldTicketInfo = ticket.getTicketInfo(); // = new TicketInfo(ticket.getTicketInfo());
        ticket.getTicketInfoHistory().add(oldTicketInfo);
        ticket.setTicketInfo(newTicketInfo);
    }
//    public void doOpenTicketDialog(Transport transport, String didBy) {
//        this.transport = transport;
//        this.didBy = didBy;
//
//        Map<String,Object> options = new HashMap<>();
//        options.put("modal", true);
//        options.put("draggable", true);
//        options.put("resizable", true);
//
//        RequestContext.getCurrentInstance().openDialog("addTicket", options, null);
//
////        FacesMessage msg = new FacesMessage(didBy, transport.getId().toString());
////        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }

//    public void doReset() {
//        transport = null;
//        didBy = null;
//        ticket = new Ticket();
//        commentContent = null;
//    }
















    public Transport getUnitOfTransport() {
        return unitOfTransport;
    }

    public void setUnitOfTransport(Transport unitOfTransport) {
        this.unitOfTransport = unitOfTransport;
    }

    public String getDidBy() {
        return didBy;
    }

    public void setDidBy(String didBy) {
        this.didBy = didBy;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public List<TicketHeader> getTicketHeaders() {
        return ticketHeaders;
    }

    public void setTicketHeaders(List<TicketHeader> ticketHeaders) {
        this.ticketHeaders = ticketHeaders;
    }
}
