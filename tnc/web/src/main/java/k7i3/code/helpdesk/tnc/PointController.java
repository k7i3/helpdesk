package k7i3.code.helpdesk.tnc;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by k7i3 on 27.02.15.
 */
@Named
@RequestScoped
public class PointController implements Serializable {
    private Point point;
    private MapModel model;

    public void initModel() {
        model = new DefaultMapModel();
        LatLng latLng = new LatLng(point.getPointInfo().getLatitude(), point.getPointInfo().getLongitude());
        model.addOverlay(new Marker(latLng, point.getPointInfo().getDate().toString() + " - " + point.getPointInfo().getSpeed().toString() + " км/ч " + "(lat/lng: " + point.getPointInfo().getLatitude() + "/" + point.getPointInfo().getLongitude() +")"));
    }














    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }
}
