package priv.hkon.theseq.sprites;

import priv.hkon.theseq.cutscenes.HistoryOfVictim1Cutscene;
import priv.hkon.theseq.misc.VillageEvent;
import priv.hkon.theseq.structures.House;
import priv.hkon.theseq.world.Village;

public class Nobody extends Villager {
	
	private static final long serialVersionUID = -5133115310564670168L;
	
	static boolean presented = false;
	
	public static final int EVENT_WAITING_FOR_PLAYER_ENTRANCE = 0;

	public Nobody(int x, int y, Village v, House h, int i) {
		super(x, y, v, h, i);
	}
	
	public boolean tick(){
		return super.tick();
	}
	
	public String[] getMeaningOfLife(){
		return new String[] {"I dunno."};
	}
	
	public String[] getPresentation(){
		return new String[] {
				"Hi!",
				"I am a Nobody",
				"Yes, that's what we're called.",
				"The reason is really just that we don't do anything",
				"It's a bit like our Mayor,",
				"But Nobodies don't even have a flick of formal responsibility",
				"It's a careless life",
				"It is all right, I guess..",
				"It is said that one day, someone will give us proper jobs",
				"The Prophet says so, anyways...",
				"I still wish they would call us something else, though",
				"Nobodies? That's kind of insulting, don't you think?",
				"Our names are even numbered..",
				"But.. I should return to my nobodyness"
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
		if(modeParameter == SPEECH_PRESENTING){
			presented = true;
		}
		return false;
	}
	
	public String getName(){
		return "Nobody " + citizenNumber;
	}
	
	@Override
	public void processEvent(VillageEvent ev){
		switch(ev.getID()){
		case Nobody.EVENT_WAITING_FOR_PLAYER_ENTRANCE : 
			{
			village.setCutscene(new HistoryOfVictim1Cutscene(this, village.getPlayer(), village.getCore()));
			}
		}
	}

}
