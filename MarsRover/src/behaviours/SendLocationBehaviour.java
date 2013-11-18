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

/**
 *
 * @author Lasse
 */
public class SendLocationBehaviour extends OneShotBehaviour{

    private SpaceshipAgent myAgent;
    
    public SendLocationBehaviour(Agent agent){
        myAgent = (SpaceshipAgent)agent;
    }
    
    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.CFP);
        message.setContent(myAgent.getxSpaceshipGridPos() + "-" + myAgent.getySpaceshipGridPos());
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
