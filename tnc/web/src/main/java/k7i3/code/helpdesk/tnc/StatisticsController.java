package k7i3.code.helpdesk.tnc;

import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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

    private PieChartModel pieModel;
    private BarChartModel barModel;
    private MeterGaugeChartModel meterGaugeModel;

    //Init

    @PostConstruct
    public void init() {
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
}
