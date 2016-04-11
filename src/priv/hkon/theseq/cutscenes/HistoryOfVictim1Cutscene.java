package priv.hkon.theseq.cutscenes;

import priv.hkon.theseq.filters.CombinedFilter;
import priv.hkon.theseq.filters.DarkenFilter;
import priv.hkon.theseq.filters.Filter;
import priv.hkon.theseq.filters.RedSnowFilter;
import priv.hkon.theseq.filters.SepiaFilter;
import priv.hkon.theseq.main.Core;
import priv.hkon.theseq.misc.VillageEvent;
import priv.hkon.theseq.nonblocks.Flowers;
import priv.hkon.theseq.sprites.Movable;
import priv.hkon.theseq.sprites.Nobody;
import priv.hkon.theseq.sprites.Player;
import priv.hkon.theseq.sprites.Sprite;
import priv.hkon.theseq.sprites.Villager;

public class HistoryOfVictim1Cutscene extends Cutscene {
	
	DarkenFilter df = new DarkenFilter(0);
	SepiaFilter sf = new SepiaFilter();
	RedSnowFilter rsf = new RedSnowFilter();
	
	boolean comeClose = false;
	boolean saidIt = false;
	
	int storyTellingStart = 100000;
	int erStartWalking = 103000;
	
	int giveControlToPlayer = 107000;
	int erTakesStep = 120000;
	
	int fadeOut = 130000;
	
	int sitting = 140000;
	
	int fadeOut2 = 150000;
	
	int inAgain = 160000;
	
	int fadeOut3 = 170000;
	
	int backToStart = 180000;
	
	Nobody no;
	Player pl;
	
	Nobody dummyNob1;
	Nobody dummyNob2;

	public HistoryOfVictim1Cutscene(Nobody nb, Player pa, Core core) {
		super(core);
		
		pl = pa;
		no = nb;
		
		pl.isPartOfCutscene = true;
		no.isPartOfCutscene = true;
		
		dummyNob1 = new Nobody(750, 750, core.village, no.getHome(), 0);
		core.village.addSprite(dummyNob1);
		core.village.setSpriteAt(null, dummyNob1.getX() + 1, dummyNob1.getY());
		for(int i = 0; i < 20; i++){
			core.village.setSpriteAt(null, dummyNob1.getX() - i - 1, dummyNob1.getY());
		}
		dummyNob2 = new Nobody(760, 750, core.village, no.getHome() , 0);
		core.village.addSprite(dummyNob2);
		//core.village.moveSpriteTo(pl, dummyNob2.getX() + 1, dummyNob2.getY());
		core.setCutsceneFilter(Filter.NO_FILTER);
		
		for( int i = 700; i < 720; i++){
			for( int j = 200; j < 220; j++){
				core.village.setSpriteAt(null, j, i);
				if(Sprite.RAND.nextInt(2) == 0){
					core.village.setTileCoverAt(new Flowers(j, i, core.village), j, i);
				}
			}
		}
		
		dummyNob1.isPartOfCutscene = true;
		dummyNob2.isPartOfCutscene = true;
		
		core.village.addSprite(dummyNob1);
		core.village.addSprite(dummyNob2);
		
		happenings.add(new Happening(30){
			public void happen(){
				no.turnTowards(Movable.DOWN);
				no.addAndShowSentence(new String[] {"Aah, there you are.."});
				pl.tryStartMoving(Movable.UP);
			}
		});
		
		happenings.add(new Happening(90){
			public void happen(){
				no.addAndShowSentence(new String[] {"I was very unsure about who I should tell this to..",
					"But you look like a trustworthy fellow",
					"And I know you won't tell it away, right?",
					"",
					"Good",
					"I knew the victim of the murder well..",
					"Er was a good friend of mine",
					"Therefore, er told me things I don't think er would have told anyone else",
					"And now I wonder what I should do",
					"I decided to share it with you",
					"Then we would be two carrying the burden",
					"And I don't risk that more than two do so...",
					"I shall tell you what er thought was the most important event of er's life",
					"The first thing er thought of when er woke up",
					"The last thought to leave er's mind when er went to sleep",
					"Listen up:"
				});
				//System.out.println(core.getCutsceneFilter());
			}
			
		});
		
		happenings.add(new Happening(storyTellingStart + 120){
			public void happen(){
				core.village.addAndShowSubtitles(new String[] {
					"Er was a happy fellow",
					"Being a Nobody never seemed to bother em at all",
					"Perhaps er felt er's carelessness take over from time to time",
					"But er was definetly one of the more positive villagers",
					"One day the Prophet returned to our village with a stranger",
					"Most of us had never seen a creature from Outside this village"
				});
			}
		});
		
		happenings.add(new Happening(erStartWalking){
			public void happen(){
				core.village.setSpriteAt(null, dummyNob1.getX() - 5, dummyNob1.getY());
				dummyNob2.startPathTo(dummyNob1.getX() - 5, dummyNob1.getY());
				
				core.village.addAndShowSubtitles(new String[] {
						"But it turned out er looked very much like ourselves",
						"Except er didn't talk",
						"Reminds me a lot about you, in fact!",
						"Anyway.. Last night's poor victim could never take er's eyes from this new fellow",
						"One day er took the courage needed and walked up to em"
					});
			}
		});
				
		
		happenings.add(new Happening(giveControlToPlayer){
			public void happen(){
				core.village.controlledSprite = dummyNob1;
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return dummyNob1.distTo(dummyNob2) == 1;
			}
		}){
			public void happen(){
				comeClose = true;
				core.village.controlledSprite = pl;
				dummyNob1.turnTowards(dummyNob1.getDirectionTo(dummyNob2));
				dummyNob2.turnTowards(dummyNob2.getDirectionTo(dummyNob1));
				
				core.village.addAndShowSubtitles(new String[] {
					"Er was stunned whenever er looked into the eyes of the newcomer.",
					"Our Doctor couldn't really help either",
					"'Shut your damned mouth and stop looking so stupid!', I think was er's exact diagnosis",
					"My poor, poor friend. It's... It's almost like er's point of view just switched",
					"You know, even for Nobodies as me, the world is always rotating around ourself",
					"But my friend suddenly found that the newcomer was the center of the world"
				});
			}
		});
		
