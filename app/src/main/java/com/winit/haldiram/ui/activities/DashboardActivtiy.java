package com.winit.haldiram.ui.activities;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.winit.haldiram.R;
import com.winit.haldiram.adapter.DashboardAdapter;
import com.winit.haldiram.dataobject.DashboardDO;
import com.winit.haldiram.dataobject.MonthlyDO;
import com.winit.haldiram.module.dashboard.DashboardPresenter;
import com.winit.haldiram.module.dashboard.IDashboardPresenter;
import com.winit.haldiram.module.dashboard.IDashboardView;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class DashboardActivtiy extends BaseActivity implements IDashboardView {

    @Nullable
    @Bind(R.id.rvDashboard)
    RecyclerView rvDashboard;

//    @Nullable
//    @Bind(R.id.graph)
//    GraphView graph;

    @Nullable
    @Bind(R.id.ll_chart)
    LinearLayout llChart;

//    @Nullable
//    @Bind(R.id.web_view)
//    WebView webView;

    private IDashboardPresenter iDashboardPresenter;
    private DashboardAdapter dashboardAdapter;
//    private LineGraphSeries<DataPoint> series2017,series2016,series2015;

    @Override
    protected void initialize() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inflater.inflate(R.layout.dashboard_activity, flBody, true);
        ButterKnife.bind(this);
        iDashboardPresenter = new DashboardPresenter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DashboardActivtiy.this);
        rvDashboard.setLayoutManager(mLayoutManager);
        rvDashboard.setItemAnimator(new DefaultItemAnimator());

        rvDashboard.setAdapter(dashboardAdapter = new DashboardAdapter(DashboardActivtiy.this,null));

