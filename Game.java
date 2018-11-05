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
  public static Deck d;
  public static int num_players;
  //we need this so the whole program knows who's turn it is
  public static int current_player=-1;
  boolean has_begun;
  final static Stack<String> holder= new Stack<String>();

  final static JFrame frame = new JFrame("Clue");
  public static JPanel panel = new JPanel(new BorderLayout());
  public static JToolBar toolBar = new JToolBar();


  public static JTextField field = new JTextField(20);
  public static JTextPane textArea=new JTextPane();
  final static String newline = "\n";

  public Game()
  {
    super(new GridBagLayout());
    //FIXME: Return array of cards for each player or keep in class?
    has_begun=false;
    textArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    panel.add(toolBar,BorderLayout.PAGE_START);

    toolBar.addSeparator(new Dimension(300, 0));

    JButton notepad = new JButton("Show Notebook");

    notepad.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            do_show_notebook();
        }
    });
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
    toolBar.add(notepad);
    GridBagConstraints c = new GridBagConstraints();
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.fill = GridBagConstraints.HORIZONTAL;
      add(toolBar, c);
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
  public void do_show_notebook()
  {
    //d.show_notebook(current_player);
    JFrame notepad_frame = new JFrame();
    notepad_frame.setVisible(true);
    notepad_frame.setSize(300,600);
    JTextPane padArea=new JTextPane();
    if(current_player==-1)
    {
      padArea.setText("You cannot view the detective notebook at this time");
    }
    else{
      padArea.setText(d.show_notebook(current_player));
    }
    padArea.setEditable(false);
    notepad_frame.add(padArea);
    //FIXME: this needs to redraw if left open
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
    textArea.setCaretPosition(textArea.getDocument().getLength());
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
    textArea.setCaretPosition(textArea.getDocument().getLength());
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
    //compares input agains key commands
    //FIXME: maybe use this to check for notebook, roll, and guessing?
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
       add(input+newline+newline, Color.blue);
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
    add(newline+"You make note of each guests in your notebook. You can view this throughout the game by clicking the 'Show Notebook' button in your toolbar", Color.red);
  }
  public static void choose_room()
  {
    Player current=players.get(current_player);
    add("Where would you like to go?"+newline);
    add(d.getRooms(current.getLocation()));
    String response=await_response();
    response=response.toLowerCase();
    if(response.equals(current.getLocation()))
    {
      add("You're already in the "+current.getLocation()+". Please choose another location."+newline, Color.red);
    }
    else if(response.equals("conservatory"))
    {
      current.setLocation("Conservatory");
    }
    else if(response.equals("billard room"))
    {
      current.setLocation("Billard Room");
    }
    else if(response.equals("kitchen"))
    {
      current.setLocation("Kitchen");
    }
    else if(response.equals("library"))
    {
      current.setLocation("Library");
    }
    else if(response.equals("study"))
    {
      current.setLocation("Study");
    }
    else if(response.equals("lounge"))
    {
      current.setLocation("Lounge");
    }
    else if(response.equals("dining room"))
    {
      current.setLocation("Dining Room");
    }
    else if(response.equals("ballroom"))
    {
      current.setLocation("Ballroom");
    }
    else
    {
      add("Please enter a valid selection."+newline, Color.red);
      choose_room();
    }
  }
  public static void roll()
  {
    //FIXME: implement roll logic
    Player current=players.get(current_player);
    //if you haven't rolled yet
    choose_room();
    add("You are now in the "+current.getLocation()+newline, Color.red);
  }
  public static void suggest()
  {
    Person suggested_murderer;
    Weapon suggested_weapon;
    Room suggested_room;
    boolean valid_input=false;
    int index=-1;
    int disprove_player=current_player+1;
    boolean disproved=false;

    while( !valid_input )
    {
      add("Who do you think committed the murder?"+newline, Color.red);
      add(d.show_people());
      String input=await_response();

      try
      {
        index=Integer.parseInt(input);
        index=index-1;
        if(index>5 || index<0)
        {
          valid_input=false;
          add("Please enter a valid number."+newline, Color.red);
        }
        else
        {
          valid_input=true;
        }
      }
      catch (NumberFormatException e)
       {
           valid_input=false;
       }

    }
    suggested_murderer=d.get_people(index);
    valid_input=false;
    while( !valid_input )
    {
      add("What weapon do you think "+suggested_murderer.getName()+" used?"+newline, Color.red);
      add(d.show_weapons());
      String input=await_response();

      try
      {
        index=Integer.parseInt(input);
        index=index-1;
        if(index>5 || index<0)
        {
          valid_input=false;
          add("Please enter a valid number."+newline, Color.red);
        }
        else
        {
          valid_input=true;
        }
      }
      catch (NumberFormatException e)
       {
           valid_input=false;
       }

    }
    suggested_weapon=d.get_weapons(index);
    valid_input=false;

    suggested_room=new Room(players.get(current_player).getLocation());
    //FIXME: Push this suggestion back to the buffer
    add(players.get(current_player).getName()+": I think it was "+suggested_murderer.getName()+" with the "+suggested_weapon.getName()+" in the "+suggested_room.getName()+newline, Color.blue);
    add("Now your fellow detectives will see if they can disprove your suggestion"+newline, Color.red);

    while(disprove_player!=current_player)
    {
      if(disprove_player>=num_players)
      {
        disprove_player=0;
      }

      if(disprove_player==current_player)
      {
        add("Your suggestion could not be disproved"+newline, Color.blue);
        disproved=true;
        break;
      }
      add("It is "+players.get(disprove_player).getName()+"'s turn to disprove "+players.get(current_player).getName()+"'s suggestion."+newline, Color.red);
      //FIXME: Check the player deck for each 3 elements of suggestion, if more than one exists
      int num_cards=d.test_disprove(disprove_player, suggested_murderer, suggested_weapon, suggested_room);
      if(num_cards>0)
      {
        //they can disprove and we break
        add(players.get(disprove_player).getName()+" can disprove, let this player press enter to select what evidence to reveal."+newline);
        await_response();

        break;
      }
      else{
        add(players.get(disprove_player).getName()+" can not disprove"+newline, Color.red);
      }
      //otherwise keep going
      disprove_player++;
    }
  }
  public static void accuse()
  {
    Person accused_murderer;
    Weapon accused_weapon;
    Room accused_room;
    boolean valid_input=false;
    int index=-1;
    int disprove_player=current_player+1;
    boolean disproved=false;

    while( !valid_input )
    {
      add("Who do you accuse of murder?"+newline, Color.red);
      add(d.show_people());
      String input=await_response();

      try
      {
        index=Integer.parseInt(input);
        index=index-1;
        if(index>5 || index<0)
        {
          valid_input=false;
          add("Please enter a valid number."+newline, Color.red);
        }
        else
        {
          valid_input=true;
        }
      }
      catch (NumberFormatException e)
       {
           valid_input=false;
       }

    }
    accused_murderer=d.get_people(index);
    valid_input=false;
    while( !valid_input )
    {
      add("What weapon do you think "+accused_murderer.getName()+" used?"+newline, Color.red);
      add(d.show_weapons());
      String input=await_response();

      try
      {
        index=Integer.parseInt(input);
        index=index-1;
        if(index>5 || index<0)
        {
          valid_input=false;
          add("Please enter a valid number."+newline, Color.red);
        }
        else
        {
          valid_input=true;
        }
      }
      catch (NumberFormatException e)
       {
           valid_input=false;
       }

    }
    accused_weapon=d.get_weapons(index);
    valid_input=false;
    while( !valid_input )
    {
      add("Were do you think "+accused_murderer.getName()+" committed the murder?"+newline, Color.red);
      add(d.show_rooms());
      String input=await_response();

      try
      {
        index=Integer.parseInt(input);
        index=index-1;
        if(index>8 || index<0)
        {
          valid_input=false;
          add("Please enter a valid number."+newline, Color.red);
        }
        else
        {
          valid_input=true;
        }
      }
      catch (NumberFormatException e)
       {
           valid_input=false;
       }

    }
    accused_room=d.get_rooms(index);
    //FIXME: Push this suggestion back to the buffer
    add(players.get(current_player).getName()+": I think it was "+accused_murderer.getName()+" with the "+accused_weapon.getName()+" in the "+accused_room.getName()+newline, Color.blue);
    //FIXME: Check against solution deck, player is out if wrong, wins if right
  }
  public static void check_response(String response)
  {
    Player current=players.get(current_player);
    if(response.equals("roll"))
    {
      roll();
    }
    else if(response.equals("suggestion") && !current.getLocation().equals("Hall"))
    {
      suggest();
    }
    else if(response.equals("accusation") && !current.getLocation().equals("Hall"))
    {
    //  accuse();
    }
    else
    {
      add("Please enter a valid command"+newline);
      response=await_response().toLowerCase();
      check_response(response);
    }
  }
  public static void start_turns()
  {
    boolean unsolved=true;
    current_player=0;
    String response;
    //while murder is unsolved, loop here somehow
    //make sure to update current_player as we go
    //while(game is still not solved)
    //do turn
    //increment player id, or reset it to the first player
    while(unsolved)
    {
      //FIXME: Maybe print previous player's turn?? Have an update buffer that prints here?
      Player current=players.get(current_player);
      add(newline+newline+"It is "+current.getName()+"\'s turn."+newline, Color.red);
      add("You are currently in the "+current.getLocation()+". What do you want to do next?"+newline);
      add(current.getMoves()+newline);
      response=await_response().toLowerCase();
      //Check response and do action
      check_response(response);
      //Next Player logic
      current_player++;
      if(current_player>=num_players)
      {
        current_player=0;
      }
    }
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
     d= new Deck(num_players);
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
      players.add(new Player(possible_characters.get(index-1), i));
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
