import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.*;
import java.io.*;
import java.lang.*;

import java.util.Iterator;
import java.util.Vector;

// The class for the Host
class Hyper extends Sim_entity {
	private Sim_port in, out1, out2;
    private double d, max, count, utilization, count1, time,totalDelay =0, memdistribution, number_of_vms, vmq, coef, cp, cpuVM;
    public String n, status, spec, data, text,a,b,f,g, form, getId, getDelay,profile, HostList;
    Sim_stat stat;
    public int x,y,ma,ident, mips=0, counter=1, ent_id, jobs,ca, totalCPU=0, totalCores =0,p ,userCPU=0, userCPUCores, users=0 ;
	
	Hyper(String name, double delay, double vmnum, double coresPer, int id, int identity, int j, String hl) {
		super(name);
        n=name;
        d = delay;
        cp = coresPer;
		number_of_vms = vmnum;
		ident = id;
		ent_id = identity;
		jobs = j;
		HostList=hl;
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
    	    users++;
  	printText pt = new printText();	
        Object obj =null;
        Splitter splitter = new Splitter();
		HostCharacteristics hc = null;
		Vector v1 = new Vector();
		Vector v2 = new Vector();
		Vector userCPUVector = new Vector();
		Vector userCPUCoresVector = new Vector();

		Vector v4 = new Vector();
		Vector v5 = new Vector();
		Vector CPUVector = new Vector();
		Vector CPUCoresVector = new Vector();
		Vector MemoryVector = new Vector();
		Vector StorageVector = new Vector();
		Vector BWVector = new Vector();
		
		Vector v7 = new Vector();
		//v7.trimToSize();
		//System.out.println(HostList);
		while (Sim_system.running()) {
			
			Sim_event e = new Sim_event();
			sim_get_next(e);
			obj = e.get_data();
			if(e.scheduled_by()!=-1)
			{

			}
			if(obj!=null){
				a = obj.toString();
				splitter.setString(a);
        			a = splitter.getDelay();
				b = splitter.getSpec();
				f = splitter.getMid();
				g = splitter.getUsername();
				profile = g+".txt";
				OpenProfile op = new OpenProfile(profile);
				
			//	System.out.println(get_name()+": "+hc.getHostName() + " instances " +ca);
			//	System.out.println(get_name());
			//	System.out.println(g);
			//	System.out.println("USER CPU: "+op.getCPU());
			//	System.out.println("HOST CPU: "+hc.getHostCPUSpeed());
			//	System.out.println("-----------------------------");
			//	System.out.println(op.getCPUCores());
			//	System.out.println(op.getMemory());
			//	System.out.println(op.getStorage());
			//	System.out.println(op.getBW());
 				//totalDelay = Double.parseDouble(a)+d;
				form=  splitter.getId() + "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b + "/"+get_name() + "/" +g ;
				//d = d + e.event_time();
				data =splitter.getId() +"\t"+d+"\t"+n + "\t\t" + splitter.getMetabroker() + "\t"+ b + "\t";
				time = e.event_time();
				sim_process(d);
				//sim_hold(500); // sim hold denotes the VM creation time
				coef = 0.75;
				//cpuVM = Double.parseDouble(hc.getHostCPUSpeed()) * (Double.parseDouble(hc.getHostCPUSpeed())* cp) * coef / number_of_vms;
				
				v1.add(e.event_time());
				Iterator it1 = v1.iterator();

				v2.add("VM"+ident+ "."+counter);
				Iterator it2 = v2.iterator();
				
				//v7.insertElementAt(ca,(int)count);
				//Iterator it3 = v7.iterator();
				//v7.setSize(10);
				
				userCPUVector.addElement(op.getCPU());
				userCPUCoresVector.addElement(op.getCPUCores());
				
				totalDelay = Double.parseDouble(a)+d;
				form=  splitter.getId() + "/" + totalDelay + "/" + splitter.getMetabroker()+ "/"+b + "/"+get_name() +"/"+g;
				//sim_schedule(ent_id, 1.3, 99, (String)form);
				//sim_hold(500); // sim hold denotes the VM creation time
				sim_schedule(out1, 0.0, 8,(String)form);
				//sim_schedule(out2, 0.0, 8,(String)form);
				//v3.add(profile);
			    	ca++;
				sim_completed(e);
				text = "5: Job "+ splitter.getId() +" came from "+in.get_src()+" at "+e.event_time()+" is executed in "+ n + " ,for "+ d+ "ms.";// scheduled in host: "+out1.get_dest_ename());
				pt.setPrintString(text); 
				pt.getString();


			    }
	          count++;
		  counter++;
		//  ent_id++;
		//  System.out.println(get_name()+": "+count);
        }
        
        if(ca!=0)
        {
        	p++;

		
        	OpenHostsList ohl = new OpenHostsList(HostList);
		v4 = ohl.getFile();
		OpenProfile op = new OpenProfile(profile);
		//System.out.println(profile + " requests "+ op.getCPU());
		v5.addElement(op.getCPU());
		//System.out.println("v5"+v5);
		for(int i=0;i<v4.size();i++)
		{
		   	obj = v4.get(i);
		   	String name = obj.toString();
		   	OpenHosts oh= new OpenHosts(name);
		   	CPUVector.addElement(oh.getHostCPUSpeed());
		   	CPUCoresVector.addElement(oh.getHostCPUCores());
		   	MemoryVector.addElement(oh.getHostMemory());
		   	StorageVector.addElement(oh.getHostStorage());
		   	BWVector.addElement(oh.getHostBW());
		 }
		

		for(int i=0;i<CPUVector.size();i++)
		{
			obj =CPUVector.get(i);
			int cpu = Integer.parseInt(obj.toString());
			totalCPU = totalCPU + cpu;
			
			obj =CPUCoresVector.get(i);
			int cores = Integer.parseInt(obj.toString()); 
			totalCores = totalCores + cores;
		}
		for(int i=0;i<userCPUVector.size();i++)
		{
			obj =userCPUVector.get(i);
			int ucpu = Integer.parseInt(obj.toString());
			userCPU = userCPU + ucpu;
			
			obj =userCPUCoresVector.get(i);
			int ucpuCores = Integer.parseInt(obj.toString());
			userCPUCores = userCPUCores + ucpuCores;
			
		}
		
		//System.out.println(get_name()+"| Hosts: "+ v4.size() + "-Hosts CPU: "+totalCPU + "- Total CPU Cores : "+totalCores+
		//" VMs: "+ userCPUVector.size() + " -VMs CPU: "+userCPU + "-Total VMs CPU Cores"+userCPUCores);
		//System.out.println("On: "+get_name()+" requests: "+ userCPU+"("+userCPUCores+")"+ "total hosts CPU: "+totalCPU + "|| Created: "+ ca + "VMs remaining: "+(totalCPU-(userCPU)));
		
		obj = v4.size();
		int hostsNumber = Integer.parseInt(obj.toString());
		//hostsNumber
		//totalCPU
		//totalCores
		totalCPU = totalCPU*totalCores;
		obj = userCPUVector.size();
		int VMsNumber = Integer.parseInt(obj.toString());
		//VMsNumber
		//userCPU
		//userCPUCores
		userCPU = userCPU*userCPUCores;
		
		System.out.println("hosts cpu: "+ totalCPU + " VMs cpu: "+userCPU + " available: "+ (totalCPU-userCPU));
	}
	//System.out.println("P: "+p);
        /*	 if (cpuVM<=500){
					System.out.println("Resources are too low in " + get_name() + " for creating VMs");
				}else{
					if(v2.size()<=number_of_vms){
					//System.out.println(f+" "+get_name() +": "+ (count-1) +" events,  VMs created: "+number_of_vms +", cores per VM: " + (Double.parseDouble(hc.getCPUCores())* cp) + ", CPU speed per VM: "+ cpuVM);
					//System.out.println(get_name() +": " +v1.size());
					//System.out.println(get_name() +": " +v1);
					//System.out.println(get_name() +": " +v2.size());
					//System.out.println(get_name() +": " +v2);
					//System.out.println("idle VMs: " + (number_of_vms-v2.size()));
					//System.out.println(cpuVM);
					//System.out.println(cpuVM);
					}else{
						System.out.println("VMs fail due to low VMs number");
					}

				}*/
	}

}
      
      
  


