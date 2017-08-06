/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarfraz
 */
public class SearchSale extends javax.swing.JFrame {

    /**
     * Creates new form SearchSale
     */
    //new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" })
    DefaultTableModel model = new DefaultTableModel();

    String stockID[];

    public SearchSale() {
        initComponents();
        this.getContentPane().setBackground(java.awt.Color.decode("#0041C2"));
        
        File filePath = new File(Constants.STOCK_FILE);
        /*if(filePath.exists()){
           
            
         JSONParser parser = new JSONParser();
        
         JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);
         String s[] = new String[array.size()+1];
         stockID = new String[array.size()];
            
         itemModel.addElement("All Products");
         for(int i=1;i<array.size()+1;i++){
         try {
         Object o = parser.parse(array.get(i-1).toString());
         JSONObject obj = (JSONObject) o;
         //model.addRow(new Object[] {obj.get("id"),obj.get("name"),obj.get("purchasePrice"),obj.get("salePrice"), obj.get("unit")});
         //s[i] = obj.get("name").toString();
         itemModel.addElement(obj.get("name"));
         stockID[i-1] = obj.get("id").toString();
         } catch (ParseException ex) {
         Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
         }

         //model.addRow(new Object[] {"one","two","three","four","five"});
         }
         //model = new DefaultComboBoxModel(s);
            
         }else{
         //String s[] = {"No item in the stock"};
         itemModel.addElement("No Items in the stock");
         } */
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fromDateChoser = new datechooser.beans.DateChooserCombo();
        toDateChoser = new datechooser.beans.DateChooserCombo();
        button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Sales");
        setLocation(new java.awt.Point(520, 100));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Sold Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("From :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Upto :");

        fromDateChoser.setCalendarPreferredSize(new java.awt.Dimension(350, 250));

        toDateChoser.setCalendarPreferredSize(new java.awt.Dimension(350, 250));

        button.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        button.setText("Search");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(fromDateChoser, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(toDateChoser, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(fromDateChoser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toDateChoser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(button)
                .addContainerGap())
        );

        jTable.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        model = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr No.","Date", "Item Name", "Sold Qty.", "Remaining Qty.", "Purchase Price", "Sold Price", "Total Sale Price", "Total Purchase Price", "Profit"
            }
        );
        jTable.setModel(model);
        jTable.setRowHeight(30);
        jScrollPane1.setViewportView(jTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed

        /* Date d = fromDateChoser.getSelectedDate().getTime();
         while(DateCompare.beforeDate(d, toDateChoser.getSelectedDate().getTime())){
         System.out.print("ok  ");
         d = DateCompare.nextDate(d);
         }*/
        model.setNumRows(0);

        JSONParser parser = new JSONParser();

        JSONArray mainArray = JsonClass.getJsonArray(Constants.SALE_RECORD_FILE);
        //System.out.println(mainArray);
        for (int i = 0; i < mainArray.size(); i++) {
            try {
                Object o = parser.parse(mainArray.get(i).toString());
                JSONArray array = (JSONArray) o;
                     //System.out.println(array);

                /*Object obj = parser.parse(array.get(0).toString());
                 JSONObject jsonObject = (JSONObject)obj;
                 String customerName = jsonObject.get("customerName").toString();
                 String customerPhone = jsonObject.get("customerPhone").toString();
                 String customerAddress = jsonObject.get("cutomerAddress").toString();
                     
                     
                 model.addRow(new Object[] {customerName});*/
                for (int j = 1; j < array.size(); j++) {
                    
                    Object obj = parser.parse(array.get(j).toString());
                    JSONObject jsonObject = (JSONObject) obj;
                    String itemName = jsonObject.get("itemName").toString();
                    String carret = jsonObject.get("carret").toString();
                    String unit = jsonObject.get("unit").toString();
                    String labourCost = jsonObject.get("labourCost").toString();
                    String total = jsonObject.get("total").toString();
                    String quantity = jsonObject.get("quantity").toString();
                    String salePrice = jsonObject.get("salePrice").toString();
                    String stockID = jsonObject.get("stockID").toString();
                    String sn = jsonObject.get("sn").toString();
                    String date = jsonObject.get("billDate").toString();

                    try {
                        Date targetDate = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(date);
                        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yy");
                        
                        Date toDate = toDateChoser.getSelectedDate().getTime();
                        Date fromDate = fromDateChoser.getSelectedDate().getTime();
                        
                        String toDateStr = y.format(toDate);
                        String fromDateStr = y.format(fromDate);
                        
                        Date to = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(toDateStr);
                        Date from = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(fromDateStr);
                        
                        
                        if (DateCompare.inRange(from, targetDate, to)) {
                            //"Sr No.", "Item Name", "Sold Qty.", "Remaining Qty.", "Purchase Price", "Sold Price", "Total Sale Price", "Total Purchase Price", "Profit"
                            Float totalSalePrice = Float.parseFloat(salePrice) * Float.parseFloat(quantity);
                            Float totalPurchPrice = Float.parseFloat(Helper.getPurchasePrice(stockID)) * Float.parseFloat(quantity);
                            Float profit = totalSalePrice - totalPurchPrice;
                            model.addRow(new Object[]{model.getRowCount() + 1, date, itemName, quantity + " " + unit, Helper.getRemainingQuantity(stockID), Helper.getPurchasePrice(stockID), salePrice, totalSalePrice, totalPurchPrice, profit});

                        }
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(SearchSale.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            } catch (ParseException ex) {
                Logger.getLogger(SearchSale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_buttonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private datechooser.beans.DateChooserCombo fromDateChoser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private datechooser.beans.DateChooserCombo toDateChoser;
    // End of variables declaration//GEN-END:variables
}
