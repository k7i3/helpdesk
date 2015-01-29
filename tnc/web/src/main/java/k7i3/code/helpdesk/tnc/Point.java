package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Point {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Point() {
    }

    public Point(Double longitude, Double latitude, Date date) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
