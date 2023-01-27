import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;

class ColorPicker extends JButton implements ListCellRenderer
{
	private static boolean b = false;

 	public ColorPicker()
    {
    	setOpaque(true);
    }   

    @Override
    public void setBackground(Color bg) 
    {
        // TODO Auto-generated method stub
	    if(!b)
	    {
	        return;
	    }

        super.setBackground(bg);
    }
    
    public Component getListCellRendererComponent(  
        JList list,  
        Object value,  
        int index,  
        boolean isSelected,  
        boolean cellHasFocus)  
    {  

        b = true;
        setText("");           
        setBackground((Color)value);
        b = false;
        return this;  
    }  	
}