package org.valkyriercp.application.docking.editor;

import org.valkyriercp.application.Editor;
import org.valkyriercp.application.PageComponent;
import org.valkyriercp.application.PageComponentPane;
import org.valkyriercp.factory.AbstractControlFactory;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class EditorComponentPane extends AbstractControlFactory implements PageComponentPane {
	private PageComponent component;
	
	public EditorComponentPane(PageComponent component){
		this.component = component;
	}

    public PageComponent getPageComponent() {
        return component;
    }
    
    protected JComponent createControl() {
    	JPanel panel = new JPanel(new BorderLayout());
    	JPanel actionBarsPanel = createActionBarsPanel();
    	if(actionBarsPanel != null){
    		panel.add(actionBarsPanel, BorderLayout.NORTH);
    	}
    	Component component = (getPageComponent()).getControl();
		panel.add(component, BorderLayout.CENTER);
		return panel;
    }
    
    private JPanel createActionBarsPanel(){
    	JPanel panel = new JPanel(new BorderLayout());
    	JComponent menuBar = createViewMenuBar();
    	JComponent toolBar = createViewToolBar();
    	if(menuBar == null && toolBar == null){
    		return null;
    	}
    	if(menuBar != null && toolBar != null){
    		panel.add(menuBar, BorderLayout.NORTH);
    		panel.add(toolBar, BorderLayout.SOUTH);
    	}
    	else if(menuBar != null){
    		panel.add(menuBar, BorderLayout.NORTH);
    	}
    	else if(toolBar != null){
    		panel.add(toolBar, BorderLayout.NORTH);
    	}
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
    	return panel;
    }
    
    /**
     * Returns the view specific toolbar if the underlying view 
     * implementation supports it.
     */
    protected JComponent createViewToolBar() {
    	if(getPageComponent() instanceof AbstractEditor){
    		AbstractEditor editor = (AbstractEditor)getPageComponent();
            return editor.getEditorToolBar();
    	}
    	return null;
    }

    /**
     * Returns the view specific menubar if the underlying view 
     * implementation supports it.
     */
    protected JComponent createViewMenuBar() {
    	if(getPageComponent() instanceof AbstractEditor){
    		AbstractEditor editor = (AbstractEditor)getPageComponent();
    		return editor.getEditorMenuBar();
    	}
    	return null;
    }

}
