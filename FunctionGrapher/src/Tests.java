import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.EmptyStackException;
import java.text.MessageFormat;
import java.lang.ArithmeticException;
import java.lang.reflect.Method;

class Tests
{
	private static Map<Integer, Runnable> rpn = new LinkedHashMap<>();
	private static Map<Integer, Runnable> parser = new LinkedHashMap<>();
	private static Map<Integer, Runnable> fraction = new LinkedHashMap<>();

	public Tests()
	{
		rpn.put(0, () -> Init_1());
		rpn.put(1, () -> RPN1());
		rpn.put(2, () -> RPN2());
		rpn.put(3, () -> RPN3());
		rpn.put(4, () -> RPN4());
		rpn.put(5, () -> RPN5());

		parser.put(0, () -> Init_2());
		parser.put(1, () -> Parser1());
		parser.put(2, () -> Parser2());
		parser.put(3, () -> Parser3());
		parser.put(4, () -> Parser4());
		parser.put(5, () -> Parser5());
		parser.put(6, () -> Parser6());
		parser.put(7, () -> Parser7());
		parser.put(8, () -> Parser8());

		fraction.put(0, () -> Init_3());
		fraction.put(1, () -> Fraction1());
		fraction.put(2, () -> Fraction2());
		fraction.put(3, () -> Fraction3());
		fraction.put(4, () -> Fraction4());
		fraction.put(5, () -> Fraction5());
		fraction.put(6, () -> Fraction6());
		fraction.put(7, () -> Fraction7());
		fraction.put(8, () -> Fraction8());
		fraction.put(9, () -> Fraction9());
	}

	public void Run()
	{
		for(var k : rpn.keySet())
		{
			rpn.get(k).run();
		}

		for(var k : parser.keySet())
		{
			parser.get(k).run();
		}

		for(var k : fraction.keySet())
		{
			fraction.get(k).run();
		}
	}

	/**
	 * ReversePolishNotation
	 * 
	 * @param tokens Evaluation To Solve Written In RPN
	 * @return A Single Double Value
	 */

	private Functions function;
	private ArrayList<String> t1_p1;
	private ArrayList<String> t1_p2;
	private ArrayList<String> t1_p3;
	private ArrayList<String> t1_p4;
	private ArrayList<String> t1_p5;

	@Before	
	public void Init_1()
	{
		function = new Functions();
		t1_p1 = new ArrayList<String>(Arrays.asList(new String[] { "13.3", "pi", "e", "^", "sin", "4", "3", "^", "/", "+" }));
		t1_p2 = new ArrayList<String>(Arrays.asList(new String[] { "2", "1", "log" }));
		t1_p3 = new ArrayList<String>(Arrays.asList(new String[] { "3", "4", "2", "*", "1", "5", "-", "2", "3", "^", "^", "/", "+" }));
		t1_p4 = new ArrayList<String>(Arrays.asList(new String[] { "2", "3", "rt", "3", "/", "pi", "*", "sin" }));
		t1_p5 = new ArrayList<String>(Arrays.asList(new String[] { "1", "-1", "2", "^", "-", "sqrt" }));
	}

	@Test
	public void RPN1()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName(); 
		double value = function.ReversePolishNotation(t1_p1);

