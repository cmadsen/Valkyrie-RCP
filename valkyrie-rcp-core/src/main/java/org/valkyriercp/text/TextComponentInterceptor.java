/**
 * Copyright (C) 2015 Valkyrie RCP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.valkyriercp.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.valkyriercp.form.builder.AbstractFormComponentInterceptor;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * Abstract base class for <code>FormComponentInterceptor</code>s that work on
 * <code>JTextComponent</code>s.
 *
 * @author Peter De Bruycker
 *
 */
public abstract class TextComponentInterceptor extends AbstractFormComponentInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final void processComponent( String propertyName, JComponent component ) {
        JTextComponent textComponent = getTextComponent( getInnerComponent( component ) );
        if( textComponent != null ) {
            processComponent( propertyName, textComponent );
        }
    }

    /**
     * Process the text component.
     *
     * @param propertyName the name of the property
     * @param textComponent the text component
     */
    protected abstract void processComponent( String propertyName, JTextComponent textComponent );

    /**
     * Converts the given component to a <code>JTextComponent</code>. This can be a
     * simple cast if the component is already a text component, or an embedded component
     * (for example a JSpinner).
     * <p>
     * This method is protected, and can be overridden when necessary.
     *
     * @param component the component
     * @return a <code>JTextComponent</code>, or <code>null</code>
     */
    protected JTextComponent getTextComponent( JComponent component ) {
        if( component instanceof JSpinner ) {
            JSpinner spinner = (JSpinner) component;
            if( spinner.getEditor() instanceof JSpinner.DefaultEditor ) {
                return ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
            } else if( spinner.getEditor() instanceof JTextField ) {
                return (JTextField) spinner.getEditor();
            } else {
                logger.warn("Cannot use JSpinner editor of type " + spinner.getEditor().getClass());
                return null;
            }
        } else if(component instanceof JTextComponent) {
            return (JTextComponent) component;
        } else {
            return null;
        }
    }

}

