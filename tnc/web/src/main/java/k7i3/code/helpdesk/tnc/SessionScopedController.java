package k7i3.code.helpdesk.tnc;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by k7i3 on 11.03.15.
 */
@Named
@SessionScoped
public class SessionScopedController implements Serializable {

    private Transport unitOfTransport;
    private Ticket ticket;
//    private Ticket ticket = new Ticket();
//    private List<Transport> filteredTransport; //not used after deletion this attribute from dataTable


    public Transport getUnitOfTransport() {
        return unitOfTransport;
    }

    public void setUnitOfTransport(Transport unitOfTransport) {
        this.unitOfTransport = unitOfTransport;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
