/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import agents.SpaceshipAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.GridField;

/**
 *
 * @author Lasse
 */
public class SendLocationBehaviour extends OneShotBehaviour{

    private SpaceshipAgent myAgent;
    private GridField[][] map;
    
    public SendLocationBehaviour(Agent agent, GridField[][] map){
        myAgent = (SpaceshipAgent)agent;
        this.map = map;
    }
    
    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.CFP);
        //.setContent(myAgent.getxSpaceshipGridPos() + "-" + myAgent.getySpaceshipGridPos());
        try {
            message.setContentObject(map);
        } catch (IOException ex) {
            Logger.getLogger(SendLocationBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("----< Spaceship Agent >----");
        System.out.println("Spaceship coordinates ( " + myAgent.getxSpaceshipGridPos() + ", " + myAgent.getySpaceshipGridPos() + " )");
        for( AID aid : myAgent.getRovers()){
            message.addReceiver(aid);
        }
        message.setSender(myAgent.getAID());
        myAgent.send(message);
        System.out.println("Message sent to all rovers! ");
        System.out.println("");
    }
    
}
