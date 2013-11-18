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
public class MarsRoverUpdateBehaviour extends CyclicBehaviour {

    private MarsRoverAgent agent;
    
    public MarsRoverUpdateBehaviour(MarsRoverAgent agent){
        this.agent = agent;
    }
    
    
    
    @Override
    public void action() {
        
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage msg = agent.receive(mt);
       if(msg != null){
            try {
                GridField field = (GridField) msg.getContentObject();
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
