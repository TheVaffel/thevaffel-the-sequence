package priv.hkon.theseq.cutscenes;

import priv.hkon.theseq.filters.DarkenFilter;
import priv.hkon.theseq.filters.Filter;
import priv.hkon.theseq.main.Core;
import priv.hkon.theseq.sprites.Movable;
import priv.hkon.theseq.sprites.Player;
import priv.hkon.theseq.sprites.Prophet;
import priv.hkon.theseq.sprites.TalkativeSprite;
import priv.hkon.theseq.sprites.Villager;

public class OpeningScene extends Cutscene {
	
	Player player;
	Villager prophet;
	
	DarkenFilter darkenFilter;
	
	int startTime = 240;
	
	boolean finished = false;

	public OpeningScene(Player player, Villager prophet, Core core) {
		super(core);
		this.player = player;
		this.prophet = prophet;
		
		player.isPartOfCutscene = true;
		prophet.isPartOfCutscene = true;
		darkenFilter = new DarkenFilter(0);
		
		int px = player.getX();
		int py = player.getY();
		core.getScreen().drawInstructions = true;
		
		happenings.add(new Happening(player, 120){
			public void happen(){
				((Movable)sprite).turnTowards(Movable.RIGHT);
			}
		});
		
		happenings.add(new Happening(player, 180){
			public void happen(){
				((Movable)sprite).turnTowards(Movable.LEFT);
			}
		});
		
		happenings.add(new Happening(prophet, startTime){
			public void happen(){
				((Movable)(sprite)).startPathTo(px, py - 2);
			}
		});
		
		happenings.add(new Happening(player, 280){
			public void happen(){
				((Movable)sprite).turnTowards(Movable.RIGHT);
			}
		});
		
		
		happenings.add(new Happening(prophet, startTime + 120){
			public void happen(){
				((TalkativeSprite)sprite).addSentence(new String[] {"Well, hello there!",
					"Can't say I've seen your face here before!",
					"Who are you?",
					"...",
					"By the look on your face,",
					"I guess you are just as confused as I am",
					"Don't worry, there will be time",
					"Come with me!"
				});
				((TalkativeSprite)sprite).showDialog = true;
			}
		});
		happenings.add(new Happening(player, startTime + 120){
			public void happen(){
				((Movable)(sprite)).turnTowards(Movable.UP);
			}
		});
		
		/*happenings.add(new Happening(prophet, startTime + 120){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("Well, hello there!", 90);
			}
		});
		
		
		
		happenings.add(new Happening(prophet,startTime +  240){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("Can't say I've seen your face here before!", 150);
			}
		});
		
		happenings.add(new Happening(prophet, startTime + 420){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("Who are you?", 120);
			}
		});
		
		happenings.add(new Happening(prophet,startTime +  600){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("...", 60);
			}
		});
		
		happenings.add(new Happening(prophet, startTime + 720){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("By the look on your face," ,120);
			}
		});
		
		happenings.add(new Happening(prophet, startTime + 840){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("I guess you are just as confused as I am", 120);
			}
		});
		
		happenings.add(new Happening(prophet, startTime + 1020){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("Don't worry, there will be time", 180);
			}
		});
		
		happenings.add(new Happening(prophet, startTime + 1260){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("Come with me!", 180);
				
			}
		});*/
		
		
	}

	@Override
	public void tick() {
		if(tickCount < 240){
			darkenFilter.darkness = 255 - tickCount;
			core.setCutsceneFilter(darkenFilter);
		}else if(tickCount == 240){
			core.setCutsceneFilter(Filter.NO_FILTER);
			
		}else if(tickCount >= 60*4 + startTime && !prophet.showDialog){
			finished = true;
		}
		super.tick();
		
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	public void close(){
		super.close();
		
		player.isPartOfCutscene = false;
		prophet.isPartOfCutscene = false;
		core.getScreen().drawInstructions = false;
		prophet.startPathTo(core.village.getTownMiddleX(), core.village.getTownStartY() + core.village.getTownHeight()  - 1);
		prophet.setToSpeakMode(((Prophet)prophet).INTRODUCTION, Villager.SPEECH_PRESENTING);
	}
}
