import java.io.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Game extends JPanel
{
  public static ArrayList<Player> players= new ArrayList<Player>();
  public Deck d;
  public static int num_players;
  boolean has_begun;
  final static Stack<String> holder= new Stack<String>();

  final static JFrame frame = new JFrame("Clue");

  public static JTextField field = new JTextField(20);
  public static JTextArea textArea=new JTextArea(5, 20);
  final static String newline = "\n";

  public Game()
  {
    super(new GridBagLayout());
    d= new Deck(num_players);
    has_begun=false;
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);

    field.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (holder) {
                holder.push(field.getText());
                holder.notify();
            }
            // frame.dispose();
        }
    });
    GridBagConstraints c = new GridBagConstraints();
          c.gridwidth = GridBagConstraints.REMAINDER;



          c.fill = GridBagConstraints.BOTH;
          c.weightx = 1.0;
          c.weighty = 1.0;
          add(scrollPane, c);

          c.weightx = 0;
          c.weighty = 0;
          c.fill = GridBagConstraints.HORIZONTAL;
          add(field, c);
  }
  public static void createAndShowGUI() {
           //Create and set up the window.

           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

           //Add contents to the window.
           frame.add(new Game());

           //Display the window.
           frame.pack();
            frame.setSize(800, 800);
           frame.setVisible(true);
           textArea.append("Welcome to Clue!"+newline+"Enter 'Begin' to play or 'Instructions' to see gameplay instructions"+newline);
           String input=await_response();
           handleEvent(input);
       }
  public static void handleEvent(String input)
  {

    input=input.toLowerCase();
    if(input.equals("begin"))
    {
      begin();
    }
  }
  public static String await_response()
  {
    while (holder.isEmpty())
    {
      try
      {
        holder.wait();
      }
      catch(Exception e)
      {
        //oops
      }
    }

       String input = holder.pop();
       textArea.append(input+newline);
       textArea.setCaretPosition(textArea.getDocument().getLength());
       field.setText("");
       return input;
  }
  public static void begin()
  {
    textArea.append("How many players in your party? Enter 1 or 2"+newline);
    String response=await_response();
    num_players=Integer.parseInt(response);
    for(int i=1; i<num_players+1; i++)
    {
      textArea.append("Player "+i+" , choose your character:"+newline);
      response=await_response();
      players.add(new Player(response));
    }
    textArea.append(response+" players"+newline);

  }
  public void print_help()
  {
    System.out.println("List of allowed actions: ");
    System.out.println("(I) Instructions");
    System.out.println("(H) Help");
    System.out.println("(Q) End Game");
  }
  public static void instructions()
  {
    System.out.println("Here's how to play: ");
  }
  public static void main(String[] args) throws Exception {
    createAndShowGUI();
    // "logic" thread
    // synchronized (holder) {
    //
    //     // wait for input from field
    //     while(true)
    //     {
    //       while (holder.isEmpty())
    //           holder.wait();
    //
    //       String nextInt = holder.pop();
    //       handleEvent(nextInt);
    //       //System.out.println(nextInt);
    //       //holder.notify();
    //       //....
    //     }
    //
    // }

  }

}
