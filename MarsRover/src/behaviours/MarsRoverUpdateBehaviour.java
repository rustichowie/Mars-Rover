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
 * Behaviour that receives updates from the spaceship and updates local variables.
 * @author Haavard
 */
public class MarsRoverUpdateBehaviour extends CyclicBehaviour {

    private MarsRoverAgent agent;
    
    public MarsRoverUpdateBehaviour(MarsRoverAgent agent){
        this.agent = agent;
    }
    
    
    
    @Override
    public void action() {
        
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchConversationId("update-grid"));
        
        ACLMessage msg = agent.receive(mt);
       if(msg != null){
            try {
                GridField field = (GridField) msg.getContentObject();
                System.out.println("Rover: " + myAgent.getLocalName() + " Grain on gridfield: " + field.getX()+"|"+field.getY()+ " :" + field.getGrain());
            
                agent.updateGridField(field);
            } catch (UnreadableException ex) {
                Logger.getLogger(MarsRoverUpdateBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       }
       else {
           block();
       }
        
    }
    
}
