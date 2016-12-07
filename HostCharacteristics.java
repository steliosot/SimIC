import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.*;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;

// The class for the Host
public class HostCharacteristics{
	int r, s, b, cr, cpi;
	String n, v1, v2, v3, v4, v5, v6, v7, v8, v9;
	String f;
	Vector v = new Vector();
	int vsize;

	public HostCharacteristics(String file) {
	
	 // file = "HostCharacteristicsFile.txt";
	  try{
		  // Open the file that is the first 
		  // command line parameter
		  FileInputStream fstream = new FileInputStream(file);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  //Read File Line By Line
		  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  v.add(strLine);
			  Iterator it = v.iterator();
			  }
		  //Close the input stream
		  in.close();
		  vsize = v.size();

		  v1 = (String)v.get(0); // HostName
		  v2 = (String)v.get(1); // HostOS
		  v3 = (String)v.get(2); // Platform
		  v4 = (String)v.get(3); // Memory
		  v5 = (String)v.get(4); // CPU Cores
		  v6 = (String)v.get(5); // CPU-Speed
		  v7 = (String)v.get(6); // H/D-Controller
		  v8 = (String)v.get(7); // Storage-H/D
		  v9 = (String)v.get(8); // BW

		  SplitterSpace ss1 = new SplitterSpace(v1); 
		  ss1.setString(v1);
		  v1 = ss1.getValue();	
		 
		  SplitterSpace ss2 = new SplitterSpace(v2); 
		  ss2.setString(v2);
		  v2 = ss2.getValue();	

		  SplitterSpace ss3 = new SplitterSpace(v3); 
		  ss3.setString(v3);
		  v3 = ss3.getValue();	
		  
		  SplitterSpace ss4 = new SplitterSpace(v4); 
		  ss4.setString(v4);
		  v4 = ss4.getValue();	

		  SplitterSpace ss5 = new SplitterSpace(v5); 
		  ss5.setString(v5);
		  v5 = ss5.getValue();	

		  SplitterSpace ss6 = new SplitterSpace(v6); 
		  ss6.setString(v6);
		  v6= ss6.getValue();	

		  SplitterSpace ss7 = new SplitterSpace(v7); 
		  ss7.setString(v7);
		  v7 = ss7.getValue();	

		  SplitterSpace ss8 = new SplitterSpace(v8); 
		  ss8.setString(v8);
		  v8= ss8.getValue();	

		  SplitterSpace ss9 = new SplitterSpace(v9); 
		  ss9.setString(v9);
		  v9 = ss9.getValue();


	  }catch (Exception e){//Catch exception if any
		  System.err.println("Error1: " + e.getMessage());
	  }
	}
	
	public Vector getFile(){
		return v;
	}
	public int getFileSize(){
		return vsize;
	}
	public String getHostName(){
		return v1;
	}
	public String getHostOS(){
		return v2;
	}
	public String getHostPlatform(){
		return v3;
	}
	public String getHostMemory(){
		return v4;
	}
	public String getHostCPUCores(){
		return v5;
	}
	public String getHostCPUSpeed(){
		return v6;
	}
	public String getHostHDController(){
		return v7;
	}
	public String getHostStorage(){
		return v8;
	}
	public String getHostBW(){
		return v9;
	}
}
      
      
  


