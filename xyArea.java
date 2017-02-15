import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;
import java.io.File;

public class xyArea {
    public static void main(String[] args) {
        // Create a simple pie chart
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("A", new Integer(75));
        pieDataset.setValue("B", new Integer(10));
        pieDataset.setValue("C", new Integer(10));
        pieDataset.setValue("D", new Integer(5));
        JFreeChart chart = ChartFactory.createPieChart("Stelios",pieDataset,true,true,false );
	// Title
	// Dataset
	// Show legend
	// Use tooltips
	// Configure chart to generate URLs?
        try {
            ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
} }
}

//setenv CLASSPATH /Users/stelios/Desktop/core/jfreechart.jar

