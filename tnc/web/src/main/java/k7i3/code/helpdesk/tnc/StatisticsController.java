package k7i3.code.helpdesk.tnc;

import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by k7i3 on 07.04.15.
 */
@Named
//@RequestScoped
@ViewScoped
public class StatisticsController implements Serializable{
    @Inject
    private TransportEJB transportEJB;
    @Inject
    private UserEJB userEJB;
    @Inject
    private TicketEJB ticketEJB;
    @Inject
    private StatisticsEJB statisticsEJB;

    private int countOfAllTickets;
    private int countOfOpenedTickets;
    private int countOfAcceptedTickets;
    private int countOfOnServiceTickets;
    private int countOfClosedTickets;
    private int countOfArchivedTickets;
    private int countOfIncorrectTickets;
    private int countOfCanceledTickets;
    private int countOfRepeatedOnServiceTickets;
    private int countOfRepeatedClosedTickets;

    private PieChartModel pieModel;
    private BarChartModel barModel;
    private MeterGaugeChartModel meterGaugeModel;

    private List <Object[]> countOfTicketsByResults;
    private List <Object[]> countOfTicketsByStatuses;
    private List <Object[]> countOfTicketsByHeaders;
    private List <Object[]> countOfTicketsByProjects;
    private List <Object[]> countOfTicketsByBranches;
    private List <Object[]> countOfTicketsByStatusesBetweenDates;

    //Init

