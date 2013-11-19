/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import agents.MarsRoverAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.GridField;

/**
 *
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
              if(!agent.isAlerting()){
                agent.setAlerting(true);
                System.out.println("ALLLLEEEEERTTRHBHBFHBDJBFUEBFUEBFKJBEJKBJKVBEJBVJEBVJBEVJEBJVEV");
              }
       }
       else {
           block();
       }
        
    }
    
}
