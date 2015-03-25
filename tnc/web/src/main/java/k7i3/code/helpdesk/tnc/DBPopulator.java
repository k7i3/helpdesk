package k7i3.code.helpdesk.tnc;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 29.01.15.
 */
@Singleton
@Startup

//@DataSourceDefinition(
//        name = "java:global/jdbc/helpdeskDS",
//
//        className = "org.apache.derby.jdbc.EmbeddedDriver",
////        working
//
////        className = "org.apache.derby.jdbc.EmbeddedDataSource",
////        doesn't working:
////        remote failure: Error occurred during deployment: Exception while preparing the app : Exception [EclipseLink-4002] (Eclipse Persistence Services - 2.5.2.v20140319-9ad6abd): org.eclipse.persistence.exceptions.DatabaseException
////        Internal Exception: java.sql.SQLException: Error in allocating a connection. Cause: Connection could not be allocated because: База данных '' не найдена.
//
//        url = "jdbc:derby:helpdeskDB;create=true;user=app;password=app"
////        url = "jdbc:derby:memory:helpdeskDB;create=true;user=app;password=app"
//)

//@DataSourceDefinition(
//        name = "java:global/jdbc/helpdeskDS",
//        className = "org.apache.derby.jdbc.EmbeddedDataSource",
////        working
//        user = "app",
//        password = "app",
//        databaseName = "helpdeskDB",
////        databaseName = "memory:helpdeskDB",
//        properties = {"connectionAttributes=;create=true"}
//)

@DataSourceDefinitions({
        @DataSourceDefinition(
            name = "java:global/jdbc/dataDS",
//            className = "org.apache.derby.jdbc.EmbeddedDataSource",
//            className = "org.apache.derby.jdbc.ClientXADataSource",
            className = "org.apache.derby.jdbc.EmbeddedXADataSource",
            user = "app",
            password = "app",
            databaseName = "dataDB",
            properties = {"connectionAttributes=;create=true"}
        ),

        @DataSourceDefinition(
            name = "java:global/jdbc/usersDS",
//            className = "org.apache.derby.jdbc.EmbeddedDataSource",
//            className = "org.apache.derby.jdbc.ClientXADataSource",
            className = "org.apache.derby.jdbc.EmbeddedXADataSource",
            user = "app",
            password = "app",
            databaseName = "usersDB",
            properties = {"connectionAttributes=;create=true"}
        )
})

public class DBPopulator {
    @Inject
    private TransportEJB transportEJB;
    @Inject
    private UserEJB userEJB;
    private Logger logger = Logger.getLogger("k7i3");
    Random random = new Random(new Date().getTime());

    private final static String[] projects;
    private final static String[] branches;
    private final static String[] models;
    private final static String[] terminalModels;
    private final static String[] didBy;
    private final static String[] commentContent;
    private final static String[] route;
    private final static String[] places;

    static {
        projects = new String[5];
        projects[0] = "БАТ";
        projects[1] = "Медицина";
        projects[2] = "АСС";
        projects[3] = "Школьники";
        projects[4] = "БАД";

        branches = new String[5];
        branches[0] = "Уфа";
        branches[1] = "Стерлитамак";
        branches[2] = "Нефтекамск";
        branches[3] = "Мелеуз";
        branches[4] = "Салават";

        models = new String[5];
        models[0] = "Audi";
        models[1] = "Mercedes";
        models[2] = "BMW";
        models[3] = "Opel";
        models[4] = "Нефаз";

        terminalModels = new String[5];
        terminalModels[0] = "Voyager 2N";
        terminalModels[1] = "Voyager 3N";
        terminalModels[2] = "Voyager 4N";
        terminalModels[3] = "Voyager 5N";
        terminalModels[4] = "Скаут";

        didBy = new String[5];
        didBy[0] = "Пифия";
        didBy[1] = "Тринити";
        didBy[2] = "Сайфер";
        didBy[3] = "Морфеус";
        didBy[4] = "Нео";

        commentContent = new String[5];
        commentContent[0] = "Быть Избранным все равно что быть влюблённым. Никто не говорит тебе, что ты влюблён. Ты просто знаешь это.";
        commentContent[1] = "Следуй за белым кроликом.";
        commentContent[2] = "Информации, получаемой из Матрицы, гораздо больше, чем ты можешь расшифровать. Ты привыкаешь к этому. Скоро твой мозг сам делает перевод. Я уже даже не вижу код. Я вижу блондинку, брюнетку и рыжую.";
        commentContent[3] = "Рано или поздно ты поймешь, как и я. Знать путь и пройти его — не одно и тоже.";
        commentContent[4] = "Не пытайся согнуть ложку; это невозможно. Вместо этого, попытайся понять главное.";

        route = new String[5];
        route[0] = "51";
        route[1] = "51а";
        route[2] = "110";
        route[3] = "110с";
        route[4] = "30к";

        places = new String[5];
        places[0] = "южный автовокзал";
        places[1] = "северный автовокзал";
        places[2] = "восточный автовокзал";
        places[3] = "западный автовокзал";
        places[4] = "центральный автовокзал";
    }

