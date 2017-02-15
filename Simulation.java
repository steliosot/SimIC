import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.Sim_event;

// The simulation class
public class Simulation {
	public static double user_delay =10;
	public static double metabroker_delay=10;
	public static double cloud_delay =10;
	public static double datacenter_delay=10;
    
	public static double host1_delay=10;
	public static double host2_delay=10;
     
	public static double vm1_delay=10;
	public static double vm2_delay=10;

	public static double metabroker_hold1=0;
	public static double metabroker_hold2=5;

	// public static double totaldelay = 0;
	// totaldelay== sd+cd+sid+did+d2d;
        
	public static int jobs=5;	
	public static int number_of_clouds=4;
	public static int number_of_users=3;

	public static double number_of_vms=10;
	public static double cores_percentage=0.25;

	public static void main(String[] args) {
	// Initialise Sim_system

        Sim_system.initialise();
        VM vm = null;
	// Add the user source
	User user1 = new User("User1", user_delay, jobs,"spec_4",1, "UserHDCharacteristicsFile1.txt","UserSWCharacteristicsFile1.txt","Cloud1");
        User user2 = new User("User2", user_delay, jobs,"spec_1" ,2,"UserHDCharacteristicsFile2.txt","UserSWCharacteristicsFile2.txt","Cloud2");
        User user3 = new User("User3", user_delay, jobs,"spec_2" ,2,"UserHDCharacteristicsFile3.txt","UserSWCharacteristicsFile3.txt","Cloud2");
	User user4 = new User("User4", user_delay, jobs,"spec_3" ,2,"UserHDCharacteristicsFile4.txt","UserSWCharacteristicsFile4.txt","Cloud3");

        // Add the whole cloud facility
	for(int i=1;i<=number_of_clouds;i++){	
		Metabroker metabroker = new Metabroker("Metabroker"+i, metabroker_delay, jobs, true, metabroker_hold1, 1,2);
		Cloud cloud = new Cloud("Cloud"+i, cloud_delay, ("spec_"+i));
		Datacenter datacenter = new Datacenter("Datacenter"+i, datacenter_delay, 0.160);
		Host host1 = new Host("HostList"+i , host1_delay,jobs, "spec_a",i,2);
		Hyper hyper1 = new Hyper("Hyper"+i, 2, number_of_vms, 0.75,1,7,jobs,"HostList"+i);
		vm = new VM("VM_cloud"+i , vm1_delay,jobs, "spec_a",i);
	}
        // Add the bucket, log and scheduler

	
	//Hyper hyper2 = new Hyper("Hyper2", 2, number_of_vms, 0.75,2,22,jobs);

	Bucket bucket = new Bucket("Bucket");
	Bucket log = new Bucket("Log");
	HostScheduler hostscheduler = new HostScheduler("HostScheduler");
	VMScheduler vmscheduler = new VMScheduler("VMScheduler");
        
	//Link ports among entities
	Sim_system.link_ports("User1", "Out", "Metabroker1", "In");
	Sim_system.link_ports("User2", "Out", "Metabroker1", "In");
	Sim_system.link_ports("User3", "Out", "Metabroker4", "In");
	Sim_system.link_ports("User4", "Out", "Metabroker3", "In");

        for(int i=1;i<=number_of_clouds;i++){
        	// Link the entities' ports
        	Sim_system.link_ports("Metabroker"+i, "Out1", "Cloud"+i,"In");
		Sim_system.link_ports("Metabroker"+i, "Out3", "Bucket","In");
		Sim_system.link_ports("Cloud"+i, "Out", "Datacenter"+i, "In");
		Sim_system.link_ports("Datacenter"+i, "Out1", "Hyper"+i, "In");
		Sim_system.link_ports("Datacenter"+i, "Out2", "Bucket", "In");
		Sim_system.link_ports("Datacenter"+i, "Out3", "HostScheduler", "In");
		Sim_system.link_ports("Hyper"+i, "Out1", "HostList"+i, "In");
		Sim_system.link_ports("HostList"+i, "Out1", "VM_cloud"+i , "In");
		Sim_system.link_ports("VM_cloud"+i, "Out", "VMScheduler", "In");
		
		if(i<number_of_clouds){		
			      Sim_system.link_ports("Metabroker"+i, "Out2", "Metabroker"+(i+1), "In");
		}
	        if(i==number_of_clouds){	
			  Sim_system.link_ports("Metabroker"+i, "Out2", "Metabroker1", "In");
			  Sim_system.link_ports("Metabroker"+i, "Out3", "Bucket", "In");
		}
	}
	Sim_system.run();
	System.out.println("Total simulation clock: "+Sim_system.sim_clock());
		OverallResults or = new OverallResults("resVM_cloud1.txt","resVM_cloud2.txt","OverallResults");

        }
      }
