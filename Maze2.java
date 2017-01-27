import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Maze2 implements KeyListener{
   private JFrame frame;
   private JPanel panel;
   private JTextField field;

   private int[][] maze;
   private int xLoc, yLoc;
   private ArrayList<String> history;
   private String options;

   public Maze2(){
      
      buildDisplay();
      getMazeFile();
      startGame();
   }

   public static void main (String[] args) throws IOException{
      new Maze2();
      }

   public void buildDisplay(){
      frame = new JFrame();
      frame.setSize(111,111);
      frame.setLocation(-100,-100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setFocusable(true);
      panel = new JPanel();
      field = new JTextField(1);
      field.addKeyListener(this);
      panel.add(field);
      frame.add(panel);
   }
   
   public void move(int x, int y){
      if(x == -1 && options.contains("left")){xLoc--; history.add("left");}
      else if(x == 1 && options.contains("right")){xLoc++; history.add("right");}
      else if(y == -1 && options.contains("up"))  {yLoc--; history.add("up");}
      else if(y == 1 && options.contains("down")) {yLoc++; history.add("down");}
      else{System.out.print("invalid move! ");}
      if(maze[yLoc][xLoc] == -1){
         System.out.printf("Congratulations! You Escaped in %d moves!\n",history.size());
         System.out.println("You Moved: " + history);
         System.exit(0);
      }
      showOptions();
   }

   public void getMazeFile(){
      try{
         File file = new File(getFileInput());
         Scanner inputFile = new Scanner(file);
         maze = new int[inputFile.nextInt()][inputFile.nextInt()];
         inputFile.nextLine();//consumes an empty line left by nextInt
         int row = 0;
         while(inputFile.hasNext()){
            String input = inputFile.nextLine();
            for(int i = 0; i < input.length(); i++){
               if(input.charAt(i) == 'x')
                  maze[row][i] = 1;
               else if(input.charAt(i) == 'f')
                  maze[row][i] = -1;
            }
         row++;
         }
      }catch(IOException e){
         System.out.println("invalid maze file");
         System.exit(1);
      }
   }
   
   public void startGame(){
      history = new ArrayList<String>();
      Random random = new Random();
      boolean test = true;
      while(test){
         yLoc = random.nextInt(maze.length);
         xLoc = random.nextInt(maze[0].length);
         if(maze[yLoc][xLoc] == 0){
            test = false;
         }
      }
      System.out.println(xLoc + ", " + yLoc);
      options = "";
      frame.setVisible(true);
      showOptions();
   }
   
   public void showOptions(){
      getOptions();
      System.out.println("Options: " + options);
      field.grabFocus();
   }

   public void showHistory(){
      System.out.println("Move History: " + history);
      showOptions();
   }

   public void getOptions(){
      options = "";
      if(maze[yLoc-1][xLoc]!=1)options += "up, ";
      if(maze[yLoc+1][xLoc]!=1)options += "down, ";
      if(maze[yLoc][xLoc-1]!=1)options += "left, ";
      if(maze[yLoc][xLoc+1]!=1)options += "right, ";
   }

   public String getFileInput(){
      String filename = "";
      Scanner keyboard = new Scanner(System.in);
      boolean test = true;
      while(test){
         System.out.print("please enter a maze file name.");
         filename = keyboard.nextLine();
         if(filename == null)filename = "maze1.txt";
         try{
            File testFile = new File(filename);
            Scanner testScan = new Scanner(testFile);
            test = false;
         }
         catch(IOException e){
            try{
               filename += ".txt";
               File testFile = new File(filename);
               Scanner testScan = new Scanner(testFile);
               test = false;
            }
            catch(IOException ex){
            }
         }
      }
      keyboard.close();
      return filename;
   }

   public void keyPressed(KeyEvent e){
      System.out.println(e.getKeyCode());
      switch(e.getKeyCode())
         {
         case 37: move(-1,0);break;
         case 38: move(0,-1);break;
         case 39: move(1,0);break;
         case 40: move(0,1);break;
         default: showHistory();
       }
   }
   public void keyTyped(KeyEvent e) {}
   public void keyReleased(KeyEvent e) {}
}
