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
  String bio;
  public Person(String name)
  {
    super(name);
    if(name.equals("Mrs. Scarlett"))
    {
      bio="a sarcastic, egotistical madam of a secret escort service in Washington that wasn’t all that secret. She is in her twenties, attractive, blonde and cunning.";
    }
    else if(name.equals("Colonel Mustard"))
    {
      bio="a stubborn man but a weak-hearted individual. In his military days, he stole radio parts during the war and sold them on them on the black market to make extra cash.";
    }
    else if(name.equals("Mrs. White"))
    {
      bio="a pale and tragic widow who may or may not have murdered her five previous husbands. While she seems outwardly proper and quiet, inwardly she is dishonest and jealous.";
    }
    else if(name.equals("Mrs. Peacock"))
    {
      bio="the talkative wife of a corrupt senator. Elegant and elderly; she wears a tiara giving her a queen-like demeanour. Rumour has it she assisted her husband in his political schemes by collecting bribes.";
    }
    else if(name.equals("Professor Plum"))
    {
      bio="a psychiatrist who works with the World Health Organization. While he had a medical license, he does not practice medicine because he had his license revoked after improper conduct with one of his patients.";
    }
    else if(name.equals("Mr. Green"))
    {
      bio="a revered, questioned by many on whether he is a saint or sinner. Known for shady dealings on the stock market, he used this money to \"help\" the Church of England.";
    }
    else
    {
      bio="a real mysterious character.";
    }
  }
  public String getName()
  {
    return name;
  }
  public String getBio()
  {
    return bio;
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
