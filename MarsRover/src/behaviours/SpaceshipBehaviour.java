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

public class SpaceshipBehaviour extends CyclicBehaviour{
        
    private SpaceshipAgent myAgent;
    private MessageTemplate template;
    private ACLMessage message;
    private AID sender;
    private GridField field;
    	
    public SpaceshipBehaviour(Agent agent){
        
        myAgent = (SpaceshipAgent)agent;
        
    }
	
    @Override
    public void action() {
        template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        
        message = myAgent.receive(template);
        
        if(message != null){
            try {
                field = (GridField)message.getContentObject();
            } catch (UnreadableException ex) {
                System.out.println("-< Spaceship Agent >- Failed retreiving serializable object from message. ");
                Logger.getLogger(SpaceshipBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
            sender = message.getSender();
            String content = message.getContent();
            String[] coords = content.split("-");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            String alert = "";
            if(coords.length > 2){
                alert = coords[2];
            }
            
            myAgent.updateGridField(field);
            myAgent.updateRover(x, y);
            
            
            message = new ACLMessage( ACLMessage.INFORM );
            for(AID aid : myAgent.getRovers()){
                if(!aid.equals(sender)){
                    message.addReceiver(aid);
                }
            }
            try {
                message.setContentObject(field);
            } catch (IOException ex) {
                System.out.println("-< Spaceship Agent >- Failed assert serializable object to message. ");
                Logger.getLogger(SpaceshipBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("---< Spaceship Agent>---");
            System.out.println("Sending message to other rover!");
            System.out.println("");
            myAgent.send(message);
        } else {
            block();
        }
        
        
    }
}
