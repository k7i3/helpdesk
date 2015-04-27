package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private PointInfo pointInfo = new PointInfo();

    public Point() {
    }

    public Point(PointInfo pointInfo) {
        this.pointInfo = pointInfo;
    }

    public Long getId() {
        return id;
    }

    public PointInfo getPointInfo() {
        return pointInfo;
    }

    public void setPointInfo(PointInfo pointInfo) {
        this.pointInfo = pointInfo;
    }
}
