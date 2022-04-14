
/**
 * AnalyzeSolution methods are used to analyze the state of a Slither Link puzzle, 
 * to determine if the puzzle is finished. 
 * 
 * @author Lyndon While and Jaimin Kirankumar Kerai 
 * @version v1.0
 */
import java.util.*;
import java.util.ArrayList;
import java.util.Random;

public class AnalyzeSolution
{
    /**
     * We don't need to create any objects of class AnalyzeSolution; all of its methods are static.
     */
    private AnalyzeSolution() {}

    /**
     * Returns the number of line segments surrounding Square r,c in p.
     * Returns 0 if the indices are illegal.
     */
    public static int linesAroundSquare(Puzzle p, int r, int c)
    {
        int NoOfLines = 0; 
        if (r >= 0 && c >= 0 && c < p.size() && r < p.size()){
            for (int i = c; i < c+2; i++){
                if (p.getVertical()[r][i] == true){
                  NoOfLines += 1;
                }
            } 
            for (int j = r; j < r+2; j++){
                if (p.getHorizontal()[j][c] == true){
                  NoOfLines += 1;
                }
            }
            return NoOfLines;
        } else{
            return 0;
        }
    }
    
    /**
     * Returns all squares in p that are surrounded by the wrong number of line segments.
     * Each l be an int[2] containing the indices of a square.
     * The order of the items on the result is unimportant.
     */
    public static ArrayList<int[]> badSquares(Puzzle p)
    {
        ArrayList<int[]> badSQ = new ArrayList<int[]>();
        for (int i = 0; i < p.size(); i++){
            for (int j = 0; j < p.size(); j++){
                if (p.getPuzzle()[i][j] != -1 && p.getPuzzle()[i][j] != linesAroundSquare(p, i, j)){
                    badSQ.add(new int[] {i,j});
                }
            }
        }
        return badSQ;
    }
   
    /**
     * Returns all dots connected by a single line segment to Dot r,c in p.
     * Each item on the result will be an int[2] containing the indices of a dot.
     * The order of the items on the result is unimportant.
     * Returns null if the indices are illegal.
     */
    public static ArrayList<int[]> getConnections(Puzzle p, int r, int c)
    {
        ArrayList<int[]> connections = new ArrayList<>();
        if (r >= 0 && c >= 0 && r <= p.size() && c <= p.size()){
            if (c >= 0 && c < p.size() && r>= 0 && r <= p.size() && p.getHorizontal()[r][c] == true){
                connections.add(new int[] {r,c+1});
            }   
            if (c >= 0 && c <= p.size() && r >= 0 && r < p.size() && p.getVertical()[r][c] == true) {
                connections.add(new int[] {r+1,c});
            } 
            if (r <= p.size() && r >= 0 && c > 0 && c <= p.size() && p.getHorizontal()[r][c-1] == true){
                connections.add(new int[] {r,c-1});
            } 
            if (c <= p.size() && c >= 0 && r > 0 && r <= p.size() && p.getVertical()[r-1][c] == true){
                connections.add(new int[] {r-1,c});
            } 
            return connections;
        } else {
            return null;
        }
    }

    /**
     * Returns an array of length 3 whose first element is the number of line segments in the puzzle p, 
     * and whose other elements are the indices of a dot on any one of those segments. 
     * Returns {0,0,0} if there are no line segments on the board. 
     */
    public static int[] lineSegments(Puzzle p)
    {
        int totallines = 0;
        int[] indices = new int[] {0, 0};
        for (int i = 0; i <= p.size(); i++){
            for (int j = 0; j < p.size(); j++){
                if (p.getHorizontal()[i][j] == true){
                    totallines += 1;
                    indices[0] = i;
                    indices[1] = j;
                }
            }
        }
        for (int i = 0; i < p.size(); i++){
            for (int j = 0; j <= p.size(); j++){
                if (p.getVertical()[i][j] == true){
                    totallines += 1;
                    indices[0] = i;
                    indices[1] = j;
                }
            }
        }      
        int[] total = new int[] {totallines, indices[0], indices[1]};
        return total;
    }
    
    /**
     * Tries to trace a closed loop starting from Dot r,c in p. 
     * Returns either an appropriate error message, or 
     * the number of steps in the closed loop (as a String). 
     * See the project page and the JUnit for a description of the messages expected. 
     */
    public static String tracePath(Puzzle p, int r, int c)
    {
        int length = 0;
        int prev_r = r;
        int curr_r = r; 
        int prev_c = c;
        int curr_c = c;
        do {
            if (getConnections(p,curr_r,curr_c).size() >= 3){
                return "Branching line"; 
            } else if (getConnections(p,curr_r,curr_c).size() == 2){
                if (getConnections(p, curr_r, curr_c).get(0)[0] != prev_r || getConnections(p, curr_r, curr_c).get(0)[1] != prev_c){
                    length += 1;
                    prev_r = curr_r;
                    prev_c = curr_c;
                    curr_r= getConnections(p,prev_r,prev_c).get(0)[0];
                    curr_c = getConnections(p,prev_r,prev_c).get(0)[1];
                } else {
                    length += 1;
                    prev_r = curr_r;
                    prev_c = curr_c;
                    curr_r= getConnections(p,prev_r,prev_c).get(1)[0];
                    curr_c = getConnections(p,prev_r,prev_c).get(1)[1];
                }
            } else if (getConnections(p,curr_r,curr_c).size() == 1){
                return "Dangling end";
            } else{
                return "No path";
            }
        } while (curr_r != r || curr_c != c);
        return ""+length;
    }
    
    /**
     * Returns a message on whether the puzzle p is finished. 
     * p is finished iff all squares are good, and all line segments form a single closed loop. 
     * An algorithm is given on the project page. 
     * See the project page and the JUnit for a description of the messages expected.
     */
    public static String finished(Puzzle p)
    {
        int lines = lineSegments(p)[0];
        int x = lineSegments(p)[1];
        int y = lineSegments(p)[2];
        if (badSquares(p).size() > 0){
            return "Wrong number";
        } else if (tracePath(p, x, y) == "No path"){
            return "Disconnected lines";
        } else if (tracePath(p, x, y) == "Dangling end"){
            return "Dangling end";
        } else if (tracePath(p, x, y) == "Branching line"){
            return "Branching line";
        } else if (Integer.parseInt(tracePath(p,x,y)) != lines){
            return "Disconnected lines";
        } else {
            return "Finished";
        }
    }
}
