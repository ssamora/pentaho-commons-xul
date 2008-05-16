package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtScript extends SwtElement implements XulScript {
  private static final long serialVersionUID = 3919768754393704152L;
  private static final Log logger = LogFactory.getLog(SwtScript.class);

  XulDomContainer windowContainer = null;
  String className = null;
  
  public SwtScript(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    windowContainer = container;
  }

  public String getSrc() {
    return className;
  }

  public void setSrc(String className) {
    this.className = className;
    if (this.getId() != null && className != null){
    	try{
    		windowContainer.addEventHandler(getId(), className);
    	} catch(XulException e){
    		logger.error("Error adding event handler",e);
    	}
    }
  }

  @Override
  /**
   *  Can't be guaranteed the order that these attributes 
   *  will be set, so register in both cases
   */
  public void setId(String id) {
    super.setId(id);
    if (this.getId() != null && className != null){
    	try{
    		windowContainer.addEventHandler(getId(), className);
    	} catch(XulException e){
    		logger.error("Error adding event handler",e);
    	}
    }
  }

}
