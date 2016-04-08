package priv.hkon.theseq.cutscenes;

import priv.hkon.theseq.main.Core;
import priv.hkon.theseq.misc.VillageEvent;
import priv.hkon.theseq.sprites.Movable;
import priv.hkon.theseq.sprites.Player;
import priv.hkon.theseq.sprites.TalkativeSprite;
import priv.hkon.theseq.sprites.Villager;
import priv.hkon.theseq.sprites.Woodcutter;

public class WoodCutterGuidesPlayerCutscene extends Cutscene {

	Player player;
	Woodcutter woodcutter;
	
	boolean fin = false;
	
	public WoodCutterGuidesPlayerCutscene(Woodcutter wc, Player p, Core c) {
		super(c);
		player = p;
		woodcutter = wc;
		player.isPartOfCutscene = true;
		wc.isPartOfCutscene = true;
		
		happenings.add(new Happening(woodcutter, 30){
			public void happen(){
				((TalkativeSprite)sprite).showDialog("Hey there!", 2*60);
			}
		});
		
		happenings.add(new Happening(woodcutter, 30){
			public void happen(){
				((Movable)sprite).turnTowards(Movable.UP);
			}
		});
		
		happenings.add(new Happening(player, 2*60){
			public void happen(){
				((Movable)sprite).startPathTo(player.getX(), player.getY() + 2);
			}
		});
		
		if(!woodcutter.classHasPresented()){
			happenings.add(new Happening(woodcutter, 3*60){
				public void happen(){
					((TalkativeSprite)sprite).showDialog("I should present myself...", 2*60);
				}
			});
			happenings.add(new Happening(woodcutter, 5*60){
				public void happen(){
					((Woodcutter)woodcutter).addSentence(((Woodcutter)woodcutter).getPresentation());
					((Woodcutter)woodcutter).showDialog = true;
					Woodcutter.presented = true;
				}
			});
			
			
		}
		
		happenings.add(new Happening(woodcutter, new VillageEvent(core.village, woodcutter, 0){
			public boolean isHappening(){
				//System.out.println("Returned " + !((TalkativeSprite)subject).showDialog);
				return !((TalkativeSprite)subject).showDialog;
			}
		}){
			public void happen(){
				((TalkativeSprite)sprite).addSentence(new String[]{
					"I heard you would stay here",
					"I thought maybe you would be",
					"interested in something to do?",
					"I would really like to get to know..",
					"If you are a good punch or",
					"Just as useless as all the nobodies",
					"Sounds like a plan?",
					"Good, follow me"
				});
				((TalkativeSprite)sprite).showDialog = true;
			}
		});
		
	}
	

	@Override
	public boolean isFinished() {
		return happenings.isEmpty()&& !woodcutter.showDialog;
	}
	
	public void tick(){
		super.tick();
	}
	
	public void close(){
		super.close();
		
		woodcutter.startPathTo(woodcutter.cabin.getX() + woodcutter.cabin.getW()/2, woodcutter.cabin.getY() + woodcutter.cabin.getH() + 2);
		woodcutter.setMode(Villager.MODE_SPEAKING_TO_PLAYER, Villager.IMPORTANT_VERY, player);
		woodcutter.nextMode = Woodcutter.MODE_WAIT_FOR_PLAYER_FILLING_CRATE;
		woodcutter.setToSpeakMode(new String[] {
				"As I told you, I am the woodcutter.",
				"I usually do the chopping.",
				"Now, there is a lot more to woodcutting than chopping",
				"For instance, there is carrying the wood to its place",
				"Dead, delicious, dry wood doesn't store itself",
				"So that's where you come in",
				"Didn't think I would give you the axe, did you?",
				"Who knows what you could do with that?",
				"Damage houses? Murder? BREAK IT??",
				"Nope, you just got here, you must start at the bottom",
				"So, the thing is.. ",
				"I have been doing a lot of chopping recently",
				"They call me a maniac, the nobodies",
				"Telling me I'm chopping too much",
				"Chopping, chopping, chopping...",
				"But hey! I like it that way!", 
				"And at least I am doing SOMETHING",
				"Those nobodies wouldn't be able to hold any job,",
				"They're way too ambitionless to be able to do anything of use",
				"Where was I..",
				"Right! I did the chopping, now you do the carrying",
				"Easy as that. Get 10 logs to the storage crate next to my cabin.",
				"I will see if you can get a reward afterwards",
				"Now off you go!"
		}, new Integer[0], 0);
		player.isPartOfCutscene = false;
		woodcutter.isPartOfCutscene = false;
	}

}
