package priv.hkon.theseq.misc;

import java.io.Serializable;

import priv.hkon.theseq.sprites.Sprite;
import priv.hkon.theseq.world.Village;

public class Sentence  implements Serializable{
	
	private static final long serialVersionUID = -1551240943294103269L;
	public static final int TYPE_STATEMENT = 0;
	public static final int TYPE_QUESTION = 1;
	public static final int TYPE_RESPONSE = 2;
	public static final int TYPE_GREETING = 3;
	public static final int TYPE_GOODBYE = 4;
	public static final int TYPE_CUSTOM_ANSWER = 5;
	public static final int TYPE_EMPTY = 6;
	public static final int TYPE_PLAYER_NOT_TALKATIVE = 7;
	public static final int TYPE_RUMOR_INTRODUCTION = 8;
	public static final int TYPE_RUMORS = 9;
	public static final int TYPE_VILLAGE_MOOD = 10;
	
	public static final int STATEMENT_NEGATIVE = 0;
	public static final int STATEMENT_NEUTRAL = 1;
	public static final int STATEMENT_POSITIVE = 2;
	
	public static final int QUESTION_SELF_CONDITION = 0;
	public static final int QUESTION_RELATION_TO = 1;
	public static final int QUESTION_MEANING_OF_LIFE = 2;
	
	public static final int RESPONSE_NEGATIVE = 0;
	public static final int RESPONSE_NEUTRAL = 1;
	public static final int RESPONSE_POSITIVE = 2;
	public static final int RESPONSE_APPLAUDE = 3;
	public static final int RESPONSE_EMPHASIZE = 4;
	public static final int RESPONSE_CARELESS = 5;
	
	public static final int GREETING_NEGATIVE = 0;
	public static final int GREETING_NEUTRAL = 1;
	public static final int GREETING_POSITIVE = 2;
	
	public static final int GOODBYE_NEGATIVE = 0;
	public static final int GOODBYE_NEUTRAL = 1;
	public static final int GOODBYE_POSITIVE = 2;
	
	public static final int CUSTOM_ANSWER_NEGATIVE = 0;
	public static final int CUSTOM_ANSWER_NEUTRAL = 1;
	public static final int CUSTOM_ANSWER_POSITIVE = 2;
	
	public static final int ADJECTIVES_NEGATIVE = 0;
	public static final int ADJECTIVES_POSITIVE = 1;
	
	public static final int NOUN_NEGATIVE = 0;
	public static final int NOUN_POSITIVE = 1;
	
	public static Village village;
	
	public static final String[][] STATEMENTS = {
			{"**o smells pretty bad","**o looks like a dead toad", "I heard that **o is actually a demon"},
			{"**o is just a normal humen", "Seems to be reasonable enough", "Hasn't seen **o much lately"},
			{"**o is really cool! I like em!", "**o is just amazing", "That humen has the look of a god!"}
	};
	
	public static final String[][] QUESTIONS = {
			{"How are you?", "How is your life by now?", "Are you having a good time?"},
			{"Honestly, what do you think of **o?", "How is **o in your eyes?" , "**o compared to Hitler?"},
			{"What is the meaning of our existence anyway?", "Is life more than just this village?", "Why are we here?"}
	};
	
	public static final String[][] RESPONSES = {
			{"How can you say that?", "That's not true", "I totally disagree"},
			{"Mhm", "Ok..","Right.."},
			{"Yes, I totally agree!", "Mhm, I agree", "That is absolutely right!"},
			{"How nice!", "Good!", "Neat"},
			{"Oh, no!", "I hope things work out", "That is not a good thing"},
			{"Whatever", "Who cares?", "Yeah, yeah...", "K"}
	};
	
	public static final String[][] GREETINGS = {
			{"Oh... Hi.", "Uhm.. Hello.", "*sigh* Hi"},
			{"Hi there", "Hello", "Good day"},
			{"Oh, hi!!", "Hello, how nice to see you!", "Heeeeyyy!!"}
			
	};
	
	public static final String[][] GOODBYE = {
			{"Bye, hope we don't meet very soon again", "Bye, you fish", "See you.. Not"},
			{"Bye!", "Goodbye to you!" ,"See ya"},
			{"Nice talking to you! Goodbye!", "Goodbye for now!", "I hope to see you again soon! Bye!"}
	};
	
	public static final String[] VERBS = {
			"smells", "looks", "has the look of", "seems to be", "behaves", "talks", "walks", "thinks"
	};
	
	public static final String[][] ADJECTIVES = {
			{"a dead", "an old", "a broken", "a drowned", "an endangered", "a sad", "a stinking", "an unpleasant", "an ugly", "a rotten", "a molten", "a disturbed", "a distorted", "a disgusting", "a kidnapped", "a devoured"},
			{"a majestic", "a golden", "a beautiful", "a fresh", "a refreshing", "a sweet", "a cute", "a mindful", "a nice", "a caring", "a royal", "a strong", "a though"}
	};
	
