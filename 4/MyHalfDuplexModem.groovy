/***************************************************/
/************Custom Modem with Energy Model V-1.1***/
/*************Author:Sourabh M Zambre*************/
/************N.I.T.K. Surathkal*********************/

import org.arl.fjage.Message
import org.arl.unet.sim.HalfDuplexModem
import org.arl.unet.phy.*

import org.arl.fjage.*
import org.arl.unet.*
import java.math.*
import Java.util.*
import org.arl.unet.sim.*

class MyHalfDuplexModem extends HalfDuplexModem {

    int neighbor,addr
    float neighbor_distance;
    def ranging
    def  init_energy = 10
    def dist
    def pct = 0
    def pcr = 0
    def sdata,rdata
    def depth
    def C = 1.3312e-9       // empirical constant
    def s_tot_bits=0,r_tot_bits=0
    def fr = 10             //carrier freq.(Khz)
    def d = 0.036*Math.pow(fr,1.5)    //Thorp's constant
    static def source,desti

    Date date = new Date();
    //This method returns the time in millis
    long timeMilli,init_time
    

    static HashMap<Integer, Integer[]> map = new HashMap<>(); 
    TxFrameReq req 

    
   @Override
   protected void setup() {
	super.setup()
	register(Services.PHYSICAL);
    	register(Services.DATAGRAM);
   }
   
   public void startup() {

	super.startup()
      //def phy = agentForService(Services.PHYSICAL);
      //subscribe (topic(phy));

      //ranging = agentForService Services.RANGING;
      //subscribe topic(ranging);

      def nodeInfo = agentForService Services.NODE_INFO;
      addr = nodeInfo.address;

     
      depth = nodeInfo.location[2]        //fetching depth of the current node in meters
      map.put(addr, nodeInfo.location); 
        init_time = date.getTime();
        //System.out.println 'Initial time :\t'+init_time
      }

  @Override
  Message processRequest(Message msg) {		
	super.processRequest(msg)
	if (msg instanceof DatagramReq) {
		req = new TxFrameReq((DatagramReq)msg);
		sdata=msg.getData();
	return new Message(msg, Performative.AGREE) 
	}
 return null
}

/*  @Override
  void processMessage(Message msg) {
	//super.processMessage(msg)
	System.out.println "hello"
	if (msg instanceof DatagramNtf && msg.protocol == REQ_PROTOCOL){
      		send new DatagramReq(recipient: msg.sender,to: msg.from, protocol: Protocol.DATA)
	}
}*/


  @Override
  boolean send(Message msg) {

    
    if (msg instanceof TxFrameNtf) 
	{
      
	   pct++
	   source=addr
	   desti=req.getTo()
           //data = msg.getData()
           //int[] loc1 = map.get(source)
	   ////System.out.println("Destination:"+req.getTo())
	   int[] loc1 = map.get(addr)			
           int[] loc2 = map.get(req.getTo())
           def x = loc1[0] - loc2[0]
           def y = loc1[1] - loc2[1]
	   def z = Math.abs(loc1[2]-loc2[2])
           def distance = Math.sqrt((x)*(x) +(y)*(y) +(z)*(z));
           def bits=32
           s_tot_bits = bits*sdata.size()
           
           ////System.out.println "\n\tNumber of bits sent :"+s_tot_bits
	   System.out.println "\n\tData sent :"+sdata
	   			
           dist = distance/1000.0      // converting the distance in Km.
           ////System.out.println '\tDepth(Km.) = '+depth*(-0.001)+'\n\tDistance(Km.) = '+dist
           BigDecimal Tx_EG = new BigDecimal("1"); // Or BigInteger.ONE 
          
           Tx_EG = Tx_EG.multiply(BigDecimal.valueOf(s_tot_bits*50e-9+ s_tot_bits*(0.001)*dist*(depth*-0.001)*C*Math.pow(Math.E,d*dist))); 
           init_energy = init_energy - Tx_EG ;
           String value = String.valueOf(Tx_EG.doubleValue());
           ////System.out.println '\n\tTransmission Energy : '+value+" Joules";
           ////System.out.println '\tRemaining Energy : '+(init_energy)+" Joules\n";
          
           //File file = new File("I:\\out.txt")
           //def text = file.getText()
	
	   
          timeMilli = date.getTime();
           System.out.println "ENERGY: -s "+source+" -d "+desti+" -b "+s_tot_bits+" -eT "+init_energy+" -pcT "+pct//+" -c "+msg.getType()
           println "ENERGY: -s "+source+" -d "+desti+" -b "+s_tot_bits+" -eT "+init_energy+" -pcT "+pct//+" -c "+msg.getType()
	 	   

	}  //end of TxFrameNtf if


    if (msg instanceof RxFrameNtf)
	{	
	  ////System.out.println("src:"+source+"desti:"+desti) 
	  ////System.out.println("from:"+msg.getFrom()+"to:"+msg.getTo()) 
	    pcr++
	   	rdata = msg.getData()       // getting data
           	////System.out.println "\tData is :"+rdata
           	def bits=32
           	r_tot_bits = bits*rdata.size()      //caculating total number of bits
           	////System.out.println "\tNumber of bits received :"+r_tot_bits
    	   
           	BigDecimal Rx_EG = new BigDecimal("1"); // Or BigInteger.ONE 
          
           	Rx_EG = Rx_EG.multiply(BigDecimal.valueOf(r_tot_bits*50e-9));        // calculating receiving energy
           	init_energy = init_energy - Rx_EG ;                                // deducting Rx_EG from initial energy i.e., 10 Joules
           	String value = String.valueOf(Rx_EG.doubleValue());
           	////System.out.println '\n\tReception Energy : '+value+" Joules";
           	////System.out.println '\tRemaining Energy : '+(init_energy)+" Joules\n";
           	////System.out.println '\tTime : '+msg.getRxTime()
           	////System.out.println '\tNode ID : '+msg.getTo()
	          timeMilli = date.getTime();
            System.out.println "ENERGY: -s "+msg.getFrom()+" -d "+addr+" -b "+r_tot_bits+" -eR "+init_energy+" -pcR "+pcr//+" -c "+msg.getType()+"  {"+msg.getFrom()+":"+msg.getTo()+"}\t"
           	println "ENERGY: -s "+msg.getFrom()+" -d "+addr+" -b "+r_tot_bits+" -eR "+init_energy+" -pcR "+pcr//+" -c "+msg.getType()+" {"+msg.getFrom()+":"+msg.getTo()+"}\t"
	  	
	} //end of RxFrameNtf if
	
   
    return super.send(msg)
  }  //end of send method

 
  protected List<Parameter> getParameterList() //listing all modem parameters along with energy parameter
	{// return allOf(org.arl.unet.DatagramParam,org.arl.unet.phy.PhysicalParam,HalfDuplexModemParam,org.arl.unet.bb.BasebandParam,MyEnergyParameters); 
}  //end of getParameterList method 

} //end of MyHalfDuplexModem class
