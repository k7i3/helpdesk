package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by k7i3 on 13.02.15.
 */
@Entity
public class PointInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private Integer speed;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public PointInfo() {
    }

    public PointInfo(Date updateDate, Double latitude, Double longitude, Date date) {
        this.updateDate = updateDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
