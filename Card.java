import java.io.*;
import java.util.*;
class Card
{
  public String name;
  public String bio;
  public boolean eliminated;
  public boolean solution;

  public Card(String name)
  {
    this.name=name;
  }
  public boolean is_solution()
  {
    return solution;
  }
  public boolean is_eliminated()
  {
    return eliminated;
  }
  public void mark_solution()
  {
    solution=true;
  }
  public void mark_eliminated()
  {
    eliminated=true;
  }
}
class Person extends Card
{
  public Person(String name)
  {
    super(name);
  }
}
class Weapon extends Card
{
  public Weapon(String name)
  {
    super(name);
  }
}
class Room extends Card
{
  public Room(String name)
  {
    super(name);
  }
}
