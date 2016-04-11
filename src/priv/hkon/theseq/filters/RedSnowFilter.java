package priv.hkon.theseq.filters;

import priv.hkon.theseq.sprites.Sprite;

public class RedSnowFilter extends Filter {

	public RedSnowFilter() {
	}
	
	public void apply(int[] data){
		for(int i = 0; i < data.length; i++){
			int r = (data[i] >> 16) & 255;
			int g = (data[i] >> 8) & 255;
			int b = data[i] & 255;
			int gr = (2*r + 3*g + b)/12;
			float d = Sprite.RAND.nextFloat();
			
			r = Math.min(255, (int)((60)*(1 + d)));
			
			data[i] = (255 << 24) | ((r + gr) << 16) | (gr << 8) | gr;
		}
	}

}
