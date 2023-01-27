import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

class Operators
{
	String name;
	int precedence;
	boolean right_associativity;

	public Operators(String name, int precedence, boolean right_associativity)
	{
		this.name = name;
		this.precedence = precedence;
		this.right_associativity = right_associativity;
	}

	public String Name()
	{
		return name;
	}

	public int PrecedenceLevel()
	{
		return precedence;
	}

	public boolean HasRightAssociativity()
	{
		return right_associativity;
	}

	public static Operators[] InitOperators()
	{
		var list = new ArrayList<Operators>();
		
		list.add(new Operators("+", 4, false));
		list.add(new Operators("-", 4, false));
		list.add(new Operators("*", 3, false));
		list.add(new Operators("/", 3, false));
		list.add(new Operators("^", 2, true));
		list.add(new Operators("rt", 2, false));
		list.add(new Operators("sin", 1, false));
		list.add(new Operators("cos", 1, false));
		list.add(new Operators("tan", 1, false));
		list.add(new Operators("asin", 1, false));
		list.add(new Operators("acos", 1, false));
		list.add(new Operators("atan", 1, false));
		list.add(new Operators("sinh", 1, false));
		list.add(new Operators("cosh", 1, false));
		list.add(new Operators("tanh", 1, false));
		list.add(new Operators("sqrt", 2, false));
		list.add(new Operators("ln", 1, false));
		list.add(new Operators("log", 1, false));
		list.add(new Operators("abs", 1, false));

		Operators[] arr = new Operators[list.size()];
		return list.toArray(arr);
	}

	public static HashMap<String, Function<Fraction, Fraction>> InitSingleParamOperators()
	{
		var hashtable = new HashMap<String, Function<Fraction, Fraction>>();

		hashtable.put("sqrt", (x) -> { return Fraction.Sqrt(x); });
		hashtable.put("sin", (x) -> { return Fraction.Sin(x); });
		hashtable.put("cos", (x) -> { return Fraction.Cos(x); });
		hashtable.put("tan", (x) -> { return Fraction.Tan(x); });
		hashtable.put("arcsin", (x) -> { return Fraction.Asin(x); });
		hashtable.put("arccos", (x) -> { return Fraction.Acos(x); });
		hashtable.put("arctan", (x) -> { return Fraction.Atan(x); });
		hashtable.put("sinh", (x) -> { return Fraction.Sinh(x); });
		hashtable.put("cosh", (x) -> { return Fraction.Cosh(x); });
		hashtable.put("tanh", (x) -> { return Fraction.Tanh(x); });
		hashtable.put("ln", (x) -> { return Fraction.Ln(x); });
		hashtable.put("abs", (x) -> { return Fraction.Abs(x); });

		return hashtable;
	}

	public static HashMap<String, BiFunction<Fraction, Fraction, Fraction>> InitMultiParamOperators()
	{
		var hashtable = new HashMap<String, BiFunction<Fraction, Fraction, Fraction>>();

		hashtable.put("+", (x, y) -> { return Fraction.Add(x, y); });
		hashtable.put("-", (x, y) -> { return Fraction.Subtract(x, y); });
		hashtable.put("*", (x, y) -> { return Fraction.Multiply(x, y); });
		hashtable.put("/", (x, y) -> { return Fraction.Divide(x, y); });
		hashtable.put("^", (x, y) -> { return Fraction.Pow(x, y); });
		hashtable.put("rt", (x, y) -> { return Fraction.Rt(x, y); });
		hashtable.put("log", (x, y) -> { return Fraction.Log(x, y); });

		return hashtable;
	}
}