		happenings.add(new Happening(erTakesStep){
			public void happen(){
				core.village.centerSubject = dummyNob2;
				dummyNob2.startPathTo(dummyNob2.getX() - 3, dummyNob2.getY());
				core.village.controlledSprite = dummyNob1;
				dummyNob1.moveSpeed/= 1.3f;
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return dummyNob2.distTo(dummyNob1) == 1 && tickCount > erTakesStep + 60;
			}
		}){
			public void happen(){
				core.village.setSpriteAt(null, dummyNob2.getX() - 100, dummyNob2.getY());
				dummyNob2.startPathTo(dummyNob2.getX() - 100, dummyNob2.getY());
				core.village.addAndShowSubtitles(new String[] {
						"It must have felt horrible, as er's only way to truly be alive was to be with the newcomer",
						"And slowly, slowly, er slipped away, losing track of erself",
						"Er never complained, er kept fighting to come into focus again, to be with the silent stranger"
				});
			}
		});
		
		happenings.add(new Happening(sitting){
			public void happen(){
				core.village.controlledSprite = pl;
				dummyNob2.setHasPath(false);
				
				core.village.moveSpriteTo(dummyNob1, 210, 705);
				dummyNob1.turnTowards(Movable.DOWN);
				core.village.moveSpriteTo(dummyNob2, 211, 705);
				dummyNob2.turnTowards(Movable.DOWN);
				
				core.village.addAndShowSubtitles(new String[] {
					"Luckily for my friend, these two happened to come along pretty well after some time",
					"The joy between them created some kind of gravitational waves, warmly attracting all of us..",
					"Yeah, gravitational waves.. Fancy word, right? No clue what it means, however..",
					"Oh, well. The next thing that happened, was the single-most important event in er life",
					"At least er said so.. You know, I trust er, I really do, but.. This seems so weird..",
					"The newcomer said to em:"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return !core.village.showSubtitle;
			}
		}){
			public void happen(){
				dummyNob2.addAndShowSentence(new String[] {
					"",
					"I love you"
				});
				saidIt = true;
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return !dummyNob2.showDialog;
			}
		}){
			public void happen(){
				dummyNob1.turnTowards(Movable.RIGHT);
				dummyNob2.turnTowards(Movable.LEFT);
				core.village.addAndShowSubtitles(new String[]{
						"Er doesn't talk at all, and when er does...",
						"Love? That isn't a word I've ever heard before",
						"Sounds like a disease or something",
						"Nevertheless, they both knew what it was all about, apparently",
						"That would be their secret, then",
						"The only ones that ever knew what 'Love' was",
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return !core.village.showSubtitle;
			}
		}){
			public void happen(){
				tickCount = fadeOut2;
			}
		});
		
		happenings.add(new Happening(inAgain){
			public void happen(){
				core.village.centerSubject = dummyNob1;
				core.village.moveSpriteTo(dummyNob2, dummyNob2.getX(), dummyNob2.getY() + 10);
			}
		});
		
		happenings.add(new Happening(inAgain + 255){
			public void happen(){
				dummyNob2.startPathTo(dummyNob1.getX(), dummyNob1.getY() + 1);
				core.village.addAndShowSubtitles(new String[] {
						"Then.. A few days later, this happened.."
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return dummyNob2.distTo(dummyNob1) == 1;
			}
		}){
			public void happen(){
				core.village.addAndShowSubtitles(new String[] {
					"The newcomer was crying",
					"Er couldn't anymore",
					"Just couldn't"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return !core.village.showSubtitle;
			}
		}){
			public void happen(){
				core.village.setSpriteAt(null, dummyNob2.getX(), dummyNob2.getY() - 50);
				dummyNob2.startPathTo(dummyNob2.getX(), dummyNob2.getY() - 50);
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return dummyNob2.distTo(dummyNob1) > 5;
			}
		}){
			public void happen(){
				tickCount = fadeOut3 - 30;
			}
		});
		
		happenings.add(new Happening(backToStart){
			public void happen(){
				no.addAndShowSentence(new String[]{
						"And noone ever saw that newcomer again..",
						"Some say er fled at night, maybe er just felt to go home",
						"Eh. I don't know. I really don't see why er would go. It's really wierd",
						"And.. Now, noone will ever see my friend again either",
						"It's a cruel world",
						"But life goes on, right?",
						"I need to.. think..",
						"Thanks for your time.. But please go now"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(){
			public boolean isHappening(){
				return !no.showDialog;
			}
		}){
			public void happen(){
				pl.startPathTo(no.getHome().getX() + no.getHome().getW()/2, no.getHome().getY() + no.getHome().getH() + 1);
			}
		});
		
		
	}
	
	@Override
	public void tick(){
		//System.out.println("" + tickCount);
		if(tickCount > 120 && !no.showDialog && tickCount < storyTellingStart){
			tickCount = storyTellingStart; 
			
		}else
		
		if(tickCount > storyTellingStart && tickCount - storyTellingStart < 120){
			df.darkness = (tickCount - storyTellingStart)*2;
			core.setCutsceneFilter(df);
		}else if(tickCount == storyTellingStart + 120){
			//System.out.println("Set new filter");
			core.setCutsceneFilter(sf);
			core.village.centerSubject = dummyNob1;
		}else if(tickCount > storyTellingStart  + 240 && !core.village.showSubtitle && tickCount < erStartWalking){
			tickCount = erStartWalking;
		}else
		
		if(tickCount > erStartWalking && tickCount < giveControlToPlayer){
			dummyNob1.turnTowards(dummyNob1.getDirectionTo(dummyNob2));
			
			if(!dummyNob2.hasPath()){
				tickCount = giveControlToPlayer;
			}
		}else
		
		if(tickCount > giveControlToPlayer  + 120 && tickCount < erTakesStep){
			if(!core.village.showSubtitle && comeClose){
				tickCount = erTakesStep;
			}
			if(comeClose){
				dummyNob1.turnTowards(Movable.LEFT);
			}
		}else
		
		if(tickCount > erTakesStep && tickCount < fadeOut){
			if(dummyNob1.distTo(dummyNob2) > 9 && !core.village.showSubtitle){
				tickCount = fadeOut;
			}
		}else
		
		if(tickCount >= fadeOut && tickCount < sitting){
			if(tickCount == fadeOut + 1){
				core.setCutsceneFilter(new CombinedFilter(sf, df));
			}
			df.darkness = tickCount - fadeOut;
			
			if(tickCount == fadeOut  + 255){
				tickCount = sitting;
			}
		}else
		
		if(tickCount >= sitting && tickCount < fadeOut2){
			if(tickCount == sitting + 1){
				core.setCutsceneFilter(sf);
				df.darkness = 0;
			}
			if(!saidIt){
				dummyNob1.turnTowards(Movable.DOWN);
				dummyNob2.turnTowards(Movable.DOWN);
			}else{
				dummyNob2.turnTowards(dummyNob2.getDirectionTo(dummyNob1));
				dummyNob1.turnTowards(dummyNob1.getDirectionTo(dummyNob2));
			}
		}else
		
		if(tickCount >= fadeOut2 + 120 && tickCount < inAgain){
			if(tickCount == fadeOut2 + 120){
				core.setCutsceneFilter(new CombinedFilter(sf, df));
			}
			df.darkness = Math.min(255, tickCount - fadeOut2 - 120);
			if(tickCount > fadeOut2 + 120 + 300){
				tickCount = inAgain;
			}
		}else
		
		if(tickCount>= inAgain && tickCount < fadeOut3 - 30){
			if(tickCount < inAgain + 255){
				df.darkness = 255 - (tickCount - inAgain);
			}
			if(tickCount == inAgain + 255){
				core.setCutsceneFilter(sf);
			}
			dummyNob1.turnTowards(dummyNob1.getDirectionTo(dummyNob2));
		}else
		
		if(tickCount == fadeOut3 - 29){
			core.setCutsceneFilter(rsf);
		}else
		
		if(tickCount > fadeOut3 && tickCount < backToStart){
			if(tickCount == fadeOut3 + 1){
				core.setCutsceneFilter(new CombinedFilter(sf, df));
			}
			df.darkness = Math.min(tickCount - fadeOut3, 255);
			if(tickCount > fadeOut3 + 300){
				tickCount = backToStart;
			}
		}else
		
		if(tickCount == backToStart + 1){
			core.setCutsceneFilter(Filter.NO_FILTER);
			core.village.centerSubject = pl;
			core.village.controlledSprite = pl;
		}
		
		super.tick();
	}

	@Override
	public boolean isFinished() {
		return !no.getHome().contains(pl.getX(), pl.getY());
	}
	
	@Override
	public void close(){
		super.close();
		
		core.village.removeSprite(dummyNob1);
		core.village.removeSprite(dummyNob2);
		no.lastVictim = pl;
		
		no.isPartOfCutscene = false;
		no.setMode(Villager.MODE_RELAX_AT_HOME, Villager.IMPORTANT_MEDIUM, null);
		pl.isPartOfCutscene = false;
		
	}
	

}
