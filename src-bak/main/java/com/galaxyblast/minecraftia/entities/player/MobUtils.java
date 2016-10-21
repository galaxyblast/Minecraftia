package com.galaxyblast.minecraftia.entities.player;

public class MobUtils
{
	public static String getTitle(int level)
	{
		String lvl = "Newb";
		
		if(level > 5 && level < 10)
			lvl = "Beginner";
		else if(level >= 10 && level < 20)
			lvl = "Novice";
		else if(level >= 20 && level < 40)
			lvl = "Apprentice";
		else if(level >= 40 && level < 65)
			lvl = "Amatuer";
		else if(level >= 65 && level < 100)
			lvl = "Adept";
		else if(level >= 100 && level < 150)
			lvl = "Epic";
		else if(level >= 150 && level < 250)
			lvl = "Elite";
		else if(level >= 250 && level < 500)
			lvl = "Master";
		else if(level >= 400 && level < 600)
			lvl = "Bishop";
		else if(level >= 600 && level < 800)
			lvl = "Archbishop";
		else if(level >= 800 && level < 1000)
			lvl = "Reaper";
		else if(level >= 1000 && level < 1250)
			lvl = "Deathdealer";
		else if(level >= 1250 && level < 1750)
			lvl = "Mythical";
		else if(level >= 1750 && level < 2500)
			lvl = "Legendary";
		else if(level >= 2500 && level < 3000)
			lvl = "Lord Of Death";
		else if(level >= 3000 && level < 3500)
			lvl = "Demigod";
		else if(level >= 3500)
			lvl = "Kami";
		
		return lvl;
	}
}
