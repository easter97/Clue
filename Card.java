import java.io.*;
import java.util.*;
class Card
{
  public String name;
  String bio;
  String alibi="didn't do it";
  String guilty;
  public boolean eliminated;
  public boolean solution;

  public Card(String name)
  {
    this.name=name;
    eliminated=false;
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
  public String getName()
  {
    return name;
  }
  public String getBio()
  {
    return bio;
  }
  public String getAlibi()
  {
    return alibi;
  }
  public String getGuilty(Card murderer, Card weapon, Card Room)
  {
    //String together your guilty spill here. Use murderer.getName() to get the string value to concatenate
    return guilty;
  }
}
class Person extends Card
{

  public Person(String name)
  {
    super(name);
    if(name.equals("Mrs. Scarlett"))
    {
      bio="For as far back as Miss Scarlet could remember she was always being reminded of how beautiful she was. In her youth men found her curly and fiery red locks, porcelain skin, and piercing blue eyes haunting. She quickly learned she could leverage her beauty in helping get what she wanted in life, and that was precisely what she did when she married the wealthiest businessman in all of Yorkshire. Upon his death expecting to inherit his fortune Scarlett was shocked to find out he had instead left all of his fortune to his children from a previous marriage. She would come to find out it was Mr. Boddy who advised her late husband on doing this since they had only been married 5 years. As she aged her beauty was fading as wrinkles creased her once flawless and milky skin. Scarlett began to feel destitute. She was livid and intended to confront Mr. Boddy for his ruining her life.";
      alibi="Angry Scarlett found herself in an intricate beaded floor length gown intent on accomplishing her mission at hand of getting even. However, as she entered into the estate her body trembled, and her head pounded. She had felt herself catching a fever the week earlier, but tried to ignore it. The fever today has seemed to reach a dizzying crescendo. As she walked in the estate she suddenly collapsed. As Mr. Boddy was taking his last breath Mr. Scarlett was being rushed by the governess and other servants to the nurses quarters. The murder remains on the loose. ";
      guilty="She soon found Mr. Boddy and confronted him. Things quickly escalated when Mr. Boddy told her he knew she was little more than an opportunistic leach and was glad he convinced his friend to remove her from the will. Engraged she took the";
    }
    else if(name.equals("Colonel Mustard"))
    {
      bio="In 1922 the destruction, and scars, of the Great War were still fresh in the minds of every Brit. Prior to the conflict in 1914 Colonel Mustard lived a relatively simple life, running the family business he inherited from his parents in town. A proud Englishman, the conflict resulted in Colonel Mustard leaving the only place he had ever known, his hometown of Yorkshire, for the trenches of France on the Western Front to serve in the Army. It had been 4 years since he’d returned home to Yorkshire and resumed running the family business. Tonight he found himself at the expansive Tudor Mansion, the dwellings of his secretive business rival Mr. Boddy for dinner. It was there he planned to confront him about claims Mr. Boddy had been bribing potential clients….";
    }
    else if(name.equals("Mrs. White"))
    {
      bio=" Mary Boddy in her youth, had always felt like the black sheep of her family. Her parents had always favored her older brother, John Boddy, and heralded him as the golden child. From the beginning she understood her place in life was to marry the boorish and callous Mr. White from the fellow prominent White family for practical purposes and not to challenge the status quo. Locked in a loveless marriage Mary yearned for more. That’s when she met Richard, her brother’s butler. Quickly their friendship evolved into a secret & torrid love affair. As her feelings for Richard grew she became filled with anger that her brother would never allow for her to leave her failing marriage, much less for a man of such low social standing.";
    }
    else if(name.equals("Mrs. Peacock"))
    {
      bio="The aging Mrs. Peacock had always loved to be the center of attention. That meant knowing any and all of the gossip related to the residents of Yorkshire. But even she was shocked to hear rumors going around the town that Mr. Boddy, her husband’s longtime business partner, had began selling alcohol in America which had been locked in prohibition. She was equal parts shocked and amused, that the shrewd Mr. Boddy had potentially decided to undertake such a risky undertaking. It was a few minutes later that she realized if the claims were in fact true, and if Mr. Boddy was caught his selfish decision could destroy the reputation of not only her husband’s shared business, but their social standing as well. ";
    }
    else if(name.equals("Professor Plum"))
    {
      bio="From a young age, William Plum had always been captivated with the laws that governed everyday life and society as a whole. Perhaps this is what led him to dedicate his life to upholding the law as a detective for the Yorkshire police force. Tonight he found himself at a lavish Estate posing as a English professor from Lancaster interested in the rare book collection of the prominent businessman and owner, Mr. Boddy. He was there to investigate claims that the elusive Mr. Boddy, a wealthy native of Yorkshire, had begun to illegally ship alcohol across the Atlantic, as Prohibition gripped the nation. As Plum walked the halls of the Estate he could fill himself fill up with seething anger. 30 years earlier, when he was only 14 he lost his beloved mother at the hands of his alcoholic father and his life was never the same.  He didn’t expect this case to stir up these emotions he had been so successful at holding back all these years.";
    }
    else if(name.equals("Mr. Green"))
    {
      bio="As it turns out, Reverend Green had many a reason to feel green with envy towards Mr. Boddy. Best friends all throughout their youth one day a young Mr. Green shared an invention idea with Mr. Boddy. Unbeknownst to him his once closest friend would betray him and later go on to pitch the idea to investors as his own, which would lead to Mr. Boddy amassing a fortune. The two hadn’t spoken since, and after years of anger and resentment Mr. Green eventually turned to the church to find peace... ";
    }
    else
    {
      bio="a real mysterious character.";
    }
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
  String passageway;
  double row;
  double column;
  public Room(String name)
  {
    super(name);
    if(name.equals("Kitchen"))
    {
      passageway="Study";
      row=4;
      column=3;
    }
    else if(name.equals("Study"))
    {
      passageway="Kitchen";
      row=1;
      column=1;
    }
    else if(name.equals("Lounge"))
    {
      passageway="Conservatory";
      row=1;
      column=3;
    }
    else if(name.equals("Conservatory"))
    {
      passageway="Lounge";
      column=1;
      row=4;
    }
    else if(name.equals("Hall"))
    {
      column=2;
      row=1;
      passageway=null;
    }
    else if(name.equals("Library"))
    {
      passageway=null;
      column=1;
      row=2;
    }
    else if(name.equals("Dining Room"))
    {
      passageway=null;
      column=3;
      row=2.5;
    }
    else if(name.equals("Billard Room"))
    {
      passageway=null;
      column=1;
      row=3;
    }
    else if(name.equals("Ballroom"))
    {
      passageway=null;
      column=2;
      row=4;
    }
    else if(name.equals("Grand Staircase"))
    {
      passageway=null;
      column=2.5;
      row=2;
    }
    else
    {
      row=0;
      column=0;
      passageway=null;
    }
  }
  public String getPassageway()
  {
    if(passageway!=null)
    {
      return passageway;
    }
    else
    {
      return null;
    }
  }
  public double getRow()
  {
    return row;
  }
  public double getCol(){
    return column;
  }
}
