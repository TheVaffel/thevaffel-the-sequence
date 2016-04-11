package priv.hkon.theseq.cutscenes;

import priv.hkon.theseq.filters.DarkenFilter;
import priv.hkon.theseq.filters.Filter;
import priv.hkon.theseq.main.Core;
import priv.hkon.theseq.misc.VillageEvent;
import priv.hkon.theseq.sprites.Doctor;
import priv.hkon.theseq.sprites.Janitor;
import priv.hkon.theseq.sprites.Movable;
import priv.hkon.theseq.sprites.Nobody;
import priv.hkon.theseq.sprites.Player;
import priv.hkon.theseq.sprites.Prophet;
import priv.hkon.theseq.sprites.Villager;
import priv.hkon.theseq.world.Village;

public class FirstVictimDiscoveredCutscene extends Cutscene {
	
	Player pl;
	Prophet pr;
	Doctor dc;
	Villager victim;
	
	int nextTalkToVillager = 2;
	
	DarkenFilter df = new DarkenFilter(0);
	
	int prophetEntersTime = 180;
	
	boolean finished = false;
	
	int doctorReachesVictimTime  = 10000;
	
	public FirstVictimDiscoveredCutscene(Player player, Prophet prophet, Doctor doctor, Villager v, Core c) {
		super(c);
		
		pl = player;
		pr = prophet;
		dc = doctor;
		victim = v;
		
		
		pl.isPartOfCutscene = true;
		pr.isPartOfCutscene = true;
		pr.setHasPath(false);
		dc.setHasPath(false);
		dc.isPartOfCutscene = true;
		pl.movedFraction = 1;
		
		happenings.add(new Happening(prophetEntersTime){
			public void happen(){
				core.village.moveSpriteTo(pl, pl.VICTIM_LOCATION_X - 1, pl.VICTIM_LOCATION_Y  + 2);
				core.village.moveSpriteTo(pr, pl.VICTIM_LOCATION_X + 1, pl.VICTIM_LOCATION_Y  + 2);
				core.village.moveSpriteTo(dc, pl.VICTIM_LOCATION_X, pl.VICTIM_LOCATION_Y  - 20);
				dc.startPathTo(pl.VICTIM_LOCATION_X + 1, pl.VICTIM_LOCATION_Y);
				pl.turnTowards(Movable.UP);
			}
		});
		
		happenings.add(new Happening(prophetEntersTime + 60){
			public void happen(){
				pr.addAndShowSentence(new String[] {
						"He should be here any minute now..",
						"",
						"What a terrible incident..."
				});
				pr.turnTowards(Movable.UP);
			}
			
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return dc.getX() == victim.getX() + 1 && dc.getY() == victim.getY();
			}
		}){
			public void happen(){
				tickCount = doctorReachesVictimTime;
				pr.addAndShowSentence(new String[]{
						"Now, doctor.. What can you say?"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !pr.showDialog;
			}
		}){
			public void happen(){
				dc.turnTowards(Movable.LEFT);
				dc.addAndShowSentence(new String[]{
						"Yup, he's dead"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !dc.showDialog;
			}
		}){
			public void happen(){
				pr.addAndShowSentence(new String[]{
						"..."
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !pr.showDialog;
			}
		}){
			public void happen(){
				dc.turnTowards(Movable.DOWN);
				dc.addAndShowSentence(new String[]{
						"What?",
						"You want me to say more?",
						"I'm not some kind of wizard, resurrecting dead humens",
						"Yes, I am sorry and blah blah blah",
						"What do you expect of me?",
						"I'm just a humen like you! I have no superpowers",
						"You know what?",
						"These expectations make me sick",
						"You better watch out and accept me as I am."
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !dc.showDialog;
			}
		}){
			public void happen(){
				pr.addAndShowSentence(new String[]{
						"Oh nonono! I didn't mean to..",
						"",
						"But.. What can you say about the cause of death?"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !pr.showDialog;
			}
		}){
			public void happen(){
				dc.turnTowards(Movable.LEFT);
				dc.addAndShowSentence(new String[]{
						"I dunno",
						"Probably drowning"
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !dc.showDialog;
			}
		}){
			public void happen(){
				dc.isPartOfCutscene = false;
				dc.goToBed();
				pr.addAndShowSentence(new String[]{
						"But.. But.."
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !pr.showDialog;
			}
		}){
			public void happen(){
				pr.turnTowards(Movable.LEFT);
				pl.turnTowards(Movable.RIGHT);
				pr.addAndShowSentence(new String[]{
						"Well. That was helpful",
						"We should be going.",
						"I will call the Janitor to clean up.."
				});
			}
		});
		
		happenings.add(new Happening(new VillageEvent(core.village, dc){
			public boolean isHappening(){
				return !pr.showDialog;
			}
		}){
			public void happen(){
				pr.startGoHome();
				finished = true;
			}
		});
	}

	public void tick(){
		if(tickCount <prophetEntersTime){
			df.darkness = Math.min(2*tickCount, 255);
			core.setCutsceneFilter(df);
		}else
		if(tickCount == prophetEntersTime){
			core.setCutsceneFilter(Filter.NO_FILTER);
		}
		
		super.tick();
	}

	@Override
	public boolean isFinished() {
		return finished;
	}
	
	public void close(){
		super.close();
		//TODO
		
		core.village.setNextDayMood(Village.MOOD_MURDER_IN_FOREST);
		core.village.setNextPublicSentence(new String[] {"You should check out house " + core.village.getHouseNum(nextTalkToVillager),
			"Er said that er had something for you specifically",
			"By the way, in case noone has told you",
			"All our houses are numbered with two digits",
			"The first is the number of the house from west to east",
			"And the other is the number from north to south",
			"Easy! If I remember correctly, that is..",
			"House " + core.village.getHouseNum(nextTalkToVillager) + "!",
			"You can't miss it!"
		});
		
		Villager v = ((Villager)core.village.getCitizen(nextTalkToVillager));
		
		v.setWaitForEvent(new VillageEvent(core.village, Nobody.EVENT_WAITING_FOR_PLAYER_ENTRANCE){
			public boolean isHappening(){
				return v.getHome().isClosedAtGlobal(core.village.getPlayer().getX(), core.village.getPlayer().getY());
			}
		});
		
		v.setPosition(v.getHome().getX() + v.getHome().getW()/2, v.getHome().getY() + v.getHome().getH()/2);
		
		dc.isPartOfCutscene = false;
		pl.isPartOfCutscene = false;
		pr.isPartOfCutscene = false;
		
		Janitor janitor = core.village.getJanitor();
		janitor.startPathTo(pl.getHome().getX() - 2, pl.getHome().getY() + pl.getHome().getH() + 2);
		janitor.setMode(Janitor.MODE_CLEANING, Villager.IMPORTANT_VERY, null);
	}

}
