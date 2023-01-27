import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.lang.Double;
import java.lang.ArithmeticException;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.MouseInfo;
import java.awt.geom.Line2D;
import java.awt.event.MouseEvent;

class Display extends JPanel
{
	private double[][] x_values;
	private Double[][] y_values;
	private double origin_x;
	private double origin_y;
	private int domain_min;
	private int domain_max;
	private int range_min;
	private int range_max;
	private int width;
	private int height;
	private int lf;
	private float[] line_width;
	private Color[] colors;
	private static Color[] default_colors = new Color[] { Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW, new Color(118, 196, 13),
		new Color(252, 119, 3), new Color(89, 34, 34), new Color(197, 222, 11), new Color(5, 197, 255), new Color(113, 5, 255), new Color(192, 171, 255), new Color(186, 229, 255),
		new Color(242, 102, 149), new Color(64, 43, 79), new Color(35, 194, 167), new Color(128, 123, 47), new Color(0, 29, 102) };

	public Display()
	{
		setBounds(200, 36, 960, 540);
		setBackground(Color.WHITE);
		origin_x = (getWidth() - 200) / 2;
		origin_y = (getHeight() - 36) / 2;
		width = getWidth() - 200;
		height = getHeight() - 36;
		domain_min = -2;
		domain_max = 2;
		range_min = -2;
		range_max = 2;
		lf = 2;
		x_values = new double[21][];
		y_values = new Double[21][];
		colors = default_colors;
		line_width = new float[21];
		Arrays.fill(line_width, 3.0f);
		ToolTipManager.sharedInstance().registerComponent(this);
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	public void ChangeVertices(ArrayList<Double> domain, ArrayList<Double> solutions, int index)
	{
		var empty_test = new ArrayList<Double>(solutions);

		for(int i = 0; i < empty_test.size();)
		{
			if(empty_test.get(i).isNaN())
			{
				empty_test.remove(i);
			}
			else
			{
				++i;
			}
		}

		if(empty_test.size() == 0)
		{
			return;
		}

		double width_density = (double)width / domain.size();
		double height_density = (double)height / (Math.abs(range_min) + Math.abs(range_max));

		x_values[index] = new double[domain.size()];
		y_values[index] = new Double[solutions.size()];
	    
	    for(int i = 0; i < domain.size(); ++i)
	    {
	    	x_values[index][i] = -width / 2 + i * width_density;
	    }
 
	    for(int i = 0; i < solutions.size(); ++i)
	    {
	    	if(!solutions.get(i).isNaN())
	    	{
	    		y_values[index][i] = solutions.get(i) * height_density;
	    	}
	    	else
	    	{
	    		y_values[index][i] = solutions.get(i);
	    	}
	    }
	}

	public void DeleteVertices(int index)
	{
		x_values[index] = null;
		y_values[index] = null;
	}

	public static Color[] Colors()
	{
		return default_colors;
	}

	public void setColor(int index, Color color)
	{
		colors[index] = color;
	}

	public void setLineWidth(int index, float value)
	{
		line_width[index] = value;
	}

	public void setDomain(int min, int max)
	{
		domain_min = min;
		domain_max = max;
	}

	public void setRange(int min, int max)
	{
		range_min = min;
		range_max = max;
	}

	public void setLabelFrequency(int value)
	{
		lf = value;
	}

	@Override
	public String getToolTipText(MouseEvent mouse_e)
	{
		Point cursor = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(cursor, this);
		double x = cursor.getX() - width / 2;
		double y = (-1) * (cursor.getY() - height / 2);
		double epsilon = 5;

		try
		{
			String value = null;
			Double diff = null;

			for(int i = 0; i < x_values.length; ++i)
			{
				if(x_values[i] != null)
				{
					for(int j = 0; j < x_values[i].length; ++j)
					{
						if(Math.abs(x_values[i][j] - x) < epsilon && Math.abs(y_values[i][j] - y) < epsilon)
						{
							if(diff == null || Math.abs(x_values[i][j] - x) < diff)
							{
								value = Double.toString(Fraction.Round(y_values[i][j] / ((double)height / (Math.abs(range_min) + Math.abs(range_max))), 3));
								diff = Math.abs(x_values[i][j] - x);
							}				
						}
					}
				}	
			}

			return value;
		}
		catch(NullPointerException | IndexOutOfBoundsException e)
		{		
		}

		return null;
	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g.translate(origin_x, origin_y);

    	g.setColor(Color.BLACK);
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
		g.setComposite(alpha);

    	for(int i = domain_min; i <= domain_max; ++i)
    	{
    		g.draw(new Line2D.Double(i * (width / (Math.abs(domain_min) + Math.abs(domain_max))), -height / 2, i * (width / (Math.abs(domain_min) + Math.abs(domain_max))), +height / 2));

    		if(lf != 0)
    		{
    			switch(lf)
    			{
    				case 1:
    					if(i % 2 == 0)
    					{
    						g.drawString(Integer.toString(i), i * (width / (Math.abs(domain_min) + Math.abs(domain_max))) + 5, -5);
    					}
    					break;
    				case 2:
    					g.drawString(Integer.toString(i), i * (width / (Math.abs(domain_min) + Math.abs(domain_max))) + 5, -5);
    					break;
    			}		
    		}
    	}

    	for(int i = range_min; i <= range_max; ++i)
    	{
    		g.draw(new Line2D.Double(-width / 2, i * (height / (Math.abs(range_min) + Math.abs(range_max))), width / 2, i * (height / (Math.abs(range_min) + Math.abs(range_max)))));

    		if(lf != 0)
    		{
    			switch(lf)
    			{
    				case 1:
    					if(i % 2 == 0)
    					{
    						g.drawString(Integer.toString((-1) * i), 5, i * (height / (Math.abs(range_min) + Math.abs(range_max))) -5);
    					}
    					break;
    				case 2:
    					g.drawString(Integer.toString((-1) * i), 5, i * (height / (Math.abs(range_min) + Math.abs(range_max))) -5);
    					break;
    			}			
    		}
    	}

		g.setComposite(AlphaComposite.Src);
    	g.draw(new Line2D.Double(-width / 2, 0, width / 2, 0));
    	g.draw(new Line2D.Double(0, -height / 2, 0, height / 2));

		try
		{
			for(int i = 0; i < y_values.length; ++i)
			{
		    	g.setColor(colors[i]);

		    	if(y_values[i] != null)
		    	{
			    	for(int j = 0; j < y_values[i].length - 1; ++j)
			    	{
			    		g.setStroke(new BasicStroke(line_width[i], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			    		
			    		if(!y_values[i][j].isNaN() && !y_values[i][j + 1].isNaN())
			    		{
			    			g.draw(new Line2D.Double(x_values[i][j], (-1) * y_values[i][j], x_values[i][j + 1], (-1) * y_values[i][j + 1]));
			    		}	
			    	}
		    	}
			}
		}
		catch(NullPointerException e)
		{
		}
	}
}