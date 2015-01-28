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
}
