     
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.data.*;
//import java.util.*;
import java.lang.*;
import java.io.*;

import java.io.File;

public class DrawVMsMakespan {
	Object[] newO=null;
	String name=null;
	public DrawVMsMakespan()
	{
		newO = null;
	}
	public DrawVMsMakespan(Object[] o, String n){
	newO=o;
	name = n;
	int size = newO.length;
	//Vector v = (Vector)newO;

	
	
        // Create a simple XY chart
        XYSeries series = new XYSeries("XYGraph");
        
	for(int i =0; i<size;i++){
	    //System.out.println(newO[i]);
	    double na = Double.parseDouble(newO[i].toString());
	    series.add(i, na);
	}
        
        // Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart(
            "VM makespan for "+name,
            "Makespan",
            "VMs",
            dataset,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
        try {
            ChartUtilities.saveChartAsJPEG(new File("Charts/chart"+name+".jpg"), chart, 500, 300);
        } catch (Exception e) {
            System.err.println("Problem occurred creating chart.");
} }
}  



//setenv CLASSPATH /Users/stelios/Desktop/core/jfreechart.jar

