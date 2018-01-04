package me;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamsExamples {

	//GENERATE
	//stream():Stream
	//parellelStream():Stream
	//of(T):Stream
	//TStream.range(T,T):Stream
	
	//OPERATIONS
	//count():long
	//sum():long
	//average():long
	//distinct():Stream		
	//concat(Stream1,Stream2):Stream
	//limit(long):Stream
	//skip(long):Stream
	//max(Comparator):Optional
	//min(Comparator):Optional
	//sorted(Comparator):Stream
	
	//SEARCH
	//filter(Predicate):Stream
	//findFirst():Optional
	//findAny():Optional
	//allMatch(Predicate):boolean
	//anyMatch(Predicate):boolean
	//noneMatch(Predicate):boolean
	
	//TERMINAL
	//forEach(Consumer):void
	
	//MAP
	//mapToT():Stream
	//flatMapToT():Stream
	
	//REDUCE
	//collect(Collector):mutableReduction
	//reduce(BinaryOperator):Stream
	
	//OPTIONAL INTERFACE
	//of(T):Optional
	//ofNullable(T):Optional
	//isPresent():boolean
	//ifPresent(Consumer):void
	//orElse(Suplier,other):T
	//orElseThrow(Suplier,Exception):TextendsThrowable
	
	public static void main(String[] args) {

		//Filter, print
		//abc, abb, aab,
		List<String> list = Arrays.asList("bac","abc","abb","aab","bbc");
		list.stream()
			.filter(s -> s.startsWith("a"))
			.forEach(s -> System.out.print(s+", "));
		
		System.out.println();
		
		//Filter, sort, doSomething, print
		//AAB, ABB, ABC,
		list.stream()
		    .filter(s -> s.startsWith("a"))
		    .sorted()
		    .map(String::toUpperCase)
		    .forEach(s -> System.out.print(s+", "));
		
		System.out.println();
		
		//Sort, find one, print
		//a1
		Arrays.asList("a2", "a1", "a1").stream()
									   .sorted()
									   .findFirst()
									   .ifPresent(System.out::print);
		
		System.out.println();
		
		//For: doSomething, print
		//3, 5, 7, 9, 11, 13, 15, 17, 19, 
		IntStream.range(1, 10).map(i -> 2*i + 1).forEach(s -> System.out.print(s+", "));
		
		System.out.println();
		
		//substring, retype, max, print
		//3
		Stream.of("a1","a2","a3")
			  .map(s -> s.substring(1))
			  .mapToInt(Integer::parseInt)
			  .max()
			  .ifPresent(System.out::print);
		
		System.out.println();
		
		//internal operations
		//filter s1 foreach s1, filter s2 foreach s2, filter s3 foreach s3, 
		Stream.of("s1","s2","s3")
			  .filter(s -> {
				  			System.out.print("filter "+s+" ");
				  			return !s.isEmpty();
			  				}
					  )
			  .forEach(s -> System.out.print("foreach "+s+", "));
		
		System.out.println();
		
		//order performance 
		//map: B1 forEach: B1, map: B3 forEach: B3
		Stream.of("d2", "a2", "b1", "b3", "c")
			    .filter(s -> {
			        System.out.print("filter: " + s + " ");
			        return s.startsWith("b");
			    })
			    .sorted((s1, s2) -> {
			        System.out.printf("sort: %s - %s", s1, s2 + " ");
			        return s1.compareTo(s2);
			    })
			    .map(s -> {
			        System.out.print("map: " + s.toUpperCase() + " ");
			        return s.toUpperCase();
			    })
			    .forEach(s -> System.out.print("forEach: " + s + ", "));
		
		System.out.println();
		
		//close exception
		//needs stream supplier
		Stream<String> stream = Stream.of("a1","a2","a3")
			        				  .filter(s -> s.startsWith("a"));
		stream.anyMatch(s -> true);
		try {
			stream.noneMatch(s -> true);
		} catch(IllegalStateException e) {}
		
		//collect to collection
		//[kasia, gosia, asia, basia]
		List<String> filtered = Stream.of("kasia","gosia","asia","basia","ewelina")
									  .filter(p -> p.endsWith("sia"))
									  .collect(Collectors.toList());
		System.out.print(filtered);

		System.out.println();
		
		//grouping by age
		//[kasia]: 18 [ewelina]: 21 [gosia, kasia]: 23 
		class Person {String name; int age; 
		  			  Person(String name, int age) {
						  this.name=name;
						  this.age=age;
		  			  }
		  			  @Override public String toString() {return name;}
		 }
		List<Person> persons = Arrays.asList(new Person("Kasia", 18),
											 new Person("Gosia", 23),
											 new Person("Kasia", 23),
											 new Person("Ewelina", 21));
		persons.stream()
		   .collect(Collectors.groupingBy(p -> p.age))
		   .forEach((age,p) -> System.out.format("%s: %s ", p, age));
		
		System.out.println();
		
		//aggregation for average
		//21.25
		Double avg = persons.stream()
						    .collect(Collectors.averagingDouble(p -> p.age));
		System.out.print(avg);
		
		System.out.println();
		
		//complete stats
		IntSummaryStatistics stats = persons.stream()
											.collect(Collectors.summarizingInt(p -> p.age));
		System.out.print(stats);
		
		System.out.println();
		
		//simple string joiner
		//In Poland Gosia, Kasia, Ewelina are of legal age
		String sentence = persons.stream()
								 .filter(p -> p.age >= 21)
								 .map(p -> p.name)
								 .collect(Collectors.joining(", ", "In Poland ", " are of legal age"));
		System.out.print(sentence);
		
		System.out.println();
		
		//User defined collector
		//KASIA | GOSIA | KASIA | EWELINA
		Collector<Person, StringJoiner, String> personNameCollector = Collector.of(
								() -> new StringJoiner(" | "), //custom supplier
								(j,p) -> j.add(p.name.toUpperCase()), //custom accumulator
								(j1,j2) -> j1.merge(j2), //custom combiner
								StringJoiner::toString); //custom finisher
		String names = persons.stream()
							  .collect(personNameCollector);
		System.out.print(names);
		
		System.out.println();
		
	}
	
}
