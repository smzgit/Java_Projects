//! Simulation: Simple 3-node network
///////////////////////////////////////////////////////////////////////////////
///
/// To run simulation:
///   bin/unet samples/rt/3-node-network
///
///////////////////////////////////////////////////////////////////////////////

import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
//import org.arl.unet.sim.*
import org.arl.unet.sim.channels.*
import org.arl.unet.Services.*
import org.arl.unet.phy.Physical.*
import org.arl.unet.net.*
import org.arl.unet.DatagramReq

import org.arl.unet.sim.channels.*

channel.model = ProtocolChannelModel

channel.communicationRange = 1000.m     // Rc
channel.detectionRange = 1100.m         // Rd
channel.interferenceRange = 2000.m      // Ri
channel.pDetection = 1                  // pd
channel.pDecoding = 1 
///////////////////////////////////////////////////////////////////////////////

/*
 * 3 Node Distributed topology
 * 1 source and 1 destination
 */

// display documentation

println '''
3-node network
--------------

Nodes: 1, 2, 3

To connect to node 2 or node 3 via telnet:
  telnet localhost 5102
  telnet localhost 5103

To connect to nodes 1, 2 or 3 via unet sh:
  bin/unet sh localhost 1101
  bin/unet sh localhost 1102
  bin/unet sh localhost 1103

Connected to node 1...
Press ^D to exit
'''

///////////////////////////////////////////////////////////////////////////////
// simulator configuration

platform = RealTimePlatform   // use real-time mode

modem.model = MyHalfDuplexModem
// run the simulation forever
simulate {

  def n1 = node '1', remote: 1101, address: 1, location: [ 0.m, 0.m, -1.m], shell: true, stack:"$home/etc/initrc-stack"
    n1.startup = {
        
        def router = agentForService Services.ROUTING
        router.send new RouteDiscoveryNtf(to: 3, nextHop: 2)
        router.send new RouteDiscoveryNtf(to: 2, nextHop: 2)

        add new TickerBehavior(5000, {
          println "data sent from 1"
          router << new DatagramReq(to: 3, data:[1,2,4])
          def txNtf = receive(TxFrameNtf, 1000)
             
        })
   } 
 def n2 =  node '2', remote: 1102, address: 2, location: [ 0.m, 0.m, -800.m], shell: 5102, stack: "$home/etc/initrc-stack" 
  n2.startup = {
        
        def router = agentForService Services.ROUTING
        router.send new RouteDiscoveryNtf(to: 3, nextHop: 3)
        router.send new RouteDiscoveryNtf(to: 1, nextHop: 1)
   } 
 def n3 =  node '3', remote: 1103, address: 3, location: [0.m, 0.m, -1500.m], shell: 5103, stack: "$home/etc/initrc-stack"
  n3.startup = {
        
        def router = agentForService Services.ROUTING
        router.send new RouteDiscoveryNtf(to: 1, nextHop: 2)
        router.send new RouteDiscoveryNtf(to: 2, nextHop: 2)
   } 
 // node '4', remote: 1104, address: 4, location: [2.km, 0.km, -15.m], shell: 5104, stack: "$home/etc/initrc-stack"
 // node '5', remote: 1105, address: 5, location: [2.8.km, 0.km, -15.m], shell: 5105, stack: "$home/etc/initrc-stack" 
}



