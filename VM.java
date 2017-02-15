import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.*;
import java.lang.Math.*;
import java.text.DecimalFormat ;

// The class for the Host
class VM extends Sim_entity {
	private Sim_port in, out;
    private double d, max, count, utilization, count1, time,totalDelay =0,avgExecTime=0, avgDelay=0, avgTotalExecTime;
    public String n, status, spec, data, text,a,b,f, g, form, getId, getDelay,profile;
    Sim_stat stat;
    public int x,y,ma, ident=0;
           Vector dataVector = new Vector();
        Vector vMakespan = new Vector();
        Object[] dt=null;
	VM(String name, double delay, double m, String specification, int id) {
		super(name);
        n=name;
        d = delay;
        max=m;
        spec = specification;
		ident = id;
        status="IDLE";
		//ports for receiving jobs from datacenter and send jobs to VMs or Log
        in = new Sim_port("In"); add_port(in);
		out = new Sim_port("Out"); add_port(out);
		// Statistics
        stat = new Sim_stat();
        stat.add_measure(Sim_stat.UTILISATION);
        set_stat(stat);
    }

    public void body() {
		//System.out.println("VM id:" +get_name()+": " +get_id());
		printText pt = new printText();	
        Object obj =null;
        Splitter splitter = new Splitter();
		while (Sim_system.running()) {
		Sim_event e = new Sim_event();
            sim_get_next(e);			  	  
            obj = e.get_data();
			  if(obj!=null){
			  	a = obj.toString();
				splitter.setString(a);
				a = splitter.getDelay();
				b = splitter.getSpec();
				f = splitter.getMid();
				g = splitter.getUsername();
				profile = g+".txt";
				OpenProfile op = new OpenProfile(profile);
				
				double cpuvm = Integer.parseInt(op.getCPU());
				double cpucoresvm = Integer.parseInt(op.getCPUCores());
				double CPIvm = Integer.parseInt(op.getCPI());
				double instructionsvm = Integer.parseInt(op.getInstructions());
				
				//System.out.println(cpuvm + " "+ cpucoresvm + " "+ CPIvm + " "+ instructionsvm);
				
				double res = instructionsvm*CPIvm/(cpucoresvm*cpuvm*100000);
				avgDelay = e.event_time()+avgDelay;
				avgExecTime = roundTwoDecimals(res) +avgExecTime;
				avgTotalExecTime = roundTwoDecimals((e.event_time()+res))+avgTotalExecTime;
				vMakespan.addElement(roundTwoDecimals((e.event_time()+res)));
				
				totalDelay = Double.parseDouble(a)+d;
				form= splitter.getId() + "/" + totalDelay+ splitter.getMetabroker()+ "/"+b+"/"+n + "/"+f + "/"+g;
				sim_process(d);
				data =splitter.getId() +"\t"+e.event_time()+"\t"+n+"_"+splitter.getId() + "\t" + splitter.getMetabroker() + "\t"+ b + "\t" + g + "\t"+roundTwoDecimals(res) +"\t"+roundTwoDecimals((e.event_time()+res)) ;
				sim_schedule(out, 0.0, 9,(String)form);
				sim_completed(e);
				text = "5: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" is executed in "+ n + " ,for "+ d+ "ms.";// scheduled in host: "+out1.get_dest_ename());
				pt.setPrintString(text); 
				pt.getString();
				System.out.println(data);
				dataVector.addElement(data);
				g = splitter.getMetabroker();
				}
          count++;
        }
        if(count!=1)
        {
        CreateResults cr = new CreateResults("res"+get_name(),dataVector, (count-1), avgDelay, avgExecTime,avgTotalExecTime );
        }
        dt = vMakespan.toArray();
        if(g!=null){
        DrawVMsMakespan dmakespan = new DrawVMsMakespan(dt,g);
        //System.out.println(g);
        }
    }
    public double getUtilization(){
        	utilization = count/max;
        	return utilization;
    }
    public double getEventCount(){
        	return count;
    }
    public double getTotalTime(){
        	return time;
    }
    public String getData(){
        	return data;
    }
    public double roundTwoDecimals(double d) {
            DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
    public Vector getDataVector(){
        	return dataVector;
    }
}
      
      
  


