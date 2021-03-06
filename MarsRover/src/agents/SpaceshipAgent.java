package agents;

import behaviours.SendLocationBehaviour;
import behaviours.SpaceshipUpdateGridBehaviour;
import behaviours.SpaceshipUpdateRoverBehaviour;
import jade.core.AID;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ontologies.GridField;
import swing.GridComponent;
import swing.BoardInstructionsComponent;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import swing.RoverComponent;

public class SpaceshipAgent extends Agent {
    
    private GridField[][] map;                      //Gridmap
    private int dimensions;                         //Size of map
    private JFrame mapFrame;                        //map frame
    private GridComponent gridComp;                 //map component
    private BoardInstructionsComponent boardComp;   //text update component
    private List<RoverComponent> roverComp;         //list of rover components
    private int gridSize;                           //size of 1 grid
    
    //Spaceship position, in map and on screen
    private int xSpaceship;                     
    private int ySpaceship;
    private int xSpaceshipGridPos;
    private int ySpaceshipGridPos;
    
    private Agent agent;                            //current agent
    private List<AID> rovers;                       //list of rovers

    /**
     * Draws everything and sets up the map.
     */
    protected void setup(){
        Object[] args = getArguments();
        agent = this;

        dimensions = 15;
        gridSize = 25;

        map = new GridField[dimensions][dimensions];

        fillMap();
        setSpaceshipLocation();

        rovers = new ArrayList<>();
        
        try{
            DFService.register(agent, getDFAgentDescription());
            updateAvailableRovers();
        }catch(FIPAException fe){
            fe.printStackTrace();
        }

        mapFrame = new JFrame();
        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapFrame.setPreferredSize(new Dimension(800, 680));

        gridComp = new GridComponent();
        gridComp.setPreferredSize(new Dimension(200, 200));
        gridComp.setBounds(0, 0, 600, 600);

        boardComp = new BoardInstructionsComponent();
        boardComp.setPreferredSize(new Dimension(90, 90));
        
        roverComp = new ArrayList<>();
        for(AID aid : rovers){
            RoverComponent rc = new RoverComponent(xSpaceshipGridPos, ySpaceshipGridPos, aid.getLocalName(), gridSize);
            rc.setPreferredSize(new Dimension( 200, 200 ));
            rc.setBounds(0, 0, 600, 600);
            roverComp.add(rc);
        }
        
        JButton button = new JButton("Start Rovers");
     
        JPanel compPanel = new JPanel();
        compPanel.setLayout(null);
        compPanel.add( gridComp, BorderLayout.CENTER );
        for(RoverComponent rc: roverComp){
            compPanel.add( rc, BorderLayout.CENTER );
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
     
        mapFrame.getContentPane().add( compPanel, BorderLayout.CENTER );
        mapFrame.getContentPane().add( boardComp, BorderLayout.WEST );
        mapFrame.getContentPane().add( buttonPanel, BorderLayout.SOUTH );

        mapFrame.pack();
        mapFrame.setVisible(true);

        printMap();
        printSpaceshipLocation();

        initObstacles();
        initRocks();

        printObstaclesAndRocks();

        printBoard();

        initButtonClicks(button);

        

        addBehaviour(new SpaceshipUpdateGridBehaviour(this));
        addBehaviour(new SpaceshipUpdateRoverBehaviour(this));
    }

    /**
     * Getter and Setter methods for some class variables.
     */
    public List<AID> getRovers(){
        return rovers;
    }
    
    public int getxSpaceshipGridPos() {
        return xSpaceshipGridPos;
    }

    public int getySpaceshipGridPos() {
        return ySpaceshipGridPos;
    }

    /**
     * Registrers the service description
     * @return 
     */
    public DFAgentDescription getDFAgentDescription(){

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName( getLocalName() );
        serviceDescription.setType("spaceship-agent");

        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName( getAID() );
        dfAgentDescription.addServices(serviceDescription);

        return dfAgentDescription;
    }

    /**
     * Finds all rovers from the Directory facilitator
     */
    private void updateAvailableRovers(){
        DFAgentDescription dfAgentDescription = new DFAgentDescription();

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("rover-agent");

        dfAgentDescription.addServices(serviceDescription);

        try{
            DFAgentDescription[] result = DFService.search( agent, dfAgentDescription );
            System.out.println("");
            System.out.println("----< Spaceship found following rovers >----");
            for( int i = 0; i < result.length; i++){
                rovers.add(result[i].getName());
                System.out.println("Rover " + (i+1) + ": " + result[i].getName().getLocalName() );
            }
            System.out.println("");
            if(rovers.isEmpty()){
                System.out.println("No rovers available");
                doDelete();
            }

        } catch(FIPAException fe){
            fe.printStackTrace();
        }
    }
    
    /**
     * Updates the map with changes from the rovers
     * @param field 
     */
    public void updateGridField(GridField field){
        map[field.getX()][field.getX()] = field;
        gridComp.updateGridField(field, gridSize, dimensions);
    }
    
    /**
     * Updates rover position on map
     * @param aid
     * @param x
     * @param y 
     */
    public void updateRover(AID aid, int x, int y){
        for( RoverComponent rc: roverComp){
            if(rc.getRoverName().equals(aid.getLocalName())){
                rc.moveRover(x, y);
            }
        }
    }
    
    /**
     * If a rover finds a cluster of rocks, alert the other rover
     * @param s 
     */
    public void sendAlert(AID s){
        ACLMessage alert = new ACLMessage(ACLMessage.INFORM);
        for(AID aid: rovers){
            if(!s.equals(aid)){
                alert.addReceiver(aid);
            }
        }
        alert.setConversationId("alert");
        alert.setContent("No Content!");
        send(alert);
    }
    
    /**
     * prints out the spaceship on the map
     */
    public void printSpaceshipLocation(){
            gridComp.addGrid(xSpaceship, ySpaceship, gridSize, gridSize, Color.BLUE, "S");
    }

    /**
     * Prints out the board in the top left corner
     */
    public void printBoard(){
        boardComp.addText("Spaceship", "Obstacles", "Rocks");
    }

    /**
     * Prints all obstacles and rocks on the map
     */
    public void printObstaclesAndRocks(){
        for(int tx = 0; tx < dimensions; tx++){
            for(int ty = 0; ty < dimensions; ty++){
                int i = ((tx+1)*gridSize) + 100;
                int j = ((ty+1)*gridSize) + 20;
                if(map[tx][ty].isObstacle()){
                    gridComp.addGrid(i, j, gridSize, gridSize, new Color(229, 24, 24), "O");
                }
                if(map[tx][ty].getNumberOfRocks() != 0){
                    gridComp.addGrid(i, j, gridSize, gridSize, new Color(52, 181, 95), "" + map[tx][ty].getNumberOfRocks());
                }
            }
        }
    }
    
    /**
     * Prints the map itself
     */
    public void printMap(){

            for(int tx = 0; tx < dimensions; tx++){
                    for(int ty = 0; ty < dimensions; ty++){
                            int x = ((tx+1)*gridSize) + 100;
                            int y = ((ty+1)*gridSize) + 20;
                            int ss = (Math.abs(xSpaceshipGridPos - tx)+Math.abs(ySpaceshipGridPos - ty));
  
                            gridComp.addGrid(x, y, gridSize, gridSize, Color.BLACK, "");

                            map[tx][ty].setSignalStrength(ss);
                            
                    }

            }
    }
	
    /**
     * Initializes the obstacles
     */
    public void initObstacles(){
        Random rand = new Random();
        int noOfObstacles = (rand.nextInt(4) + 3);

        int count = 0;
        while(count < noOfObstacles){
            int i = rand.nextInt(dimensions);
            int j = rand.nextInt(dimensions);

            if(!map[i][j].isObstacle() && !map[i][j].isSpaceship()){
                map[i][j].setObstacle(true);
                count++;
            }
        }

    }
        
    /**
     * Initializes the rocks
     */
    public void initRocks(){
        Random rand = new Random();
        int count = 0;
        int noOfRocks = (rand.nextInt(6) + 10);

        while(count < noOfRocks){
            int i = rand.nextInt(dimensions);
            int j = rand.nextInt(dimensions);

            if(!map[i][j].isObstacle() && !map[i][j].isSpaceship() && map[i][j].getNumberOfRocks() == 0){
                int cluster = rand.nextInt(10)+1;
                if(cluster < 4){
                    map[i][j].setNumberOfRocks(1);
                }
                else {
                    map[i][j].setNumberOfRocks(cluster);
                }
                count++;
            }
        }
    }
        
    /**
     * Sets the spaceship location
     */
    public void setSpaceshipLocation(){
        xSpaceshipGridPos = (int) (Math.random() * dimensions);
        ySpaceshipGridPos = (int) (Math.random() * dimensions);
        map[xSpaceshipGridPos][ySpaceshipGridPos].setSpaceship(true);
        xSpaceship = ((xSpaceshipGridPos+1)*gridSize) + 100;
        ySpaceship = ((ySpaceshipGridPos+1)*gridSize) + 20;
    }
	
    /**
     * makes sure that the rover won't go outside the boundaries.
     */
    public void fillMap(){
	for(int i = 0; i < dimensions; i++){
            for(int j = 0; j < dimensions; j++){
                map[i][j] = new GridField();
		map[i][j].setX(i);
                map[i][j].setY(j);
                
                if(i == 0){
                    map[i][j].setLeft(false);
		}
                if(i == dimensions-1){
                    map[i][j].setRight(false);
		}
		if(j == 0){
                    map[i][j].setTop(false);
                }
                if(j == dimensions-1){
                    map[i][j].setBottom(false);
                }	
            }
        }
    }    
    /**
     * Button listener
     * @param button 
     */
    public void initButtonClicks(JButton button){
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                agent.addBehaviour(new SendLocationBehaviour(agent, map));
            }
        });
        
    }
}
