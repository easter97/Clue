import java.io.*;
import java.util.*;
//import Card.java;

class Deck
{
  public int num_players;
  public ArrayList<Card> main_deck=new ArrayList<Card>();
  public ArrayList<Card> solution_deck=new ArrayList<Card>();
  public ArrayList<Person> person_deck= new ArrayList<Person>();
  public ArrayList<Weapon> weapon_deck= new ArrayList<Weapon>();
  public ArrayList<Room> room_deck= new ArrayList<Room>();
  public ArrayList<Person> const_person_deck= new ArrayList<Person>();
  public ArrayList<Weapon> const_weapon_deck= new ArrayList<Weapon>();
  public ArrayList<Room> const_room_deck= new ArrayList<Room>();
  public ArrayList<String> people= new ArrayList<String>(Arrays.asList("Professor Plum", "Mrs. White", "Mrs. Scarlett","Mrs.Peacock","Colonel Mustard","Mr. Green"));
  public ArrayList<String> weapons=new ArrayList<String>(Arrays.asList("Lead Pipe","Knife","Candlestick","Revolver","Rope","Wrench"));
  public ArrayList<String> rooms=new ArrayList<String>(Arrays.asList("Conservatory","Billard Room", "Kitchen", "Library", "Study", "Hall", "Lounge", "Dining Room", "Ballroom"));
  public  ArrayList<ArrayList<Card>> player_decks = new ArrayList<ArrayList<Card>>();

