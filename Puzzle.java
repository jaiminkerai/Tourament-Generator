

/**
 * Puzzle maintains the internal representation of a square Slither Link puzzle.
 * 
 * @author Lyndon While and Jaimin Kirankumar Kerai (22718975)
 * @version 1.0
 */
import java.util.ArrayList;

public class Puzzle 
{
    private int[][] puzzle;         // the numbers in the squares, i.e. the puzzle definition
                                    // -1 if the square is empty, 0-3 otherwise
    private boolean[][] horizontal; // the horizontal line segments in the current solution
                                    // true if the segment is on, false otherwise
    private boolean[][] vertical;   // the vertical line segments in the current solution
                                    // true if the segment is on, false otherwise
    private boolean[][] XClickH;    // the horizontal Red X's in the current solution
                                    // true if the x is on, false otherwise
    private boolean[][] XClickV;    // the vertical Red X's in the current solution
                                    // true if the x is on, false otherwise                            

    /**
     * Creates the puzzle from file filename, and an  empty solution.
     * filename is assumed to hold a valid puzzle.
     */
    public Puzzle(String filename)
    {
        FileIO file = new FileIO(filename);
        parseFile(file.getLines());
        horizontal = new boolean[size()+1][size()];
        XClickH = new boolean[size()+1][size()];
        vertical = new boolean[size()][size()+1];
        XClickV = new boolean[size()][size()+1];
        clear();
    }
    
    /**
     * Creates the puzzle from "eg3_1.txt".
     */
    public Puzzle()
    {
        this("eg3_1.txt");
    }

    /**
     * Returns the size of the puzzle.
     */
    public int size()
    {
        return puzzle.length;
    }

    /**
     * Returns the number layout of the puzzle.
     */
    public int[][] getPuzzle()
    {
        return puzzle;
    }
    
    /**
     * Returns the state of the X's in the current solution, horizontally.
     */
    public boolean[][] getXClickV()
    {
        return XClickV;
    }
    
    /**
     * Returns the state of the X's in the current solution, horizontally.
     */
    public boolean[][] getXClickH()
    {
        return XClickH;
    }
    
    /**
     * Returns the state of the current solution, horizontally.
     */
    public boolean[][] getHorizontal()
    {
        return horizontal;
    }

    /**
     * Returns the state of the current solution, vertically.
     */
    public boolean[][] getVertical()
    {
        return vertical;
    }

    /**
     * Turns lines into a Slither Link puzzle.
     * The first String in the argument goes into puzzle[0], 
     * The second String goes into puzzle[1], etc. 
     * lines is assumed to hold a valid square puzzle; see eg3_1.txt and eg5_1.txt for examples.
     */
    public void parseFile(ArrayList<String> lines)
    {
        puzzle = new int[lines.size()][lines.size()];
        for (int i = 0; i < lines.size(); i++){
            String s = lines.get(i);
            String[] line = s.split(" ");
            for (int j = 0; j < lines.size(); j++){
                puzzle[i][j] = Integer.parseInt(line[j]);
            }
        }
    }
  
    /**
     * Toggles a Red X to the right of Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void RightClickXH(int r, int c)
    {
        if (r >= 0 && r <= size() && c <= size()-1 && c >= 0){
            if(XClickH[r][c] == false){
               XClickH[r][c] = true;
            }
            else{
               XClickH[r][c] = false; 
            }
        }
    }
    
    /**
     * Toggles a Red X segment below Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void RightClickXV(int r, int c)
    {
        if (r >= 0 && r <= size()-1 && c <= size() && c >= 0){
            if (XClickV[r][c] == false){
                XClickV[r][c] = true;
            } 
            else{
                XClickV[r][c] = false;
            }
        }
    }
    
    /**
     * Toggles the horizontal line segment to the right of Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void horizontalClick(int r, int c)
    {
        if (r >= 0 && r <= size() && c <= size()-1 && c >= 0){
            if(horizontal[r][c] == false){
               horizontal[r][c] = true;
            }
            else{
               horizontal[r][c] = false; 
            }
        } 
    }
    
    /**
     * Toggles the vertical line segment below Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void verticalClick(int r, int c)
    {
        if (r >= 0 && r <= size()-1 && c <= size() && c >= 0){
            if (vertical[r][c] == false){
                vertical[r][c] = true;
            } 
            else{
                vertical[r][c] = false;
            }
        }
    }
    
    /**
     * Clears all line and X segments out of the current solution.
     */
    public void clear()
    {
        for (int i = 0; i <= size(); i++){
            for (int j = 0; j < size(); j++){
               horizontal[i][j] = false;
               XClickH[i][j] = false;
            }
        }
        for (int i = 0; i < size(); i++){
            for (int j = 0; j <= size(); j++){
               vertical[i][j] = false;
               XClickV[i][j] = false;
            }
        }
    }
}
