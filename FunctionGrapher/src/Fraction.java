import java.lang.Math;
import java.lang.ArithmeticException;
import java.math.BigDecimal;
import java.math.RoundingMode;

class Fraction
{
	int num;
	int den;

	public Fraction(int num, int den)
	{
		this.num = num;
		this.den = den;
	}

	public Fraction(int num)
	{
		this.num = num;
		this.den = 1;
	}

	public Fraction(double decimal) 
	{
	    String stringNumber = String.valueOf(decimal);
	    int numberDigitsDecimals = stringNumber.length() - 1 - stringNumber.indexOf('.');
	    int denominator = 1;
	    
	    for (int i = 0; i < numberDigitsDecimals; ++i) 
	    {
	        decimal *= 10;
	        denominator *= 10;
	    }

	    this.num = (int)Math.round(decimal);
	    this.den = denominator;
    }

    public static double Round(double value, int places)
    {
		try
		{
			BigDecimal bd = new BigDecimal(Double.toString(value));
	    	bd = bd.setScale(places, RoundingMode.HALF_UP);
	    	return bd.doubleValue();
		}
		catch(IllegalArgumentException e)
		{
			return value;
		}
	    
	}

	public int Num()
	{
		return num;
	}

	public int Den()
	{
		return den;
	}

	public double InDouble()
	{
		return num / (double)den;
	}

	public static Fraction Multiply(Fraction f1, Fraction f2)
	{
		return new Fraction(f1.Num() * f2.Num(), f1.Den() * f2.Den());
	}

	public static Fraction Divide(Fraction f1, Fraction f2)
	{
		if(f1.Den() * f2.Num() == 0)
		{
			throw new ArithmeticException("DIVIDING BY 0");
		}

		return new Fraction(f1.Num() * f2.Den(), f1.Den() * f2.Num());
	}

	public static Fraction Add(Fraction f1, Fraction f2)
	{
		return new Fraction(f2.Den() * f1.Num() + f1.Den() * f2.Num(), f1.Den() * f2.Den());
	}

	public static Fraction Subtract(Fraction f1, Fraction f2)
	{
		return new Fraction(f2.Den() * f1.Num() - f1.Den() * f2.Num(), f1.Den() * f2.Den());
	}

	public static Fraction Pow(Fraction f1, Fraction power)
	{
		if(f1.Num() % f1.Den() != 0 && f1.InDouble() < 0 && power.Num() % power.Den() != 0 && power.InDouble() < 0)
		{
			throw new ArithmeticException("NEGATIVE RATIONAL NUMBER RAISED TO NEGATIVE RATIONAL POWER");
		}

		double num = Math.pow(f1.Num(), power.InDouble());
		double den = Math.pow(f1.Den(), power.InDouble());

		if(power.Num() % power.Den() == 0)
		{
			return new Fraction(num / den);
		}

		return new Fraction(Round(num / den, 3));
	}

	public static Fraction Rt(Fraction n, Fraction f1)
	{
		if(n.InDouble() < 2.0 || f1.InDouble() < 0)
		{
			throw new ArithmeticException("UNDEFINED ROOT");
		}

		Fraction reciprocal = new Fraction(n.Den(), n.Num());

		return Pow(f1, reciprocal);
	}

	public static Fraction Sqrt(Fraction f1)
	{
		if(f1.InDouble() < 0)
		{
			throw new ArithmeticException("UNDEFINED ROOT");
		}

		Fraction reciprocal = new Fraction(1, 2);

		return Pow(f1, reciprocal);
	}

	public static Fraction Abs(Fraction f1)
	{
		return new Fraction(Math.abs(f1.Num()), Math.abs(f1.Den()));
	}

	public static Fraction Ln(Fraction f1)
	{
		if(f1.InDouble() <= 0)
		{
			throw new ArithmeticException("NEGATIVE VALUE INSIDE LOGARITHMIC FUNCTION");
		}

		return new Fraction(Round(Math.log(f1.InDouble()), 3));
	}

	public static Fraction Log(Fraction n, Fraction f1)
	{
		if(f1.InDouble() <= 0 || n.InDouble() < 2)
		{
			throw new ArithmeticException("NEGATIVE VALUE INSIDE LOGARITHMIC FUNCTION");
		}

		return new Fraction(Round(Math.log10(f1.InDouble()) / Math.log10(n.InDouble()), 3));
	}

	public static Fraction Sin(Fraction f1)
	{
		return new Fraction(Round(Math.sin(f1.InDouble()), 3));
	}

	public static Fraction Cos(Fraction f1)
	{
		return new Fraction(Round(Math.cos(f1.InDouble()), 3));
	}

	public static Fraction Tan(Fraction f1)
	{
		return new Fraction(Round(Math.tan(f1.InDouble()), 3));
	}

	public static Fraction Asin(Fraction f1)
	{
		return new Fraction(Round(Math.asin(f1.InDouble()), 3));
	}

	public static Fraction Acos(Fraction f1)
	{
		return new Fraction(Round(Math.acos(f1.InDouble()), 3));
	}

	public static Fraction Atan(Fraction f1)
	{
		return new Fraction(Round(Math.atan(f1.InDouble()), 3));
	}

	public static Fraction Sinh(Fraction f1)
	{
		return new Fraction(Round(Math.sinh(f1.InDouble()), 3));
	}

	public static Fraction Cosh(Fraction f1)
	{
		return new Fraction(Round(Math.cosh(f1.InDouble()), 3));
	}

	public static Fraction Tanh(Fraction f1)
	{
		return new Fraction(Round(Math.tanh(f1.InDouble()), 3));
	}
}