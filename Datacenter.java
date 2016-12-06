import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.Iterator;
import java.util.Vector;
     
// The class for the processor
class Datacenter extends Sim_entity {
        private Sim_port in, out1, out2, out3;
        public double delay, d, totalDelay, cost;
        public String n, text,a,b,f,g, form, profile=null; 
        Sim_stat stat;
        public int i=0;

        Datacenter(String name, double delay, double c) {
			super(name);
			this.delay=delay;
			d= delay;  
			n=name;
			cost=c;
			// Port for receiving events from cloud (localbroker)
			in = new Sim_port("In"); add_port(in);
			out1 = new Sim_port("Out1"); add_port(out1);
			out2 = new Sim_port("Out2");add_port(out2);
			out3 = new Sim_port("Out3"); add_port(out3);
     
			// Statistics
			stat = new Sim_stat();
			stat.add_measure(Sim_stat.THROUGHPUT);
			stat.add_measure(Sim_stat.RESIDENCE_TIME);
			set_stat(stat);
        }

        public void body() {
        	Vector v = new Vector();
        	printText pt = new printText();
        	Object obj = null;
        	Object obj1 = null;
        	Splitter splitter = new Splitter();
        	
        	
        	//System.out.println("Datacenter energy:"+ac.getEnergyConsumption());
        	//System.out.println("Datacenter cost:"+ac.getTotalCost());

        	while (Sim_system.running()) {
        		Sim_event e = new Sim_event();
			sim_hold(0);
			sim_get_next(e);
			sim_process(d);
			sim_completed(e);
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
				
				totalDelay = Double.parseDouble(a)+d;

				Accounting ac = new Accounting(1000, Double.parseDouble(op.getHours()), cost);
				//System.out.println("For "+ g+" energy consumption is:"+ac.getEnergyConsumption());
				//System.out.println("For " +g+ " cost is:"+ac.getTotalCost());
				
				// Even jobs send to out1 (host1)
							
		//if ((i % 2) == 0) {
			form=  splitter.getId() + "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b + "/"+f + "/" +g;
			sim_schedule(out1, 0.0, 6,(String)form);
			text = "4: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" is now in "+ n + " ,for "+ d+ "ms. scheduled in host: "+40;
			pt.setPrintString(text); 
			pt.getString();
			//Jobs send to scheduler
			//sim_schedule(out3, 0.0, 6,(String)form);
			// Odd jobs go to out2 (host2)
			//} else {
			//	form=  splitter.getId() + "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b + "/"+f + "/" +g;
			//	sim_schedule(out2, 0.0, 6,(String)form);
			////	text = "4: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" is now in "+ n + " ,for "+ d+ "ms. scheduled in host: "+out2.get_dest_ename();
				pt.setPrintString(text); 
				pt.getString();
				//Jobs send to scheduler
				//sim_schedule(out3, 0.0, 6,(String)form);
			//		}
            }
			i++;
          }
       }
}
