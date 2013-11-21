import java.util.Iterator;

// ***********************************************************************
//
// Test15 -- Checks the iterator() doesn't iterate on a changed map
// 			 
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test15 extends TestHarness {

	public Test15(String s) { super(s); }

	public boolean test() { 
		SortedMap<Character, String> m = new BSTMap<Character,String>();

		try {
			boolean test3 = false, test5 = false, test4 = false;
			m.put('F',"1");
			m.put('B',"1");
			m.put('A',"1");
			m.put('D',"1");
			m.put('E',"1");

			Iterator<Character> iter = m.iterator();
			m.remove('A');
			try{
				iter.next();
				iter.hasNext();
				iter.remove();
			}catch(RuntimeException e){
				test3 = true;
			}
			if(!test3){
				return false;
			}

			Iterator<Character> iter4 = m.iterator();
			m.put('A',"1");
			try{
				iter4.next();
				iter4.hasNext();
				iter4.remove();
			}catch(RuntimeException e){
				test4 = true;
			}
			if(!test4){
				return false;
			}

			Iterator<Character> iter5 = m.iterator();
			m.get('A');
			try{
				iter5.next();
				iter5.hasNext();
				iter5.remove();
			}catch(RuntimeException e){
				test5 = true;
			}
			if(test5){
				return false;
			}
			m.put('A',"1");
			Iterator<Character> iter6 = m.iterator();
			String test1 ="";
			try{
				while(iter6.hasNext()){
					Character c = iter6.next();
					System.out.println(c);
					if(c.equals('D')){
						m.remove(c);
					}
					test1+=c;
				}
			}catch(Exception e){
				
			}

			if(!test1.equals("ABD")){
				return false;
			}

			return true;

		}catch(Exception e){
			//Threw wrong exception.
			e.printStackTrace();
			return false;
		}
	}
}
