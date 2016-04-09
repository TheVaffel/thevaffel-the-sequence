package priv.hkon.theseq.nonblocks;

import priv.hkon.theseq.sprites.Sprite;
import priv.hkon.theseq.world.Tile;
import priv.hkon.theseq.world.Village;

public class Blood extends TileCover{
	
	private static final long serialVersionUID = 3051188166401489596L;

	public Blood(int nx, int ny, Village v) {
		super(nx, ny, v);
	}

	@Override
	public void makeData(){
		for(int i = H - Tile.HEIGHT; i < H;  i++){
			for(int j = 0; j < W ;j++){
				int di = Math.abs(i - H/2);
				int dj = Math.abs(j - W/2);
				if(16*di*di + 9*dj*dj < 100 + RAND.nextInt(300)){
					data[i][j] = Sprite.getColor(128, 0, 0);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "blood";
	}

}
