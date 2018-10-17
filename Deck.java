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
  }
  public void deal()
  {
    for(int i=0; i<person_deck.size(); i++)
    {
      solution_deck.add(person_deck.get(i));
      person_deck.remove(i);
    }
    for(int i=0; i<weapon_deck.size(); i++)
    {
      solution_deck.add(weapon_deck.get(i));
      weapon_deck.remove(i);
    }
    for(int i=0; i<room_deck.size(); i++)
    {
      solution_deck.add(room_deck.get(i));
      room_deck.remove(i);
    }
    //Shuffle the deck before we deal
    Collections.shuffle(solution_deck);
    //FIXME: Deal evenly to each deck, then add the player_decks arraylist

  }
  public boolean is_in(Card card, ArrayList<Card> list)
  {
    //FIXME: not sure if this helps, maybe we should compare the name string from the cards??
    if(list.indexOf(card)>-1)
    {
      return true;
    }
    return false;
  }
  public boolean check_solution()
  {
    //FIXME: check if the submitted cards are in the solution
    return false;
  }
  public String show_notebook(int player_id)
  {
    //ArrayList<Card> current_deck=player_decks.get(player_id);
    String notebook="";
    String mark;
    notebook+="\nPeople\n";
    for(int i=0; i<const_person_deck.size(); i++)
    {
      Card character=const_person_deck.get(i);
      if(character.is_eliminated())
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
      if(room.is_eliminated())
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
      if(weapon.is_eliminated())
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
}
