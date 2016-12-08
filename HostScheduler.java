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

import java.util.Iterator;
import java.util.Vector;

// A bucket, simply keeps a count of how many messages it recieves
class HostScheduler extends Sim_entity {
  int count = 0;
  Sim_port in;
  String a,b,f,totalDelay,form,data = null;

  public HostScheduler(String name) {
  	  super(name);
  	  count = 0;
  	  in = new Sim_port("In"); add_port(in);
  }
  public void body() {
	Splitter splitter = new Splitter();
	Object obj = null;

	Vector v = new Vector();
	while (Sim_system.running()) {
	Sim_event e = new Sim_event();
	sim_get_next(e);
	obj = e.get_data();
	if(obj!=null){
		a = obj.toString();
		splitter.setString(a);
		// System.out.println("Job id in "+ n+" from "+e.get_src() +" "+ splitter.getId());
		// System.out.println("Delay in "+ n+" from "+e.get_src() +" "+ splitter.getDelay()); 
		a = splitter.getDelay();
		b = splitter.getSpec();
		f = splitter.getMid();
		v.add(e.event_time());
		Iterator it = v.iterator();
	}
      count++;
    }
	
    count = count-1;
    //System.out.println(get_name() +": "+ v);
	
  }	
}
