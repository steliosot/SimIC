import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.io.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Iterator;
import java.util.Vector;

public class CreateResults{
	
	FileOutputStream outputFile = null;
	//tring content = "This is the text content";
	PrintStream p;
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();		
	public CreateResults(String filename, Vector data, double count, double avgDelay, double avgExecTime, double avgTotalExecTime){
	   try {
	    	outputFile = new FileOutputStream("Results/"+filename+".txt");
	    	p = new PrintStream(outputFile);
	    	p.println(date);
	    	p.println();
	    	p.println("Average_delay: "+avgDelay/count);
	    	p.println("Average_execution_time: "+avgExecTime/count);
	    	p.println("Average_total_execution_time: "+avgTotalExecTime/count);
	    	p.println();
	      	p.println("Event id (user group-event) | Delay | VM | Metabroker | SLA | User | Execution time | Total Execution Time");
	   for(int i=0;i<data.size();i++)
	   {
	   	p.println(data.get(i));
	   }

	   } catch (FileNotFoundException e) {
	   	    e.printStackTrace(System.err);
	   }
		
}
}
