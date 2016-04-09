package priv.hkon.theseq.sprites;

import priv.hkon.theseq.nonblocks.Blood;
import priv.hkon.theseq.nonblocks.TileCover;
import priv.hkon.theseq.structures.House;
import priv.hkon.theseq.world.Village;

public class Janitor extends Villager {
	
	static boolean presented = false;
	
	public static final int CLEAN_RANGE = 8;
	
	public static final int MODE_CLEANING = 1000;
	boolean isCleaning = false;

	public Janitor(int x, int y, Village v, House h, int i) {
		super(x, y, v, h, i);
	}

	private static final long serialVersionUID = -4398530349799908294L;
	
	public void makeAnimationFrames(){
		super.makeAnimationFrames();
		
		for(int k = 0; k < animationFrames.length; k++){
			for(int u = 0; u < animationFrames[k].length; u++){
				for(int i = 0 ; i < H; i++){
					for( int j = 0; j < W; j++){
						if(i < 3 && Math.abs(j - W/2) < 4){
							animationFrames[k][u][i][j] = Sprite.getColor(255 - Math.abs(j -W/2)*20, 0, 0);
						}
					}
				}
			}
		}
		
		for(int u = 0; u < animationFrames[0].length; u++){
			for(int i = 0 ; i < H; i++){
				for( int j = 0; j < W; j++){
					
					if(j > W/2 && i == 2){
						animationFrames[RIGHT][u][i][j] = Sprite.getColor(255, 0, 0);
					}
					
					if(j < W/2 && i == 2){
						animationFrames[LEFT][u][i][j] = Sprite.getColor(255, 0, 0);
					}
					
					if(i == 2 && Math.abs(j - W/2) < 4){
						animationFrames[DOWN][u][i][j] = Sprite.getColor(255, 0, 0);
					}
				}
			}
		}
	}
	
	public boolean tick(){
		if(super.tick()){
			return true;
		}
		
		switch(targetMode){
			case MODE_CLEANING: {
				//System.out.println("Should clean");
				TileCover tc;
				if(targetSprite != null){
					cleanUp((TileCover)targetSprite);
				}else
				if((tc = findDirt()) != null){
					cleanUp(tc);
					//System.out.println("Found dirt");
					return true;
				}
				break;
			}
		}
		return false;
	}

	@Override
	public String[] getMeaningOfLife() {
		return new String[] {
			"A clean house resolves to a clean mind",
			"And nothing could be better than a clean mind, eyh?",
			"So: Keep everything clean, that's the meaning of life!"
		};
	}

	@Override
	public String[] getPresentation() {
		return new String[] {
				"I am the Janitor!",
				"I clean up and things like that",
				"",
				"You don't seem very impressed by my profession",
				"You're not the only one, either",
				"Even the Nobodies seem to mock me",
				"They believe that doing nothing is better than cleaning all day",
				"But, I tell you, that is utterly, utterly wrong!",
				"You see, I fancy things that are clean",
				"Things that are shiny",
				"Things that are properly maintained",
				"Nothing makes me more happy than breathing in the fresh air",
				"In a newly cleaned house!",
				"And the Nobodies would rather do nothing?",
				"That's pure madness, I tell you! Pure madness!",
				"Well, I will resume to my duty",
				"We will meet again, until then...",
				"Keep it clean"
		};
	}

	@Override
	public boolean classHasPresented() {
		return presented;
	}

	@Override
	public boolean subclassSpeechInterrupted() {
		return false;
	}

	@Override
	public boolean subclassSpeechFinished() {
		return false;
	}
	
	public boolean isDirt(TileCover tc){
		return tc instanceof Blood;
	}
	
	public TileCover findDirt(){
		TileCover t = null;
		int mint = 10000;
		for(int i = getY() - Janitor.CLEAN_RANGE; i < getY() + Janitor.CLEAN_RANGE; i++){
			for(int j = getX() - Janitor.CLEAN_RANGE; j < getX() + Janitor.CLEAN_RANGE; j++){
				if(village.getTileCoverAt(j, i) != null && isDirt(village.getTileCoverAt(j,i))&& village.getSpriteAt(j, i) == null){
					if(mint > distTo(j, i)){
						t = village.getTileCoverAt(j, i);
						mint = distTo(j, i);
					}
				}
			}
		}
		return t;
	}
	
	public Villager findCorpse(){
		for(int i = getY() - 2; i < getY() + 2; i++){
			for(int j = getX() - 2; j < getX() + 2; j++){
				Sprite s;
				if((s =village.getSpriteAt(j, i) ) != null && s instanceof Villager && ((Villager)s).dead){
					return (Villager)s;
				}
			}
		}
		return null;
	}
	
	public void cleanUp(TileCover tc){
		Villager v;
		if((v =findCorpse()) != null){
			//showDialog("Oh no..", 60);
			tryStepTowards(v.getX(), v.getY());
			village.setSpriteAt(null, v.getX(), v.getY());
			return;
		}
		if(hasPath){
			return;
		}
		isCleaning = true;
		if(tc.getX() == getX() && tc.getY() == getY()){
			village.setTileCoverAt(null, getX(), getY());
			//showDialog("Clean.. Clean..", 60);
			TileCover tc2;
			if((tc2 = findDirt()) != null){
				
				isCleaning = true;
				cleanUp(tc2);
			}else{
				isCleaning = false;
				setMode(Villager.MODE_RELAXING, IMPORTANT_NOT, null);
				return;
			}
		}else
		if(village.getSpriteAt(tc.getX(), tc.getY()) == null){
			targetSprite = tc;
			startPathTo(tc.getX(), tc.getY());
		}
	}

}