    @PostConstruct
    public void init() {
        countOfAllTickets = statisticsEJB.countAllTickets();
        countOfOpenedTickets = statisticsEJB.countTicketsByStatus(TicketStatus.OPENED);
        countOfAcceptedTickets = statisticsEJB.countTicketsByStatus(TicketStatus.ACCEPTED);
        countOfOnServiceTickets = statisticsEJB.countTicketsByStatus(TicketStatus.ON_SERVICE);
        countOfClosedTickets = statisticsEJB.countTicketsByStatus(TicketStatus.CLOSED);
        countOfArchivedTickets = statisticsEJB.countTicketsByStatus(TicketStatus.ARCHIVED);
        countOfIncorrectTickets = statisticsEJB.countTicketsByStatus(TicketStatus.INCORRECT);
        countOfCanceledTickets = statisticsEJB.countTicketsByStatus(TicketStatus.CANCELED);
        countOfRepeatedOnServiceTickets = statisticsEJB.countTicketsByStatus(TicketStatus.REPEATED_ON_SERVICE);
        countOfRepeatedClosedTickets = statisticsEJB.countTicketsByStatus(TicketStatus.REPEATED_CLOSED);

        doCountTicketsByResults();
        countOfTicketsByStatuses = statisticsEJB.countTicketsByStatuses();
        countOfTicketsByHeaders = statisticsEJB.countTicketsByHeaders();
        countOfTicketsByProjects = statisticsEJB.countTicketsByProjects();
        countOfTicketsByBranches = statisticsEJB.countTicketsByBranches();
//        countOfTicketsByStatusesBetweenDates = statisticsEJB.countTicketsByStatusesBetweenDates(LocalDate.of(2015, Month.JUNE, 25), LocalDate.of(2015, Month.JUNE, 25));
        countOfTicketsByStatusesBetweenDates = statisticsEJB.countTicketsByStatusesBetweenDates(new Date(0), new Date());

        createMeterGaugeModel();
        createPieModel();
        createBarModel();
    }

    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){{
            add(10);
            add(20);
            add(30);
            add(40);
            add(50);
        }};
        return new MeterGaugeChartModel(25, intervals);
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries branch1 = new ChartSeries();
        branch1.setLabel("Уфа");
        branch1.set("БАТ", 6);
        branch1.set("БАД", 1);
        branch1.set("АСС", 2);
        branch1.set("Школьники", 0);
        branch1.set("Медицина", 3);

        ChartSeries branch2 = new ChartSeries();
        branch2.setLabel("Стерлитамак");
        branch2.set("БАТ", 2);
        branch2.set("БАД", 1);
        branch2.set("АСС", 2);
        branch2.set("Школьники", 0);
        branch2.set("Медицина", 0);

        ChartSeries branch3 = new ChartSeries();
        branch3.setLabel("Салават");
        branch3.set("БАТ", 1);
        branch3.set("БАД", 0);
        branch3.set("АСС", 2);
        branch3.set("Школьники", 0);
        branch3.set("Медицина", 0);

        ChartSeries branch4 = new ChartSeries();
        branch4.setLabel("Нефтекамск");
        branch4.set("БАТ", 1);
        branch4.set("БАД", 0);
        branch4.set("АСС", 2);
        branch4.set("Школьники", 2);
        branch4.set("Медицина", 0);

        ChartSeries branch5 = new ChartSeries();
        branch5.setLabel("Мелеуз");
        branch5.set("БАТ", 2);
        branch5.set("БАД", 1);
        branch5.set("АСС", 0);
        branch5.set("Школьники", 0);
        branch5.set("Медицина", 2);

        model.addSeries(branch1);
        model.addSeries(branch2);
        model.addSeries(branch3);
        model.addSeries(branch4);
        model.addSeries(branch5);

        return model;
    }

    //Do COUNT

    public void doCountTicketsByResults() {
        int count;
        countOfTicketsByResults = new ArrayList<>();
        for (TicketResult result: ticketEJB.findAllTicketResults()) {
            Object[] resultCount = new Object[2];
            resultCount[0] = result;
            count = statisticsEJB.countTicketsByResult(result);
            if (count == 0) continue;
            resultCount[1] = count;
            countOfTicketsByResults.add(resultCount);
        }

        countOfTicketsByResults.sort((o1,o2) -> (int) o2[1] - (int) o1[1]);

//        Collections.sort(countOfTicketsByResults, new Comparator<Object[]>() {
//            @Override
//            public int compare(Object[] o1, Object[] o2) {
//                return ((Number) o1[1]).intValue() - ((Number) o2[1]).intValue();
//            }
//        });

//        countOfTicketsByResults.sort(new Comparator<Object[]>() {
//            @Override
//            public int compare(Object[] o1, Object[] o2) {
//                return o1[1].toString().compareTo(o2[1].toString());
//            }
//        });
    }

    //Create

    private void createMeterGaugeModel() {
        meterGaugeModel = initMeterGaugeModel();
        meterGaugeModel.setTitle("Количество активных заявок:");
        meterGaugeModel.setSeriesColors("66cc66,93b75f,E7E658,cc6666,F50000");
        meterGaugeModel.setGaugeLabel("заявки");
    }

    private void createPieModel() {
        pieModel = new PieChartModel();

        pieModel.set("БАТ(10)", 10);
        pieModel.set("БАД(3)", 3);
        pieModel.set("АСС(7)", 7);
        pieModel.set("Медицина(3)", 3);
        pieModel.set("Школьники(2)", 2);

        pieModel.setTitle("Активные заявки по проектам:");
        pieModel.setLegendPosition("ne");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
    }

    private void createBarModel() {
        barModel = initBarModel();
        barModel.setTitle("Активные заявки по проектам/филиалам:");
        barModel.setAnimate(true);
        barModel.setLegendPosition("ne");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

    //GETTERS AND SETTERS

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public MeterGaugeChartModel getMeterGaugeModel() {
        createMeterGaugeModel();
        int i = (int)(Math.random() * 50);
        meterGaugeModel.setValue(i);
        return meterGaugeModel;
    }

    public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
        this.meterGaugeModel = meterGaugeModel;
    }

    public int getCountOfAllTickets() {
        return countOfAllTickets;
    }

    public void setCountOfAllTickets(int countOfAllTickets) {
        this.countOfAllTickets = countOfAllTickets;
    }

    public int getCountOfOpenedTickets() {
        return countOfOpenedTickets;
    }

    public void setCountOfOpenedTickets(int countOfOpenedTickets) {
        this.countOfOpenedTickets = countOfOpenedTickets;
    }

    public int getCountOfAcceptedTickets() {
        return countOfAcceptedTickets;
    }

    public void setCountOfAcceptedTickets(int countOfAcceptedTickets) {
        this.countOfAcceptedTickets = countOfAcceptedTickets;
    }

    public int getCountOfOnServiceTickets() {
        return countOfOnServiceTickets;
    }

    public void setCountOfOnServiceTickets(int countOfOnServiceTickets) {
        this.countOfOnServiceTickets = countOfOnServiceTickets;
    }

    public int getCountOfClosedTickets() {
        return countOfClosedTickets;
    }

    public void setCountOfClosedTickets(int countOfClosedTickets) {
        this.countOfClosedTickets = countOfClosedTickets;
    }

    public int getCountOfArchivedTickets() {
        return countOfArchivedTickets;
    }

    public void setCountOfArchivedTickets(int countOfArchivedTickets) {
        this.countOfArchivedTickets = countOfArchivedTickets;
    }

    public int getCountOfIncorrectTickets() {
        return countOfIncorrectTickets;
    }

    public void setCountOfIncorrectTickets(int countOfIncorrectTickets) {
        this.countOfIncorrectTickets = countOfIncorrectTickets;
    }

    public int getCountOfCanceledTickets() {
        return countOfCanceledTickets;
    }

    public void setCountOfCanceledTickets(int countOfCanceledTickets) {
        this.countOfCanceledTickets = countOfCanceledTickets;
    }

    public int getCountOfRepeatedOnServiceTickets() {
        return countOfRepeatedOnServiceTickets;
    }

    public void setCountOfRepeatedOnServiceTickets(int countOfRepeatedOnServiceTickets) {
        this.countOfRepeatedOnServiceTickets = countOfRepeatedOnServiceTickets;
    }

    public int getCountOfRepeatedClosedTickets() {
        return countOfRepeatedClosedTickets;
    }

    public void setCountOfRepeatedClosedTickets(int countOfRepeatedClosedTickets) {
        this.countOfRepeatedClosedTickets = countOfRepeatedClosedTickets;
    }

    public List<Object[]> getCountOfTicketsByStatuses() {
        return countOfTicketsByStatuses;
    }

    public void setCountOfTicketsByStatuses(List<Object[]> countOfTicketsByStatuses) {
        this.countOfTicketsByStatuses = countOfTicketsByStatuses;
    }

    public List<Object[]> getCountOfTicketsByHeaders() {
        return countOfTicketsByHeaders;
    }

    public void setCountOfTicketsByHeaders(List<Object[]> countOfTicketsByHeaders) {
        this.countOfTicketsByHeaders = countOfTicketsByHeaders;
    }

    public List<Object[]> getCountOfTicketsByResults() {
        return countOfTicketsByResults;
    }

    public void setCountOfTicketsByResults(List<Object[]> countOfTicketsByResults) {
        this.countOfTicketsByResults = countOfTicketsByResults;
    }

    public List<Object[]> getCountOfTicketsByProjects() {
        return countOfTicketsByProjects;
    }

    public void setCountOfTicketsByProjects(List<Object[]> countOfTicketsByProjects) {
        this.countOfTicketsByProjects = countOfTicketsByProjects;
    }

    public List<Object[]> getCountOfTicketsByBranches() {
        return countOfTicketsByBranches;
    }

    public void setCountOfTicketsByBranches(List<Object[]> countOfTicketsByBranches) {
        this.countOfTicketsByBranches = countOfTicketsByBranches;
    }

    public List<Object[]> getCountOfTicketsByStatusesBetweenDates() {
        return countOfTicketsByStatusesBetweenDates;
    }

    public void setCountOfTicketsByStatusesBetweenDates(List<Object[]> countOfTicketsByStatusesBetweenDates) {
        this.countOfTicketsByStatusesBetweenDates = countOfTicketsByStatusesBetweenDates;
    }
}
