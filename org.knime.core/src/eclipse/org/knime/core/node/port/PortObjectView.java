/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
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
 *  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
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
 *   Dec 26, 2016 (wiswedel): created
 */
package org.knime.core.node.port;

import org.knime.core.node.property.hilite.HiLiteHandler;
import org.knime.core.node.workflow.CredentialsProvider;

/**
 * Interface optionally implemented by views returned by {@linkplain PortObject#getViews()}. It adds a few additional
 * methods that provide more framework information to the view, e.g. {@link HiLiteHandler} etc.
 *
 * <p>This interface was added after the {@link PortObject} interface was defined, hence it's optional to implement
 * it.
 *
 * @author Bernd Wiswedel, KNIME AG, Zurich, Switzerland
 * @since 3.4
 */
public interface PortObjectView {

    /** Sets the hilite handler associated with the port.
     * @param hiliteHandler the handler, might be null to reset.
     */
    default void setHiliteHandler(final HiLiteHandler hiliteHandler) {
        // possibly overridden
    }

    /** Sets the credentials provider associated with the node.
     * @param credentialsProvider the provider, might be null to reset.
     */
    default void setCredentialsProvider(final CredentialsProvider credentialsProvider) {
        // possibly overridden
    }

    /** Called when the view is discarded (won't be opened again).
     * @since 3.6 */
    default void dispose() {}

    /**
     * Called when the view window is closed but could potentially be opened again.
     * @since 3.8
     */
    default void close() {}

    /**
     * Called every time the view window is opened.
     * @since 3.8
     */
    default void open() {}

}
