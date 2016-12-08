import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import eduni.simjava.distributions.Sim_negexp_obj;
import eduni.simanim.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import eduni.simanim.*;
import eduni.simjava.*;

// A bucket, simply keeps a count of how many messages it recieves
class Hypervisor extends Sim_entity {
  int count = 0;
  Sim_port in;
  String a,b,f,totalDelay,form,data = null;
  double memdistribution, number_of_vms, cpuVM, coresPersentage, coef;
  HostCharacteristics hc = null;
  
  public Hypervisor(String name, double num_of_vms, double coresPer) {
    super(name);
    count = 0;
	number_of_vms = num_of_vms;
	coresPersentage = coresPer;
    in = new Sim_port("In"); add_port(in);
 //   add_param(new Anim_param("Count", Anim_param.NAME_VALUE, "0", -10, -5));
  }
  public void body() {
	  
	  //VMCharacteristics vmc = new VMCharacteristics("VMCharacteristicsFile.txt");
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
				hc = new HostCharacteristics("HostList/"+f);
		  }
      count++;
    }
    count = count-1;
	coef = 0.75;
	cpuVM = Double.parseDouble(hc.getCPUSpeed()) * (Double.parseDouble(hc.getCPUCores())* coresPersentage) * coef / number_of_vms;
	if (cpuVM<=500)
	{
		System.out.println("Resources are too low in " + get_name() + " for creating VMs");
	}else{
	System.out.println(f+" "+get_name() +": "+ count +" events,  VMs created: "+number_of_vms +", cores per VM: " + (Double.parseDouble(hc.getCPUCores())* coresPersentage) + ", CPU speed per VM: "+ cpuVM);
	}
  }
}
