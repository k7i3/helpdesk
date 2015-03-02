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
    private Logger logger = Logger.getLogger("k7i3");

    private Transport transport;
    private Ticket ticket = new Ticket();
    private List<TicketHeader> ticketHeaders = Arrays.asList(TicketHeader.values());
    private String didBy;
    private String commentContent;

    public void doAddTransportComment(Transport transport, String commentDidBy) {
        Comment comment = new Comment(new LifeCycleInfo(new Date(), commentDidBy), new CommentInfo(new LifeCycleInfo(new Date(), commentDidBy), commentContent));
        transport.getComments().add(comment);
        transportEJB.updateTransport(transport);
        commentContent = null;

        FacesMessage msg = new FacesMessage("Комментарий к транспорту сохранен", transport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAddTicketComment(Ticket ticket, String didBy) {
        Comment comment = new Comment(new LifeCycleInfo(new Date(), didBy), new CommentInfo(new LifeCycleInfo(new Date(), didBy), commentContent));
        ticket.getComments().add(comment);
        transportEJB.updateTicket(ticket);
        commentContent = null;

        FacesMessage msg = new FacesMessage("Комментарий к заявке сохранен", ticket.getTicketInfo().getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAddTicket() {
        logger.info("=>=>=>=>=> TicketController.doAddTicket()");
        ticket.setCreation(new LifeCycleInfo(new Date(), didBy));
        ticket.getTicketInfo().setModification(ticket.getCreation());
        ticket.getTicketInfo().setTicketStatus(TicketStatus.OPENED);
        ticket.getTicketInfo().setTransportInfo(transport.getTransportInfo());
        ticket.getTicketInfo().setTerminalInfo(transport.getTerminal().getTerminalInfo());
        ticket.getTicketInfo().setPointInfo(transport.getPoint().getPointInfo());
        ticket.getComments().add(new Comment(ticket.getCreation(), new CommentInfo(ticket.getCreation(), commentContent)));
        transport.getTickets().add(ticket);
        transportEJB.updateTransport(transport);

        FacesMessage msg = new FacesMessage("Заявка сохранена", transport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);

//        doReset(); for PF('addTicketDialog').show() via JS (@SessionScoped) and for Primefaces dialog framework (@SessionScoped)
//        RequestContext.getCurrentInstance().closeDialog(null); for Primefaces dialog framework (@SessionScoped)
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


















    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
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
