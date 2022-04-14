 
/**
 * SlitherLink does the user interaction for a square Slither Link puzzle.
 * 
 * @author Lyndon While and Jaimin Kirankumar Kerai
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class SlitherLink implements MouseListener
{    
    private Puzzle game;     // internal representation of the game
    private SimpleCanvas sc; // the display window
   
    private final Color green    = Color.green; // Colour green
    private final Color red = Color.red; // Colour red
    private final Color black  = Color.black; // Colour black
    private final Color bgColor  = Color.white; // Colour white
   
    /**
     * Creates a display for playing the puzzle p.
     */
    public SlitherLink(Puzzle p)
    {
        p.getPuzzle();
        displayPuzzle();
    }
    
    /**
     * Returns the current state of the game.
     */
    public Puzzle getGame()
    {
        return game;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }

    /**
     * Displays the initial puzzle on sc. 
     * Have a look at puzzle-loop.com for a basic display, or use your imagination. 
     */
    public void displayPuzzle()
    {
        int size = game.size();
        int canvas_size = (size+1)*40;
        sc = new SimpleCanvas("SlitherLink", canvas_size, canvas_size+150, bgColor);
        sc.addMouseListener(this);
        for (int i = 0; i <= size; i++){
           int offset_y = canvas_size/(2*(size+1))+(canvas_size/(size+1))*i;         
           for (int j = 0; j <= size; j++){
               int offset_x = canvas_size/(2*(size+1))+(canvas_size/(size+1))*j;
               sc.drawDisc(offset_x, offset_y, 5,black);
           }
        }
        for (int n = 0; n < size; n++){
           int offset_y1 = (canvas_size/(size+1))+(canvas_size/(size+1))*n;
           for (int m = 0; m < size; m++){
               int offset_x1 = (canvas_size/(size+1))+(canvas_size/(size+1))*m;           
               if (game.getPuzzle()[n][m] != -1){
                   sc.setFont(new Font("Arial",Font.BOLD,20));
                   sc.drawString(game.getPuzzle()[n][m], offset_x1-4,offset_y1+8, black);
               }
           }
        }
        int offset_x1 = canvas_size / (size+1);
        int offset_y1 = canvas_size / (size+1);
        for (int r = 0; r <= size; r++){
            for (int c = 0; c < size; c++){
               if (game.getHorizontal()[r][c] == true){
                   sc.drawLine(25+offset_x1*c,20+offset_y1*r,offset_x1*c+55,20+offset_y1*r,black);
              } else if (game.getHorizontal()[r][c] == false){
                   sc.drawLine(25+offset_x1*c,20+offset_y1*r,offset_x1*c+55,20+offset_y1*r,bgColor);
              }
              if (game.getXClickH()[r][c] == true){
                   sc.drawString("x", 37+offset_x1*c, 25+offset_y1*r, red);
              } else if (game.getXClickH()[r][c] == false){
                   sc.drawString("x", 37+offset_x1*c, 25+offset_y1*r, bgColor);
              }
            }
        }
        for (int r = 0; r < size; r++){
            for (int c = 0; c <= size; c++){
               if (game.getVertical()[r][c] == true){
                   sc.drawLine(20+offset_x1*c,25+offset_y1*r,20+offset_x1*c,offset_y1*r+55,black);
               } else if (game.getVertical()[r][c] == false){
                   sc.drawLine(20+offset_x1*c,25+offset_y1*r,20+offset_x1*c,offset_y1*r+55,bgColor);
               }
               if (game.getXClickV()[r][c] == true){
                   sc.drawString("x", 17+offset_x1*c, 44+offset_y1*r, red);
               } else if (game.getXClickV()[r][c] == false){
                   sc.drawString("x", 17+offset_x1*c, 44+offset_y1*r, bgColor);
               }
            }
        }
        sc.drawString("SLITHERLINK", canvas_size/2-64, canvas_size+25, black);
        sc.drawRectangle(canvas_size/2-36, canvas_size+35,canvas_size/2+36, canvas_size+60,red);
        sc.drawString("CLEAR", canvas_size/2-33, canvas_size+55, bgColor);
        sc.drawRectangle(canvas_size/2-40, canvas_size+64,canvas_size/2+40, canvas_size+90,green);
        sc.drawString("CHECK", canvas_size/2-34, canvas_size+85, bgColor);
    }
    
    /**
     * Makes a horizontal click to the right of Dot r,c.
     * Update game and the display, if the indices are legal; otherwise do nothing.
     */
    public void horizontalClick(int r, int c)
    {
        int size = game.size();
        int canvas_size = (size+1)*40;
        int offset_x1 = canvas_size / (size+1);
        int offset_y1 = canvas_size / (size+1);
        game.horizontalClick(r,c);
        if (game.getHorizontal()[r][c] == true){
            sc.drawLine(25+offset_x1*c,20+offset_y1*r,offset_x1*c+55,20+offset_y1*r,black);
        } else if (game.getHorizontal()[r][c] == false){
            sc.drawLine(25+offset_x1*c,20+offset_y1*r,offset_x1*c+55,20+offset_y1*r,bgColor);
        }
    }
    
    /**
     * Makes a vertical click below Dot r,c. 
     * Update game and the display, if the indices are legal; otherwise do nothing. 
     */
    public void verticalClick(int r, int c)
    {
        int size = game.size();
        int canvas_size = (size+1)*40;
        int offset_x1 = canvas_size / (size+1);
        int offset_y1 = canvas_size / (size+1);
        game.verticalClick(r,c);
        if (game.getVertical()[r][c] == true){
            sc.drawLine(20+offset_x1*c,25+offset_y1*r,20+offset_x1*c,offset_y1*r+55,black);
        } else if (game.getVertical()[r][c] == false){
            sc.drawLine(20+offset_x1*c,25+offset_y1*r,20+offset_x1*c,offset_y1*r+55,bgColor);
        }
    }
    
    /**
     * Makes a Red X below the Dot r,c.
     * Update game and the display, if the indices are legal; otherwise do nothing.
     */
    public void RightClickXV(int r, int c)
    {
        int size = game.size();
        int canvas_size = (size+1)*40;
        int offset_x1 = canvas_size / (size+1);
        int offset_y1 = canvas_size / (size+1);
        game.RightClickXV(r,c);
        sc.setFont(new Font("Arial",Font.BOLD,15));
        if (game.getXClickV()[r][c] == true){
           sc.drawString("x", 17+offset_x1*c, 44+offset_y1*r, red);
        } else if (game.getXClickV()[r][c] == false){
           sc.drawString("x", 17+offset_x1*c, 44+offset_y1*r, bgColor);
        }
    }
    
    /**
     * Makes a Red X to the right of Dot r,c.
     * Update game and the display, if the indices are legal; otherwise do nothing.
     */
    public void RightClickXH(int r, int c)
    {
        int size = game.size();
        int canvas_size = (size+1)*40;
        int offset_x1 = canvas_size / (size+1);
        int offset_y1 = canvas_size / (size+1);
        game.RightClickXH(r,c);
        sc.setFont(new Font("Arial",Font.BOLD,15));
        if (game.getXClickH()[r][c] == true){
           sc.drawString("x", 37+offset_x1*c, 25+offset_y1*r, red);
        } else if (game.getXClickH()[r][c] == false){
           sc.drawString("x", 37+offset_x1*c, 25+offset_y1*r, bgColor);
        }
    }
   
    /**
     * Actions for a mouse press.
     * Finds nea
     */
    public void mousePressed(MouseEvent e) 
    {
        int size = game.size();
        int canvas_size = (size+1)*40;
        int x = e.getX()-20;
        int y = e.getY()-20;
        int offset_x1 = canvas_size / (2*(size+1));
        int offset_y1 = canvas_size / (2*(size+1));
        System.out.println(e.getX()+" "+e.getY());
        if (e.getButton() == 3){
            if (x >= 0 && x <= size*40 && y >= 0 && y <= size*40){
              for (int i = 0; i <= size; i++){
                offset_y1 = canvas_size/(2*(size+1)) + (canvas_size/(size+1))*i;
                for (int j = 0; j <= size; j++){
                    offset_x1 = canvas_size/(2*(size+1)) + (canvas_size/(size+1))*j;        
                    double dist = Math.sqrt(Math.pow(x-(offset_x1-20),2)+Math.pow(y-(offset_y1-20),2)); 
                    int distx = x-(offset_x1-20);
                    int disty = y-(offset_y1-20);
                    int xi = ((offset_y1-20)/40);
                    int yi = ((offset_x1-20)/40);
                    if (dist < 20 && distx > 0 && disty > 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getHorizontal()[xi][yi] == true){
                            horizontalClick(xi,yi);
                        }
                        RightClickXH(xi,yi);
                    } else if (dist < 20 && distx > 0 && disty > 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getVertical()[xi][yi] == true){
                            verticalClick(xi,yi);
                        }
                        RightClickXV(xi,yi);
                    } else if (dist < 20 && distx > 0 && disty < 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getVertical()[xi-1][yi] == true){
                            verticalClick(xi-1,yi);
                        }
                        RightClickXV(xi-1,yi);
                    } else if (dist < 20 && distx > 0 && disty < 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getHorizontal()[xi][yi] == true){
                            horizontalClick(xi,yi);
                        }
                        RightClickXH(xi,yi);
                    } else if (dist < 20 && distx < 0 && disty < 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getHorizontal()[xi][yi-1] == true){
                            horizontalClick(xi,yi-1);
                        }
                        RightClickXH(xi,yi-1);
                    } else if (dist < 20 && distx < 0 && disty < 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getVertical()[xi-1][yi] == true){
                            verticalClick(xi-1,yi);
                        }
                        RightClickXV(xi-1,yi);
                    } else if (dist < 20 && distx < 0 && disty > 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getVertical()[xi][yi] == true){
                            verticalClick(xi,yi);
                        }
                        RightClickXV(xi,yi);
                    } else if (dist < 20 && distx < 0 && disty > 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getHorizontal()[xi][yi-1] == true){
                            horizontalClick(xi,yi-1);
                        }
                        RightClickXH(xi,yi-1);
                    }
                    }
              }
            }
        } else if (e.getButton() == 1){
            if (x >= 0 && x <= size*40 && y >= 0 && y <= size*40){
            for (int i = 0; i <= size; i++){
                offset_y1 = canvas_size/(2*(size+1)) + (canvas_size/(size+1))*i;         
                for (int j = 0; j <= size; j++){
                    offset_x1 = canvas_size/(2*(size+1)) + (canvas_size/(size+1))*j;          
                    double dist = Math.sqrt(Math.pow(x-(offset_x1-20),2)+Math.pow(y-(offset_y1-20),2)); 
                    int distx = x-(offset_x1-20);
                    int disty = y-(offset_y1-20);
                    int xi = ((offset_y1-20)/40);
                    int yi = ((offset_x1-20)/40);
                    if (dist < 20 && distx > 0 && disty > 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getXClickH()[xi][yi] == true){
                            RightClickXH(xi,yi);
                        }
                        horizontalClick(xi,yi);
                    } else if (dist < 20 && distx > 0 && disty > 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getXClickV()[xi][yi] == true){
                            RightClickXV(xi,yi);
                        }
                        verticalClick(xi,yi);
                    } else if (dist < 20 && distx > 0 && disty < 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getXClickV()[xi-1][yi] == true){
                            RightClickXV(xi-1,yi);
                        }
                        verticalClick(xi-1,yi);
                    } else if (dist < 20 && distx > 0 && disty < 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getXClickH()[xi][yi] == true){
                            RightClickXH(xi,yi);
                        }
                        horizontalClick(xi,yi);
                    } else if (dist < 20 && distx < 0 && disty < 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getXClickH()[xi][yi-1] == true){
                            RightClickXH(xi,yi-1);
                        }
                        horizontalClick(xi,yi-1);
                    } else if (dist < 20 && distx < 0 && disty < 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getXClickV()[xi-1][yi] == true){
                            RightClickXV(xi-1,yi);
                        }
                        verticalClick(xi-1,yi);
                    } else if (dist < 20 && distx < 0 && disty > 0 && Math.abs(distx) < Math.abs(disty)){
                        if (game.getXClickV()[xi][yi] == true){
                            RightClickXV(xi,yi);
                        }
                        verticalClick(xi,yi);
                    } else if (dist < 20 && distx < 0 && disty > 0 && Math.abs(distx) > Math.abs(disty)){
                        if (game.getXClickH()[xi][yi-1] == true){
                            RightClickXH(xi,yi-1);
                        }
                        horizontalClick(xi,yi-1);
                    }
                }
             }  
        } else if (e.getX() >= canvas_size/2-36 && e.getX() <= canvas_size/2+36 && e.getY() <= canvas_size+60 && e.getY() >= canvas_size+35) {
            offset_x1 = canvas_size / (size+1);
            offset_y1 = canvas_size / (size+1);
            game.clear();
            for (int r = 0; r <= size; r++){
              for (int c = 0; c < size; c++){
              sc.drawLine(25+offset_x1*c,20+offset_y1*r,offset_x1*c+55,20+offset_y1*r,bgColor);  
              sc.drawString("x", 37+offset_x1*c, 25+offset_y1*r, bgColor);
             }
            }
            for (int r = 0; r < size; r++){
             for (int c = 0; c <= size; c++){
               sc.drawLine(20+offset_x1*c,25+offset_y1*r,20+offset_x1*c,offset_y1*r+55,bgColor);
               sc.drawString("x", 17+offset_x1*c, 44+offset_y1*r, bgColor);
             }
            }
          } else if (e.getX() >= canvas_size/2-40 && e.getX() <= canvas_size/2+40 && e.getY() <= canvas_size+90 && e.getY() >= canvas_size+64){
              if (AnalyzeSolution.finished(game) != "Finished"){
                  sc.setFont(new Font("Arial",Font.ITALIC,15));  
                  sc.drawString("Incorrect!", canvas_size/2-30, canvas_size+110, red);
                  sc.drawString(AnalyzeSolution.finished(game), canvas_size/2-50, canvas_size+130, red);
              } else{
                  sc.setFont(new Font("Arial",Font.ITALIC,15));
                  sc.drawString("Good job!", canvas_size/2-30,canvas_size+110, green);
              }
           }
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {
        
    }
}