  public Deck(int players)
  {
    //create deck
    num_players=players;

    for(int i=0; i<people.size(); i++)
    {
      Person p=new Person(people.get(i));
      person_deck.add(p);
      const_person_deck.add(p);
    }
    for(int i=0; i<weapons.size(); i++)
    {
      Weapon p=new Weapon(weapons.get(i));
      weapon_deck.add(p);
      const_weapon_deck.add(p);
    }
    for(int i=0; i<rooms.size(); i++)
    {
      Room p=new Room(rooms.get(i));
      room_deck.add(p);
      const_room_deck.add(p);
    }

    //select solution from Deck
    select_solution();

    //deal the remaining cards to the players
    deal();
  }
  public void select_solution()
  {
    Random rand = new Random();
    int roomRand = rand.nextInt(room_deck.size());
    int personRand= rand.nextInt(person_deck.size());
    int weaponRand=rand.nextInt(weapon_deck.size());

    solution_deck.add(person_deck.get(personRand));
    solution_deck.add(room_deck.get(roomRand));
    solution_deck.add(weapon_deck.get(weaponRand));
    person_deck.remove(personRand);
    room_deck.remove(roomRand);
    weapon_deck.remove(weaponRand);
    //DEBUG: To see the solution
    System.out.println("Solution:");
    for(int i=0; i<solution_deck.size(); i++)
    {
      System.out.println(solution_deck.get(i).getName());
    }
  }
  public void deal()
  {
    for(int i=0; i<person_deck.size(); i++)
    {
      main_deck.add(person_deck.get(i));
      // person_deck.remove(i);
    }
    for(int i=0; i<weapon_deck.size(); i++)
    {
      main_deck.add(weapon_deck.get(i));
      // weapon_deck.remove(i);
    }
    for(int i=0; i<room_deck.size(); i++)
    {
      main_deck.add(room_deck.get(i));
      // room_deck.remove(i);
    }

    //Shuffle the deck before we deal
    Collections.shuffle(main_deck);

    int current_player=0;
    boolean initial_loop=true;

    while(main_deck.size()>0)
    {
      if(current_player>=num_players)
      {
        current_player=0;
        initial_loop=false;
      }
      if(initial_loop)
      {
        ArrayList<Card> current_deck=new ArrayList<Card>();
        current_deck.add(main_deck.get(0));
        main_deck.remove(0);
        player_decks.add(current_deck);
      }
      else{
        player_decks.get(current_player).add(main_deck.get(0));
        main_deck.remove(0);
      }
      current_player++;
    }
    //DEBUG: to check player decks
    for(int i=0; i<player_decks.size(); i++)
    {
      System.out.println("\nPlayer "+i+"'s Deck:");
      ArrayList<Card> current_deck=player_decks.get(i);
      for(int j=0; j<current_deck.size(); j++)
      {
        System.out.println(current_deck.get(j).getName());
      }
    }
  }
  public boolean check_solution()
  {
    //FIXME: check if the submitted cards are in the solution
    return false;
  }
  public String show_people()
  {
    String people="";
    for(int i=0; i<const_person_deck.size(); i++)
    {
      Card character=const_person_deck.get(i);
      people+="("+(i+1)+") "+character.getName()+"\n";
    }
    return people;
  }
  public Person get_people(int index)
  {
    return const_person_deck.get(index);
  }
  public String show_weapons()
  {
    String weapons="";
    for(int i=0; i<const_weapon_deck.size(); i++)
    {
      Card weapon=const_weapon_deck.get(i);
      weapons+="("+(i+1)+") "+weapon.getName()+"\n";
    }
    return weapons;
  }
  public Weapon get_weapons(int index)
  {
    return const_weapon_deck.get(index);
  }
  public String show_rooms()
  {
    String rooms="";
    for(int i=0; i<const_room_deck.size(); i++)
    {
      Card room=const_room_deck.get(i);
      rooms+="("+(i+1)+") "+room.getName()+"\n";
    }
    return rooms;
  }
  public Room get_rooms(int index)
  {
    return const_room_deck.get(index);
  }
  public boolean is_in(String name, ArrayList<Card> list)
  {
    //FIXME: not sure if this helps, maybe we should compare the name string from the cards??
    for(int i=0; i<list.size();i++)
    {
      if(list.get(i).getName().equals(name))
      {
        return true;
      }
    }
    return false;
  }
  public Card get_card(String name, ArrayList<Card> list)
  {
    //FIXME: not sure if this helps, maybe we should compare the name string from the cards??
    for(int i=0; i<list.size();i++)
    {
      if(list.get(i).getName().equals(name))
      {
        return list.get(i);
      }
    }
    return null;
  }
  public String show_notebook(int player_id)
  {
    ArrayList<Card> current_deck=player_decks.get(player_id);
    String notebook="";
    String mark;
    notebook+="\nPeople\n";
    for(int i=0; i<const_person_deck.size(); i++)
    {
      Card character=const_person_deck.get(i);
      if(is_in(character.getName(), current_deck))
      {
        mark="X";
      }
      else
      {
        mark=" ";
      }
      notebook+="["+mark+"] "+character.getName()+"\n";
    }
    notebook+="\nRooms\n";
    for(int i=0; i<const_room_deck.size(); i++)
    {
      Card room=const_room_deck.get(i);
      if(is_in(room.getName(), current_deck))
      {
        mark="X";
      }
      else
      {
        mark=" ";
      }
      notebook+="["+mark+"] "+room.getName()+"\n";
    }
    notebook+="Weapons\n";
    for(int i=0; i<const_weapon_deck.size(); i++)
    {
      Card weapon=const_weapon_deck.get(i);
      if(is_in(weapon.getName(), current_deck))
      {
        mark="X";
      }
      else
      {
        mark=" ";
      }
      notebook+="["+mark+"] "+weapon.getName()+"\n";
    }
    return notebook;
    //FIXME: If we have a card then it needs to be marked with an X, if another player has showed us a card, we need
    //to mark it with an X as well. Have a master deck of all cards, compared with player deck? Toolbar?
  }
  public String getRooms(String location)
  {
    String room_list="";
    for(int i=0; i<const_room_deck.size(); i++)
    {
      if(!const_room_deck.get(i).getName().equals(location))
      {
        room_list+=const_room_deck.get(i).getName()+"\n";
      }
    }
    return room_list;
  }
  public int test_disprove(int disprove_player, Person suggested_murderer, Weapon suggested_weapon, Room suggested_room)
  {
    //Returns number of matching cards they have to the suggestion
    int num_cards=0;
    if(is_in(suggested_murderer.getName(), player_decks.get(disprove_player)))
    {
      num_cards++;
    }
    if(is_in(suggested_weapon.getName(), player_decks.get(disprove_player)))
    {
      num_cards++;
    }
    if(is_in(suggested_room.getName(), player_decks.get(disprove_player)))
    {
      num_cards++;
    }
    return num_cards;
  }
  public ArrayList<Card> get_disprove(int disprove_player, Person suggested_murderer, Weapon suggested_weapon, Room suggested_room)
  {
    ArrayList<Card> disprove_options=new ArrayList<Card>();
    //Returns number of matching cards they have to the suggestion
    Card current=get_card(suggested_murderer.getName(), player_decks.get(disprove_player));
    if(current!=null)
    {
      disprove_options.add(current);
    }
    current=get_card(suggested_weapon.getName(), player_decks.get(disprove_player));
    if(current!=null)
    {
      disprove_options.add(current);
    }
    current=get_card(suggested_room.getName(), player_decks.get(disprove_player));
    if(current!=null)
    {
      disprove_options.add(current);
    }
    return disprove_options;
  }
  public void disprove(int current_player, Card evidence)
  {
    //add evidence to deck, and consequently to notebook.
    player_decks.get(current_player).add(evidence);
  }
  public boolean check_solution(Card accused_murderer, Card accused_weapon, Card accused_room)
  {
    if(is_in(accused_murderer.getName(), solution_deck) && is_in(accused_weapon.getName(), solution_deck) && is_in(accused_room.getName(), solution_deck))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
