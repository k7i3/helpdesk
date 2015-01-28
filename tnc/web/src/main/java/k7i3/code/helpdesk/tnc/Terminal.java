package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Terminal {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Integer number;
    @NotNull
    private String firmware;
    @NotNull
    private String mobile;
    @OneToOne
    private Point lastPoint;
    @OneToMany
    private List<Ticket> tickets = new ArrayList<>();

}