    @PostConstruct
    private void createMockData() {
        logger.info("=>=>=>=>=> DBPopulator.createMockData()");
        for (int i = 0; i < 50; i++) {
            Transport transport = new Transport(getRandomTransportInfo(), getRandomLifeCycleInfo(), getRandomTerminal());
            transport.getTickets().addAll(getRandomTickets());
            transport.getComments().addAll(getRandomComments());
            transport.getTransportInfoHistory().addAll(getRandomTransportInfoHistory());
            transport.setPoint(getRandomPoint());
            transportEJB.createTransport(transport);
        }
        logger.info("=>=>=>=>=> Inserted " + transportEJB.findAllTransport().size() + " unit(s) of transport");

        User user = new User("tnc", "12345");
        user.getRoles().add("admin");
        user.getProjects().add("БАТ");
        user.getBranches().add("Уфа");
        userEJB.createUser(user);
        logger.info("=>=>=>=>=> (tnc-12345-admin-БАТ-Уфа) user was inserted");
    }

// RANDOM METHODS BEGIN //

    // TRANSPORT

    private TransportInfo getRandomTransportInfo() {
        TransportInfo transportInfo = new TransportInfo(getRandomLifeCycleInfo(), getRandomProject(), getRandomStateNumber());
        transportInfo.setBranch(getRandomBranch());
        transportInfo.setGarageNumber(getRandomGarageNumber());
        transportInfo.setModel(getRandomModel());
        transportInfo.setRoute(getRandomRoute());
        transportInfo.setTransportEquipment(getRandomTransportEquipment());
        return transportInfo;
    }

