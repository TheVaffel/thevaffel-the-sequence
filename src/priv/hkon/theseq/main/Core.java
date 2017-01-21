package priv.hkon.theseq.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import priv.hkon.theseq.filters.CombinedFilter;
import priv.hkon.theseq.filters.Filter;
import priv.hkon.theseq.filters.NightFilter;
import priv.hkon.theseq.world.Tile;
import priv.hkon.theseq.world.Village;

public class Core implements Runnable{ 
	Screen screen;
	public static final String TITLE = "The Sequence";
	
	public Village village;
	
	boolean worldInitiated = false;
	boolean loadingInitiated = false;
	
	Filter cutsceneFilter = Filter.NO_FILTER;
	Filter villageFilter = Filter.NO_FILTER;
	
	NightFilter nightFilter = new NightFilter(0);
	
	boolean changedFilter = true;

	boolean playing = false;
	static final double TICKS_PER_SECOND = 60;
	
	public static final String MIDI_TITLE = "sound/TitleScreen.MID";
	public static final String MIDI_SLEEP = "sound/Sleep.MID";
	public static final String MIDI_MAIN_THEME_DAY = "sound/MainThemeDay.MID";
	public static final String MIDI_MAIN_THEME_NIGHT = "sound/MainThemeNight.MID";
	
	public static final String WAV_NOISE = "sound/noise.wav";
	public static final String WAV_TITLE = "sound/TitleScreen.WAV";
	public static final String WAV_SLEEP = "sound/Sleep.WAV";
	public static final String WAV_MAIN_THEME_DAY = "sound/MainThemeDay.WAV";
	public static final String WAV_MAIN_THEME_NIGHT = "sound/MainThemeNight.WAV";
	public static final String WAV_LOVE_THEME = "sound/LoveTheme.WAV";
	Clip currClip = null;
	Sequencer seq = null;
	
	public static void main(String[] args) {
		Core c = new Core();
		c.start();

	}
	
	public Core(){
		screen = new Screen(this);
	}
	
	long currentTime = System.nanoTime();
	long lastTime = currentTime;
	
	public void start(){
		screen.runTitleScreen();
		screen.update();
		playWavSound(WAV_TITLE);
		
		
		while(Controller.numin == 0){
			screen.runTitleScreen();
			screen.update();
			try{
				Thread.sleep(40);
			}catch(Exception e){}
		}
		
		loadingInitiated = true;
		if(seq != null){
			seq.stop();
		}
		
		new Thread(this).start();
		//System.out.println("At first");
		
		playWavSoundDampened(WAV_NOISE);

		while(!worldInitiated){
			screen.runTitleScreen();
			screen.update();
			try{
				Thread.sleep(40);
			}catch(Exception e){}
		}
		for(int i = 0; i < 255; i+=10){
			screen.runTitleScreen();
			screen.darkenImage(i);
			screen.update();
			try{
				Thread.sleep(40);
			}catch(Exception e){}
		}
		stopWavSound();
		
		//screen.setData(village.getScreenData(Screen.W, Screen.H));
		currentTime = System.nanoTime();
		lastTime = currentTime;
		
		playing = true;
		
		
		double unprocessedSeconds = .02;
		
		int numTicks = 0;
		int fps = 0;
		
		while(playing){
			currentTime = System.nanoTime();
			unprocessedSeconds += (currentTime - lastTime)/1000000000.0;
			lastTime = currentTime;
			while(unprocessedSeconds > 1.0/TICKS_PER_SECOND){
				numTicks++;
				unprocessedSeconds -= 1.0/TICKS_PER_SECOND;
				tick();
				if(numTicks == 60){
					System.out.println("FPS: " + fps);
					fps = 0;
					numTicks = 0;
				}
			}
			
			draw();
			fps++;
			
			try{
				if(System.nanoTime() - lastTime < 1000000000L/TICKS_PER_SECOND)
					Thread.sleep((long)(1000.0/TICKS_PER_SECOND - (System.nanoTime() - lastTime)/1000000.0));
			}catch(Exception e){}
		}
	}
	
	void tick(){
		screen.count++;
		village.tick();
	}
	
	public void setCutsceneFilter(Filter f){
		if(f != cutsceneFilter){
			cutsceneFilter = f;
			changedFilter = true;
		}
	}
	
	public void setVillageFilter(Filter f){
		if(f != villageFilter){
			changedFilter = true;
			villageFilter = f;
		}
		
	}
	
	void draw(){
		screen.setData(village.getScreenData(Screen.W, Screen.H));
		float f = village.getNightFactor();
		nightFilter.factor = f;
		if(changedFilter){
			screen.setFilter(new CombinedFilter(nightFilter, villageFilter, cutsceneFilter));
		}
		screen.update();
		changedFilter = false;
	}
	
	public Filter getCutsceneFilter(){
		return cutsceneFilter;
	}

	@Override
	public void run() {
		Tile.init();
		village = new Village(this);
		//System.out.println("At last");
		for(int i = 0; i< 3*30; i++){
			village.tick();
			try{
				Thread.sleep(30);
			}catch(Exception e){}
		}
		
		worldInitiated = true;
	}

	public void saveVillage(int savenr){
		String filename = "saves/" + savenr + ".sav";
		ObjectOutputStream oos = null;
		try{
			oos= new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(village);
			village.lastSave = village.getTime();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void loadImage(int savenr){
		String filename = "saves/"  + savenr + ".sav";
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream(new FileInputStream(filename));
			village = (Village)ois.readObject();
			village.setCore(this);
			currentTime = System.nanoTime();
			lastTime = currentTime;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void resyncronizeTime(){
		currentTime = System.nanoTime();
		lastTime = currentTime;
	}
	
	public boolean isInitiated(){
		return loadingInitiated;
	}
	
	public void playMidiSound(String str){
		stopMidiSound();
		try {
			if(seq == null){
				seq = MidiSystem.getSequencer();
			}
			
			seq.open();
			
			InputStream is = new BufferedInputStream(new FileInputStream(new File(str)));
			
			seq.setSequence(is);
			
			seq.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//System.out.println("Midi length: " + seq.getMicrosecondLength()/1000000.0);
	}
	
	public void stopMidiSound(){
		if(seq != null){
			seq.stop();
		}
	}
	
	public boolean midiIsPlaying(){
		return seq.isRunning();
	}
	
	public void playWavSound(String str){
		if(currClip != null && currClip.isRunning()){
			currClip.stop();
		}
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
				    new File(str));
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			
		    currClip = (Clip)AudioSystem.getLine(info);
		    currClip.open(audioInputStream);
		    //FloatControl gainControl = 
		        //(FloatControl) currClip.getControl(FloatControl.Type.MASTER_GAIN);
		    //gainControl.setValue(-20.0f); 
		    currClip.start();
		 
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void playWavSoundDampened(String str){
		if(currClip != null && currClip.isRunning()){
			currClip.stop();
		}
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
				    new File(str));
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			
		    currClip = (Clip)AudioSystem.getLine(info);
		    currClip.open(audioInputStream);
		    /*FloatControl gainControl = 
		        (FloatControl) currClip.getControl(FloatControl.Type.MASTER_GAIN);
		    gainControl.setValue(-20.0f);*/ 
		    currClip.start();
		 
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void stopWavSound(){
		if(currClip != null){
			currClip.stop();
			currClip.close();
		}
	}
	
	public boolean wavIsPlaying(){
		return currClip.isActive();
	}
	
	public Screen getScreen(){
		return screen;
	}
}
