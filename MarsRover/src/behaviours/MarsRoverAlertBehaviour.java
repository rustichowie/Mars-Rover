/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import agents.MarsRoverAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


/**
 * A cyclic behaviour that waits for a alert message.
 * If another rover finds a cluster, it sends an alert which this one receives.
 * @author Haavard
 */
public class MarsRoverAlertBehaviour extends CyclicBehaviour {

    private MarsRoverAgent agent;
    
    public MarsRoverAlertBehaviour(MarsRoverAgent agent){
        this.agent = agent;
    }
    
    
    
    @Override
    public void action() {
        
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchConversationId("alert"));
        ACLMessage msg = agent.receive(mt);
        
       if(msg != null){           
              if(!agent.isAlerting() && !agent.hasRock()){
                agent.setAlerting(true);
               
              }
       }
       else {
           block();
       }
        
    }
    
}
