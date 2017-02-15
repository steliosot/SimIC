import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_stat;
import java.util.*;

// The class for the Host
public class ServiceCharacteristics{
	int c, i, cl, mips, cpi;
	String n;
    
	public ServiceCharacteristics(int cpi, int clock) {

	   //cpi = cycles/instructions;
	   mips = clock/cpi*10^6;
	}
	
    public int getMips(){
        	return mips;
    }
}
      
      
  


