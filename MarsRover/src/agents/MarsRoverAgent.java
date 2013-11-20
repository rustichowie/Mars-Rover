/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import behaviours.CustomReceiverBehaviour;
import behaviours.MarsRoverAlertBehaviour;
import behaviours.MarsRoverMovingBehaviour;
import behaviours.MarsRoverUpdateBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.GridField;

/**
 *
 * A Mars rover, it moves around on a gridmap and tries to find
 * rock samples.
 */
public class MarsRoverAgent extends Agent {

    //Current position in grid
    private int posX;                   
    private int posY;
    
    //is rover carrying a rock
    private boolean hasRock = false;
    
    //Instance of the spaceship
    private AID spaceshipAgent;
    
    //gridmap
    private GridField[][] map;
    
    //has rover been alerted of a cluster somewhere
    private boolean alerting = false;

    /**
     * Setup method, it waits until the spaceship gives the go signal before starting it's search.
     */
    protected void setup() {

        try {
            //Registers the agent to DF
            DFService.register(this, getDFAgentDescription());
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        final MarsRoverAgent agent = this;
        MessageTemplate tmpl = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        addBehaviour(new CustomReceiverBehaviour(this, -1, tmpl) {
            public void handle(ACLMessage msg) {
                System.out.println("");
                if (msg == null) {              
                    block();
                } else {
                    setSpaceshipAgent(msg.getSender());
                    
                    try {
                        //Gets the gridmap and sets current location.
                        map = (GridField[][]) msg.getContentObject();
                        String s = "";
                        for(int i = 0; i < map.length; i++){
                            for (int j = 0; j < map.length; j++){
                                 if(map[i][j].isSpaceship())
                                 {
                                     setPosX(i);
                                     setPosY(j);
                                 }
                            }
                            
                            
                        }
                        System.out.println(s);
                    } catch (UnreadableException ex) {
                        Logger.getLogger(MarsRoverAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    System.out.println("---< RoverAgent - " + myAgent.getLocalName() + " >---");
                    System.out.println("Coordinates received ( " + posX + ", " + posY + " )");
                    addBehaviour(new MarsRoverMovingBehaviour(agent, "jess/MarsRover.clp"));
                    addBehaviour(new MarsRoverUpdateBehaviour(agent));
                    addBehaviour(new MarsRoverAlertBehaviour(agent));
                }
            }
        });

    }

    /**
     * Sets the service description and DF description
     *
     * @return dfAgentDescription
     */
    private DFAgentDescription getDFAgentDescription() {

        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setName(getLocalName());

        serviceDescription.setType("rover-agent");

        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName( getAID() );
        dfAgentDescription.addServices( serviceDescription );

        return dfAgentDescription;
    }
    /**
     * method that updates the map if a field is changed. 
     */
    public void updateGridField(GridField gf){
        map[gf.getX()][gf.getY()] = gf;
    }
    
    /**
     * Getter and Setter methods for all class variables. 
     */
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean hasRock() {
        return hasRock;
    }

    public void setHasRock(boolean hasRock) {
        this.hasRock = hasRock;
    }
    
    
    
    
    public GridField[][] getMap(){
        return map;
    }

    public AID getSpaceshipAgent() {
        return spaceshipAgent;
    }

    public void setSpaceshipAgent(AID spaceshipAgent) {
        this.spaceshipAgent = spaceshipAgent;
    }

    public boolean isAlerting() {
        return alerting;
    }

    public void setAlerting(boolean alerting) {
        this.alerting = alerting;
    }
    
}
