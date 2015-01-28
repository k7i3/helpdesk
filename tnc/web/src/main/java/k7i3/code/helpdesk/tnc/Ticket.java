package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    @NotNull
    private String openedBy;
    private String closedBy;

    @ElementCollection
    private List<String> comments = new ArrayList<>();



}
