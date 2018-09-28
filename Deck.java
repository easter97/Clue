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
  public ArrayList<String> people= new ArrayList<String>(Arrays.asList("Professor Plum", "Mrs. White", "Mrs. Scarlett","Mrs.Peacock","Colonel Mustard","Mr. Green"));
  public ArrayList<String> weapons=new ArrayList<String>(Arrays.asList("lead pipe","knife","candlestick","revolver","rope","wrench"));
  public ArrayList<String> rooms=new ArrayList<String>(Arrays.asList("Conservatory","Billard Room", "Kitchen", "Library", "Study", "Hall", "Lounge", "Dining Room", "Ballroom"));

  public Deck(int players)
  {
    num_players=players;
    for(int i=0; i<people.size(); i++)
    {
      Person p=new Person(people.get(i));
      person_deck.add(p);
    }
    for(int i=0; i<weapons.size(); i++)
    {
      Weapon p=new Weapon(weapons.get(i));
      weapon_deck.add(p);
    }
    for(int i=0; i<rooms.size(); i++)
    {
      Room p=new Room(rooms.get(i));
      room_deck.add(p);
    }
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
    //FIXME: How to store player decks and dynamically create them?
  }
  public boolean is_in(Card card, ArrayList<Card> list)
  {
    if(list.indexOf(card)>-1)
    {
      return true;
    }
    return false;
  }
}
