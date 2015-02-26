package k7i3.code.helpdesk.tnc;

import org.primefaces.context.RequestContext;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 24.02.15.
 */
@Named
@SessionScoped
public class TicketController implements Serializable {
//    @Inject
//    private Conversation conversation;
    @Inject
    private TransportEJB transportEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private Transport transport;
    private String didBy;
    private Ticket ticket = new Ticket();
    private TicketHeader ticketHeader;
//    List<TicketHeader> ticketHeaders = Arrays.asList(TicketHeader.values());
    private String commentContent;

    public void doOpenTicketDialog() {
//        this.transport = transport;
//        this.didBy = didBy;

        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
//        options.put("contentHeight", 320);

        FacesMessage msg = new FacesMessage("!!!", "!!!");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().openDialog("addTicket", options, null);
    }

    public void doCloseTicketDialog() {
        logger.info("=>=>=>=>=> TicketController.doCloseTicketDialog()");
        RequestContext.getCurrentInstance().closeDialog(ticket);
    }

    public void doAddTicket() {
        logger.info("=>=>=>=>=> TicketController.doAddTicket()");
        ticket.setCreation(new LifeCycleInfo(new Date(), didBy));
        ticket.getTicketInfo().setModification(ticket.getCreation());
        ticket.getTicketInfo().setTicketStatus(TicketStatus.OPENED);
        ticket.getTicketInfo().setTicketHeader(TicketHeader.OTHER); // temporary
        ticket.getTicketInfo().setTransportInfo(transport.getTransportInfo());
        ticket.getTicketInfo().setTerminalInfo(transport.getTerminal().getTerminalInfo());
        ticket.getTicketInfo().setPointInfo(transport.getPoint().getPointInfo());
        ticket.getComments().add(new Comment(ticket.getCreation(), new CommentInfo(ticket.getCreation(), commentContent)));
        transport.getTickets().add(ticket);
        transportEJB.updateTransport(transport);


        FacesMessage msg = new FacesMessage(didBy, transport.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

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

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
