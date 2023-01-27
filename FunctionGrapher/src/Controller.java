import javax.swing.JFrame;

class Controller
{
	JFrame frame;

	public Controller()
	{
		frame = new JFrame("Function Grapher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Windows());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
	}
}