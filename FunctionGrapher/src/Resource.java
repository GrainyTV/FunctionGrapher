import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

class Resource
{
	private JPanel window;
	private static final JFileChooser fc = new JFileChooser();
	private static FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");

	public Resource(JPanel window)
	{
		this.window = window;
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
	}

	@SuppressWarnings("unchecked")
	public void Save(ArrayList<ArrayList<String>> functions)
	{
		int result = fc.showSaveDialog(window);

		if(result == JFileChooser.APPROVE_OPTION)
		{
			File fileToSave = fc.getSelectedFile();

			try
			{
				JSONObject functions_container = new JSONObject();
		      	JSONArray jsonFile = new JSONArray();      	
		      	char name = 'f';

		      	for(int i = 0; i < functions.size(); ++i)
		      	{
		      		if(functions.get(i).size() > 0)
		      		{
			      		JSONObject jsonObject = new JSONObject();
		      			JSONArray jsonArray = new JSONArray();

			      		for(int j = 1; j < functions.get(i).size(); ++j)
			      		{
			      			jsonArray.add(functions.get(i).get(j));
			      		}

			      		jsonObject.put(MessageFormat.format("{0}({1})", name, functions.get(i).get(0)), jsonArray);
			      		++name;

			      		jsonFile.add(jsonObject);
		      		}
		      	}

				functions_container.put("functions", jsonFile);
				
				FileWriter file = (fileToSave.getName().length() > 5 && fileToSave.getName().toLowerCase().substring(fileToSave.getName().length() - 5, fileToSave.getName().length()).equals(".json"))
					? new FileWriter(fileToSave.getParentFile() + File.separator + fileToSave.getName())
					: new FileWriter(fileToSave.getParentFile() + File.separator + fileToSave.getName().concat(".json"));
	        	
	        	file.write(functions_container.toJSONString());
	    		file.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public ArrayList<ArrayList<String>> Load()
	{
		int result = fc.showOpenDialog(window);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			JSONParser parser = new JSONParser();
			File file = fc.getSelectedFile();

			try
			{
		        FileReader reader = new FileReader(file);
	        	JSONObject jsonObject = (JSONObject) parser.parse(reader);
	        	JSONArray functions = (JSONArray) jsonObject.get("functions");
				ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		      	
		      	for(int i = 0; i < functions.size(); ++i)
		      	{
		      		JSONObject function = (JSONObject) functions.get(i);
		      		var key = 'f' + i;
        			input.add(new ArrayList<String>());
        			input.get(i).add(Character.toString(function.toString().charAt(4)));

        			for(var value : (JSONArray) function.get(MessageFormat.format("{0}({1})", (char) key, Character.toString(function.toString().charAt(4)) ))) 
        			{
        				input.get(i).add((String) value);
        			}
		      	}

	    		reader.close();
	    		return input;
			}
			catch(IOException | ParseException e)
			{
				e.printStackTrace();
			}
		}

		return new ArrayList<ArrayList<String>>();
	}
}