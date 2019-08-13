The UnetStack architecture defines a set of software agents that work together
to provide a complete underwater networking solution. Agents play the role
that layers play in traditional network stacks.[1]. To achieve the agent based approach, UnetStack uses the open source fjage lightweight agent framework where
in, the generic behaviour and functionality of an agent are defined. further an
UnetStack agent extend this basic agent and implements specific requirements.
An UnetStack agent is a self-contained software component that provides
a well-defined functionality. Agents play a similar role as layers in traditional
network stacks, but are more flexible in their interactions with other agents.
Agents interact with each other through messages. Typical messages include
requests,responses and notifications. Responses are always associated with a
request, while notifications may be unsolicited.[1] As mentioned earlier an UnetStack agent is a extension of fj°age agent with some specific requirements. To
make it easy to implement such agents, the UnetStack provides a UnetAgent
base class to inherit functionality from.
In developing our energy model, we found the right place to keep track of
residual energy is at the Physical layer agent (phy) which provides the physical
layer services for a node. In UnetStack the physical agents are modem drivers
and simulated modems[1]. For conducting simulations an implementation of
3
HalfDuplexModem is available which is a physical agent and extends the UnetAgent base class, any simulation uses this as default modem, which simulates
the behavior of an underwater modem considering parameters specific to underwater communications like sound speed, temperature, salinity etc., handling all
data transmissions and receptions on a half-duplex channel.Hence we considered
extending this HalfDuplexModem class and define a subclass to implement our
energy model,which adds energy monitoring capability to the base modem.
The extended modem class should be able to handle incoming datagram requests for which it should be registered with physical and datagram services,
further our extended modem should monitor each transmissions and receptions
to calculate the energy consumption for a node. To achieve this our extended
modem class should monitor two physical agent notification messages, which
are the transmit frame notification(TxFrameNtf) and received frame notification(RxFrameNtf) the base modem class send these notification messages upon
handling a nodes data transmission and reception respectively. To send these
messages the base modem class invokes the send() defined in fjage’s Agent and
passes the instance of message as parameter, since the base modem agent is extension of UnetAgent class and in turn UnetAgent is extension of fjage’s Agent
class, the send() is invoked from Agent class, which is at topmost in the hierarchy. Thus in our extended modem class which comes at last level in this
hierarchy, this send() is overriden and defined to check whether the message
instance received as parameter is an instance of TxFrameNtf or RxFrameNtf
message, the Energy consumed for data transmission is calculated when parameter is instance of TxFrameNtf message, and reception if it is of RxFrameNtf
message, as our energy model design for transmission energy calculation requires
computing size of data and distance between sender and receiver nodes,we need
to obtain data being sent from the node and location of node,these are fetched by
handling the datagram request message which is generated when node request
for data transmission.
Finally, in simulation, the extended modem,customized for energy modelling
is added by configuring the modem settings,thereby it is added as physical agent
to the container for use by nodes running in simulation.