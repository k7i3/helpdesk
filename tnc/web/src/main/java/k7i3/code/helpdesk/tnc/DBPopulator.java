package k7i3.code.helpdesk.tnc;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 29.01.15.
 */
@Singleton
@Startup
@DataSourceDefinition(name = "java:global/jdbc/helpdeskDS",
        className = "org.apache.derby.jdbc.EmbeddedDriver",
        url = "jdbc:derby:memory:helpedskDB;create=true;user=app;password=app"
)
public class DBPopulator {
    @Inject
    private TransportEJB transportEJB;
    private Logger logger = Logger.getLogger("k7i3");

    Comment mockTicketComment;
    List<Comment> mockTicketComments = new ArrayList<>();
    Ticket mockTicket;
    List<Ticket> mockTickets = new ArrayList<>();
    Point mockPoint;
    Terminal mockTerminal;
    Comment mockTransportComment;
    List<Comment> mockTransportComments = new ArrayList<>();
    Transport transport;

    @PostConstruct
    private void createMockData() {

        logger.info("=>=>=>=>=> DBPopulator.createMockData()");

//        transportEJB.createTransport(new Transport("БашАвтоТранс", "Стерлитамак", "105БВ02", "108883", "Нефаз"));
//
//        Transport transport = new Transport("БашАвтоТранс", "Стерлитамак", "105БВ02", "108883", "Нефаз");
//        transportEJB.createTransport(transport);

        transportEJB.createTransport(new Transport("БашАвтоТранс", "Стерлитамак", "105БВ02", "108883", "Нефаз", new Terminal(5546, "REV. 07.627.019", "9177565745", new Point(54.78517, 56.04562, new Date()), null), null));


        mockTicketComment = new Comment("Стерлитамак", "шеф все пропало!", new Date(), null, null, null);
        mockTicketComments.add(mockTicketComment);
        mockTicket = new Ticket(new Date(), null, null, null, TicketStatus.OPENED, TicketHeader.NOT_ONLINE, "Стерлитамак", null, mockTicketComments);
        mockTickets.add(mockTicket);
        mockPoint = new Point(54.78517, 56.04562, new Date());
        mockTerminal = new Terminal(5546, "REV. 07.627.019", "9177565745", mockPoint, mockTickets);
        mockTransportComment = new Comment("Администратор", "автобус эксплуатируется?", new Date(), null, null, null);
        mockTransportComments.add(mockTransportComment);
        transport = new Transport("БашАвтоТранс", "Стерлитамак", "105БВ02", "108883", "Нефаз", mockTerminal, mockTransportComments);
        transportEJB.createTransport(transport);

        transportEJB.createTransport(new Transport("БашАвтоТранс", "Салават", "105БВ02", "108883", "Нефаз",
                new Terminal(5546, "REV. 07.627.019", "9177565745", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        transportEJB.createTransport(new Transport("БашАвтоТранс", "Уфа-1", "А111АА102", "104423", "Mersedes Sprinter",
                new Terminal(6666, "REV. 07.627.017", "9177677645", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        transportEJB.createTransport(new Transport("БашАвтоТранс", "Уфа-1", "105ГГ02", "103333", "Нефаз",
                new Terminal(5111, "REV. 07.627.019", "9177333645", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        transportEJB.createTransport(new Transport("Медицина", "ГКБ Салават", "А175БВ102", "177723", "УАЗ",
                new Terminal(5555, "REV. 07.627.020", "9177222245", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        transportEJB.createTransport(new Transport("Медицина", "ГКБ Нефтекамск", "А777АА102", "106663", "Land Rover",
                new Terminal(6755, "REV. 07.627.018", "9177635645", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        transportEJB.createTransport(new Transport("АСС", "Белорецк", "177БВ02", "134223", "Toyota",
                new Terminal(5578, "REV. 07.627.016", "9176666645", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        transportEJB.createTransport(new Transport("Школьники", "Янаул", "133НР02", "133333", "ПАЗ",
                new Terminal(5555, "REV. 07.627.021", "9177667845", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        mockTicketComments.add(new Comment("Стерлитамак", "шеф все пропало!", new Date(), null, null, null));
        mockTicketComments.add(new Comment("Администратор", "выпейте чашечку кофе, сейчас все будет", new Date(), null, null, null));
        mockTransportComments.add(new Comment("Администратор", "автобус эксплуатируется?", new Date(), null, null, null));
        mockTransportComments.add(new Comment("Стерлитамак", "стоит без двигателя с 90-х", new Date(), null, null, null));
        mockTickets.add(new Ticket(new Date(), null, null, null, TicketStatus.OPENED, TicketHeader.NOT_ONLINE, "Стерлитамак", null, mockTicketComments));
        transportEJB.createTransport(new Transport("БашАвтоТранс", "Стерлитамак", "А105БВ102", "105123", "Mersedes Sprinter",
                new Terminal(5555, "REV. 07.627.018", "9177635645", new Point(54.78517, 56.04562, new Date()), mockTickets), mockTransportComments));

        logger.info("=>=>=>=>=> Inserted " + transportEJB.findAllTransport().size() + " unit(s) of transport");
    }
}
