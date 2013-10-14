/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import logic.Product;

/**
 *
 * @author M
 */
public class ProductInfoJDialog extends javax.swing.JDialog {
    
    private Product product;

    /**
     * Creates new form ProductInfoJDialog
     */
    public ProductInfoJDialog(java.awt.Frame parent, boolean modal, Product product) {
	super(parent, "Описание товара - " + product.getName(), modal);
	this.product = product;
	setLocationRelativeTo(parent);
	initComponents();
	
	ProductInfoTableModel productInfoTableModel = new ProductInfoTableModel(this.product);
	productInfoTable.setModel(productInfoTableModel);
	
	// Setting columns parameters
	int tableWidth = productInfoTable.getWidth();
	productInfoTable.getColumnModel().getColumn(0).setPreferredWidth(tableWidth/2);
	productInfoTable.getColumnModel().getColumn(1).setPreferredWidth(tableWidth/2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        productInfoTable = new javax.swing.JTable();
        closeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        productInfoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        productInfoTable.setRowMargin(3);
        productInfoTable.setRowSelectionAllowed(false);
        productInfoTable.setShowHorizontalLines(false);
        productInfoTable.setShowVerticalLines(false);
        productInfoTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(productInfoTable);

        closeBtn.setText("Закрыть");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 152, Short.MAX_VALUE)
                        .addComponent(closeBtn)
                        .addGap(0, 152, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable productInfoTable;
    // End of variables declaration//GEN-END:variables
}
