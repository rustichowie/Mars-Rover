package behaviours;

import agents.SpaceshipAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Receives the position of the rovers
 * @author Haavard
 */
public class SpaceshipUpdateRoverBehaviour extends CyclicBehaviour{
        
    private SpaceshipAgent myAgent;
    private MessageTemplate template;
    private ACLMessage message;
    private AID sender;
    	
    public SpaceshipUpdateRoverBehaviour(Agent agent){
        
        myAgent = (SpaceshipAgent)agent;
        
    }
	
    @Override
    public void action() {
        template = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
                MessageTemplate.MatchConversationId("update-rover"));
        
        message = myAgent.receive( template );
        
        if(message != null){
            sender = message.getSender();
            String content = message.getContent();
            String[] coords = content.split("-");
            
            
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            
            myAgent.updateRover(sender, x, y);
            
            if(coords.length > 2){
                String alert = coords[2];
                if(alert.equals("alert")){
                    myAgent.sendAlert( sender );
                }
            }
            
        } else {
            block();
        }
        
        
    }
}
