import java.lang.Math;
class Player
{
  Person character;
  //This id is to help the deck class keep track of player decks and notepads
  int id;
  double current_roll_val;
  int dest_roll_val;
  Room dest_location;
  Room location;

  public Player(String char_name, int index)
  {
    //Character is a person so that we can access bio and other person classes
    character=new Person(char_name);
    id=index;
    location=new Room("Grand Staircase");
    current_roll_val=-1;
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
  public void setDestLocation(String room)
  {
    dest_location=new Room(room);
    double curr_row=location.getRow();
    double curr_col=location.getCol();
    double dest_row=dest_location.getRow();
    double dest_col=dest_location.getCol();
    //Do the math for dest_roll_val here!
    dest_roll_val=(int)Math.round((Math.abs(dest_row-curr_row)*5)+(Math.abs(dest_col-curr_col)*5));
  }
  public void setRoll(double roll_val)
  {
    current_roll_val=roll_val;
  }
  public double getRoll()
  {
    return current_roll_val;
  }
  public double getDestRoll()
  {
    return dest_roll_val;
  }
  public String getDestLocation()
  {
    return dest_location.getName();
  }
}
