import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.EmptyStackException;
import java.lang.Math;
import java.lang.ArithmeticException;

class Functions
{
	HashMap<String, BiFunction<Fraction, Fraction, Fraction>> mp_operators;
	HashMap<String, Function<Fraction, Fraction>> sp_operators;
	Operators[] op;

	public Functions()
	{
		op = Operators.InitOperators();
		sp_operators = Operators.InitSingleParamOperators();
		mp_operators = Operators.InitMultiParamOperators();
	}

	public ArrayList<String> FunctionInterpreter(String args)
	{
		var space_less = args.replaceAll(" ", "");
		var pt1 = space_less.substring(0, 4);

		if(pt1.charAt(1) != '(' && pt1.charAt(3) != ')' && 
			(pt1.charAt(2) != 'x' || pt1.charAt(2) != 'q' || pt1.charAt(2) != 'z' ||
			pt1.charAt(2) != 'X' || pt1.charAt(2) != 'Q' || pt1.charAt(2) != 'Z'))
		{
			return new ArrayList<String>();
		}


		var pt2 = space_less.substring(5, space_less.length());
		var variable = Character.toString(pt1.charAt(2));
		
		var output = new ArrayList<String>();
		var operator_stack = new Stack<Operators>();
		enum Previous { DEFAULT, NUMERIC, OPERATOR }
		var previous = Previous.DEFAULT;
		String letters = "";

		for(int i = 0; i < pt2.length(); ++i)
		{
			String current = Character.toString(pt2.charAt(i));
			letters = letters.concat(current);

			if(isNumeric(current))
			{
				switch(previous)
				{
					case NUMERIC:
						output.set(output.size() - 1, output.get(output.size() - 1).concat(current));
						break;
					case OPERATOR:
					default:
						output.add(current);
						previous = Previous.NUMERIC;
						break;
				}

				letters = "";
			}
			else if(isNumeric(letters))
			{
				output.add(letters);
				letters = "";
				previous = Previous.NUMERIC;
			}
			else
			{				
				if(current.equals("("))
				{
					operator_stack.push(new Operators(current, 0, false));
					letters = "";
				}
				else if(current.equals(")"))
				{
					while(operator_stack.peek().Name().equals("(") != true)
					{
						var operator = operator_stack.pop();
						output.add(operator.Name());
					}

					operator_stack.pop();
					letters = "";
				}
				else if(current.equals("|"))
				{
					boolean contains = false;

					for(var v : operator_stack)
					{
						if(v.Name().equals("|"))
						{
							contains = true;
						}
					}

					if(contains == true)
					{
						while(operator_stack.peek().Name().equals("|") != true)
						{
							var operator = operator_stack.pop();
							output.add(operator.Name());
						}

						operator_stack.pop();
					}
					else
					{
						int index = -1;

						for(int j = 0; j < op.length; ++j)
						{
							if(op[j].Name().equals("abs"))
							{
								index = j;
								break; 
							}
						}

						operator_stack.push(op[index]);
						operator_stack.push(new Operators(current, 0, false));
					}

					letters = "";
				}
				else if(current.equals(variable))
				{
					if(previous == Previous.NUMERIC)
					{
						operator_stack.push(new Operators("*", 3, false));
					}
					
					output.add(variable);
					letters = "";
				}
				else if(current.equals(","))
				{
					letters = "";
				}
				else if(sp_operators.containsKey(letters) || mp_operators.containsKey(letters))
				{
					int index = -1;

					for(int j = 0; j < op.length; ++j)
					{
						if(op[j].Name().equals(letters))
						{
							index = j;
							break; 
						}
					}

					if(op[index].PrecedenceLevel() != 1)
					{
						while(operator_stack.empty() == false && operator_stack.peek().PrecedenceLevel() != 0 && op[index].PrecedenceLevel() >= operator_stack.peek().PrecedenceLevel() &&
							op[index].HasRightAssociativity() == false && operator_stack.peek().HasRightAssociativity() == false)
						{
							output.add(operator_stack.pop().Name());			
						}
					}
					
					operator_stack.push(op[index]);
					letters = "";
				}

				previous = Previous.OPERATOR;
			}

			/*System.out.print(i+1 + ".) ");
			for(Operators o : operator_stack)
			{
				System.out.print(o.Name() + " ");
			}
			System.out.println();*/
		}

		while(operator_stack.empty() == false)
		{
			output.add(operator_stack.pop().Name());
		}

		return output;
	}

