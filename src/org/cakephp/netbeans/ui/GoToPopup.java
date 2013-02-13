/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.cakephp.netbeans.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import org.cakephp.netbeans.CakePhp;
import org.netbeans.modules.csl.api.UiUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;

/**
 * This file is originally from Retouche, the Java Support infrastructure in
 * NetBeans. I have modified the file as little as possible to make merging
 * Retouche fixes back as simple as possible.
 *
 * (This used to be IsOverriddenPopup in
 * org.netbeans.modules.java.editor.overridden)
 *
 *
 * @author Jan Lahoda
 * @author Tor Norbye
 * @author junichi11
 */
public class GoToPopup extends JPanel implements FocusListener {

    private static final long serialVersionUID = -2662042633320594913L;
    private String caption;
    private List<GoToItem> items;

    /**
     * Creates new form GoToPopup
     */
    public GoToPopup(String caption, List<GoToItem> items) {
        this.caption = caption;
        this.items = items;

        initComponents();

        jList1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addFocusListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setFocusCycleRoot(true);
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(caption
        );
        jLabel1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jLabel1, gridBagConstraints);

        jList1.setModel(createListModel());
        jList1.setCellRenderer(new RendererImpl());
        jList1.setSelectedIndex(0);
        jList1.setVisibleRowCount(items.size()
        );
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList1KeyPressed(evt);
            }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            openSelected();
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && evt.getModifiers() == 0) {
            openSelected();
        }
    }//GEN-LAST:event_jList1KeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void openSelected() {
        GoToItem item = (GoToItem) jList1.getSelectedValue();
        FileObject fileObject = item.getFileObject();
        if (fileObject != null) {
            UiUtils.open(fileObject, item.getOffset());
        } else {
            Toolkit.getDefaultToolkit().beep();
        }

        PopupUtil.hidePopup();
    }

    private ListModel createListModel() {
        DefaultListModel dlm = new DefaultListModel();

        for (GoToItem el : items) {
            dlm.addElement(el);
        }

        return dlm;
    }

    private static class RendererImpl extends DefaultListCellRenderer {

        private static final long serialVersionUID = 5408637835559113711L;

        @Override
        public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof GoToItem) {
                ImageIcon icon = ImageUtilities.loadImageIcon(CakePhp.CAKE_ICON_16, true);
                setIcon(icon);
            }

            return c;
        }
    }

    @Override
    public void focusGained(FocusEvent arg0) {
        jList1.requestFocus();
        jList1.requestFocusInWindow();
    }

    @Override
    public void focusLost(FocusEvent arg0) {
    }
}
