import java.io.*;
import java.util.*;
class Card
{
  public String name;
  String bio;
  String alibi;
  String guilty;
  public boolean eliminated;
  public boolean solution;
  String guilty_pt2;

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


  public String getGuilty(Card murderer, Card weapon, Card room)
  {
    //String together your guilty spill here. Use murderer.getName() to get the string value to concatenate
    String story=guilty+weapon.getName()+guilty_pt2+room.getName();
    return story;
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
      guilty="Miss Scarlett found Mr. Boddy and confronted him. Things quickly escalated when Mr. Boddy told her he knew she was little more than an opportunistic leech and was glad he convinced his friend to remove her from the will. Enraged she took the ";
      guilty_pt2 = " she found next to her, and took his life in the ";
    }
    else if(name.equals("Colonel Mustard"))
    {
      bio="In 1922 the destruction, and scars, of the Great War were still fresh in the minds of every Brit. Prior to the conflict in 1914 Colonel Mustard lived a relatively simple life, running the family business he inherited from his parents in town. A proud Englishman, the conflict resulted in Colonel Mustard leaving the only place he had ever known, his hometown of Yorkshire, for the trenches of France on the Western Front to serve in the Army. It had been 4 years since he’d returned home to Yorkshire and resumed running the family business. Tonight he found himself at the expansive Tudor Mansion, the dwellings of his secretive business rival Mr. Boddy for dinner. It was there he planned to confront him about claims Mr. Boddy had been bribing potential clients….";
      alibi=" On his way to locate Mr. Boddy Colonel Mustard ran into his former combat mate, now the personal chauffeur for Mr. Boddy, Edmund. As they reminisced about restless nights spent in the France, Mr. Boddy was taking his last breaths. The murderer remains on the loose. ";
      guilty="Once the colonel found Mr. Boddy, he confronted him and a shouting match ensued. Mr. Boddy smuggly told the colonel the allegations were true. But if Colonel Mustard tried to do anything about it he would use his political connections to destroy both him and his business. Enraged, he took the ";
      guilty_pt2 = " and killed his business rival in the ";
    }
    else if(name.equals("Mrs. White"))
    {
      bio=" Mary Boddy in her youth, had always felt like the black sheep of her family. Her parents had always favored her older brother, John Boddy, and heralded him as the golden child. From the beginning she understood her place in life was to marry the boorish and callous Mr. White from the fellow prominent White family for practical purposes and not to challenge the status quo. Locked in a loveless marriage Mary yearned for more. That’s when she met Richard, her brother’s butler. Quickly their friendship evolved into a secret & torrid love affair. As her feelings for Richard grew she became filled with anger that her brother would never allow for her to leave her failing marriage, much less for a man of such low social standing.";
      alibi="As her brother’s eyes began to glass over Mary found herself with Richard. She no longer cared about the approval of her brother or family. She and Richard were coming forward with their relationship. The murderer remains on the loose… ";
      guilty="Mrs. White found her brother, and told him the truth and about her intention to leave Mr. White for Richard. Her brother sternly locked eyes with her and coldly stated he would never allow it and that she was a disgrace to the family. She was furious and decided if she couldn’t decide her own future, neither would he. She took the ";
      guilty_pt2 = " and killed her own brother in the ";
    }
    else if(name.equals("Mrs. Peacock"))
    {
      bio="The aging Mrs. Peacock had always loved to be the center of attention. That meant knowing any and all of the gossip related to the residents of Yorkshire. But even she was shocked to hear rumors going around the town that Mr. Boddy, her husband’s longtime business partner, had began selling alcohol in America which had been locked in prohibition. She was equal parts shocked and amused, that the shrewd Mr. Boddy had potentially decided to undertake such a risky undertaking. It was a few minutes later that she realized if the claims were in fact true, and if Mr. Boddy was caught his selfish decision could destroy the reputation of not only her husband’s shared business, but their social standing as well. ";
      alibi="At the time of the murder Mrs. Peacock was seen by other domestic servants prying the maid Theresa if she had heard anything about the rumors. The murder remains on the loose. ";
      guilty="Mrs. Peacock felt numb after reading the tinged and mangled piece of paper she’d been given by one of Mr. Boddy’s servants. She was told the servant was instructed to throw several documents in the fireplace in front of Mr. Boddy, which she did. But as soon as he left she had managed to pull the top paper from the fireplace and smuggle it out. The rumors were true. Mrs. Peacock  knew what she had to do. She calmly found Mr. Boddy and killed him with the ";
      guilty_pt2 = " in the ";
    }
    else if(name.equals("Professor Plum"))
    {
      bio="From a young age, William Plum had always been captivated with the laws that governed everyday life and society as a whole. Perhaps this is what led him to dedicate his life to upholding the law as a detective for the Yorkshire police force. Tonight he found himself at a lavish Estate posing as a English professor from Lancaster interested in the rare book collection of the prominent businessman and owner, Mr. Boddy. He was there to investigate claims that the elusive Mr. Boddy, a wealthy native of Yorkshire, had begun to illegally ship alcohol across the Atlantic, as Prohibition gripped the nation. As Plum walked the halls of the Estate he could fill himself fill up with seething anger. 30 years earlier, when he was only 14 he lost his beloved mother at the hands of his alcoholic father and his life was never the same.  He didn’t expect this case to stir up these emotions he had been so successful at holding back all these years.";
      alibi=" Professor Plum was on his way to meet Mr. Boddy when he noticed a gap in the floor underneath a rug. At the time of the murder as he stopped to take note of this abnormality Ava, the newly hired maid, observed the strange professor jotting down something in a notepad. The murderer remains on the loose. ";
      guilty="The professor found a trap door covered by a rug. It led to a cellar filled with hundred of bottles of alcohol. His blood boiled as his late mother entered his mind once more. He knew a man with Mr. Boddy’s political connections would be let go with nothing more than a slap on the wrist. He had to take justice into his own hands. He took the ";
      guilty_pt2 = " he found and killed Mr. Boddy in the ";
    }
    else if(name.equals("Mr. Green"))
    {
      bio="As it turns out, Reverend Green had many a reason to feel green with envy towards Mr. Boddy. Best friends all throughout their youth one day a young Mr. Green shared an invention idea with Mr. Boddy. Unbeknownst to him his once closest friend would betray him and later go on to pitch the idea to investors as his own, which would lead to Mr. Boddy amassing a fortune. The two hadn’t spoken since, and after years of anger and resentment Mr. Green eventually turned to the church to find peace... ";
      alibi="After 20 years of silence the Reverend was ready to see his former friend once more. During the time of the murder he was seen asking the maid Helen the whereabout of Mr. Boddy -- he was ready to tell his old friend he forgave him. The murderer remains on the loose… ";
      guilty="The reverend found his old friend. Before he could so much as utter a single word Mr. Boddy scoffed that he was a stupid fool, who hadn’t amount to anything. He then calmly began to call for a servant to remove the reverend.  That was the final straw; Reverend Green had taken enough. He took the ";
      guilty_pt2 = " and killed his old friend in the ";
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
    if(name.equals("Lead Pipe"))
    {
      alibi="A lead pipe had been sitting outside one of the downstairs bathrooms in the mansion for a few days after the bathroom had sprung a leak, and the pipe had to be replaced. The eyesore was noted by the butler who had instructed the day before it be disposed of. The lead pipe was not the weapon that ended Mr. Boddy’s life. ";
    }
    else if(name.equals("Knife"))
    {
      alibi="Upon the permission of the butler the dagger had been taking by one of the gardeners as he left the day prior to go hunt an hour away from Yorkshire. The dagger was not the weapon that ended Mr. Boddy’s life.";
    }
    else if(name.equals("Candlestick"))
    {
      alibi=" As the dinner date approached all candlesticks had been removed from the grounds to be polished. The candlestick was not the weapon that ended Mr. Boddy’s life. ";
    }
    else if(name.equals("Revolver"))
    {
      alibi=" Paranoid in recent weeks Mr. Boddy had all firearms locked away in a vault located in the cellar. This included his prized revolver. No one but him knew where the key was located. Mr. Boddy’s prized Colt M1911 revolver was not the weapon that ended Mr. Boddy’s life. ";
    }
    else if(name.equals("Rope"))
    {
      alibi=" A terrible storm a week earlier had destroyed a portion of the fence surrounding the pasture adjacent to the estate. As such, the horses had to be stabled inside, and the rope was taken there to tie them up. The rope was not the weapon that ended Mr. Boddy’s life. ";
    }
    else if(name.equals("Wrench"))
    {
      alibi=" The estate next to the Tudor mansion was undergoing extensive renovation and a servant had come over a few days prior requested a wrench. The groundsman of the Tudor Mansion happily obliged. The wrench was not the weapon that ended Mr. Boddy’s life. ";
    }
    else{
      alibi= "That object didn't seem to be located in the masion at the time of the murder.";
    }
  }//end of super parenthesis
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
      alibi=" At the time of the murder the head chef and the rest of his team were in the kitchen diligently preparing tonight’s dinner rinsing plump heads of lettuce, stirring bubbling pots, and putting the final touches on pastries fresh from the oven. The murder did not happen in the kitchen.";
    }
    else if(name.equals("Study"))
    {
      passageway="Kitchen";
      row=1;
      column=1;
      alibi="Easily agitated and particularly fickle lately, Mr. Boddy had instructed the staff to completely renovate his personal study despite it being changed just two months prior. He also had his butler dispose of a handful of documents in the fire place of the study too. At the time of the murder the room was completely locked as the walls had just been painted. The murder did not happen in the Study.";
    }
    else if(name.equals("Lounge"))
    {
      passageway="Conservatory";
      row=1;
      column=3;
      alibi=" In the lounge at the time of the murder the porter desperately tried to catch Mr. Boddy’s two boisterous dachshunds. He knew he would be in a great deal of trouble if the energetic dogs were to escape to the same room as the guests. The lounge did not bear witness to the murder of Mr. Boddy.";
    }
    else if(name.equals("Conservatory"))
    {
      passageway="Lounge";
      column=1;
      row=4;
      alibi=" In preparation of the night’s event two of the groundskeepers found themselves in the conservatory. The glass room was filled with plants in terracotta pots blooming in a dazzling array of color. As their master took his last breath elsewhere in the mansion the men were cutting stems of blushing pink peonies, scarlet roses, golden colored marigolds, and ivory colored hydrangeas for a fresh floral arrangement to be placed on the dining room table. The conservatory did not bear witness to the murder of Mr. Boddy. ";
    }
    else if(name.equals("Hall"))
    {
      column=2;
      row=1;
      passageway=null;
      alibi="In the hall at the time of the murder the housekeeper sternly briefed the waiting staff on the expectations for serving the guests at tonight’s dinner. Tonight was to go on without so much as a single hitch. The hall did not bear witness to the murder of Mr. Boddy.";
    }
    else if(name.equals("Library"))
    {
      passageway=null;
      column=1;
      row=2;
      alibi="Maids scurried throughout the library as they frantically dusted every single book shelf. They were told a prominent professor was to attend the dinner to see the rare books in the collection of their Master, Mr. Boddy, and that a story was to be done about him afterward in the local news. The room had to be immaculate. The library did not bear witness to the murder of Mr. Boddy. ";
    }
    else if(name.equals("Dining Room"))
    {
      passageway=null;
      column=3;
      row=2.5;
      alibi="Knowing the master would be preoccupied with the dinner tonight some rambunctious younger servants had decided to sneak into the billiards room for a quick game. As Mr. Boddy was being murdered the young men were playing billiards. The billiards room did not bear witness to the murder of Mr. Boddy. ";
    }
    else if(name.equals("Billard Room"))
    {
      passageway=null;
      column=1;
      row=3;
      alibi="Knowing the master would be preoccupied with the dinner tonight some rambunctious younger servants had decided to sneak into the billiards room for a quick game. As Mr. Boddy was being murdered the young men were playing billiards. The billiards room did not bear witness to the murder of Mr. Boddy.";
    }
    else if(name.equals("Ballroom"))
    {
      passageway=null;
      column=2;
      row=4;
      alibi="A local band was setting up, and starting to warm up at the time of the murder as the festivities were set to move there after dinner. The ballroom did not bear witness to the murder of Mr. Boddy.";
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
