import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.*;

// The class for the Host
public class Accounting{
	double watts, userhours, costkwph, energy, totalcost;
    
	public Accounting(double w, double uh, double ckwph) {
		energy = w * uh /1000;
		totalcost = energy * ckwph;
	}
    public double getEnergyConsumption(){
        	return energy;
    }
    public double getTotalCost(){
        	return totalcost;
    }
}
      
      
  


