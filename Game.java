import java.io.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.Font;


class Game extends JPanel
{
  public static ArrayList<Player> players= new ArrayList<Player>();
  public static ArrayList<String> possible_characters= new ArrayList<String>(Arrays.asList("Professor Plum", "Mrs. White", "Mrs. Scarlett","Mrs. Peacock","Colonel Mustard","Mr. Green"));
  public Deck d;
  public static int num_players;
  boolean has_begun;
  final static Stack<String> holder= new Stack<String>();

  final static JFrame frame = new JFrame("Clue");


  public static JTextField field = new JTextField(20);
  //public static JTextArea textArea=new JTextArea(5, 20);
  public static JTextPane textArea=new JTextPane();
  final static String newline = "\n";

  public Game()
  {
    super(new GridBagLayout());
    d= new Deck(num_players);
    has_begun=false;
    textArea.setEditable(false);
  //  textArea.setFont(new Font("Serif", Font.PLAIN, 14));
    //textArea.setLineWrap(true);
    //textArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
        //  scrollPane.setViewportView(textArea);

          c.weightx = 0;
          c.weighty = 0;
          c.fill = GridBagConstraints.HORIZONTAL;
          add(field, c);
  }
  public static void add(String s)
  {
    Document doc = textArea.getDocument();
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.black);
    try
    {
      textArea.getStyledDocument().insertString(doc.getLength(), s, aset);
    }
    catch(Exception e) { System.out.println(e); }

  }
  public static void add(String s, Color c)
  {
    Document doc = textArea.getDocument();
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
    try
    {
      textArea.getStyledDocument().insertString(doc.getLength(), s, aset);
    }
    catch(Exception e) { System.out.println(e); }

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
           textArea.setText("Welcome to Clue!"+newline+"Enter 'Begin' to play or 'Instructions' to see gameplay instructions"+newline);
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
       add(input+newline+newline, Color.green);
       textArea.setCaretPosition(textArea.getDocument().getLength());
       field.setText("");
       //before we return we need to see if input is something like 'help'
       //handle it, then await_response again
       return input;
  }
  public static void clear_screen()
  {
    textArea.setText("");
    textArea.setCaretPosition(textArea.getDocument().getLength());
  }
  public static void do_intro()
  {
    clear_screen();
    String intro="You have arrived at the Tudor Mansion. Your host, suave playboy Mr. John Boddy, has met an untimely end- he's the victim of foul play. To win this game, you must determine the answer to these three questions: Who done it? Where? And with what Weapon?";
    add(intro+newline);
    add("Upon arrival you take some time to examine your fellow dinner guests: "+newline+newline);
    for(int i=0; i<possible_characters.size(); i++)
    {
      add(possible_characters.get(i)+", ");
      Person p=new Person(possible_characters.get(i));
      add(p.getBio()+newline+newline);
    }
    add(newline+"You make note of each guests in your notebook. You can view this throughout the game by entering 'Notebook' or just 'N'.", Color.red);
  }
  public static void start_turns()
  {
    //while murder is unsolved, loop here somehow
  }
  public static void begin()
  {
    int index=-1;
    boolean valid_input=true;
    add("How many players in your party? Enter 1 or 2"+newline);
    String response=await_response();
    try
    {
      num_players=Integer.parseInt(response);
    }
    catch (NumberFormatException e)
     {
         valid_input=false;
     }
     while(!valid_input)
     {
       try
       {
         num_players=Integer.parseInt(response);
         valid_input=true;
       }
       catch (NumberFormatException e)
        {
            valid_input=false;
        }
     }

    for(int i=1; i<num_players+1; i++)
    {
      add("Player "+i+" , choose your character by entering the corresponding number:"+newline);
      for(int k=0; k<possible_characters.size(); k++)
      {
        add("("+(k+1)+")"+possible_characters.get(k)+newline);
      }
      String input=await_response();
      try
      {
        index=Integer.parseInt(input);
      }
      catch (NumberFormatException e)
       {
           valid_input=false;
       }
      while(index>possible_characters.size() || index<0 || !valid_input)
      {
        add("Please enter a valid number"+newline);
        input=await_response();
        try
        {
          index=Integer.parseInt(input);
          valid_input=true;
        }
        catch (NumberFormatException e)
         {
             valid_input=false;
         }
      }
      players.add(new Player(possible_characters.get(index-1)));
      possible_characters.remove(index-1);
    }
    add(response+" players"+newline);
    do_intro();
    start_turns();

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