	public static final String[][] NOUNS = {
			{"mouse", "sloth", "snake", "ghoul", "ghost", "witch", "hag", "fetus", "ball of goo", "animal", "carnivore", "wall"},
			{"lion", "dragon", "rose", "flower", "wizard", "tiger", "freedom fighter", "artist"}
	};
	
	public static final String[][] PLAYER_NOT_TALKATIVE = {
			{"What are you staring at?"},
			{"You're not very talkative, are you?"},
			{"Heh, the handsome, silent type, aren't you?"}
	};
	
	public static final String[][] RUMOR_INTRODUCTION = {
			{
				"Hey, did you know..",
				"Listen here",
				"I should tell you something"
			}
	};
	
	public static final String[][] GENERAL_RUMORS ={
			{
				"The Mayor's house is the one in marble"},{
				"The house of trees belongs to a real tree-lover"},{
				"There are sometimes howling at night. Scary!"},{
				"The wall surrounding us has always been there"},{
				"A sequence is the true meaning of life"},{
				"You have a purpose.. Fulfill it"},{
				"Nothing is wrong. Don't worry"},{
				"The Painter tends to get mad about trespassers",
				"But his canvas floor is just too much fun!"},{
			    "The Mayor really doesn't do anything useful",
			    "He is just afraid of not being important",
			    "He has a hat and a fancy title, but is otherwise another Nobody"},{
				"The Painter is a bit... Special",
				"His floating quotes only makes sense to people close to him, though"},{
				"I am a little worried about my Nobodiness",
				"Am I really doomed to walk about, doing nothing all day?",
				"Or... Are we perhaps Nobodies, all of us?"
			}
	};
	
	public static final String[][] MOOD_SENTENCE = {{}};
	
	public static final String NONE[][] = {{""}};
	
	public static final String[][] CUSTOM_ANSWERS = {{}};
	
	public static final String[][][] SENTENCES = {
		STATEMENTS,
		QUESTIONS,
		RESPONSES,
		GREETINGS,
		GOODBYE,
		CUSTOM_ANSWERS,
		NONE,
		PLAYER_NOT_TALKATIVE,
		RUMOR_INTRODUCTION,
		GENERAL_RUMORS,
		MOOD_SENTENCE
	};
	
	String[] strings;
	
	public static final float[] meaningWeight = {-1.0f, 0.0f, 1.0f};
	
	Sprite object;
	Sprite subject;
	Sprite topic;
	
	int arg2, type; // The role of arg2 depends fully on the value of type
	
	public Sentence(Sprite o, int type, int arg2, Sprite s){
		object = o;
		subject = s;
		
		this.arg2 = arg2;
		this.type = type;
		
		strings = new String[1];
		strings[0] = SENTENCES[type][arg2][Sprite.RAND.nextInt(SENTENCES[type][arg2].length)];
		parseAllStrings();
	}
	
	public Sentence(Sprite o, int type, int arg2, Sprite s, Sprite topic){
		this(o, type, arg2, s);
		this.topic = topic;
		parseAllStrings();
	}
	
	public Sentence(Sprite o, int type, Sprite s){
		this(o, type, Sprite.RAND.nextInt(SENTENCES[type].length), s);
		parseAllStrings();
	}
	
	public Sentence(Sprite o, int type, int arg2, String[] strings, Sprite s){
		object = o;
		subject = s;
		this.type = type;
		this.arg2 = arg2;
		this.strings = strings;
		parseAllStrings();
	}
	
	public Sentence(Sprite o, int type, int arg2, String[] strings, Sprite s, Sprite t){
		object = o;
		subject = s;
		this.type = type;
		this.arg2 = arg2;
		this.strings = strings;
		topic = t;
		parseAllStrings();
	}
	
	public Sentence(Sprite o, int type, Sprite s, Sprite t) {
		object = o;
		subject = s;
		this.type = type;
		topic = t;
		strings = new String[1];
		arg2 = Sprite.RAND.nextInt(SENTENCES[type].length);
		strings[0] = SENTENCES[type][arg2][Sprite.RAND.nextInt(SENTENCES[type][arg2].length)];
		parseAllStrings();
	}

	public void parseAllStrings(){
		for(int i = 0; i < strings.length; i++){
			strings[i] = parse(strings[i]);
		}
	}
	
	public int getArg2(){
		return arg2;
	}
	
	public Sprite getSubject(){
		return subject;
	}
	
	public Sprite getObject(){
		return object;
	}
	
	public int getType(){
		return type;
	}
	
	public Sprite getTopic(){
		return topic;
	}
	
	public String parse(String s){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< s.length(); i++){
			if(s.charAt(i)== '*'){
				if(i < s.length() -2&& s.charAt(i + 1) == '*'){
					switch(s.charAt(i + 2)){
					case 'o': sb.append(topic.getName());
					break;
					}
					i+= 2;
					continue;
				}
			}
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
	
	public String[] getStrings(){
		return strings;
	}
	
	public static String getRandomString(int type, int mood){
		return SENTENCES[type][mood][Sprite.RAND.nextInt(SENTENCES[type][mood].length)];
	}
	

}
