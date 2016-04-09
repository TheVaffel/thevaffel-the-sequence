package priv.hkon.theseq.sprites;

import priv.hkon.theseq.structures.House;
import priv.hkon.theseq.world.Village;

public class Doctor extends Villager {
	
	private static final long serialVersionUID = -5622601122451566586L;
	static boolean presented = false;

	public Doctor(int x, int y, Village v, House h, int i) {
		super(x, y, v, h, i);
	}
	
	@Override
	public void makeAnimationFrames(){
		super.makeAnimationFrames();
		
		for(int k = 0; k < animationFrames.length; k++){
			for(int u = 0 ; u < animationFrames[k].length; u++){
				for(int i = 2; i < 4;i ++){
					for(int j = 0 ;j < W ;j++){
						if((animationFrames[k][u][i][j] & (255 << 24)) != 0){
							animationFrames[k][u][i][j] = Sprite.getColor(80, 50, 20);
						}
					}
				}
			}
		}
		for(int u = 0 ;u < animationFrames[0].length; u++){
			for(int i = 0; i < H; i++){
				for( int j = 0; j < W; j ++){
					if(j == W - 3&& i < 4){
						animationFrames[RIGHT][u][i][j] = Sprite.getColor(180, 180, 230);
					}
					if(j == 2 && i < 4){
						animationFrames[LEFT][u][i][j] = Sprite.getColor(180, 180, 230);
					}
					if(i < 4){
						float di = i - 1.5f;
						int dj = j - W/2;
						if(di*di + dj *dj < 8){
							animationFrames[DOWN][u][i][j] = Sprite.getColor(180, 180, 230);
						}
					}
				}
			}
		}
	}

	@Override
	public String[] getMeaningOfLife() {
		return new String[] {
				"Health! Healthy humens!",
				"That's my meaning in this life for you"
		};
	}

	@Override
	public String[] getPresentation() {
		return new String[] {
				"I am the doctor!",
				"Whenever someone gets sick, I am the one to call!",
				"",
				"Well. That's what I tell the others, anyways",
				"You seem like a trustworthy fellow.. I should tell you this",
				"My license is just as valuable as the grass outside your house",
				"The reason I am the doctor is that deep inside, we humens are afraid.",
				"Deeply, deeply afraid of becoming ill, unable to recover",
				"I mean. Our lives aren't THAT exciting",
				"But I believe we all feel comfort in just being what we are",
				"So, the story is:",
				"I once helped a Nobody recover from anxiety",
				"I just told him that 'noone could be more than exactly what they are'",
				"That helped em out somehow",
				"He had suffered a while, so the word spread quickly",
				"I tried to convince them that I in no way have healing powers",
				"But those short-minded fools didn't really trust that..",
				"I realized that they really are afraid.",
				"That's why I took this burden on my shoulders",
				"I get called out to a lot of cases, but really never do anything but talk",
				"But the comfort of my voice is all they need, apparently",
				"Oh well. I was a Nobody once. Now I am a liar",
				"Hopefully, I can quit this nonsense sometime",
				"Oh, anyway, hope to see you around!",
				".. Or perhaps not. Heh"
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

}
