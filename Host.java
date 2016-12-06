import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.*;

// The class for the Host
class Host extends Sim_entity {
	private Sim_port in, out1, out2;
    private double d, max, count, utilization, count1, time,totalDelay =0, memdistribution, vmq;
    public String n, status, spec, data, text,a,b,f,g, form, getId, getDelay;
    Sim_stat stat;
    public int x,y,ma,ident, mips=0;
	
	Host(String name, double delay, double m, String specification, int id, double vmnum) {
		super(name);
        n=name;
        d = delay;
        max=m;
        spec = specification;
		ident = id;
        status="IDLE";
		vmq=vmnum;
		//ports for receiving jobs from datacenter and send jobs to VMs or Log
        in = new Sim_port("In"); add_port(in);
	out1 = new Sim_port("Out1"); add_port(out1);
		// Statistics
        stat = new Sim_stat();
        stat.add_measure(Sim_stat.UTILISATION);
        set_stat(stat);
	// (host characteristics)		
    }

    public void body() {
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
				totalDelay = Double.parseDouble(a)+d;
				form=  splitter.getId() + "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b + "/"+get_name()+"/"+g;
				//d = d + e.event_time();
				data =splitter.getId() +"\t"+d+"\t"+n + "\t\t" + splitter.getMetabroker() + "\t"+ b + "\t";
				time = e.event_time();
				sim_process(d);
				//sim_hold(500); // sim hold denotes the VM creation time
				sim_schedule(out1, 0.0, 8,(String)form);
				//sim_schedule(out2, 0.0, 8,(String)form);
				sim_completed(e);
				text = "5: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" is executed in "+ n + " ,for "+ d+ "ms.";// scheduled in host: "+out1.get_dest_ename());
				pt.setPrintString(text); 
				pt.getString();

			    }
          count++;
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
}
      
      
  


