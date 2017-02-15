public class SplitterSpace 
{
        public String a,b,c,d,e,f;
        int delay,i;

        // Constructor to initialize balance
        public SplitterSpace(String in)
	{
		a = in;
	}

       // Overloaded constructor for empty balance
        public SplitterSpace()
	{
		a = null;
		b = null;
		c = null;
		d = null;
		e = null;
		f = null;
		delay = 0;
		i = 0 ;
	}
	public void setString(String po)
	{
	   a = po;
	   String[] temp;
           String del =" ";
           temp = a.split(del);
           for(int ii =0; ii < temp.length ; ii++){
            //   System.out.println(temp[ii]);
              	
              	if(temp.length==2)
              	{
			b=temp[0]; 
              		c=temp[1];
             	}
           }
    }
	
			
	public String getText()
	{
		return b;
	}
	public String getValue()
	{
		return c;
	}
	
}  