    private List<TransportInfo> getRandomTransportInfoHistory() {
        ArrayList<TransportInfo> transportInfoHistory = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            transportInfoHistory.add(getRandomTransportInfo());
        }
        return transportInfoHistory;
    }

    private String getRandomRoute() {
        return route[(int) (Math.random() * 5)];
    }

    private TransportEquipment getRandomTransportEquipment() {
        return new TransportEquipment(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
    }

    private String getRandomProject() {
        return projects[(int) (Math.random() * 5)];
    }

    private String getRandomBranch() {
        return branches[(int) (Math.random() * 5)];
    }

    private String getRandomStateNumber() {
        return "А" + (int) ((Math.random() * 900) + 100) + "АА102";
    }

    private String getRandomGarageNumber() {
        return "0" + (int) ((Math.random() * 9000) + 1000);
    }

    private String getRandomModel() {
        return models[(int) (Math.random() * 5)];
    }

    //TERMINAL

    private Terminal getRandomTerminal() {
        Terminal terminal = new Terminal(getRandomLifeCycleInfo(), getRandomTerminalInfo());
        terminal.getTerminalInfoHistory().addAll(getRandomTerminalInfoHistory());
        return terminal;
    }

    private TerminalInfo getRandomTerminalInfo() {
        TerminalInfo terminalInfo = new TerminalInfo(getRandomLifeCycleInfo(), getRandomNumber());
        terminalInfo.setFirmware(getRandomFirmware());
        terminalInfo.setMobileNumber(getRandomMobile());
        terminalInfo.setSerialNumber(getRandomSerialNumber());
        terminalInfo.setModel(getRandomTerminalModel());
        return terminalInfo;
    }

    private String getRandomTerminalModel() {
        return terminalModels[(int) (Math.random() * 5)];
    }

    private List<TerminalInfo> getRandomTerminalInfoHistory() {
        ArrayList<TerminalInfo> terminalInfoHistory = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            terminalInfoHistory.add(getRandomTerminalInfo());
        }
        return terminalInfoHistory;
    }

    private String getRandomSerialNumber() {
        return "SN" + (int) (Math.random() * 100000000);
    }

    private int getRandomNumber() {
        return (int) ((Math.random() * 5000) + 5000);
    }

    private String getRandomFirmware() {
        return "REV. 07.627.0" + (int) (Math.random() * 10 + 17);
    }

    private String getRandomMobile() {
        return "893" + (int) ((Math.random() * 10000000) + 70000000);
    }

    // POINT

    private Point getRandomPoint() {
        return new Point(getRandomPointInfo());
    }

    private PointInfo getRandomPointInfo() {
        PointInfo pointInfo = new PointInfo(getRandomLifeCycleInfo(), (random.nextInt(90)) + random.nextDouble(), (random.nextInt(180)) + random.nextDouble(), getRandomDate());
        pointInfo.setSpeed(random.nextInt(180));
        return pointInfo;
    }

    // TICKET

    private List<Ticket> getRandomTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            tickets.add(getRandomTicket(TicketStatus.CLOSED));
            tickets.add(getRandomTicket(TicketStatus.ARCHIVED));
        }

        if (random.nextBoolean()) {
            if (random.nextBoolean())
                tickets.add(getRandomTicket(TicketStatus.ACCEPTED));
            else
                tickets.add(getRandomTicket(TicketStatus.OPENED));
        }
        return tickets;
    }

    private Ticket getRandomTicket(TicketStatus ticketStatus) {
        Ticket ticket = new Ticket(getRandomTicketInfo(ticketStatus), getRandomLifeCycleInfo());
        logger.info("=>=>=>=>=> DBPopulator.getRandomTickets() - before switch (ticketStatus)");
        switch (ticketStatus) {
            case CLOSED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTickets() - switch (CLOSED)");
                ticket.setClosing(getRandomLifeCycleInfo());
                break;
            case ACCEPTED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTickets()) - switch (ACCEPTED)");
                ticket.setAcceptance(getRandomLifeCycleInfo());
                break;
            case OPENED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTickets() - switch (OPENED)");
                break;
            case ARCHIVED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTickets() - switch (ARCHIVED)");
                ticket.setAcceptance(getRandomLifeCycleInfo());
                ticket.setService(getRandomLifeCycleInfo());
                ticket.setClosing(getRandomLifeCycleInfo());
                ticket.setArchiving(getRandomLifeCycleInfo());
                break;
            default:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - switch (default)");
        }
        ticket.getTicketInfoHistory().addAll(getRandomTicketInfoHistory());
        ticket.getComments().addAll(getRandomComments());
        return ticket;
        }

    private TicketInfo getRandomTicketInfo(TicketStatus ticketStatus) {
        logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - before TicketInfo ticketInfo = new TicketInfo(...)");
        return new TicketInfo(ticketStatus, getRandomTicketHeader(), getRandomLifeCycleInfo(), getRandomTicketDetails(), getRandomTransportInfo(), getRandomTerminalInfo(), getRandomPointInfo());
    }

    private List<TicketInfo> getRandomTicketInfoHistory() {
        ArrayList<TicketInfo> ticketInfoHistory = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            ticketInfoHistory.add(getRandomTicketInfo(getRandomTicketStatus()));
        }
        return ticketInfoHistory;
    }

    private TicketStatus getRandomTicketStatus() {
        TicketStatus[] ticketStatuses = TicketStatus.values();
        return ticketStatuses[random.nextInt(ticketStatuses.length)];
    }

    private TicketDetails getRandomTicketDetails() {
        return new TicketDetails(random.nextBoolean(), random.nextBoolean(), getRandomPlace(), getRandomMobile(), getRandomMobile());
    }

    private String getRandomPlace() {
        return places[(int) (Math.random() * 5)];
    }

    private TicketHeader getRandomTicketHeader() {
        if (random.nextBoolean()) {
            if (random.nextBoolean()){
                return TicketHeader.NOT_ONLINE;
            }
            return TicketHeader.OTHER;
        }
        return TicketHeader.BAD_TRACK;
    }

    // COMMON

    private List<Comment> getRandomComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            comments.add(getRandomComment());
        }
        return comments;
    }

    private Comment getRandomComment() {
        Comment comment = new Comment(getRandomLifeCycleInfo(), getRandomCommentInfo());
        if (random.nextBoolean()) comment.setDeletion(getRandomLifeCycleInfo());
        comment.getCommentInfoHistory().addAll(getRandomCommentInfoHistory());
        return comment;
    }

    private CommentInfo getRandomCommentInfo() {
        return new CommentInfo(getRandomLifeCycleInfo(), getRandomContent());
    }

    private String getRandomContent() {
        return commentContent[(int) (Math.random() * 5)];
    }

    private List<CommentInfo> getRandomCommentInfoHistory() {
        ArrayList<CommentInfo> commentInfoHistory = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            commentInfoHistory.add(getRandomCommentInfo());
        }
        return commentInfoHistory;
    }

    private Date getRandomDate() {
        if (random.nextBoolean())
            return new Date();

        return new Date(new Date().getTime() - (long) 86400000);
    }

    private LifeCycleInfo getRandomLifeCycleInfo() {
        return new LifeCycleInfo(getRandomDate(), getRandomDidBy());
    }

    private String getRandomDidBy() {
        return didBy[(int) (Math.random() * 5)];
    }

// RANDOM METHODS END //
}