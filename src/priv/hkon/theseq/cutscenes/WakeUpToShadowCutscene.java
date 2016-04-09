package priv.hkon.theseq.cutscenes;

import priv.hkon.theseq.filters.DarkenFilter;
import priv.hkon.theseq.filters.Filter;
import priv.hkon.theseq.filters.RedSnowFilter;
import priv.hkon.theseq.main.Core;
import priv.hkon.theseq.sprites.Movable;
import priv.hkon.theseq.sprites.Player;
import priv.hkon.theseq.sprites.Shadow;

public class WakeUpToShadowCutscene extends Cutscene {
	
	Player pl;
	Shadow sh;
	
	RedSnowFilter rsf = new RedSnowFilter();
	DarkenFilter df = new DarkenFilter(255);
	
	int wakeUpMoment = 3*60;
	
	public boolean showShadow;

	public WakeUpToShadowCutscene(Player player, Shadow shadow, Core core) {
		super(core);
		pl = player;
		sh = shadow;
		
		pl.isPartOfCutscene = true;
		sh.isPartOfCutscene = true;
		
		happenings.add(new Happening(pl,wakeUpMoment + 0){
			public void happen(){
				pl.turnTowards(Movable.DOWN);
			}
		});
		
		happenings.add(new Happening(pl, wakeUpMoment + 60*7){
			public void happen(){
				pl.startPathTo(pl.getHome().getX() + pl.getHome().getW()/2, pl.getHome().getY() + pl.getHome().getH() + 1);
				
			}
		});
		
	}
	
	@Override
	public void tick(){
		if(tickCount < wakeUpMoment - 60){
			core.setCutsceneFilter(df);
			pl.hideDialog();
		}else if(tickCount == wakeUpMoment-60){
			core.setCutsceneFilter(Filter.NO_FILTER);
			core.village.nightBoost = false;
		}
		if(tickCount == wakeUpMoment + 60 ){
			showShadow = true;
			core.setCutsceneFilter(rsf);
			int sx = pl.getHome().getX() + pl.getHome().getW()/2;
			int sy = pl.getHome().getY() + pl.getHome().getH()/2;
			core.village.setSpriteAt(sh, sx, sy);
			sh.setX(sx);
			sh.setY(sy);
			if(!sh.hasPath()){
				sh.startPathTo(pl.getX() - 1, pl.getY());
			}
		}

		if(tickCount == wakeUpMoment + 150){
			showShadow = false;
			if(core.wavIsPlaying()){
				core.stopWavSound();
			}
			core.setCutsceneFilter(Filter.NO_FILTER);
			core.village.setSpriteAt(null, sh.getX(), sh.getY());
			sh.setHasPath(false);
		}else
		if(showShadow){
			
			if(!core.wavIsPlaying()){
				core.playWavSound(Core.WAV_NOISE);
			}
		}
		
		if(tickCount > wakeUpMoment + 60 && !showShadow ){
			if(tickCount % (2*60) == 0){
				if(!core.wavIsPlaying()){
					core.playWavSoundDampened(Core.WAV_NOISE);
				}
			}else if(tickCount % (2*60) == 60){
				if(core.wavIsPlaying()){
					core.stopWavSound();
				}
			}
		}
		
		super.tick();
	}

	@Override
	public boolean isFinished() {
		return !pl.getHome().contains(pl.getX(), pl.getY());
	}
	
	@Override
	public void close(){
		super.close();
		
		pl.isPartOfCutscene = false;
		sh.isPartOfCutscene = false;
		pl.createWerewolfSetting();
		if(core.wavIsPlaying()){
			core.stopWavSound();
		}
	}

}
