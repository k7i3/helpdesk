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
        if (!doCheckForPossibilityToAddTicket(unitOfTransport)) { // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
            FacesMessage msg = new FacesMessage("Уже есть открытая заявка", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        //TODO thinking about updating unitOfTransport by unitOfTransport = TransportEJB.findTransportById(unitOfTransport.getId)
        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        ticket.setCreation(lifeCycleInfo);
        ticket.getTicketInfo().setModification(lifeCycleInfo);
        ticket.getTicketInfo().setTicketStatus(TicketStatus.OPENED);
        ticket.getTicketInfo().setTransportInfo(unitOfTransport.getTransportInfo());
        ticket.getTicketInfo().setTerminalInfo(unitOfTransport.getTerminal().getTerminalInfo());
        ticket.getTicketInfo().setPointInfo(unitOfTransport.getPoint().getPointInfo());
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, commentContent)));
        unitOfTransport.getTickets().add(ticket);
        transportEJB.updateTransport(unitOfTransport);

        FacesMessage msg = new FacesMessage("Сохранено (заявка)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);

//        doReset(); for PF('addTicketDialog').show() via JS (@SessionScoped) and for Primefaces dialog framework (@SessionScoped)
//        RequestContext.getCurrentInstance().closeDialog(null); for Primefaces dialog framework (@SessionScoped)
    }

    public void doUpdateTicketInfo() {
        logger.info("=>=>=>=>=> TicketController.doUpdateTicketInfo()");
        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());
        Ticket ticketForUpdates = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        newTicketInfo.setModification(lifeCycleInfo);
        newTicketInfo.setTicketStatus(ticketForUpdates.getTicketInfo().getTicketStatus());
        newTicketInfo.setTransportInfo(transportForUpdates.getTransportInfo());
        newTicketInfo.setTerminalInfo(transportForUpdates.getTerminal().getTerminalInfo());
        newTicketInfo.setPointInfo(transportForUpdates.getPoint().getPointInfo());

//        TODO bug was here (it was fixed, but maybe needed to optimize)!!!
//        TicketInfo oldTicketInfo = new TicketInfo(ticketForUpdates.getTicketInfo());
        TicketInfo oldTicketInfo = ticketForUpdates.getTicketInfo();
        ticketForUpdates.getTicketInfoHistory().add(oldTicketInfo);

        ticketForUpdates.setTicketInfo(newTicketInfo);

        ticketEJB.updateTicket(ticketForUpdates);

        FacesMessage msg = new FacesMessage("Обновлено (параметры заявки)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAcceptTicket() {
        logger.info("=>=>=>=>=> TicketController.doAcceptTicket()");
        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());
        Ticket ticketForUpdates = ticketEJB.findTicketById(ticket.getId());

        if (ticketForUpdates.getClosing() != null || ticketForUpdates.getDeletion() != null) { // difference
            FacesMessage msg = new FacesMessage("Заявка неактуальна (закрыта или удалена)", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticketForUpdates.getTicketInfo()); // difference with doUpdateTicketInfo(): instead of ticket ticketForUpdates
        newTicketInfo.setModification(lifeCycleInfo);
        newTicketInfo.setTicketStatus(TicketStatus.ACCEPTED); // difference
        newTicketInfo.setTransportInfo(transportForUpdates.getTransportInfo());
        newTicketInfo.setTerminalInfo(transportForUpdates.getTerminal().getTerminalInfo());
        newTicketInfo.setPointInfo(transportForUpdates.getPoint().getPointInfo());

        TicketInfo oldTicketInfo = ticketForUpdates.getTicketInfo();
        ticketForUpdates.getTicketInfoHistory().add(oldTicketInfo);

        ticketForUpdates.setTicketInfo(newTicketInfo);
        ticketForUpdates.setAcceptance(lifeCycleInfo); // difference

        ticketEJB.updateTicket(ticketForUpdates);

        FacesMessage msg = new FacesMessage("Заявка принята", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Boolean doCheckForPossibilityToAddTicket(Transport transport) {
        unitOfTransport = transportEJB.findTransportById(transport.getId());
        List<Ticket> tickets = unitOfTransport.getTickets();
        return tickets.isEmpty() || !(tickets.get(tickets.size() - 1).getClosing() == null && tickets.get(tickets.size() - 1).getClosing() == null);
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
