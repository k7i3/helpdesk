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
    private final static String[] commentCreatedBy;
    private final static String[] commentContent;

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

        commentCreatedBy = new String[5];
        commentCreatedBy[0] = "Агент Смит";
        commentCreatedBy[1] = "Тринити";
        commentCreatedBy[2] = "Сайфер";
        commentCreatedBy[3] = "Морфеус";
        commentCreatedBy[4] = "Мальчик";

        commentContent = new String[5];
        commentContent[0] = "Я этот город… ненавижу. Этот зоопарк, тюрьму, эту реальность — называйте как хотите — меня просто выворачивает. Даже ваш запах. Я дышу им, ощущаю кожей вашу вонь. И хотя, я понимаю, что это глупо, я опасаюсь подхватить вашу заразу, каждый день думаю об этом!";
        commentContent[1] = "Следуй за белым кроликом.";
        commentContent[2] = "Информации, получаемой из Матрицы, гораздо больше, чем ты можешь расшифровать. Ты привыкаешь к этому. Скоро твой мозг сам делает перевод. Я уже даже не вижу код. Я вижу блондинку, брюнетку и рыжую.";
        commentContent[3] = "Рано или поздно ты поймешь, как и я. Знать путь и пройти его — не одно и тоже.";
        commentContent[4] = "Не пытайся согнуть ложку; это невозможно. Вместо этого, попытайся понять главное.";
    }

    @PostConstruct
    private void createMockData() {
        logger.info("=>=>=>=>=> DBPopulator.createMockData()");
        for (int i = 0; i < 100; i++) {
            transportEJB.createTransport(new Transport(getRandomProject(), getRandomBranch(), getRandomStateNumber(), getRandomGarageNumber(), getRandomModel(), getRandomTerminal(), getRandomComments()));
        }
        logger.info("=>=>=>=>=> Inserted " + transportEJB.findAllTransport().size() + " unit(s) of transport");
    }

// RANDOM METHODS BEGIN//

    private String getRandomProject() {
        return projects[(int) (Math.random() * 5)];
    }

    private String getRandomBranch() {
        return branches[(int) (Math.random() * 5)];
    }

    private String getRandomStateNumber() {
        return "А" + (int) ((Math.random() * 900) + 100) + "БВ102";
    }

    private String getRandomGarageNumber() {
        return "0" + (int) ((Math.random() * 9000) + 1000);
    }

    private String getRandomModel() {
        return models[(int) (Math.random() * 5)];
    }

    private Terminal getRandomTerminal() {
        return new Terminal(getRandomNumber(), getRandomFirmware(), getRandomMobile(), getRandomPoint(), getRandomTickets());
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

    private Point getRandomPoint() {
        return new Point(54.78517, 56.04562, getRandomDate());
    }

    private Date getRandomDate() {
        if (random.nextBoolean())
            return new Date();

        return new Date(new Date().getTime() - (long) 86400000);
    }

    private List<Ticket> getRandomTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            tickets.add(getRandomTicket(TicketStatus.CLOSED));
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
        switch (ticketStatus) {
            case CLOSED:
                return new Ticket(new Date(), new Date(), new Date(), null, TicketStatus.CLOSED, getRandomTicketHeader(), "создатель заявки", "закрыватель заявки", getRandomComments());
            case IN_PROGRESS:
                return new Ticket(new Date(), new Date(), null, null, TicketStatus.IN_PROGRESS, getRandomTicketHeader(), "создатель заявки", null, getRandomComments());
            case OPENED:
                return new Ticket(new Date(), new Date(), null, null, TicketStatus.OPENED, getRandomTicketHeader(), "создатель заявки", null, getRandomComments());
            default:
                return new Ticket(new Date(), new Date(), new Date(), null, TicketStatus.CLOSED, getRandomTicketHeader(), "создатель заявки", "закрыватель заявки", getRandomComments());
        }
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

    private List<Comment> getRandomComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 11); i++) {
            comments.add(getRandomComment());
        }
        return comments;
    }

    private Comment getRandomComment() {
        if (random.nextBoolean())
            return new Comment("Вася", "короткий комментарий с датой изменения - Монолог Гамлета (Быть или не быть?)", new Date(), new Date(), null, null);
        else
            return new Comment("Шекспир", "длинный комментарий без даты изменения - Монолог «To be, or not to be» является, пожалуй, одним из наиболее известных фрагментов наследия Шекспира. Даже человек, не читавший «Гамлета», наверняка слышал слова «Быть или не быть – вот в чём вопрос?» ", new Date(), null, null, null);
    }

// RANDOM METHODS END//
}