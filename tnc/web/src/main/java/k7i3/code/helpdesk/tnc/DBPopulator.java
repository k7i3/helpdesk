package k7i3.code.helpdesk.tnc;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
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
@DataSourceDefinition(name = "java:global/jdbc/helpdeskDS",
        className = "org.apache.derby.jdbc.EmbeddedDriver",
        url = "jdbc:derby:memory:helpedskDB;create=true;user=app;password=app"
)
public class DBPopulator {
    @Inject
    private TransportEJB transportEJB;
    private Logger logger = Logger.getLogger("k7i3");
    Random random = new Random(new Date().getTime());

    private final static String[] projects;
    private final static String[] branches;
    private final static String[] models;
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

        didBy = new String[5];
        didBy[0] = "Пифия";
        didBy[1] = "Тринити";
        didBy[2] = "Сайфер";
        didBy[3] = "Морфеус";
        didBy[4] = "Мальчик";

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
        for (int i = 0; i < 100; i++) {
            Transport transport = new Transport(getRandomTransportInfo(), getRandomLifeCycleInfo(), getRandomTerminal());
            transport.getTickets().addAll(getRandomTickets());
            transport.getComments().addAll(getRandomComments());
            transport.getTransportInfoHistory().addAll(getRandomTransportInfoHistory());
            transport.setPoint(getRandomPoint());
            transportEJB.createTransport(transport);
        }
        logger.info("=>=>=>=>=> Inserted " + transportEJB.findAllTransport().size() + " unit(s) of transport");
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
        terminalInfo.setModel(getRandomModel());
        return terminalInfo;
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
            tickets.add(getRandomTicket(TicketStatus.DELETED));
        }

        if (random.nextBoolean()) {
            if (random.nextBoolean())
                tickets.add(getRandomTicket(TicketStatus.IN_PROGRESS));
            else
                tickets.add(getRandomTicket(TicketStatus.OPENED));
        }
        return tickets;
    }

    private Ticket getRandomTicket(TicketStatus ticketStatus) {
        Ticket ticket = new Ticket(getRandomTicketInfo(ticketStatus), getRandomLifeCycleInfo());
        ticket.getTicketInfoHistory().addAll(getRandomTicketInfoHistory());
        ticket.getComments().addAll(getRandomComments());
        return ticket;
        }

    private TicketInfo getRandomTicketInfo(TicketStatus ticketStatus) {
        logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - before TicketInfo ticketInfo = new TicketInfo(...)");
        TicketInfo ticketInfo = new TicketInfo(ticketStatus, getRandomTicketHeader(), getRandomLifeCycleInfo(), getRandomTicketDetails(), getRandomTransportInfo(), getRandomTerminalInfo(), getRandomPointInfo());
        logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - before switch (ticketStatus)");
        switch (ticketStatus) {
            case CLOSED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - switch (CLOSED)");
                ticketInfo.setClosing(getRandomLifeCycleInfo());
                return ticketInfo;
            case IN_PROGRESS:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - switch (IN_PROGRESS)");
                return ticketInfo;
            case OPENED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - switch (OPENED)");
                return ticketInfo;
            case DELETED:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - switch (DELETED)");
                ticketInfo.setClosing(getRandomLifeCycleInfo());
                ticketInfo.setDeletion(getRandomLifeCycleInfo());
                return ticketInfo;
            default:
                logger.info("=>=>=>=>=> DBPopulator.getRandomTicketInfo() - switch (default)");
                return ticketInfo;
        }
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
        comment.getCommentInfoHistory().addAll(getRandomCommentInfoHistory());
        return comment;
    }

    private CommentInfo getRandomCommentInfo() {
        CommentInfo commentInfo = new CommentInfo(getRandomLifeCycleInfo(), getRandomContent());
        if (random.nextBoolean()) {
            commentInfo.setModification(getRandomLifeCycleInfo());
            commentInfo.setDeletion(getRandomLifeCycleInfo());
            return commentInfo;
        } else {
            commentInfo.setModification(getRandomLifeCycleInfo());
            return commentInfo;
        }
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