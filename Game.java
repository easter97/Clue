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
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


class Game extends JPanel
{
  public static ArrayList<Player> players= new ArrayList<Player>();
  public static ArrayList<String> possible_characters= new ArrayList<String>(Arrays.asList("Professor Plum", "Mrs. White", "Mrs. Scarlett","Mrs. Peacock","Colonel Mustard","Mr. Green"));
  public static ArrayList<String> event_buffer = new ArrayList<String>();
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
  public static boolean unsolved=true;

  public Game()
  {
    super(new GridBagLayout());
    //FIXME: Return array of cards for each player or keep in class?
    has_begun=false;
    textArea.setEditable(false);
    textArea.setFont(new Font("Serif", Font.PLAIN, 18));
    textArea.setBackground(new Color(255, 255, 204));
    toolBar.setBackground(new Color(0 ,102, 0));

    JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    panel.add(toolBar,BorderLayout.PAGE_START);

    toolBar.addSeparator(new Dimension(300, 0));

    JButton notepad = new JButton("Show Notebook");
    JButton map = new JButton("Show Map");

    notepad.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            do_show_notebook();
        }
    });
    map.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            do_show_map();
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
    toolBar.add(map);
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
    notepad_frame.setSize(300,500);
    JTextPane padArea=new JTextPane();
    padArea.setBackground(new Color(255,255,204));
    if(current_player==-1)
    {
      padArea.setText("You cannot view the detective notebook at this time");
    }
    else{
      Player current=players.get(current_player);
      padArea.setText(d.show_notebook(current_player, current.getName()));
    }
    padArea.setEditable(false);
    notepad_frame.add(padArea);
    //FIXME: this needs to redraw if left open
  }
  public void do_show_map()
  {
    //d.show_notebook(current_player);
    JFrame map_frame = new JFrame();
    map_frame.setVisible(true);
    map_frame.setSize(500,509);
    JTextPane mapArea=new JTextPane();
    mapArea.setBackground(new Color(255,255,204));
    String file_path="clue-board.jpg";
    try
    {
      BufferedImage myPicture = ImageIO.read(new File(file_path));
      mapArea.insertIcon ( new ImageIcon ( myPicture ) );
    }catch(IOException e)
    {
      System.out.println(e);
    }

    mapArea.setEditable(false);
    map_frame.add(mapArea);
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
           textArea.setText("\nWelcome to Clue!"+newline+"Enter 'Begin' to play or 'Instructions' to see gameplay instructions"+newline);
           String file_path="Clue-Poster.png";
           try
           {
             BufferedImage myPicture = ImageIO.read(new File(file_path));
             textArea.insertIcon ( new ImageIcon ( myPicture ) );
           }catch(IOException e)
           {
             System.out.println(e);
           }
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
    else if(input.equals("instructions"))
    {
      add("To view your player's notebook, select the Show Notebook button in the toolbar."+newline);
      add("To view your a map of the mansion, select the Show Map button in the toolbar."+newline);
      add("To view your gain evidence during gameplay make a suggestion in the room you'd like to suggest."+newline);
      add("To end the game, make an accusation at any place or time you'd like. Be careful, if you're wrong you're out of the game!"+newline);
      add("Type \'Begin\' to begin the game"+newline, Color.red);
      input=await_response();
      handleEvent(input);
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
      await_response();
      //FIXME: Press enter to continue, add second bio parts
    }
    add(newline+"You make note of each guests in your notebook. You can view this throughout the game by clicking the 'Show Notebook' button in your toolbar"+newline, Color.red);
    add("Press enter to continue..."+newline, Color.red);
    await_response();
  }
  public static void choose_room()
  {
    //FIXME: set the destination location, achevied with dest roll >= current roll
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
      current.setDestLocation("Conservatory");
    }
    else if(response.equals("billard room"))
    {
      current.setDestLocation("Billard Room");
    }
    else if(response.equals("kitchen"))
    {
      current.setDestLocation("Kitchen");
    }
    else if(response.equals("library"))
    {
      current.setDestLocation("Library");
    }
    else if(response.equals("study"))
    {
      current.setDestLocation("Study");
    }
    else if(response.equals("lounge"))
    {
      current.setDestLocation("Lounge");
    }
    else if(response.equals("dining room"))
    {
      current.setDestLocation("Dining Room");
    }
    else if(response.equals("ballroom"))
    {
      current.setDestLocation("Ballroom");
    }
    else if(response.equals("hall"))
    {
      current.setDestLocation("Hall");
    }
    else
    {
      add("Please enter a valid selection."+newline, Color.red);
      choose_room();
    }
    current.setLocation("Hallway"); //in transit!
    current.setRoll(0);
  }
  public static void roll()
  {
    //FIXME: implement roll logic
    Player current=players.get(current_player);
    if(current.getRoll()==-1)
    {
      //You haven't rolled yet
      choose_room();
    }

    add("You need "+(current.getDestRoll()-current.getRoll())+" paces to get to the "+current.getDestLocation()+newline);

    ArrayList<Integer> die_faces = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));

    Random rand = new Random();
    int rand_num = rand.nextInt(6-1+1) + 1;
    String file_path = "die"+Integer.toString(rand_num)+".png";
    new SoundClip("dice_roll.wav");


    // JFrame die_face_frame = new JFrame();

    try
    {
      BufferedImage myPicture = ImageIO.read(new File(file_path));
      textArea.insertIcon ( new ImageIcon ( myPicture ) );
      add(newline);
      // JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      //
      // die_face_frame.add(picLabel);
      // die_face_frame.setVisible(true);
      // die_face_frame.setSize(200,200);
    }catch(IOException e)
    {
      System.out.println(e);
    }
    //FIXME: Add rand_num to the current roll, check if dest_location is achieved, then set it
    current.setRoll(current.getRoll()+rand_num);
    if(current.getDestRoll()<=current.getRoll())
    {
      current.setLocation(current.getDestLocation());
      add("You are now in the "+current.getLocation()+newline, Color.red);
      current.setRoll(-1);
      event_buffer.add(current.getName() + " rolled the dice for " + rand_num + " and arrived at the " + current.getDestLocation() + ".");
      suggest(); //let's assume they want to accuse someone as soon as they get there.
    }
    else{
      add("You have "+(current.getDestRoll()-current.getRoll())+" paces to get to the "+current.getDestLocation()+newline);
      event_buffer.add(current.getName() + " rolled the dice for " + rand_num + " and is " + (current.getDestRoll()-current.getRoll()) + " spaces away from the " +
      current.getDestLocation()+newline);
    }
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
    //They have to make a suggestion about the room they are currently in
    suggested_room=new Room(players.get(current_player).getLocation());
    //FIXME: Push this suggestion back to the buffer
    add(players.get(current_player).getName()+": I think it was "+suggested_murderer.getName()+" with the "+suggested_weapon.getName()+" in the "+suggested_room.getName()+newline, Color.blue);
    add("Now your fellow detectives will see if they can disprove your suggestion"+newline, Color.red);

    event_buffer.add(players.get(current_player).getName() + " suggested " + suggested_murderer.getName() + " committed the murder with the " +  suggested_weapon.getName() +
    " in the " + suggested_room.getName() + ".");

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
        clear_screen();
        //set current player to be disprove player, temporarily!
        int true_player=current_player;
        current_player=disprove_player;
        //list disproved cards, let player pick
        add("What evidence would you like to reveal to "+players.get(true_player).getName()+"?"+newline, Color.red);
        ArrayList<Card> disproven=d.get_disprove(disprove_player, suggested_murderer, suggested_weapon, suggested_room);
        for(int i=0; i<disproven.size(); i++)
        {
          add("("+(i+1)+") "+disproven.get(i).getName()+newline);
        }
        while( !valid_input )
        {
          String input=await_response();

          try
          {
            index=Integer.parseInt(input);
            index=index-1;
            if(index>disproven.size() || index<0)
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
        d.disprove(true_player, disproven.get(index));
        current_player=true_player;
        clear_screen();
        add(players.get(disprove_player).getName()+" disproved your solution with "+disproven.get(index).getName()+"."+newline);
        //To output alibi for disproven card
        add(disproven.get(index).getAlibi()+newline);
        event_buffer.add(players.get(current_player).getName()+"\'s suggestion was disproved by "+players.get(disprove_player).getName());
        //FIXME: Add alibi here!
        add("This evidence has been marked in your notebook."+newline, Color.red);
        return;
      }
      else{
        add(players.get(disprove_player).getName()+" can not disprove"+newline, Color.red);
      }
      //otherwise keep going
      disprove_player++;
    }
  }
  public static boolean accuse()
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
    return d.check_solution(accused_murderer, accused_weapon, accused_room);
  }
  public static void check_response(String response)
  {
    Player current=players.get(current_player);
    if(response.equals("roll"))
    {
      roll();
    }
    else if(response.equals("suggestion") && !current.getLocation().equals("Hallway"))
    {
      suggest();
    }
    else if(response.equals("accusation") && !current.getLocation().equals("Hallway"))
    {
     boolean result=accuse();
     if(result)
     {
       unsolved=false;
       add("Game Over");
     }
     else{
       add("Incorrect Accusation");
       players.remove(current_player);
       if(players.size()<2)
       {
         unsolved=false;
          add("Game Over");
       }
     }
    }
    else if(response.equals("passageway") && current.getLocationRoom().getPassageway()!=null)
    {
      current.setLocation(current.getLocationRoom().getPassageway());
      add("You are currently in the "+current.getLocation()+". What do you want to do next?"+newline);
      add(current.getMoves()+newline);
      response=await_response().toLowerCase();
      //Check response and do action
      check_response(response);
    }
    else
    {
      add("Please enter a valid command"+newline);
      response=await_response().toLowerCase();
      check_response(response);
    }
  }

  public static void printEventBuffer(){
    String name = players.get(current_player).getName();
    String event;

    for(int i = 0; i < event_buffer.size(); i++){
      //if the event in buffer corresponds to current player's turn, remove it.
      event = event_buffer.get(i);
      if(event.substring(0, 5).equals(name.substring(0, 5))){
        event_buffer.remove(i);
        i--;
      }
      else{
        add(event_buffer.get(i) + newline);
      }
    }
  }

  public static void start_turns()
  {
    current_player=0;
    String response;
    //while murder is unsolved, loop here somehow
    //make sure to update current_player as we go
    //while(game is still not solved)
    //do turn
    //increment player id, or reset it to the first player
    while(unsolved)
    {
      clear_screen();
      //FIXME: Maybe print previous player's turn?? Have an update buffer that prints here?
      printEventBuffer();
      Player current=players.get(current_player);
      add(newline+newline+"It is "+current.getName()+"\'s turn."+newline, Color.red);
      add("You are currently in the "+current.getLocation()+". What do you want to do next?"+newline);
      add(current.getMoves()+newline);
      response=await_response().toLowerCase();
      //Check response and do action
      check_response(response);
      if(unsolved)
      {
        add("Press enter to end your turn."+newline, Color.red);
        await_response();
      }
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
    add("How many players in your party? Enter a number from 2 to 6"+newline);
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
         if(num_players>6 || num_players<2)
         {
           valid_input=false;
         }
         else{
            valid_input=true;
         }
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
