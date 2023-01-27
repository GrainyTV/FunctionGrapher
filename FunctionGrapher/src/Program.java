class Program
{
	private static final boolean TESTING = false;

	public static void main(String[] args)
	{
		if(!TESTING) 
		{
		    Controller ctrl = new Controller();
		}
		else
		{
			Tests unit_test = new Tests();
			unit_test.Run();
		}
	}
}