//        series2017 = new LineGraphSeries<DataPoint>();
//        series2016 = new LineGraphSeries<DataPoint>();
//        series2015 = new LineGraphSeries<DataPoint>();
//
//        series2017.setColor(Color.GREEN);
//        series2016.setColor(Color.BLUE);
//        series2015.setColor(Color.RED);
//
//        series2017.setThickness(6);
//        series2016.setThickness(4);
//        series2015.setThickness(2);
//
//        graph.addSeries(series2017);
//        graph.addSeries(series2016);
//        graph.addSeries(series2015);
//
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
////        graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(3);
////        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(3);
//        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//
//
//        // customize a little bit viewport
//        Viewport viewport = graph.getViewport();
//        viewport.setYAxisBoundsManual(true);
//        viewport.setMinY(0);
//        viewport.setMaxY(500000);
//        viewport.setMinX(0);
//        viewport.setMaxX(12);
//        viewport.setScrollable(true);
//        viewport.setScalable(true);//giving error

        iDashboardPresenter.getAchieved();
    }

    @Override
    public void onBackPressed() {
        showCustomDialog(DashboardActivtiy.this, getResources().getString(R.string.warning),
                getResources().getString(R.string.do_you_want_to_logout), getResources().getString(R.string.Yes), getResources().getString(R.string.No), "logout");
    }

    @Override
    protected void setTypeFace() {

    }

    private String getLineGraph(HashMap<String,ArrayList<MonthlyDO>> hmSales)
    {
        String[] month = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        //strXML will be used to store the entire XML document generated
        String strXML = "<chart caption=\"Sales - year wise\" numberprefix=\"₹\" plotgradientcolor=\"\" bgcolor=\"FFFFFF\" " +
                "showalternatehgridcolor=\"0\" divlinecolor=\"CCCCCC\" showvalues=\"0\" showcanvasborder=\"0\" " +
                "canvasborderalpha=\"0\" canvasbordercolor=\"CCCCCC\" canvasborderthickness=\"1\" " +
                "yaxismaxvalue=\"300000\" captionpadding=\"30\" linethickness=\"3\" yaxisvaluespadding=\"15\" " +
                "legendshadow=\"0\" legendborderalpha=\"0\" " +
                "palettecolors=\"#f8bd19,#008ee4,#33bdda,#e44a00,#6baa01,#583e78\" showborder=\"0\">";

        strXML += "<categories>";
        for(int i=0;i<12;i++){
            strXML += "<category label=\""+month[i]+"\" stepskipped=\"false\" labeltooltext=\"\" />";
        }
        strXML += "</categories>";
        Set<String> keySet = hmSales.keySet();
        for(String year : keySet){
            strXML += "<dataset seriesname=\""+year+"\">";
            ArrayList<MonthlyDO> arrSales = hmSales.get(year);
            for(MonthlyDO monthlyDO : arrSales){
                strXML +="<set value=\""+monthlyDO.sales+"\" />";
            }
            strXML += "</dataset>";
        }
        strXML += "</chart>";

        String strHtml = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + " <head>\n"
//                + " <script language=\"JavaScript\" src=\"file:///android_asset/FusionCharts.js\"></script>\n"
                + " </head> \n"
                + "<body>\n"
                + "<p>&nbsp;</p>\n"
                + "<div id=\"chartdiv\" align=\"center\"> \n"
                + " FusionCharts. </div>\n"
                +"<script type=\"text/javascript\">\n"
                + "var chart = new FusionCharts(\"file:///android_asset/FCF_MSLine.swf\", \"ChartId\", \"340\", \"250\", \"0\", \"0\");\n"
                + "chart.setXMLData(\" "
                + strXML
                + " \");"
                +"\t\t chart.render(\"chartdiv\");\n"
                + "\t\t</script>\n"
                + " </body>\n" + " </html>\n" + "";

        return strHtml;
    }

    @Override
    public void onAchieved(List<DashboardDO> dashboardDOs,HashMap<String,ArrayList<MonthlyDO>> hmSales) {
        dashboardAdapter.refresh(dashboardDOs);
        openChart(hmSales);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadDataWithBaseURL(null, getLineGraph(hmSales), "text/html", "utf8", null);
//        Set<String> keySet = hmSales.keySet();
//        for(String key : keySet){
//            if(key.equalsIgnoreCase("2017")){
//                ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(key);
//                if(arrMonthlySales == null)
//                    arrMonthlySales = new ArrayList<>();
//                for(MonthlyDO monthlyDO : arrMonthlySales){
//                    series2017.appendData(new DataPoint(monthlyDO.month, monthlyDO.sales), true, 11);
//                }
//            }
//            else if(key.equalsIgnoreCase("2016")){
//                ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(key);
//                if(arrMonthlySales == null)
//                    arrMonthlySales = new ArrayList<>();
//                for(MonthlyDO monthlyDO : arrMonthlySales){
//                    series2016.appendData(new DataPoint(monthlyDO.month, monthlyDO.sales), true, 11);
//                }
//            }
//            else if(key.equalsIgnoreCase("2015")){
//                ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(key);
//                if(arrMonthlySales == null)
//                    arrMonthlySales = new ArrayList<>();
//                for(MonthlyDO monthlyDO : arrMonthlySales){
//                    series2015.appendData(new DataPoint(monthlyDO.month, monthlyDO.sales), true, 11);
//                }
//            }
//        }
    }

    private void openChart(HashMap<String,ArrayList<MonthlyDO>> hmSales){
        String[] month = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
// Creating an XYSeries for Income
        XYSeries series2017 = new XYSeries("2017");
        XYSeries series2016 = new XYSeries("2016");
        XYSeries series2015 = new XYSeries("2015");
// Adding data to Income and Expense Series
        Set<String> keySet = hmSales.keySet();
        for(String key : keySet){
            if(key.equalsIgnoreCase("2017")){
                ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(key);
                if(arrMonthlySales == null)
                    arrMonthlySales = new ArrayList<>();
                for(MonthlyDO monthlyDO : arrMonthlySales){
                    series2017.add(monthlyDO.month-1, monthlyDO.sales);
                }
            }
            else if(key.equalsIgnoreCase("2016")){
                ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(key);
                if(arrMonthlySales == null)
                    arrMonthlySales = new ArrayList<>();
                for(MonthlyDO monthlyDO : arrMonthlySales){
                    series2016.add(monthlyDO.month-1, monthlyDO.sales);
                }
            }
            else if(key.equalsIgnoreCase("2015")){
                ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(key);
                if(arrMonthlySales == null)
                    arrMonthlySales = new ArrayList<>();
                for(MonthlyDO monthlyDO : arrMonthlySales){
                    series2015.add(monthlyDO.month-1, monthlyDO.sales);
                }
            }
        }
// Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series2017);
        dataset.addSeries(series2016);
        dataset.addSeries(series2015);

// Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer renderer2017 = new XYSeriesRenderer();
        renderer2017.setColor(Color.GREEN); //color of the graph set to cyan
        renderer2017.setFillPoints(true);
        renderer2017.setLineWidth(2f);
        renderer2017.setDisplayChartValues(true);
        renderer2017.setChartValuesTextSize(20f);
//setting chart value distance
//        renderer2017.setDisplayChartValuesDistance(10);
//setting line graph point style to circle
        renderer2017.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        renderer2017.setStroke(BasicStroke.SOLID);

// Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer renderer2016 = new XYSeriesRenderer();
        renderer2016.setColor(Color.BLUE);
        renderer2016.setFillPoints(true);
        renderer2016.setLineWidth(2f);
        renderer2016.setDisplayChartValues(true);
        renderer2016.setPointStyle(PointStyle.CIRCLE);
        renderer2016.setStroke(BasicStroke.SOLID);
        renderer2016.setChartValuesTextSize(20f);

        XYSeriesRenderer renderer2015 = new XYSeriesRenderer();
        renderer2015.setColor(Color.RED);
        renderer2015.setFillPoints(true);
        renderer2015.setLineWidth(2f);
        renderer2015.setDisplayChartValues(true);
        renderer2015.setPointStyle(PointStyle.CIRCLE);
        renderer2015.setStroke(BasicStroke.SOLID);
        renderer2016.setChartValuesTextSize(20f);

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
//        multiRenderer.setChartTitle("Sales Chart");
        multiRenderer.setXTitle("Month");
        multiRenderer.setYTitle("Achieved in Rs.");
/***
 104
 * Customizing graphs
 105
 */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(true);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(true, true);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(true, true);
//setting lines to display on y axis
        multiRenderer.setShowGridY(true);
//setting lines to display on x axis
        multiRenderer.setShowGridX(true);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(true);
//setting zoom to false
        multiRenderer.setZoomEnabled(true);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(true);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(true);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setYLabels(10);
// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(300000);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(11);
//setting bar size or space between two bars
//multiRenderer.setBarSpacing(0.5);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.gray_light));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(2f);
//setting x axis point size
        multiRenderer.setPointSize(4f);
//        multiRenderer.setLegendTextSize(25f);//2017-2016-2015
//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 110, 30, 30});

        for(int i=0; i< 12;i++){
            multiRenderer.addXTextLabel(i, month[i]);
        }
// Adding renderer2017 and renderer2016 to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
        multiRenderer.addSeriesRenderer(renderer2017);
        multiRenderer.addSeriesRenderer(renderer2016);
        multiRenderer.addSeriesRenderer(renderer2015);
//this part is used to display graph on the xml
//        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
//remove any views before u paint the chart
        llChart.removeAllViews();
//drawing bar chart
        View mChart = ChartFactory.getLineChartView(DashboardActivtiy.this, dataset, multiRenderer);
//adding the view to the linearlayout
        llChart.addView(mChart);
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
