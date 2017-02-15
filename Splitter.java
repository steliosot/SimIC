public class Splitter 
{
        public String a,b,c,d,e,f,g;
        int delay,i;

        // Constructor to initialize balance
        public Splitter(String in)
	{
		a = in;
	}

       // Overloaded constructor for empty balance
        public Splitter()
	{
		a = null;
		b = null;
		c = null;
		d = null;
		e = null;
		f = null;
		g = null;
		delay = 0;
		i = 0 ;
	}
	public void setString(String po)
	{
	   a = po;
	   String[] temp;
           String del ="/";
           temp = a.split(del);
           for(int ii =0; ii < temp.length ; ii++){
            //   System.out.println(temp[ii]);
              	
              	if(temp.length==6)
              	{
			b=temp[0]; 
              		c=temp[1];
              		d=temp[2];
              		e=temp[3];
			f=temp[4];
			g=temp[5];

             	}
           }
        }
	
			
	public String getId()
	{
		return b;
	}
	public String getDelay()
	{
		return c;
	}
	public String getMetabroker()
	{
		return d;
	}
	public String getSpec()
	{
		return e;
	}
	public String getMid()
	{
		return f;
	}
	public String getUsername()
	{
		return g;
	}
}  


