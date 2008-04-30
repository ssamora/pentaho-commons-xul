package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingDialog extends SwingElement implements XulDialog{
	
	private JDialog dialog;
	private String buttonlabelaccept;

	private String buttonlabelcancel;
	private TreeMap<SwingDialog.BUTTONS, XulButton> buttons = new TreeMap<SwingDialog.BUTTONS, XulButton>();
	private String ondialogaccept;
	private String ondialogcancel;
	private String title = "Dialog";
	private String onload;
	
	private XulDialogheader header;
	
	private int height = 300;
	private int width = 450;
	private BUTTON_ALIGN buttonAlignment;
	
	private enum BUTTONS{ ACCEPT, CANCEL, HELP };

	private enum BUTTON_ALIGN{ START, CENTER, END, LEFT, RIGHT, MIDDLE };
	
	public SwingDialog(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("dialog");
    
    this.orientation = Orient.VERTICAL;
    
    container = new JPanel(new GridBagLayout());
    
    managedObject = "empty"; // enclosing containers should not try to attach this as a child
    
    resetContainer();
  }
	

  public void resetContainer(){
    
    container.removeAll();
    
    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(2,2,2,2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;
    
  }

	public String getButtonlabelaccept() {
		return buttonlabelaccept;
	}

	public String getButtonlabelcancel() {
		return buttonlabelcancel;
	}

	public String getButtons() {
		return null; //new ArrayList<XulButton>(this.buttons.values());
	}

	public String getOndialogaccept() {
		return ondialogaccept;
	}

	public String getOndialogcancel() {
		return ondialogcancel;
	}

	public String getTitle() {
		return title;
	}

	public void setButtonlabelaccept(String label) {
		this.buttonlabelaccept = label;
	}

	public void setButtonlabelcancel(String label) {
		this.buttonlabelcancel = label;
	}

	public void setButtons(String buttons) {
		String[] tempButtons = buttons.split(",");

		for(int i=0; i< tempButtons.length; i++){
			this.buttons.put(
					SwingDialog.BUTTONS.valueOf(tempButtons[i].trim().toUpperCase()),
					new SwingButton()
			);
		}
	}

	public void setOndialogaccept(String command) {
		this.ondialogaccept = command;
		
	}

	public void setOndialogcancel(String command) {
		this.ondialogcancel = command;
		
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void show(){
		Document doc = getDocument();
    Element rootElement = doc.getRootElement();
    XulWindow window = (XulWindow) rootElement;
    

		dialog = new JDialog((JFrame)window.getManagedObject());
    dialog.setLayout(new BorderLayout());
		
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    dialog.setTitle(title);
    dialog.setModal(true);
    dialog.add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(container, BorderLayout.CENTER);
    
    if(this.header != null){
	    
	    JPanel headerPanel = new JPanel(new BorderLayout());
	    headerPanel.setBackground(Color.decode("#888888"));
			JPanel headerPanelInner = new JPanel(new BorderLayout());
			headerPanelInner.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			headerPanelInner.setOpaque(false);
			
			headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.decode("#AAAAAA"), Color.decode("#666666")));
			
			JLabel title = new JLabel(this.header.getTitle());
			
			title.setForeground(Color.white);
			headerPanelInner.add(title, BorderLayout.WEST);
			
			JLabel desc = new JLabel(this.header.getDescription());
			desc.setForeground(Color.white);
			headerPanelInner.add(desc, BorderLayout.EAST);
	
			headerPanel.add(headerPanelInner, BorderLayout.CENTER);
			
			mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
		Box buttonPanel = Box.createHorizontalBox();
		
		if(	this.buttonAlignment == BUTTON_ALIGN.RIGHT || 
				this.buttonAlignment == BUTTON_ALIGN.END ||
				this.buttonAlignment == BUTTON_ALIGN.MIDDLE ||
				this.buttonAlignment == BUTTON_ALIGN.CENTER)
		{
			buttonPanel.add(Box.createHorizontalGlue());
		}
		
		Set<SwingDialog.BUTTONS> keys = this.buttons.descendingKeySet();
		
		for(SwingDialog.BUTTONS btn : keys){
			buttonPanel.add(Box.createHorizontalStrut(5));
			buttonPanel.add((JButton) this.buttons.get(btn).getManagedObject());
		}
		buttonPanel.add(Box.createHorizontalStrut(5));
		
		if(	this.buttonAlignment == BUTTON_ALIGN.START || 
				this.buttonAlignment == BUTTON_ALIGN.LEFT ||
				this.buttonAlignment == BUTTON_ALIGN.MIDDLE ||
				this.buttonAlignment == BUTTON_ALIGN.CENTER)
		{
			buttonPanel.add(Box.createHorizontalGlue());
		}
		
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		dialog.setSize(new Dimension(getWidth(), getHeight()));
		dialog.setPreferredSize(new Dimension(getWidth(), getHeight()));
		dialog.setMinimumSize(new Dimension(getWidth(), getHeight()));
		

		if(buttons.containsKey(SwingDialog.BUTTONS.ACCEPT)){
			this.buttons.get(SwingDialog.BUTTONS.ACCEPT).setLabel(this.getButtonlabelaccept());
			this.buttons.get(SwingDialog.BUTTONS.ACCEPT).setOnclick(this.getOndialogaccept());
		}
		if(buttons.containsKey(SwingDialog.BUTTONS.CANCEL)){
			this.buttons.get(SwingDialog.BUTTONS.CANCEL).setLabel(this.getButtonlabelcancel());
			this.buttons.get(SwingDialog.BUTTONS.CANCEL).setOnclick(this.getOndialogcancel());
		}
		
		
    
    
    dialog.setVisible(true);
	}
	
	public void hide(){
		dialog.setVisible(false);
	}
	
	public void setVisible(boolean visible){
		if(visible){
			show();
		} else {
			hide();
		}
	}
	

	@Override
	public void layout() {
		super.layout();
		
		for(XulComponent comp : this.children){
			if(comp instanceof XulDialogheader){
				header = (XulDialogheader) comp;
			}
		}
	}


	public int getHeight() {
		return this.height;
	}


	public int getWidth() {
		return this.width;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public String getButtonalign() {
		return this.buttonAlignment.toString().toLowerCase();
	}


	public void setButtonalign(String align) {
		this.buttonAlignment = SwingDialog.BUTTON_ALIGN.valueOf(align.toUpperCase());
		
	}


	public String getOnload() {
		return onload;
	}


	public void setOnload(String onload) {
		this.onload = onload;
	}
	
}
