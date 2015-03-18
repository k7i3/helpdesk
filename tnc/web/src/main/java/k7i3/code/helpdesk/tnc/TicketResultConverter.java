package k7i3.code.helpdesk.tnc;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * Created by k7i3 on 18.03.15.
 */
@FacesConverter(value="ticketResultConverter")
public class TicketResultConverter extends EnumConverter {

    public TicketResultConverter() {
        super(TicketResult.class);
    }
}