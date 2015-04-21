package k7i3.code.helpdesk.tnc;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Created by k7i3 on 18.03.15.
 */
@FacesConverter(value="ticketResultConverter")
//public class TicketResultConverter extends EnumConverter {
//
//    public TicketResultConverter() {
//        super(TicketResult.class);
//    }
//}
public class TicketResultConverter implements Converter {

    @Inject
    private TicketEJB ticketEJB;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return ticketEJB.findTicketResultById(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
