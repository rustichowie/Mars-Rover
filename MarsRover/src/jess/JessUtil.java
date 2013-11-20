package jess;

import agents.MarsRoverAgent;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.GridField;



public class JessUtil {

    // class variables
    private Rete jess; // holds the pointer to jess
    private MarsRoverAgent myAgent; // holds the pointer to this agent

    /**
     * Starts the jess file
     * @param agent the agent that adds the behaviour
     * @param jessFile the name of the Jess file to be executed
     */
    public JessUtil(MarsRoverAgent agent, String jessFile) {
        jess = new Rete();
        myAgent = agent;
        try {
            jess.batch(jessFile);
            jess.store("active_cluster", "false");
            String rover = "(bind ?rover (assert (rover (name "+agent.getLocalName()+") (carrying " + agent.hasRock()+")(cluster_found "+agent.isAlerting()+") )))";
            this.makeassert(rover);
            jess.run();
        } catch (JessException ex) {
            Logger.getLogger(JessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    /**
     * makeasserts a fact representing an ACLMessage in Jess. It is called after
     * the arrival of a message.
     */
    public void makeassert(String fact) {
        try {
            jess.executeCommand(fact);
        } catch (JessException re) {
            re.printStackTrace(System.err);
        }
    }
    
    /**
     * Makes the next direction available to the rover, so that it can move.
     * @return
     * @throws JessException 
     */
    public String getNextDirection() throws JessException{
        String dir = "";
        if(jess.fetch("direction") != null)
            dir = (String)jess.fetch("direction").externalAddressValue(null);
        else
            dir = "false";
        
        return dir;
        
    }
    /**
     * Checks if the rover should pickup a rock or not
     * @param gf
     * @param rover
     * @throws JessException 
     */
    public void pickup(GridField gf, MarsRoverAgent rover) throws JessException{
        
        String pickup = "";
        if(jess.fetch("pickup") != null)
            pickup = (String)jess.fetch("pickup").externalAddressValue(null);
        else
            pickup = "false";
        
        if(pickup.equals("true")){
            
            if(gf.getNumberOfRocks() > 1)
                gf.setCluster_found(true);
            if(gf.getNumberOfRocks() > 0){
                gf.setNumberOfRocks(gf.getNumberOfRocks() - 1);
                System.out.println("Rover: "+myAgent.getLocalName()+" current rocks on grid: " + gf.getX()+"|"+gf.getY()+" is: " + gf.getNumberOfRocks());
            }
            if(gf.getNumberOfRocks() <= 1){
                jess.store("active_cluster", "false");
            }
            rover.setHasRock(true);
            jess.store("pickup", "false");
        }    
    }
    
    /**
     * if rover is at spaceship it should drop the rocks it is carrying
     * @param rover
     * @throws JessException 
     */
    public void drop(MarsRoverAgent rover) throws JessException{
        String drop = "";
        if(jess.fetch("drop") != null)
            drop = (String)jess.fetch("drop").externalAddressValue(null);
        else
            drop = "false";
        
        if(drop.equals("true")){
            jess.store("drop", "false");
            rover.setHasRock(false);
        }    
    }
    
    /**
     * Checks if the rover should pick up grain in its path
     * @param gf
     * @throws JessException 
     */
    public void pickUpGrain(GridField gf) throws JessException{
         String grain = "";
        if(jess.fetch("pickup_grain") != null)
            grain = (String)jess.fetch("pickup_grain").externalAddressValue(null);
        else
            grain = "false";
        
         if(grain.equals("true")){
           if(gf.getGrain() > 0)  
              gf.setGrain(gf.getGrain() - 1);
           jess.store("pickup_grain", "false");
         }
         
       
    }
    
    /**
     * Methods to update the rover in jess
     * @param message
     * @throws JessException 
     */
    public void modifyRover(String message) throws JessException{
        jess.executeCommand(message);
        jess.run();
    }
    
    /**
     * Checks if the rover should drop grain
     * @param gf
     * @throws JessException 
     */
    public void dropGrain(GridField gf) throws JessException{
        String grain = "";
        
        if(jess.fetch("drop_grain") != null)
            grain = (String)jess.fetch("drop_grain").externalAddressValue(null);
        else
            grain = "false";
        
        if(grain.equals("true")){
            gf.setGrain(2);
            jess.store("drop_grain", "false");
        }
        
    }
    /**
     * Checks if the rover found a cluster, if so: alert!
     * @return
     * @throws JessException 
     */
    public boolean alert() throws JessException{
        String alert = "";
        if(jess.fetch("alert") != null)
            alert = (String)jess.fetch("alert").externalAddressValue(null);
        else
            alert = "false";
        
        jess.store("alert", "false");
        if(alert.equals("true"))
            return true;
        else
            return false;
    }
    
    /**
     * Main method, that makes the rover moves. it just asserts 5 gridfields.
     * @param left
     * @param right
     * @param top
     * @param bottom
     * @param current
     * @throws JessException 
     */
    public void search(GridField left, GridField right, GridField top, GridField bottom, GridField current) throws JessException{
        
        String left_assert = "(assert (gridbox (direction left) (signal "+left.getSignalStrength()+")"
                                    + " (obstacle "+left.isObstacle()+") (rocks "+left.getNumberOfRocks()+")"
                                        + " (grain "+left.getGrain()+") (came_from "+left.isCame_from()+")(cluster_found "+left.isCluster_found()+") (is_spaceship "+left.isSpaceship()+")))";
        String right_assert = "(assert (gridbox (direction right) (signal "+right.getSignalStrength()+")"
                                    + " (obstacle "+right.isObstacle()+") (rocks "+right.getNumberOfRocks()+")"
                                        + " (grain "+right.getGrain()+") (came_from "+right.isCame_from()+")(cluster_found "+right.isCluster_found()+") (is_spaceship "+right.isSpaceship()+")))";
        String top_assert = "(assert (gridbox (direction top) (signal "+top.getSignalStrength()+")"
                                    + " (obstacle "+top.isObstacle()+") (rocks "+top.getNumberOfRocks()+")"
                                        + " (grain "+top.getGrain()+") (came_from "+top.isCame_from()+")(cluster_found "+top.isCluster_found()+") (is_spaceship "+top.isSpaceship()+")))";

        String bottom_assert = "(assert (gridbox (direction bottom) (signal "+bottom.getSignalStrength()+")"
                                    + " (obstacle "+bottom.isObstacle()+") (rocks "+bottom.getNumberOfRocks()+")"
                                        + " (grain "+bottom.getGrain()+")(cluster_found "+bottom.isCluster_found()+") (came_from "+bottom.isCame_from()+")"
                                            + " (is_spaceship "+bottom.isSpaceship()+")))";
         String current_assert = "(assert (gridbox (direction this) (signal "+current.getSignalStrength()+")"
                                    + " (obstacle "+current.isObstacle()+")(cluster_found "+current.isCluster_found()+") (rocks "+current.getNumberOfRocks()+")"
                                        + " (grain "+current.getGrain()+") (came_from "+current.isCame_from()+") (is_spaceship "+current.isSpaceship()+")))";
        
        this.makeassert(left_assert);
        this.makeassert(top_assert);
        this.makeassert(right_assert);
        this.makeassert(bottom_assert);
        this.makeassert(current_assert);
        jess.run();
    }
    
    
}