	public static int[] Occurrences(ArrayList<String> list, String obj)
	{
		var indexList = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); ++i) 
		{
        	if(obj.equals(list.get(i)))
        	{
            	indexList.add(i);
        	}
		}

		int[] elements = new int[indexList.size()];
		
	    for (int i = 0; i < indexList.size(); ++i)
	    {
	        elements[i] = indexList.get(i).intValue();
	    }

    	return elements;
	}
	    
	public static boolean isNumeric(String str) 
	{ 
	  	try 
	  	{  
	    	Double.parseDouble(str);  
	    	return true;
	  	}
	  	catch(NumberFormatException e)
	  	{
	  		String input = str.toUpperCase();

			switch(input)
			{
				case "PI":
					return true;
				case "E":
					return true;
				case ".":
					return true;
				default:
					return false;
			}
	  	}  
	}

	public static int Domain(int min, int max)
	{
		return (Math.abs(min) + Math.abs(max)) * 100; 
	}

	public ArrayList<Double> GetDomain(int min, int max)
	{
		ArrayList<Double> domain_values = new ArrayList<Double>();

		for(int i = 0; i <= Domain(min, max); ++i)
		{
			Fraction domain_min = new Fraction(min);
			Fraction product = Fraction.Multiply(new Fraction(i), new Fraction(1, 100)); 
			Fraction x_values = Fraction.Add(domain_min, product);
			domain_values.add(x_values.InDouble());
		}

		return domain_values;
	}

	public Double ReversePolishNotation(ArrayList<String> args_list)
	{
		Stack<Fraction> evaluation = new Stack<Fraction>();
		String[] args = new String[args_list.size()];
		args_list.toArray(args);

		try
		{
			for(int i = 0; i < args.length; ++i)
			{
				if(isNumeric(args[i]))
				{
					if(args[i].toUpperCase().equals("PI"))
					{
						evaluation.push(new Fraction(Fraction.Round(Math.PI, 4)));
					}
					else if(args[i].toUpperCase().equals("E"))
					{
						evaluation.push(new Fraction(Fraction.Round(Math.E, 6)));
					}
					else
					{
						evaluation.push(new Fraction(Double.parseDouble(args[i])));
					}		
				}
				else if(mp_operators.containsKey(args[i]))
				{
					var val2 = evaluation.pop();

					try
					{						
						var val1 = evaluation.pop();												
						evaluation.push(mp_operators.get(args[i]).apply(val1, val2));
					}
					catch(EmptyStackException e)
					{
						evaluation.push(mp_operators.get("*").apply(new Fraction(-1), val2));
					}
				}
				else
				{
					var val = evaluation.pop();

					evaluation.push(sp_operators.get(args[i]).apply(val));
				}
			}

			return evaluation.pop().InDouble();
		}
		catch(EmptyStackException | NullPointerException | ArithmeticException e)
		{
			return Double.NaN;
		}
	}

	public ArrayList<Double> SolveFunctionOnDomain(int min, int max, String variable, ArrayList<String> to_solve)
	{
		ArrayList<String> postfix_notation = new ArrayList<String>(to_solve);

		ArrayList<Double> solutions = new ArrayList<Double>();

  		int[] vars = Occurrences(postfix_notation, variable);

		for(int i = 0; i <= Domain(min, max); ++i)
		{
			Fraction domain_min = new Fraction(min);
			Fraction product = Fraction.Multiply(new Fraction(i), new Fraction(1, 100)); 
			Fraction x_values = Fraction.Add(domain_min, product);

			for(int j : vars)
			{
				postfix_notation.set(j, Double.toString(x_values.InDouble()));
			}

			solutions.add(ReversePolishNotation(postfix_notation));
		}

		/*for(var v : solutions)
		{
			System.out.print(v + " ");
		}
		System.out.println();*/

		return solutions;
	}

	public ArrayList<String> InfixNotation(String args)
	{
		var space_less = args.replaceAll(" ", "");
		var pt1 = space_less.substring(0, 4);
		var pt2 = space_less.substring(5, space_less.length());
		var variable = Character.toString(pt1.charAt(2));
		
		var output = new ArrayList<String>();
		var operator_stack = new Stack<Operators>();
		enum Previous { DEFAULT, NUMERIC, OPERATOR }
		var previous = Previous.DEFAULT;
		String letters = "";

		output.add(variable);

		for(int i = 0; i < pt2.length(); ++i)
		{
			String current = Character.toString(pt2.charAt(i));
			letters = letters.concat(current);

			if(isNumeric(current))
			{
				switch(previous)
				{
					case NUMERIC:
						output.set(output.size() - 1, output.get(output.size() - 1).concat(current));
						break;
					case OPERATOR:
					default:
						output.add(current);
						previous = Previous.NUMERIC;
						break;
				}

				letters = "";
			}
			else if(isNumeric(letters))
			{
				output.add(letters);
				letters = "";
				previous = Previous.NUMERIC;
			}
			else
			{				
				if(current.equals("("))
				{
					output.add("(");
					letters = "";
				}
				else if(current.equals(")"))
				{
					output.add(")");
					letters = "";
				}
				else if(current.equals("|"))
				{
					output.add("|");
					letters = "";
				}
				else if(current.equals(variable))
				{
					output.add(variable);
					letters = "";
				}
				else if(current.equals(","))
				{
					output.add(",");
					letters = "";
				}
				else if(sp_operators.containsKey(letters) || mp_operators.containsKey(letters))
				{
					output.add(letters);
					letters = "";
				}

				previous = Previous.OPERATOR;
			}
		}

		return output;
	}
}