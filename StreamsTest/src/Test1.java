import java.util.ArrayList;
import java.util.List;

public class Test1 {

	public static void main(String... args) {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");

//		System.out.println(stringCollection.stream().allMatch(x -> x.contains("")));
//		stringCollection.stream().filter(x -> x.contains("b")).forEach(x -> System.out.println(x));

//		stringCollection.stream().skip(2).forEach(System.out::println);
		
		System.out.println("#####");
		stringCollection.stream().limit(3).forEach(System.out::println);;
		
//		printVarArgs("asdf", "asdf", "uio");

	}
	
	public static void printVarArgs(String... strings) {
		
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
