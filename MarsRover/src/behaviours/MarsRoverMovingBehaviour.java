/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import agents.MarsRoverAgent;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
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
    
    public MarsRoverMovingBehaviour(MarsRoverAgent a, String file){
        myAgent = a;
        util = new JessUtil(myAgent, file);
        
        
        
    }
    
    @Override
    public void action() {
        GridField[][] map = myAgent.getMap();
        GridField current = map[myAgent.getPosX()][myAgent.getPosY()];
        GridField left = null;
        GridField right = null;
        GridField top = null;
        GridField bottom = null;
        //GridField left = map[myAgent.getPosX()];
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
            
            String direction = util.getNextDirection();
            switch (direction) {
                case "left":
                    
                    break;
                case "right":
                    break;
                case "top":
                    break;
                case "bottom":
                
                    break;
            }
            
            Thread.sleep(1000);
            
        } catch (JessException | InterruptedException ex) {
            Logger.getLogger(MarsRoverMovingBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
