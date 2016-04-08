package priv.hkon.theseq.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import priv.hkon.theseq.filters.DarkenFilter;
import priv.hkon.theseq.filters.Filter;
import priv.hkon.theseq.sprites.Sprite;

public class Screen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static final int W = 300;
	public static final int H = 200;
	public static final int SCALE = 3;
	Core core;
	
	BufferedImage mainImage;
	int[] mainData;
	BufferedImage gameImage;
	int[] gameData;
	Controller controller;
	public int count = 0;
	public static int[] charPositions;
	public static int[][][] charData;
	BufferedImage bu;
	
	DarkenFilter fade;
	
	public Font font;
	
	public boolean drawInstructions = false;
	
	public static final int FONT_WIDTH = 5;
	public static final int FONT_HEIGHT = 8;
	
	public static final int FW = FONT_WIDTH;
	public static final int FH = FONT_HEIGHT;
	
	public BufferedImage titleScreen = null;
	int[] titleData;
	
	Filter filter = Filter.NO_FILTER;
	
	public Screen(Core c){
		core = c;
		setSize(W*SCALE, H*SCALE);
		initCharacters();
		setResizable(false);
		mainImage = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
		mainData = ((DataBufferInt)(mainImage.getRaster().getDataBuffer())).getData();
		gameImage = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
		gameData = ((DataBufferInt)(gameImage.getRaster().getDataBuffer())).getData();
		controller = new Controller();
		addKeyListener(controller);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Core.TITLE);
		setVisible(true);
		
		fade = new DarkenFilter(0);
	}
	
	public void paint(Graphics g){
		/*for(int i = 0; i<W*H; i++){
			data[i] = ((255<<24) - count*i)  + (255<<24);
		}*/
		g.drawImage(mainImage, 0, 0, SCALE*W, SCALE*H, null);
	}
	
	public void setFilter(Filter f){
		filter = f;
	}
	
	public void update(){
		filter.apply(mainData);
		paint(getGraphics());
	}
	
	public static void draw(int[][] baseData, int bw, int bh, int[][] drawData, int dw, int dh, int offX, int offY){
		int offDX = Math.max(0, -offX); int offDY = Math.max(0,  -offY); //Offset in drawbuffer
		int restrictDX = Math.min(dw, bw - offX); int restrictDY = Math.min(dh, bh - offY);
		for(int i = offDY; i<restrictDY; i++){
			for(int j = offDX; j<restrictDX; j++){
				if((drawData[i][j] &(255<<24)) != 0)
					baseData[offY + i][offX + j] = drawData[i][j];
			}
		}
	}
	
	public static void draw(int[] baseData, int bw, int bh, int[][] drawData, int dw, int dh, int offX, int offY){
		int offDX = Math.max(0, -offX); int offDY = Math.max(0,  -offY); //Offset in drawbuffer
		int restrictDX = Math.min(dw, bw - offX); int restrictDY = Math.min(dh, bh - offY);
		for(int i = offDY; i<restrictDY; i++){
			for(int j = offDX; j<restrictDX; j++){
				if((drawData[i][j] &(255<<24)) != 0)
					baseData[(offY + i)*bw + offX + j] = drawData[i][j];
			}
		}
	}
	
	public void setData(int[][] ndata){
		
		if(drawInstructions){
			String str = "Press S to fast-forward dialogs";
			Screen.drawString(str, ndata, W, H, W-str.length()*FW - 3, H - FH - 5);
		}
		draw(gameData, W, H, ndata, ndata[0].length, ndata.length, 0, 0);
		mainImage.getGraphics().drawImage(gameImage, 0, 0, null);
	}
	
	public void initCharacters(){
		charPositions = new int[256];
		font = new Font("Monospaced", Font.PLAIN, FH);
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,.!?* -'0123456789<>";
		//NB: If you are to add characters to the alphabet, you will need to output the string to the image file by uncommenting lines below
		for(int i = 0; i < str.length(); i++){
			charPositions[str.charAt(i)] = i;
		}
		
		charData = new int[str.length()][FH + 5][FW];
		
		/*bu = new BufferedImage(FW*str.length(), FH+5, BufferedImage.TYPE_INT_ARGB);
		int[] bdata = ((DataBufferInt)(bu.getRaster().getDataBuffer())).getData();
		for(int i = 0; i < str.length(); i++){
			bdata[i] = 0;
		}*/
		
		/*Graphics g = bu.getGraphics();
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(str, 0, FH);*/
		
		/*try {
			ImageIO.write(bu, "png", new File("Alphabet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		try {
			bu = ImageIO.read(new File("Alphabet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < str.length(); i++){
			for(int j = 0; j < FH + 5; j++){
				for(int k = 0; k < FW; k++){
					charData[i][j][k] = (bu.getRGB(i*FW + k, j) & (255 << 24)) == 0? 0: 255<<24;
				}
			}
		}
	}
	
	public static void drawString(String str, int[][] baseData, int bw, int bh, int offX, int offY){
		for(int i = 0; i < str.length(); i++){
			draw(baseData, bw, bh, charData[charPositions[str.charAt(i)]], FW, FH + 5, offX + i*FW, offY);
		}
	}
	
	public static int nightFilter(int col, float factor){ // Does not preserve alpha channel
		int r = (col >>16)&255;
		int g = (col >>8)&255;
		int b = (col)&255;
		
		int av = (r +b + g)/3;
		
		r = (int)Math.max(r*factor, r*0.2);
		g = (int)Math.max(g*factor, g*0.3);
		b = (int)Math.max(b*factor, av*0.3);
		
		return (r<<16) | (g<<8) | b | (255 << 24);
	}
	
	
	int titleScreenCount = 0;
	
	String[] altTitles = {"The Sequence", "The_Sequence"};
	String pressKeyString = "Press any key to start";
	int titleNum = 0;
	
	public void runTitleScreen(){
		if(titleScreen == null){
			titleScreen = new BufferedImage(W,H, BufferedImage.TYPE_INT_ARGB);
			titleData = ((DataBufferInt)(titleScreen.getRaster().getDataBuffer())).getData();
		}
		titleScreenCount++;
		
		Graphics g = titleScreen.getGraphics();
		g.setColor(new Color(70, 30, 30));
		g.fillRect(0, 0, W, H);
		for(int i = 0; i< H; i++){
			
			for(int j = 0; j< W; j++){
				int re = titleData[i*W + j]>>16 & 255;
				int gr = titleData[i*W + j] >> 8 & 255;
				int bl = titleData[i*W + j] & 255;
				
				re *= Math.exp(-((i - H/2)*(i - H/2) + (j - W/2)*(j - W/2))/8955.0);
				gr *= Math.exp(-((i - H/2)*(i - H/2) + (j - W/2)*(j - W/2))/8955.0);
				bl *= Math.exp(-((i - H/2)*(i - H/2) + (j - W/2)*(j - W/2))/8955.0);
				
				
				
				/*if((r = Sprite.RAND.nextFloat()) < p/2){
					re = 150;
					gr = 60;
					bl = 60;
				}else if((r = Sprite.RAND.nextFloat()) < p){
					re = gr = bl = 0;
				}*/
				
				titleData[i*W + j] = (255 << 24) | re << 16 | gr << 8 | bl;
				
			}
		}
		
		
		
		Font f = new Font("Courier", Font.PLAIN, 20);
		g.setFont(f);
		g.setColor(Color.BLACK);
		if(titleScreenCount%10 == 0){
			titleNum = (titleNum+1) % 2;
		}
		
		String str = altTitles[titleNum];
		
		g.drawString(str, 75, 2*H/5);
		
		if(!core.isInitiated() && titleScreenCount % 28 < 21){
			str = pressKeyString;
			g.setFont(new Font("Courier", Font.PLAIN, 12));
			g.setColor(Color.gray);
			g.drawString(str, 70, 3*H/5);
		}
		for(int i = 0; i< H; i++){
			float p = Sprite.RAND.nextFloat()/10;
			if(core.isInitiated()){
				for(int j = 0; j < W ;j++){
					int re = titleData[i*W + j]>>16 & 255;
					int gr = titleData[i*W + j] >> 8 & 255;
					int bl = titleData[i*W + j] & 255;
					float r = Sprite.RAND.nextFloat();
					
					re += 3*p*re + 5*r;
					gr += 3*p*gr + 5*r;
					bl += 3*p*bl + 5*r;
					
					titleData[i*W + j] = (255 << 24) | re << 16 | gr << 8 | bl;
				}
			}
		}
		
		mainImage.getGraphics().drawImage(titleScreen, 0, 0, null);
	}
	
	public void darkenImage(int d){
		/*float a = 1 - (float)d/255;
		for(int i = 0; i< H; i++){
			for(int j = 0 ; j < W ; j++){
				int rgb = titleData[i*W + j];
				titleData[i*W + j] = (255 << 24) | ((int)((rgb & (255 << 16))*a) & (255 << 16)) | ((int)((rgb & (255 << 8))*a) & (255<<8)) | ((int)((rgb & (255))*a) & (255));
				//mainImage.setRGB(j, i, ((int)((rgb & (255 << 16))*a) & (255 << 16)) | ((int)((rgb & (255 << 8))*a) & (255 << 16)) | ((int)((rgb & (255))*a) & (255 << 16)));
			}
		}*/
		
		fade.darkness = d;
		fade.apply(mainData);
	}
	
	//TODO: Make loading (and saving?) screen
}
