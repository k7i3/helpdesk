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
 * Created by k7i3 on 21.04.15.
 */
@Named
@RequestScoped
public class TicketHeaderController {
    @Inject
    private TicketEJB ticketEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private TicketHeader ticketHeader = new TicketHeader();
    private List<TicketHeader> ticketHeaders;
    private List<TicketHeader> checkboxSelectedTicketHeaders;

    //Do FIND

    @PostConstruct
    private void doFindAllTicketHeaders() {
        ticketHeaders = ticketEJB.findAllTicketHeaders();
    }

    //Do ADD

    public void doAddTicketHeader() {
        ticketEJB.createTicketHeader(ticketHeader);

        FacesMessage msg = new FacesMessage("Сохранено (неисправность)", ticketHeader.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        TicketHeader editedTicketHeader = (TicketHeader) event.getObject();

//        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        ticketEJB.updateTicketHeader(editedTicketHeader);

        FacesMessage msg = new FacesMessage("Сохранено (неисправность)" , editedTicketHeader.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Отмена", ((TicketHeader) event.getObject()).getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //GETTERS AND SETTERS

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }

    public List<TicketHeader> getTicketHeaders() {
        return ticketHeaders;
    }

    public void setTicketHeaders(List<TicketHeader> ticketHeaders) {
        this.ticketHeaders = ticketHeaders;
    }

    public List<TicketHeader> getCheckboxSelectedTicketHeaders() {
        return checkboxSelectedTicketHeaders;
    }

    public void setCheckboxSelectedTicketHeaders(List<TicketHeader> checkboxSelectedTicketHeaders) {
        this.checkboxSelectedTicketHeaders = checkboxSelectedTicketHeaders;
    }
}
