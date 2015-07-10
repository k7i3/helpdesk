package k7i3.code.helpdesk.tnc;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * Created by k7i3 on 10.07.15.
 */
@FacesConverter(value="ticketStatusConverter")
public class TicketStatusConverter extends EnumConverter {

    public TicketStatusConverter() {
        super(TicketStatus.class);
    }
}
