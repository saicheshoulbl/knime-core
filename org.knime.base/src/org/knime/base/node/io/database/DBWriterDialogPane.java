
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
 */
package org.knime.base.node.io.database;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.port.database.DatabaseConnectionSettings;

/**
 * Dialog pane of the database writer.
 *
 * @author Thomas Gabriel, University of Konstanz
 */
final class DBWriterDialogPane extends NodeDialogPane {

    private final DBDialogPane m_loginPane = new DBDialogPane();

    private final JTextField m_table = new JTextField("");

    private final JCheckBox m_append = new JCheckBox("... to existing table (if any!)");

    private final DBSQLTypesPanel m_typePanel;

    private final JTextField m_batchSize;

    /**
     * Creates new dialog.
     */
    DBWriterDialogPane() {
// add login and table name tab
        final JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(" Table Name "));
        m_table.setFont(DBDialogPane.FONT);
        tablePanel.add(m_table, BorderLayout.CENTER);
        m_loginPane.add(tablePanel);

        final JPanel appendPanel = new JPanel(new BorderLayout());
        appendPanel.setBorder(
                BorderFactory.createTitledBorder(" Append Data "));
        m_append.setFont(DBDialogPane.FONT);
        m_append.setToolTipText("Table structure from input and database table"
                + " must match!");
        appendPanel.add(m_append, BorderLayout.CENTER);
        m_loginPane.add(appendPanel);

        final JPanel p = new JPanel(new BorderLayout());
        p.add(m_loginPane, BorderLayout.NORTH);
        super.addTab("Settings", p);

// add SQL Types tab
        m_typePanel = new DBSQLTypesPanel();
        final JScrollPane scroll = new JScrollPane(m_typePanel);
        scroll.setPreferredSize(m_loginPane.getPreferredSize());
        super.addTab("SQL Types", scroll);

// advanced tab with batch size
        final JPanel batchSizePanel = new JPanel(new FlowLayout());
        batchSizePanel.add(new JLabel("Batch Size: "));
        m_batchSize = new JTextField();
        m_batchSize.setPreferredSize(new Dimension(100, 20));
        batchSizePanel.add(m_batchSize);
        super.addTab("Advanced", batchSizePanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadSettingsFrom(final NodeSettingsRO settings,
            final DataTableSpec[] specs) throws NotConfigurableException {
        // get workflow credentials
        Collection<String> creds = super.getCredentialsNames();
        m_loginPane.loadSettingsFrom(settings, specs, creds);
        // table name
        m_table.setText(settings.getString(DBWriterNodeModel.KEY_TABLE_NAME, ""));
        // append data flag
        m_append.setSelected(settings.getBoolean(DBWriterNodeModel.KEY_APPEND_DATA, false));

        // load SQL Types for each column
        try {
            NodeSettingsRO typeSett = settings.getNodeSettings(DBWriterNodeModel.CFG_SQL_TYPES);
            m_typePanel.loadSettingsFrom(typeSett, specs);
        } catch (InvalidSettingsException ise) {
            m_typePanel.loadSettingsFrom(null, specs);
        }

        // load batch size
        final int batchSize = settings.getInt(DBWriterNodeModel.KEY_BATCH_SIZE,
                                              DatabaseConnectionSettings.BATCH_WRITE_SIZE);
        m_batchSize.setText(Integer.toString(batchSize));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings)
            throws InvalidSettingsException {
        m_loginPane.saveSettingsTo(settings);

        settings.addString(DBWriterNodeModel.KEY_TABLE_NAME, m_table.getText().trim());
        settings.addBoolean(DBWriterNodeModel.KEY_APPEND_DATA, m_append.isSelected());

        // save SQL Types for each column
        NodeSettingsWO typeSett = settings.addNodeSettings(DBWriterNodeModel.CFG_SQL_TYPES);
        m_typePanel.saveSettingsTo(typeSett);

        // save batch size
        final String strBatchSite = m_batchSize.getText().trim();
        if (strBatchSite.isEmpty()) {
            throw new InvalidSettingsException("Batch size must not be empty.");
        }
        try {
            final int intBatchSize = Integer.parseInt(strBatchSite);
            settings.addInt(DBWriterNodeModel.KEY_BATCH_SIZE, intBatchSize);
        } catch (final NumberFormatException nfe) {
            throw new InvalidSettingsException("Can't parse batch size \"" + strBatchSite
                                               + "\", reason: " + nfe.getMessage(), nfe);
        }
    }
}
