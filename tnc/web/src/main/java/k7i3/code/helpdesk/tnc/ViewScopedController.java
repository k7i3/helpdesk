package k7i3.code.helpdesk.tnc;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by k7i3 on 03.04.15.
 */
@Named
@ViewScoped
public class ViewScopedController {
    private List<Transport> filteredTransport;

    //GETTERS AND SETTERS

    public List<Transport> getFilteredTransport() {
        return filteredTransport;
    }

    public void setFilteredTransport(List<Transport> filteredTransport) {
        this.filteredTransport = filteredTransport;
    }
}
