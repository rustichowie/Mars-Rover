/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import agents.MarsRoverAgent;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jess.JessException;
import jess.JessUtil;
import ontologies.GridField;

/**
 *
 * @author Haavard
 */
public class MarsRoverMovingBehaviour extends CyclicBehaviour{

    private MarsRoverAgent myAgent;
    private JessUtil util;
    private GridField current;
    
    public MarsRoverMovingBehaviour(MarsRoverAgent a, String file){
        myAgent = a;
        util = new JessUtil(myAgent, file);
        
        
        
    }
    
    @Override
    public void action() {
        GridField[][] map = myAgent.getMap();
        current = map[myAgent.getPosX()][myAgent.getPosY()];
        System.out.println("Current: " + current.getX() + " | " + current.getY());
        GridField left = null;
        GridField right = null;
        GridField top = null;
        GridField bottom = null;
        
        try {
            if(current.hasLeft()){
                System.out.println("has left");
                left = map[myAgent.getPosX() - 1][myAgent.getPosY()];
            } else{
                left = new GridField(false, true, false, false, false, 0, 0, 0, true, 0, 0);
            }
            if(current.hasRight()){
                System.out.println("has right");
                right = map[myAgent.getPosX() + 1][myAgent.getPosY()];
            } else{
                right = new GridField(true, false, false, false, false, 0, 0, 0, true, 0, 0);
            }
            if(current.hasBottom()){
                System.out.println("has bottom");
                 bottom = map[myAgent.getPosX()][myAgent.getPosY() + 1];
            } else{
                bottom = new GridField(false, false, true, false, false, 0, 0, 0, true, 0, 0);
            }
            if(current.hasTop()){
                 System.out.println("has top");
                 top = map[myAgent.getPosX()][myAgent.getPosY() - 1];
            } else{
                top = new GridField(false, false, false, true, false, 0, 0, 0, true, 0, 0);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        
        
        try {
            
            util.search(left, right, top, bottom, current);
            ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
             ACLMessage updateRover = new ACLMessage(ACLMessage.INFORM);
            inform.setConversationId("update-grid");
            updateRover.setConversationId("update-rover");
            updateRover.addReceiver(myAgent.getSpaceshipAgent());
            inform.addReceiver(myAgent.getSpaceshipAgent());
            GridField change = current;
            
            util.pickup(change, myAgent);
            util.drop(myAgent);
            util.pickUpGrain(change);
            System.out.println("Current: " + current.getX() + " | " + current.getY());
            System.out.println("Agent: " + myAgent.getPosX() + " | " + myAgent.getPosY());
            
            
            String direction = util.getNextDirection();
            
            for(int x = 0; x < map.length; x++){
                for(int y = 0; y < map.length; y++)
                    map[x][y].setCame_from(false);
            }
            map[current.getX()][current.getY()].setCame_from(true);
            
            switch (direction) {
                case "left":
                    System.out.println("Going: " + direction);
                    
                    current = map[current.getX() - 1][current.getY()];
                    myAgent.setPosX(current.getX());
                    myAgent.setPosY(current.getY());
                    
                    break;
                case "right":
                    System.out.println("Going: " + direction);
                    current = map[current.getX() + 1][current.getY()];
                    myAgent.setPosX(current.getX());
                    myAgent.setPosY(current.getY());
                    break;
                case "top":
                    System.out.println("Going: " + direction);
                    current = map[current.getX()][current.getY() - 1];
                    myAgent.setPosX(current.getX());
                    myAgent.setPosY(current.getY());
                    break;
                case "bottom":
                    System.out.println("Going: " + direction);
                    current = map[current.getX()][current.getY() + 1];
                    myAgent.setPosX(current.getX());
                    myAgent.setPosY(current.getY());
                    break;
            }
            if(util.alert()){
                updateRover.setContent(current.getX()+"-"+current.getY()+"-alert");
            }
            else {
                updateRover.setContent(current.getX()+"-"+current.getY());
            }
            inform.setContentObject(change);
            myAgent.send(updateRover);
            myAgent.send(inform);
            Thread.sleep(500);
            
        } catch (JessException | InterruptedException ex) {
            Logger.getLogger(MarsRoverMovingBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MarsRoverMovingBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
