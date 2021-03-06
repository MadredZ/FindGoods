/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import logic.User;
import service.UserService;
import utils.UserUtils;

/**
 *
 * @author M
 */
public class UsersJPanel extends javax.swing.JPanel {
    
    javax.swing.JDialog parent;
    private LinkedList<User> users;

    /**
     * Creates new form UsersJPanel
     */
    public UsersJPanel(javax.swing.JDialog parent) {
	this.parent = parent;
	initComponents();
	
	users = new LinkedList<>();
	try {
	    users.addAll(UserService.loadUsers(false));
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Can't get existing users: " + ex.getLocalizedMessage(),
		    "Error", JOptionPane.ERROR_MESSAGE, null);
	}
	
	UsersTableModel usersTableModel = new UsersTableModel();
	usersTableModel.setData(users);
	usersTable.setModel(usersTableModel);
	
	// Setting columns parameters
	int tableWidth = usersTable.getWidth();
	usersTable.getColumnModel().getColumn(0).setPreferredWidth(tableWidth/10);
	usersTable.getColumnModel().getColumn(1).setPreferredWidth(tableWidth/10);
	usersTable.getColumnModel().getColumn(2).setPreferredWidth(tableWidth/5);
	usersTable.getColumnModel().getColumn(3).setPreferredWidth(tableWidth/5);
	usersTable.getColumnModel().getColumn(4).setPreferredWidth(tableWidth/5);
	usersTable.getColumnModel().getColumn(5).setPreferredWidth(tableWidth/5);
	for (int i = 0; i < usersTable.getColumnModel().getColumnCount(); i++) {
	    usersTable.getColumnModel().getColumn(i).setResizable(false);
	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteUserBtn = new javax.swing.JButton();
        editUserBtn = new javax.swing.JButton();
        usersScrollPane = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();

        deleteUserBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/delete.png"))); // NOI18N
        deleteUserBtn.setToolTipText("Удалить пользователя");
        deleteUserBtn.setBorder(null);
        deleteUserBtn.setBorderPainted(false);
        deleteUserBtn.setContentAreaFilled(false);
        deleteUserBtn.setFocusPainted(false);
        deleteUserBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/deleteDark.png"))); // NOI18N
        deleteUserBtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/deleteHighlighted.png"))); // NOI18N
        deleteUserBtn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/deleteHighlighted.png"))); // NOI18N
        deleteUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserBtnActionPerformed(evt);
            }
        });

        editUserBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/edit.png"))); // NOI18N
        editUserBtn.setToolTipText("Редактировать данные пользователя");
        editUserBtn.setBorder(null);
        editUserBtn.setBorderPainted(false);
        editUserBtn.setContentAreaFilled(false);
        editUserBtn.setFocusPainted(false);
        editUserBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/editDark.png"))); // NOI18N
        editUserBtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/editHighlighted.png"))); // NOI18N
        editUserBtn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/editHighlighted.png"))); // NOI18N
        editUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserBtnActionPerformed(evt);
            }
        });

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        usersTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersTable.setShowHorizontalLines(false);
        usersTable.setShowVerticalLines(false);
        usersTable.getTableHeader().setResizingAllowed(false);
        usersTable.getTableHeader().setReorderingAllowed(false);
        usersScrollPane.setViewportView(usersTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usersScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteUserBtn)
                        .addGap(18, 18, 18)
                        .addComponent(editUserBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editUserBtn)
                    .addComponent(deleteUserBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usersScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserBtnActionPerformed
	int selectedRow = usersTable.getSelectedRow();
	
	if (selectedRow == -1) {
	    return;
	}
	
	Long userId = (Long) ((UsersTableModel) usersTable.getModel()).getValueAt(selectedRow, 0);
	try {
	    User user = UserUtils.getUserFromList(users, userId);
	    if (user == null) {
		JOptionPane.showMessageDialog(null,
			"Unexpected error: can't find user with ID = " + userId,
			"Error", JOptionPane.ERROR_MESSAGE, null);
		return;
	    }
	    
	    UserService.removeUser(userId);
	    users.remove(user);
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Can't delete selected user: " + ex.getLocalizedMessage(),
		    "Error", JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_deleteUserBtnActionPerformed

    private void editUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserBtnActionPerformed
        int selectedRow = usersTable.getSelectedRow();
	
	if (selectedRow == -1) {
	    return;
	}
	
	Long userId = (Long) ((UsersTableModel) usersTable.getModel()).getValueAt(selectedRow, 0);
	
	User user = UserUtils.getUserFromList(users, userId);
	
	if (user == null) {
	    JOptionPane.showMessageDialog(null,
		    "Unexpected error: can't find user with ID = " + userId,
		    "Error", JOptionPane.ERROR_MESSAGE, null);
	    return;
	}
	
	EditUserJDialog editUserDlg = new EditUserJDialog(this.parent, true, user);
	editUserDlg.setVisible(true);
	if (editUserDlg.user != null) {
	    users.remove(user);
	    users.add(editUserDlg.user);
	    ((UsersTableModel) usersTable.getModel()).setData(users);
	}
    }//GEN-LAST:event_editUserBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteUserBtn;
    private javax.swing.JButton editUserBtn;
    private javax.swing.JScrollPane usersScrollPane;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables
}
