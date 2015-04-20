package k7i3.code.helpdesk.tnc;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Created by k7i3 on 20.04.15.
 */
@FacesConverter(value="ticketHeaderConverter")
public class TicketHeaderConverter implements Converter{

    @Inject
    private TicketEJB ticketEJB;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return ticketEJB.findTicketHeaderById(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
