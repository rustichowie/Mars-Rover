package behaviours;

import agents.SpaceshipAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.GridField;


/**
 * Receives updates from rovers, stores them and sends the update to the other rover
 * @author Haavard
 */
public class SpaceshipUpdateGridBehaviour extends CyclicBehaviour{
        
    private final SpaceshipAgent myAgent;
    private MessageTemplate template;
    private ACLMessage message;
    private AID sender;
    private GridField field;
    	
    public SpaceshipUpdateGridBehaviour(Agent agent){
        
        myAgent = (SpaceshipAgent)agent;
        
    }
	
    @Override
    public void action() {
        template = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
                MessageTemplate.MatchConversationId("update-grid"));
        
        message = myAgent.receive(template);
        
        if(message != null){
            try {
                field = (GridField)message.getContentObject();
            } catch (UnreadableException ex) {
                System.out.println("-< Spaceship Agent >- Failed retreiving serializable object from message. ");
                Logger.getLogger(SpaceshipUpdateGridBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
            sender = message.getSender();
            
            myAgent.updateGridField(field);
            
            message = new ACLMessage( ACLMessage.INFORM );
            message.setConversationId("update-grid");
            for(AID aid : myAgent.getRovers()){
                if(!aid.equals( sender )){
                    message.addReceiver( aid );
                }
            }
            try {
                message.setContentObject( field );
            } catch (IOException ex) {
                System.out.println("-< Spaceship Agent >- Failed assert serializable object to message. ");
                Logger.getLogger(SpaceshipUpdateGridBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("");
            System.out.println("");
            myAgent.send( message );
        } else {
            block();
        }
    }
}
