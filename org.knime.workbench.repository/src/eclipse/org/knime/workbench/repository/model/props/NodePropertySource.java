/* 
 * ------------------------------------------------------------------------
 *
 *  Copyright (C) 2003 - 2013
 *  University of Konstanz, Germany and
 *  KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * -------------------------------------------------------------------
 * 
 * History
 *   16.03.2005 (georg): created
 */
package org.knime.workbench.repository.model.props;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import org.knime.workbench.repository.model.NodeTemplate;

/**
 * Property Source for a "NodeTemplate" object.
 * 
 * @author Florian Georg, University of Konstanz
 */
public class NodePropertySource implements IPropertySource {
    private static final PropertyDescriptor NAME_DESC = new PropertyDescriptor(
            "name", "Name");

    private static final PropertyDescriptor DESC_DESC = new PropertyDescriptor(
            "description", "Description");

    private static final PropertyDescriptor ID_DESC = new PropertyDescriptor(
            "id", "ID");

    private static final PropertyDescriptor TYPE_DESC = new PropertyDescriptor(
            "type", "Type");

    private static final PropertyDescriptor FACT_DESC = new PropertyDescriptor(
            "factory", "Factory class");

    private static final PropertyDescriptor ICON_DESC = new PropertyDescriptor(
            "icon.small", "Small icon");


    private static final PropertyDescriptor CAT_DESC = new PropertyDescriptor(
            "category", "Category path");

    private static final String CATEGORY_INFO = "Info";

    private static final String CATEGORY_ICONS = "Icons";

    private static final String CATEGORY_PATH = "Path";

    private NodeTemplate m_node;

    static {
        NAME_DESC.setCategory(CATEGORY_INFO);
        DESC_DESC.setCategory(CATEGORY_INFO);
        FACT_DESC.setCategory(CATEGORY_INFO);
        TYPE_DESC.setCategory(CATEGORY_INFO);

        ICON_DESC.setCategory(CATEGORY_ICONS);
        ID_DESC.setCategory(CATEGORY_PATH);
    }

    /**
     * Constructs a new property source for the.
     * 
     * @param model The node that is wrapped by this property source
     */
    public NodePropertySource(final NodeTemplate model) {
        m_node = model;
    }

    /**
     * {@inheritDoc}
     */
    public Object getEditableValue() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {ID_DESC, NAME_DESC, TYPE_DESC,
                DESC_DESC, FACT_DESC, CAT_DESC, ICON_DESC};
    }

    /**
     * {@inheritDoc}
     */
    public Object getPropertyValue(final Object id) {
        if ("name".equals(id)) {
            return m_node.getName();
        }
        if ("id".equals(id)) {
            return m_node.getID();
        }
        if ("type".equals(id)) {
            return m_node.getType();
        }
        if ("factory".equals(id)) {
            return m_node.getFactory();
        }
        if ("category".equals(id)) {
            return m_node.getCategoryPath();
        }

        // this should not happen
        assert false : "Unknown property id: " + id;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPropertySet(final Object id) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void resetPropertyValue(final Object id) {

    }

    /**
     * {@inheritDoc}
     */
    public void setPropertyValue(final Object id, final Object value) {
        // TODO Auto-generated method stub
    }
}
