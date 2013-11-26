import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class SpeedTestOptimized {

	public static void main(String[] args)
	{

		try{
			LinkedHashMap<SortedMap<String,Integer>,String> maps = new LinkedHashMap<SortedMap<String,Integer>,String>();
			SortedMap<String,Integer> bstMap = new BSTMap<String,Integer>();
			for(double i=0.1;i<=0.9;i+=0.2){
				SortedMap<String,Integer> skiplistMap = new SkiplistMap<String,Integer>(50,i);
				maps.put(skiplistMap, ("SkiplistMap "+"w/ prob "+i));
			}
			//SortedMap<String,Integer> skiplistMap = new SkiplistMap<String,Integer>(50,0.3);
			//maps.put(bstMap, "BSTMap");
			//maps.put(skiplistMap, "SkiplistMap");

			LinkedHashMap<FileParser,String> fileParsers = new LinkedHashMap<FileParser,String>();

			FileParser fpRomeo = new FileParser("RomeoJuliet.txt");
			FileParser fpWar = new FileParser("WarAndPeace.txt");
			FileParser fpScrabble = new FileParser("TWL06.txt");
			fileParsers.put(fpRomeo, "Romeo & Juliet");
			//fileParsers.put(fpWar, "War And Peace");
			//fileParsers.put(fpScrabble, "Scrabble Dictionary");

			FileParser dict = new FileParser("TWL06.txt");//list of scrabble words
			List<String> dictWords = dict.getAllWords();

			for(Entry<FileParser,String> fileParserEntry : fileParsers.entrySet()){
				FileParser fp = fileParserEntry.getKey();
				List<String> words = fp.getAllWords();

				System.out.println("");
				System.out.println("File : "+fileParserEntry.getValue());
				System.out.println("");

				for(Entry<SortedMap<String,Integer>,String> map : maps.entrySet()){

					System.out.println("Map Type : "+map.getValue());
					System.out.println("__________________________________");
					System.out.println("");

					SortedMap<String,Integer> t = map.getKey();

					Date startTime1 = new Date();
					//add all the words
					for(String word : words){
						
						Integer i = t.get(word);
						if (i == null) {
							t.put(word, 1);
						} else {
							t.put(word,i+1);
						}
					}

					Date endTime1 = new Date();
					System.out.println("Time to add all words in script: " +  
							(endTime1.getTime() - startTime1.getTime()));


					System.out.println("The statistics for the data-structure are:");
					System.out.println(t.calculateStats());

					//now, find the most common word in the script
					//that is also in the scrabble dictionary
					int bestOccurances = 0;
					String mostCommonWord = null;
					Date startTime2 = new Date();
					for (int i = 0; i < 100; i++){
						for(String word : dictWords){
							Integer wordOccurances = t.get(word);
							if(wordOccurances != null && wordOccurances > bestOccurances){
								bestOccurances = wordOccurances;
								mostCommonWord = word;
							}
						}
					}
					Date endTime2 = new Date();
					System.out.println("Time to find most common word: " +  
							(endTime2.getTime() - startTime2.getTime()));

					System.out.println("The most common Scrabble word in the script is: " 
							+ mostCommonWord);

					Date startTime3 = new Date();
					int foolTheOptimizer = 0;
					for (int i = 0; i < 100; i++){
						for(String word : t){
							foolTheOptimizer++;
						}
					}
					Date endTime3 = new Date();
					System.out.println("Time to iterate through the set was " +  
							(endTime3.getTime() - startTime3.getTime()));

					System.out.println("The foolTheOptimizer value was:" + foolTheOptimizer);




				}

			}



		} catch (Exception e){e.printStackTrace();}
	}
}