		try
		{
			assertEquals(name, 13.29, value, 0.01);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, 13.29, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, 13.29, value));
		}
	}

	@Test
	public void RPN2()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		double value = function.ReversePolishNotation(t1_p2);

		try
		{
			assertEquals(name, 0, value, 0.01);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, 0, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, 0, value));
		}
	}

	@Test
	public void RPN3()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		double value = function.ReversePolishNotation(t1_p3);

		try
		{
			assertEquals(name, 3, value, 0.01);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, 3, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, 3, value));
		}
	}

	@Test
	public void RPN4()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		double value = function.ReversePolishNotation(t1_p4);

		try
		{
			assertEquals(name, 0.97, value, 0.01);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, 0.97, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, 0.97, value));
		}
	}

	@Test
	public void RPN5()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		double value = function.ReversePolishNotation(t1_p5);

		try
		{
			assertEquals(name, 0, value, 0);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, 0, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, 0, value));
		}
	}

	/**
	 * Parser
	 * 
	 * @param string User Input Of A Function
	 * @return String Tokens In RPN
	 */

	private String t2_p1;
	private String t2_p2;
	private String t2_p3;
	private String t2_p4;
	private String t2_p5;
	private String t2_p6;
	private String t2_p7;
	private String t2_p8;

	@Before
	public void Init_2()
	{
		t2_p1 = "rfuhusgdikuhfjsdifuhisdfuhds";
		t2_p2 = "j(Z) = (-1) ^ Z";
		t2_p3 = "f(x) = 3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3";
		t2_p4 = "g(x) = sin ( rt ( 2, 3 ) / 3 * pi )";
		t2_p5 = "h(x) = 13.3 + sin ( pi ^ e ) / 4 ^ x";
		t2_p6 = "i(x) = log ( 2 , x )";
		t2_p7 = "k(x) = sqrt ( 1 - x ^ 2)";
		t2_p8 = "l(x) = sqrt ( x + 5 ) / ( ( x ^ 2 ) - 4 )";
	}

	@Test
	public void Parser1()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p1);
		List<String> correct = new ArrayList<>();

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser2()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p2);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "1", "-", "Z", "^" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser3()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p3);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "3", "4", "2", "*", "1", "5", "-", "2", "3", "^", "^", "/", "+" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser4()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p4);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "2", "3", "rt", "3", "/", "pi", "*", "sin" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser5()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p5);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "13.3", "pi", "e", "^", "sin", "4", "x", "^", "/", "+" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser6()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p6);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "2", "x", "log" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser7()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p7);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "1", "x", "2", "^", "-", "sqrt" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	@Test
	public void Parser8()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		List<String> value = function.FunctionInterpreter(t2_p8);
		List<String> correct = new ArrayList<>(Arrays.asList(new String[] { "x", "5", "+", "sqrt", "x", "2", "^", "4", "-", "/" }));

		try
		{
			assertEquals(name, correct, value);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct, value));
		}
	}

	/**
	 * Fraction Class
	 */

	private Fraction t3_p1;
	private Fraction t3_p2;
	private Fraction t3_p3;
	private Fraction t3_p4;

	@Before
	public void Init_3()
	{
		t3_p1 = new Fraction(3, 4);
		t3_p2 = new Fraction(2, 3);
		t3_p3 = new Fraction(-2);
		t3_p4 = new Fraction(-2, 3);
	}

	@Test
	public void Fraction1()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		Fraction value = Fraction.Add(t3_p1, t3_p2);
		Fraction correct = new Fraction(17, 12);

		try
		{
			assertEquals(name, correct.Num(), value.Num());
			assertEquals(name, correct.Den(), value.Den());
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
	}

	@Test
	public void Fraction2()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		Fraction value = Fraction.Subtract(t3_p1, t3_p2);
		Fraction correct = new Fraction(1, 12);

		try
		{
			assertEquals(name, correct.Num(), value.Num());
			assertEquals(name, correct.Den(), value.Den());
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
	}

	@Test
	public void Fraction3()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		Fraction value = Fraction.Multiply(t3_p1, t3_p2);
		Fraction correct = new Fraction(6, 12);

		try
		{
			assertEquals(name, correct.Num(), value.Num());
			assertEquals(name, correct.Den(), value.Den());
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
	}

	@Test
	public void Fraction4()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		Fraction value = Fraction.Divide(t3_p1, t3_p2);
		Fraction correct = new Fraction(9, 8);

		try
		{
			assertEquals(name, correct.Num(), value.Num());
			assertEquals(name, correct.Den(), value.Den());
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
	}

	@Test
	public void Fraction5()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		Fraction value = Fraction.Pow(t3_p2, t3_p3);
		Fraction correct = new Fraction(81, 36);

		try
		{
			assertEquals(name, correct.InDouble(), value.InDouble(), 0.01);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
	}

	@Test
	public void Fraction6()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();

		try
		{
			Fraction value = Fraction.Rt(t3_p3, t3_p2);
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <ArithmeticException>, Actual: <->)", name));
		}
		catch(ArithmeticException e)
		{
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <ArithmeticException>, Actual: <ArithmeticException>)", name));
		}
	}

	@Test
	public void Fraction7()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();

		try
		{
			Fraction value = Fraction.Sqrt(t3_p3);
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <ArithmeticException>, Actual: <->)", name));
		}
		catch(ArithmeticException e)
		{
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <ArithmeticException>, Actual: <ArithmeticException>)", name));
		}
	}

	@Test
	public void Fraction8()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		Fraction value = Fraction.Abs(t3_p3);
		Fraction correct = new Fraction(2);

		try
		{
			assertEquals(name, correct.InDouble(), value.InDouble(), 0.01);
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
		catch(AssertionError e)
		{
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <{1}>, Actual: <{2}>)", name, correct.InDouble(), value.InDouble()));
		}
	}

	@Test
	public void Fraction9()
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();

		try
		{
			Fraction value = Fraction.Pow(t3_p4, t3_p4);
			System.out.println(MessageFormat.format("TEST {0} FAILED(Expected: <ArithmeticException>, Actual: <->)", name));
		}
		catch(ArithmeticException e)
		{
			System.out.println(MessageFormat.format("TEST {0} PASSED(Expected: <ArithmeticException>, Actual: <ArithmeticException>)", name));
		}
	}
}