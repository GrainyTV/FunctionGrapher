import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.EmptyStackException;
import java.text.MessageFormat;

class Windows extends JPanel
{
	private JMenuBar jcomp1;
	private JTextField[] jcomp2;
	private JComboBox[] jcomp3;
	private JSpinner[] jcomp4;
	private int count;
	private Functions function_ctrl;
	private Display dsp;
	private Resource rsc;
	private ArrayList<ArrayList<String>> functions;
	private int domain_min;
	private int domain_max;
	private int range_min;
	private int range_max;

	public Windows()
	{
		count = 0;
		function_ctrl = new Functions();
		functions = new ArrayList<ArrayList<String>>();
		rsc = new Resource(this);

		domain_min = -2;
		domain_max = 2;
		range_min = -2;
		range_max = 2;

		for(int i = 0; i < 21; ++i)
		{
			functions.add(new ArrayList<String>());
		}

		//adjust size and set layout
        setPreferredSize(new Dimension(960, 540));
        setLayout(null);

		CreateMenu();
		CreateInputFields();
		CreateDrawingPlane();
		CreateColorPickers();
		CreateLineWidthChangers();
	}

	@SuppressWarnings("unchecked")
	public void CreateMenu()
	{
		//construct preComponents
		JMenu fileMenu = new JMenu("File");
        
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		var input = rsc.Load();
        		
        		if(input.size() > 0)
        		{
        			var key = 'f';

        			for(int i = 0; i < input.size(); ++i)
        			{
        				String value = Character.toString((char) key) + MessageFormat.format("({0})=", input.get(i).get(0));
        				input.get(i).remove(0);

        				for(String s : input.get(i))
        				{
        					value = value.concat(s);
        				}

        				jcomp2[i].setText(value);
        				++key;
        			}
        		}
        	}
        });
        fileMenu.add(loadItem);
        
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();

        		for(int i = 0; i < functions.size(); ++i)
        		{
        			if(functions.get(i).isEmpty() == false)
        			{
        				output.add(new ArrayList<String>());
        				output.set(i, function_ctrl.InfixNotation(jcomp2[i].getText()));
        			}
        		}

        		rsc.Save(output);
        	}
        });
        fileMenu.add(saveItem);
        
        JMenu optionsMenu = new JMenu("Options");
        
        JLabel domainLabel = new JLabel("Domain");
        JTextField domainItem = new JTextField("[-2;2]");
        domainItem.setEditable(false);
        JButton editDomain = new JButton("Edit");
        editDomain.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent action_e)
        	{
        		switch(editDomain.getText())
        		{
        			case "Edit":
        				editDomain.setText("Save");
        				domainItem.setText("");
        				domainItem.setEditable(true);
        				break;
        			case "Save":
        				editDomain.setText("Edit");
        				domainItem.setEditable(false);
        				String value = domainItem.getText();

        				try
        				{
	        				if(value.charAt(0) != '[' || value.charAt(value.length() - 1) != ']' || !value.contains(";"))
	        				{
	        					domainItem.setText(MessageFormat.format("[{0};{1}]", domain_min, domain_max));
	        					return;
	        				}

        					int semicolon_pos = value.indexOf(";");
        					int v1 = Integer.parseInt(value.substring(1, semicolon_pos));
        					int v2 = Integer.parseInt(value.substring(semicolon_pos + 1, value.length() - 1));

        					if(v1 > v2)
        					{
        						throw new NumberFormatException();
        					}

        					domain_min = v1;
        					domain_max = v2;
        					dsp.setDomain(v1, v2);
        					for(int i = 0; i < functions.size(); ++i)
        					{
        						final int j = i;

        						if(functions.get(i).isEmpty() == false)
        						{
        							dsp.ChangeVertices(function_ctrl.GetDomain(domain_min, domain_max), function_ctrl.SolveFunctionOnDomain(domain_min, domain_max,
				      				Character.toString(jcomp2[j].getText().charAt(2)), functions.get(j)), j);
        						}
        					}
        					repaint();
        				}
        				catch(NumberFormatException | IndexOutOfBoundsException e)
        				{
        					domainItem.setText(MessageFormat.format("[{0};{1}]", domain_min, domain_max));
        				}

        				break;
        		}
        	}
        });

        JPanel domain = new JPanel();
        domainLabel.setPreferredSize(new Dimension(48, 20));
        domainItem.setPreferredSize(new Dimension(75, 20));
        editDomain.setPreferredSize(new Dimension(75, 20));
        domain.add(domainLabel);
        domain.add(domainItem);
        domain.add(editDomain);
        optionsMenu.add(domain);
        
        JLabel rangeLabel = new JLabel("Range");
        JTextField rangeItem = new JTextField("[-2;2]");
        rangeItem.setEditable(false);
        JButton editRange = new JButton("Edit");
        editRange.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent action_e)
        	{
        		switch(editRange.getText())
        		{
        			case "Edit":
        				editRange.setText("Save");
        				rangeItem.setText("");
        				rangeItem.setEditable(true);
        				break;
        			case "Save":
        				editRange.setText("Edit");
        				rangeItem.setEditable(false);
        				String value = rangeItem.getText();

        				try
        				{
	        				if(value.charAt(0) != '[' || value.charAt(value.length() - 1) != ']' || !value.contains(";"))
	        				{
	        					rangeItem.setText(MessageFormat.format("[{0};{1}]", range_min, range_max));
	        					return;
	        				}
        					
        					int semicolon_pos = value.indexOf(";");
        					int v1 = Integer.parseInt(value.substring(1, semicolon_pos));
        					int v2 = Integer.parseInt(value.substring(semicolon_pos + 1, value.length() - 1));

        					if(v1 > v2)
        					{
        						throw new NumberFormatException();
        					}

        					range_min = v1;
        					range_max = v2;
        					dsp.setRange(v1, v2);
        					for(int i = 0; i < functions.size(); ++i)
        					{
        						final int j = i;

        						if(functions.get(i).isEmpty() == false)
        						{
        							dsp.ChangeVertices(function_ctrl.GetDomain(domain_min, domain_max), function_ctrl.SolveFunctionOnDomain(domain_min, domain_max,
				      				Character.toString(jcomp2[j].getText().charAt(2)), functions.get(j)), j);
        						}
        					}
        					repaint();
        				}
        				catch(NumberFormatException e)
        				{
        					rangeItem.setText(MessageFormat.format("[{0};{1}]", range_min, range_max));
        				}

        				break;
        		}
        	}
        });

        JPanel range = new JPanel();
        rangeLabel.setPreferredSize(new Dimension(48, 20));
        rangeItem.setPreferredSize(new Dimension(75, 20));
        editRange.setPreferredSize(new Dimension(75, 20));
        range.add(rangeLabel);
        range.add(rangeItem);
        range.add(editRange);
        optionsMenu.add(range);

        JPanel labelFrequency = new JPanel(); 
        JLabel labelsLabel = new JLabel("Labels");
        JSlider labels = new JSlider(JSlider.HORIZONTAL, 0, 2, 2);
        labels.addChangeListener(new ChangeListener()
        {
			public void stateChanged(ChangeEvent e)
			{
				int value = labels.getValue();
				dsp.setLabelFrequency(value);
				repaint();
			}
        });
    	labels.setMinorTickSpacing(1);  
		labels.setMajorTickSpacing(2);  
		labels.setPaintTicks(true);  
		labels.setPaintLabels(true);
		Dictionary text = new Hashtable();
		text.put(0, new JLabel("0%"));
		text.put(1, new JLabel("50%"));
		text.put(2, new JLabel("100%"));
		labels.setLabelTable(text);
        labelFrequency.add(labelsLabel);
        labelFrequency.add(labels);
        optionsMenu.add(labelFrequency);

        //construct components
        jcomp1 = new JMenuBar();
        jcomp1.add(fileMenu);
        jcomp1.add(optionsMenu);

        //add components
        add(jcomp1);

        //set component bounds(only needed by Absolute Positioning)
        jcomp1.setBounds(0, 0, 960, 36);
	}

	public void CreateLineWidthChangers()
	{
		jcomp4 = new JSpinner[21];

		for(int i = 0; i < 21; ++i)
		{
			final int j = i;

			SpinnerNumberModel model = new SpinnerNumberModel(3.0, 0.5, 10.0, 0.25);
			jcomp4[i] = new JSpinner(model);

			add(jcomp4[i]);
			jcomp4[i].setBounds(150, 36 + (i * 24), 50, 24);
			((JSpinner.DefaultEditor)jcomp4[i].getEditor()).getTextField().setEditable(false);
			jcomp4[i].addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					float f = ((Double) jcomp4[j].getValue()).floatValue();
					dsp.setLineWidth(j, f);
					repaint();
				}
			});

        	switch(i)
        	{
        		case 0:
        			jcomp4[i].setVisible(true);
        			break;
        		default:
        			jcomp4[i].setVisible(false);
        			break;
        	}
		}
	}

	@SuppressWarnings("unchecked")
	public void CreateColorPickers()
	{
		jcomp3 = new JComboBox[21];

		for(int i = 0; i < 21; ++i)
		{
			final int j = i;
			Color[] colors = Display.Colors();
			jcomp3[i] = new JComboBox(colors);

			add(jcomp3[i]);
			jcomp3[i].setBounds(100, 36 + (i * 24), 50, 24);
			jcomp3[i].setRenderer(new ColorPicker());
			jcomp3[i].setSelectedIndex(j);
			jcomp3[i].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Color c = (Color) jcomp3[j].getSelectedItem();
					dsp.setColor(j, c);
					repaint();
				}
			});

        	switch(i)
        	{
        		case 0:
        			jcomp3[i].setVisible(true);
        			break;
        		default:
        			jcomp3[i].setVisible(false);
        			break;
        	}
		}
	}

	public void CreateDrawingPlane()
	{
		dsp = new Display();
		add(dsp);
	}

	public void CreateInputFields()
	{
		jcomp2 = new JTextField[21];

        for(int i = 0; i < 21; ++i)
        {
        	jcomp2[i] = new JTextField();
        	add(jcomp2[i]);
        	jcomp2[i].setBounds(0, 36 + (i * 24), 100, 24);
        	jcomp2[i].setEditable(true);

        	switch(i)
        	{
        		case 0:
        			jcomp2[i].setVisible(true);
        			//functions.add(new ArrayList<String>());
        			break;
        		default:
        			jcomp2[i].setVisible(false);
        			break;
        	}

        	if(i != 20)
        	{
        		final int j = i;

        		jcomp2[j].getDocument().addDocumentListener(new DocumentListener()
	        	{
	        		public void changedUpdate(DocumentEvent e)
	        		{
					    changed();
					}
					
					public void removeUpdate(DocumentEvent e)
					{
					    changed();
					}
					
					public void insertUpdate(DocumentEvent e)
					{
					    changed();
					}

				  	public void changed()
				  	{
					    if(jcomp2[j].getText().equals(""))
					    {
					    	jcomp2[j + 1].setVisible(false);
					    	jcomp2[j + 1].setText("");
					    	jcomp3[j + 1].setVisible(false);
					    	jcomp3[j + 1].setSelectedIndex(j + 1);
					    	jcomp4[j + 1].setVisible(false);
					    	jcomp4[j + 1].setValue(3.0);
					    	
					    	count = HowManyVisible();

					    	if(j > 0)
					    	{
					    		jcomp2[j - 1].setEditable(true);
					    	}
					    	
					    	functions.get(j).clear();
					    	dsp.DeleteVertices(j);
					    	repaint();    	
					    }
					    else
					    {
					      	jcomp2[j + 1].setVisible(true);
					      	jcomp3[j + 1].setVisible(true);
					      	jcomp4[j + 1].setVisible(true);
					      	//functions.add(new ArrayList<String>());
					      	count = HowManyVisible();

				      		for(int k = 0; k < count - 2; ++k)
				      		{
				      			jcomp2[k].setEditable(false);
				      		}

				      		try
				      		{
				      			//functions.get(j).clear();
				      			functions.set(j, new ArrayList<String>(function_ctrl.FunctionInterpreter(jcomp2[j].getText())));
				      			dsp.ChangeVertices(function_ctrl.GetDomain(domain_min, domain_max), function_ctrl.SolveFunctionOnDomain(domain_min, domain_max,
				      				Character.toString(jcomp2[j].getText().charAt(2)), functions.get(j)), j);
				      			repaint();      			
				      		}
						    catch(StringIndexOutOfBoundsException | EmptyStackException e)
						    {
						    	functions.get(j).clear();
						    }
					    }
					}
	        	});
        	}
        	else
        	{
        		final int j = i;

        		jcomp2[j].getDocument().addDocumentListener(new DocumentListener()
	        	{
	        		public void changedUpdate(DocumentEvent e)
	        		{
					    changed();
					}
					
					public void removeUpdate(DocumentEvent e)
					{
					    changed();
					}
					
					public void insertUpdate(DocumentEvent e)
					{
					    changed();
					}

					public void changed()
				  	{
					    if(jcomp2[j].getText().equals(""))
					    {	
					    	functions.get(j).clear();
					    	dsp.DeleteVertices(j);
					    	repaint();    	
					    }
					    else
					    {
				      		try
				      		{
				      			//functions.get(j).clear();
				      			functions.set(j, function_ctrl.FunctionInterpreter(jcomp2[j].getText()));
				      			dsp.ChangeVertices(function_ctrl.GetDomain(domain_min, domain_max), function_ctrl.SolveFunctionOnDomain(domain_min, domain_max,
				      				Character.toString(jcomp2[j].getText().charAt(2)), functions.get(j)), j);
				      			repaint();
				      		}
						    catch(StringIndexOutOfBoundsException | EmptyStackException e)
						    {
						    	functions.get(j).clear();
						    }
					    }
					}
	        	});
        	}
        }
	}

	public int HowManyVisible()
	{
		int count = 0;

		for(int i = 0; i < 21; ++i)
		{
			if(jcomp2[i].isVisible())
			{
				++count;
			}
			else
			{
				break;
			}
		}

		return count;
	}
}

