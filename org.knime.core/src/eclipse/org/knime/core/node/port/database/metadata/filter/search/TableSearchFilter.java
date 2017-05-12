/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME GmbH, Konstanz, Germany
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
 * ---------------------------------------------------------------------
 *
 * History
 *   Apr 20, 2017 (adewi): created
 */
package org.knime.core.node.port.database.metadata.filter.search;

import java.util.regex.Pattern;

import org.knime.core.node.port.database.metadata.model.DBObject;
import org.knime.core.node.port.database.metadata.model.DBSchema;
import org.knime.core.node.port.database.metadata.model.DBTable;
import org.knime.core.node.port.database.metadata.model.DBView;

/**
 * Search filter for the DBTable objects.
 *
 * @author Andisa Dewi, KNIME.com, Berlin, Germany
 * @since 3.4
 */
public class TableSearchFilter implements SearchFilter {

    private static final int TABLE_ONLY = 0;

    private static final int VIEW_ONLY = 1;

    private final Pattern m_regex;

    private final int m_option;

    /**
     * Constructor for the Table search filter.
     *
     * @param regex the regular expression that should be matched
     * @param filterOption if 0, then only tables will be filtered, 1 for views only, else for both
     *
     */
    public TableSearchFilter(final Pattern regex, final int filterOption) {
        m_regex = regex;
        m_option = filterOption;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean filter(final DBObject object) {
        if (object instanceof DBTable) {
            if (m_option == VIEW_ONLY) {
                return false;
            }
            return m_regex.matcher(object.getName()).matches();
        }
        if (object instanceof DBView) {
            if (m_option == TABLE_ONLY) {
                return false;
            }
            return m_regex.matcher(object.getName()).matches();
        }
        if (object instanceof DBSchema) {
            return ((DBSchema)object).getColumnContainers().iterator().hasNext();
        }

        return true;

    }

}
