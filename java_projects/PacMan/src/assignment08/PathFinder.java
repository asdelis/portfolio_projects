package assignment08;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class PathFinder {

    static int height_ = 0;
    static int width_ = 0;
    static Node[][] maze_;
    static Node start_;
    static Node goal_;
    static Queue<Node> frontier_ = new LinkedList<>();

    /**
     * "solveMaze" just calls a bunch of helper functions
     * Their names are self-explanatory for the most part and are
     * described in more detail in their own javadoc comments
     *
     * @param inputFile : the input file name as a string
     * @param outputFile : the output file name as a string
     * @throws IOException
     */
    public static void solveMaze(String inputFile, String outputFile) throws IOException {

        buildMaze( inputFile );
        assignNeighbors();
        assignStartAndGoal();
        breadthFirstSearch();
        writeOutSolvedMaze( outputFile );

    }

    /**
     * "buildMaze" uses a BufferedReader and a FileReader to read
     * in the maze file given to us character by character and
     * builds a graph version of the file that is saved in the
     * member variable "maze_." It also saves the height and width of the
     * maze, saves them as a member variable, and uses them to initialize the
     * graph which is a 2d array of nodes.
     *
     * @param inputFile : the input file name as a string
     * @throws IOException
     */
    private static void buildMaze(String inputFile) throws IOException {

        try {
            // read in the file
            BufferedReader input = new BufferedReader( new FileReader( inputFile ) );

            // find the dimensions of the maze
            String[] dimensions = input.readLine().split(" ");
            height_ = Integer.parseInt(dimensions[0]);
            width_ = Integer.parseInt(dimensions[1]);

            // initialize size of the maze
            maze_ = new Node[height_][width_];

            // fill in the maze
            for ( int i = 0; i < height_; i++){
                for ( int j = 0; j < width_; j++){
                    //get the individual value and save it
                    maze_[i][j] = new Node( (char) input.read() );
                }
                input.read();
            }

        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * "assignNeighbors" is a void function that loops through
     * the entire graph and saves the neighbors of that particular
     * node in the graph.
     */
    private static void assignNeighbors(){

        for ( int i = 0; i < height_; i++){
            for ( int j = 0; j < width_; j++){

                // you are not a wall
                if( maze_[i][j].value_ != 'X'){

                    // add neighbors
                    // up
                    if ( i + 1 < height_ &&
                            ( maze_[i + 1][j].value_ == 'S' || maze_[i + 1][j].value_ == 'G' || maze_[i + 1][j].value_ == ' ' ) ) {
                        maze_[i][j].neighbors_.add(maze_[i + 1][j]);
                    }
                    // down
                    if ( i - 1 >= 0 &&
                            ( maze_[i - 1][j].value_ == 'S' || maze_[i - 1][j].value_ == 'G' || maze_[i - 1][j].value_ == ' ' ) ) {
                        maze_[i][j].neighbors_.add(maze_[i - 1][j]);
                    }
                    // right
                    if ( j + 1 < width_ &&
                            ( maze_[i][j + 1].value_ == 'S' || maze_[i][j + 1].value_ == 'G' || maze_[i][j + 1].value_ == ' ' ) ) {
                        maze_[i][j].neighbors_.add(maze_[i][j + 1]);
                    }
                    // left
                    if ( j - 1 >= 0 &&
                            ( maze_[i][j - 1].value_ == 'S' || maze_[i][j - 1].value_ == 'G' || maze_[i][j - 1].value_ == ' ' ) ) {
                        maze_[i][j].neighbors_.add(maze_[i][j - 1]);
                    }
                }
            }
        }
    }

    /**
     * "assignStartAndGoal" loops through the graph and finds the
     * start and goal nodes in the graph and saves them in the
     * start_ and goal_ member variables.
     */
    private static void assignStartAndGoal(){

        for ( int i = 0; i < height_; i++) {
            for (int j = 0; j < width_; j++) {

                if ( maze_[i][j].value_ == 'S' ){
                    start_ = maze_[i][j];
                }
                if ( maze_[i][j].value_ == 'G' ){
                    goal_ = maze_[i][j];
                }

            }
        }
    }

    /**
     * "breadthFirstSearch" is the method that actually finds the
     * solved path of the maze for us. Instead of creating an output,
     * it modifies the graph that we made by adding periods to show
     * the path of the solved maze.
     *
     * First it finds all the neighbors of the start_ nodes and puts
     * them into a linked list priority queue called frontier_. Then it
     * pops start_ and checks all the other nodes in frontier_'s neighbors
     * for the goal_ node. When found, it will use the cameFrom_ node saved
     * in each node to build a path to the start_ node. It will also only
     * add nodes that have not already been visited to frontier_.
     */
    private static void breadthFirstSearch(){

        start_.visited_ = true;
        frontier_.add( start_ );
        boolean pathFound = false;

        while ( !frontier_.isEmpty() ) {

            //get the current node
            Node current = frontier_.remove();

            // if we have found the goal
            if ( current.value_ == ( goal_.value_ ) ) {

                Node previous = current.cameFrom_;

                while ( previous.value_ != start_.value_ ){

                    //System.out.println( backwardsNode.value_ );
                    previous.value_ = '.';
                    previous = previous.cameFrom_;

                }

                pathFound = true;

            }
            if( !pathFound ) {

                for ( Node n : current.neighbors_ ){
                    if ( !n.visited_ ){
                        n.cameFrom_ = current;
                        n.visited_ = true;
                        frontier_.add( n );
                    }

                }

            }

        }

    }

    /**
     * "outputFileName" loops through the graph and prints the result of
     * the solved maze to the outputFile that was given to us. It uses
     * a PrintWriter and a FileWriter to do this.
     *
     * @param outputFileName
     */
    private static void writeOutSolvedMaze( String outputFileName ) {

        try {

            PrintWriter output = new PrintWriter( new FileWriter( outputFileName ));
            output.println( height_ + " " + width_ );

            for ( int i = 0; i < height_; i++){
                for ( int j = 0; j < width_; j++){
                    output.print( maze_[i][j].value_ );
                }
                // add the new lines to format it like the original
                output.println();
            }

            output.flush();

        }
        catch ( IOException e ){
            throw new RuntimeException();
        }

    }

}
