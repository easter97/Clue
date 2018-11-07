class Player
{
  Person character;
  //This id is to help the deck class keep track of player decks and notepads
  int id;
  Room location;

  public Player(String char_name, int index)
  {
    //Character is a person so that we can access bio and other person classes
    character=new Person(char_name);
    id=index;
    location=new Room("Grand Staircase");
  }
  public String getLocation()
  {
    return location.getName();
  }
  public String getMoves()
  {
    String moves="";
    //determines what moves a player can make based on Location
    //FIXME: implement trapdoor logic
    if(location.getName()=="Grand Staircase" || location.getName()=="Hallway")
    {
      moves+="   - Roll\n";
    }
    else if(location.getPassageway()!=null)
    {
      moves+="   - Roll\n";
      moves+="   - Suggestion\n";
      moves+="   - Accusation\n";
      moves+="   - Secret Passageway to "+location.getPassageway()+"\n";
    }
    else
    {
      moves+="   - Roll\n";
      moves+="   - Suggestion\n";
      moves+="   - Accusation\n";
    }
    return moves;
  }
  public String getName()
  {
    return character.getName();
  }
  public void setLocation(String room)
  {
    location=new Room(room);
  }
}
