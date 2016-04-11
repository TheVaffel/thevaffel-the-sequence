package priv.hkon.theseq.filters;

public class SepiaFilter extends Filter {

	public SepiaFilter() {
	}
	
	public void apply(int[] data){
		for(int i = 0; i < data.length; i++){
			int r = ((data[i] & (255 << 16))>> 16);
			int g = ((data[i] & (255 << 8))>> 8);
			int b = (data[i] & (255));
			int gr = (2*r + 3*g + b)/8;
			data[i] = (255 << 24) | (gr << 16) | (gr << 8) | (gr/2);
		}
	}

}
