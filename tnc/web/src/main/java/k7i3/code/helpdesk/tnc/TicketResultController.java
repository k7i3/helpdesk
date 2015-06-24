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
public class TicketResultController {
    @Inject
    private TicketEJB ticketEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private TicketResult ticketResult = new TicketResult();
    private List<TicketResult> ticketResults;
    private List<TicketResult> checkboxSelectedTicketResults;

    //Do FIND

    @PostConstruct
    private void doFindAllTicketResults() {
        ticketResults = ticketEJB.findAllTicketResults();
    }

    //Do ADD

    public void doAddTicketResult() {
        ticketEJB.createTicketResult(ticketResult);

        FacesMessage msg = new FacesMessage("Сохранено (реагирование)", ticketResult.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        TicketResult editedTicketResult = (TicketResult) event.getObject();

//        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        ticketEJB.updateTicketResult(editedTicketResult);

        FacesMessage msg = new FacesMessage("Сохранено (реагирование)" , editedTicketResult.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Отмена", ((TicketResult) event.getObject()).getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //GETTERS AND SETTERS

    public TicketResult getTicketResult() {
        return ticketResult;
    }

    public void setTicketResult(TicketResult ticketResult) {
        this.ticketResult = ticketResult;
    }

    public List<TicketResult> getTicketResults() {
        return ticketResults;
    }

    public void setTicketResults(List<TicketResult> ticketResults) {
        this.ticketResults = ticketResults;
    }

    public List<TicketResult> getCheckboxSelectedTicketResults() {
        return checkboxSelectedTicketResults;
    }

    public void setCheckboxSelectedTicketResults(List<TicketResult> checkboxSelectedTicketResults) {
        this.checkboxSelectedTicketResults = checkboxSelectedTicketResults;
    }
}
