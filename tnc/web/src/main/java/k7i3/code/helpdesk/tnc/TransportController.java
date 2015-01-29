package k7i3.code.helpdesk.tnc;

import javax.inject.Inject;

/**
 * Created by k7i3 on 29.01.15.
 */
public class TransportController {
    @Inject
    private TransportEJB transportEJB;
    private Transport transport = new Transport();

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
