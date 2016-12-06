import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.*;


// The class for the cloud manager (localbroker)
class Cloud extends Sim_entity {
        private Sim_port in, out;
        private double delay, d, totalDelay;
        Sim_stat stat;
        String spec,n,text, a,b,f,g, form=null;
        public int count, i=0;
        
        Cloud(String name, double delay, String specification) {
          super(name);
          this.delay = delay;
          d = delay;
          spec = specification;
          n = name;
          // Port for receiving events from the metabroker
          in = new Sim_port("In");add_port(in);
          // Port for sending events to local resources (datacenter)
          out = new Sim_port("Out");add_port(out);
    
		  //Statistics
		  stat = new Sim_stat();
          stat.add_measure(Sim_stat.THROUGHPUT);
          stat.add_measure(Sim_stat.RESIDENCE_TIME);
          set_stat(stat);
        }

        public void body() {
			printText pt = new printText();
			Object obj = null;
			Splitter splitter = new Splitter();
				while (Sim_system.running()) {
					Sim_event e = new Sim_event();
					sim_get_next(e);
                    sim_process(delay);
					obj = e.get_data();
						if(obj!=null){
						a = obj.toString();
						splitter.setString(a);
						a = splitter.getDelay();
						b = splitter.getSpec();
						f = splitter.getMid();
						g = splitter.getUsername();
						totalDelay = Double.parseDouble(a)+d;
						
						form=  splitter.getId()+ "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b+ "/"+f + "/"+g;
						// if cloud specification matches the job specifiaction then it forwards the job to datacenter for execution
							if(b.equals(spec)){
								sim_completed(e);
								sim_schedule(out, 0.0, 5,(String)form);
								text = "3: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" is now in "+ n + " ,for "+ d+ "ms. scheduled in datacenter "+out.get_dest_ename();
								pt.setPrintString(text); 
								pt.getString();
							// else job returned back to metabroker for distribution
							}else{
            					count++;
            					form=  splitter.getId()+ "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b+ "/"+f + "/"+g;
            				        sim_schedule(in, 0.0, 2,(String)form);
							    text = "3: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" does not match specification with "+ n + " is returned back to "+in.get_dest_ename();
								pt.setPrintString(text); 
         						pt.getString();
							}
             			}
       				i++;
          
				}

        }

		public int getCount(){
        	return count;
        }
}
