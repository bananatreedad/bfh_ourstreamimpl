import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class was created to test the behavior of the native String library of Java.
 * It makes no contribution to the project.
 *
 */
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
//		printVarArgs("asdf", "asdf", "uio");

//		stringCollection.stream().limit(3).forEach(System.out::println);;
		
		Consumer<String> ourCons = new Consumer<String>() {

			@Override
			public void accept(String t) {
				System.out.println("hello " + t + "!");
			}
		};
		
		ourCons.accept("Chris");
	}
	
	/**
	 * This function was created to test the behavior of VarArgs. 
	 * 
	 * @param strings
	 */
	public static void printVarArgs(String... strings) {
		
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
