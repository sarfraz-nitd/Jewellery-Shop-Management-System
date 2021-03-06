/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarfraz
 */
public class AllDues extends javax.swing.JFrame {

    DefaultTableModel duesModel;

    /**
     * Creates new form AllDues
     */
    public AllDues() {
        initComponents();
        amountPanel.setVisible(false);
        setVisible(true);
        fillTable();
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            // do some actions here, for example
            // print first column value from selected row
            //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
            amountPanel.setVisible(true);
            amountTv.setText("");
            if(table.getSelectedRow()!=-1){
                String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                setDays(id);
            }
        }
    });
      
    }
    public void setDays(String id){
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        for(int i=0;i<array.size();i++){
            JSONObject obj = (JSONObject) array.get(i);
            String cId = obj.get("customerId").toString();
            if(cId.equals(id)){
                changeDateTv.setText(obj.get("days").toString());
            }
        }
    }

    public void fillTable() {
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        if(array.size()==0){
            duesModel.addRow(new Object[] {"","No Dues Record Available","","",""});
        }
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String dues = obj.get("dues").toString();
            String cId = obj.get("customerId").toString();
            String days = obj.get("days").toString();
            String billDate = obj.get("billDate").toString();
            String cName = obj.get("customerName").toString();

            Date dueDate = DateCompare.getDateDaysAfter(billDate, days);
           
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String d = sdf.format(dueDate);
            
            duesModel.addRow(new Object[] {cId, cName, dues, billDate, d});

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

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        amountPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        changeDateTv = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        amountTv = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        discAmtField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        searchedKeyText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        searchByCombo = new javax.swing.JComboBox();
        seachByLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(400, 100));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "All Dues", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        duesModel = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name", "Dues", "Bill Date", "Due Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(duesModel);
        table.setRowHeight(40);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMaxWidth(250);
            table.getColumnModel().getColumn(2).setMaxWidth(300);
        }
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Amount Paid: ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Change Scheduled Days of payment");

        changeDateTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("days");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Change");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        amountTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Paid");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        discAmtField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Rs. ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Rs. ");

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("Give Discount");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout amountPanelLayout = new javax.swing.GroupLayout(amountPanel);
        amountPanel.setLayout(amountPanelLayout);
        amountPanelLayout.setHorizontalGroup(
            amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(amountPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(amountPanelLayout.createSequentialGroup()
                        .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(amountPanelLayout.createSequentialGroup()
                                .addComponent(changeDateTv, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addGap(48, 48, 48))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, amountPanelLayout.createSequentialGroup()
                                .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(discAmtField, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(amountTv))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(amountPanelLayout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(amountPanelLayout.createSequentialGroup()
                        .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        amountPanelLayout.setVerticalGroup(
            amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(amountPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(changeDateTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jButton2)))
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(amountTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(jLabel6))
                .addGap(47, 47, 47)
                .addGroup(amountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(discAmtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jButton3))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        searchedKeyText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchedKeyText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchedKeyTextKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Search By: ");

        searchByCombo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchByCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Customer ID", "Customer Name", "Village / address", "Mobile No." }));
        searchByCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchByComboItemStateChanged(evt);
            }
        });
        searchByCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByComboActionPerformed(evt);
            }
        });

        seachByLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        seachByLabel.setText("Enter Customer ID : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(amountPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(searchedKeyText)
                            .addComponent(searchByCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(seachByLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(amountPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchByCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(seachByLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchedKeyText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if(table.getSelectedRow() != -1){
           if(amountTv.getText().equals("")){
               AlertMessage msg = new AlertMessage("Please specify the amount paid");
           }else if(!Helper.priceFormatCheck(amountTv.getText())){
               AlertMessage msg = new AlertMessage("Paid amount is not in correct format");
           }else{
               String id = table.getValueAt(table.getSelectedRow(), 0).toString();
               updateValueInDuesFile(id, amountTv.getText()); 
           }
       }else{
           AlertMessage msg = new AlertMessage("Please select the customer who paid you");
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int t = table.getSelectedRow();
        if(t==-1) {
            AlertMessage msg = new AlertMessage("Please select the customer !!");
            return;
        }
        String d = changeDateTv.getText();
        if(d.equals("") || !Helper.isInt(d)){
            AlertMessage msg = new AlertMessage("Please enter the no. of days in correct format");
        }else{
           String id = table.getValueAt(t, 0).toString();
           updateDate(id, d);
           
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void searchByComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByComboActionPerformed
        
    }//GEN-LAST:event_searchByComboActionPerformed

    private void searchByComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchByComboItemStateChanged
        if(searchByCombo.getSelectedIndex() ==0 ){
            seachByLabel.setText("Enter Customer ID");
        }else if(searchByCombo.getSelectedIndex() ==1 ){
            seachByLabel.setText("Enter Customer Name");
        } else if(searchByCombo.getSelectedIndex() ==2 ){
            seachByLabel.setText("Enter Address / village name");
        } else if(searchByCombo.getSelectedIndex() ==3 ){
            seachByLabel.setText("Enter Customer Mobile No.");
        } 
    }//GEN-LAST:event_searchByComboItemStateChanged

    private void searchedKeyTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchedKeyTextKeyReleased
        
        table.setFont(new java.awt.Font("Tahoma", 0, 14));
        table.setForeground(new java.awt.Color(0, 0, 0));
        
        String s = searchedKeyText.getText().toString();
        if(searchByCombo.getSelectedIndex() ==0 ){
            searchById(s);
        }else if(searchByCombo.getSelectedIndex() ==1 ){
            searchByName(s);
        } else if(searchByCombo.getSelectedIndex() ==2 ){
            searchByAddress(s);
        } else if(searchByCombo.getSelectedIndex() ==3 ){
            searchByPhone(s);
        } 
        if (duesModel.getRowCount() == 0) {
            duesModel.addRow(new Object[]{"", "No match found"});
            Font f = new Font("Tahoma", Font.BOLD, 18);
            table.setFont(f);
            table.setForeground(new java.awt.Color(255, 102, 153));
        }
    }//GEN-LAST:event_searchedKeyTextKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String disc = discAmtField.getText().toString();
        
        if(table.getSelectedRow()==-1){
            AlertMessage msg = new AlertMessage("Please select a customer to apply the function !!");
            return;
        }
        
        if(disc.equals("") || !Helper.priceFormatCheck(disc)){
            AlertMessage msg = new AlertMessage("Please provide discount amount in correct format..");
        }else{
            float dues=0;
            float discount=0;
            String id = table.getValueAt(table.getSelectedRow(), 0).toString();
            JSONArray mainArray = JsonClass.getJsonArray(Constants.DUES_FILE);
             JSONArray newArray = new JSONArray();
            for(int i=0;i<mainArray.size();i++){
                JSONObject obj = (JSONObject) mainArray.get(i);
                String gotId = obj.get("customerId").toString();
                if(gotId.equals(id)){
                    dues  = Float.parseFloat(obj.get("dues").toString());
                    discount = Float.parseFloat(disc);
                    obj.replace("dues", dues - discount);
                    newArray.add(obj);

                }else{
                    newArray.add(obj);
                }
            }
            FileWriter.write(Constants.DUES_FILE, newArray.toString());
            duesModel.setNumRows(0);
            fillTable();
            discAmtField.setText("");
            AlertMessage msg = new AlertMessage("<html>Amount of Rs. "+disc+" has been deducted from dues as discount !!<br>Remaining Amount is Rs. "+(dues-discount)+"</html>");
        }
         
    }//GEN-LAST:event_jButton3ActionPerformed

    public void updateDuesList(String id, float amt){
        JSONArray newArray = new JSONArray();
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        for(int i=0;i<array.size();i++){
            JSONObject obj = (JSONObject) array.get(i);
            String gotId = obj.get("customerId").toString();
            if(gotId.equals(id)){
                float dues  = Float.parseFloat(obj.get("dues").toString());
                obj.replace("dues", dues - amt);
                newArray.add(obj);
                
            }else{
                newArray.add(obj);
            }
        }
        FileWriter.write(Constants.DUES_FILE, newArray.toString());
    }
    private void searchById(String s){
        duesModel.setNumRows(0);
        JSONArray mainArray = JsonClass.getJsonArray(Constants.DUES_FILE);
        for(int i=0;i<mainArray.size();i++){
            JSONObject obj = (JSONObject) mainArray.get(i);
            String id = obj.get("customerId").toString();
            
            if(id.trim().toLowerCase().startsWith(s.trim().toLowerCase())){
                String dues = obj.get("dues").toString();
                String cId = obj.get("customerId").toString();
                String days = obj.get("days").toString();
                String billDate = obj.get("billDate").toString();
                String cName = obj.get("customerName").toString();

                Date dueDate = DateCompare.getDateDaysAfter(billDate, days);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String d = sdf.format(dueDate);

                duesModel.addRow(new Object[] {cId, cName, dues, billDate, d});
            }
        }
    }
    private void searchByName(String s){
        duesModel.setNumRows(0);
        JSONArray mainArray = JsonClass.getJsonArray(Constants.DUES_FILE);
        for(int i=0;i<mainArray.size();i++){
            JSONObject obj = (JSONObject) mainArray.get(i);
            String name = obj.get("customerName").toString();
            
            if(name.trim().toLowerCase().contains(s.trim().toLowerCase())){
                String dues = obj.get("dues").toString();
                String cId = obj.get("customerId").toString();
                String days = obj.get("days").toString();
                String billDate = obj.get("billDate").toString();
                String cName = obj.get("customerName").toString();

                Date dueDate = DateCompare.getDateDaysAfter(billDate, days);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String d = sdf.format(dueDate);

                duesModel.addRow(new Object[] {cId, cName, dues, billDate, d});
            }
        }
    }
    private void searchByAddress(String s){
        duesModel.setNumRows(0);
        JSONArray array = JsonClass.getJsonArray(Constants.SALE_RECORD_FILE);
        JSONParser parser = new JSONParser();
        Object o;
        for (int i = array.size() - 1; i >= 0; i--) {
            try {
                o = parser.parse(array.get(i).toString());
                JSONArray arr = (JSONArray) o;

                o = parser.parse(arr.get(0).toString());
                JSONObject jobj = (JSONObject) o;
                String custId = jobj.get("customerID").toString();
                String custName = jobj.get("customerName").toString();
                String addr = jobj.get("customerAddress").toString();
                
                
                if (addr.toLowerCase().trim().contains(s.toLowerCase().trim())) {
                    //searchTableModel.addRow(new Object[]{custId, custName});
                    insertInTable(custId);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void searchByPhone(String s){
        duesModel.setNumRows(0);
        JSONArray array = JsonClass.getJsonArray(Constants.SALE_RECORD_FILE);
        JSONParser parser = new JSONParser();
        Object o;
        for (int i = array.size() - 1; i >= 0; i--) {
            try {
                o = parser.parse(array.get(i).toString());
                JSONArray arr = (JSONArray) o;

                o = parser.parse(arr.get(0).toString());
                JSONObject jobj = (JSONObject) o;
                String custId = jobj.get("customerID").toString();
                String custName = jobj.get("customerName").toString();
                String phone = jobj.get("customerPhone").toString();
                if (phone.toLowerCase().trim().startsWith(s.toLowerCase().trim())) {
                    insertInTable(custId);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void insertInTable(String id){
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String cId = obj.get("customerId").toString();
            if(cId.equals(id)){
                String dues = obj.get("dues").toString();
                String days = obj.get("days").toString();
                String billDate = obj.get("billDate").toString();
                String cName = obj.get("customerName").toString();

                Date dueDate = DateCompare.getDateDaysAfter(billDate, days);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String d = sdf.format(dueDate);

                duesModel.addRow(new Object[] {cId, cName, dues, billDate, d});
            }
        }
    }
    
    public void updateDate(String id, String d){
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        JSONArray newArray = new JSONArray();
        for(int i=0;i<array.size();i++){
            JSONObject obj = (JSONObject) array.get(i);
            String cId = obj.get("customerId").toString();
           
            if(cId.equals(id)){
                obj.put("days", Integer.parseInt(d));
                newArray.add(obj);
                AlertMessage msg = new AlertMessage("No. of days updated to "+d+" days from the billing date.");
            }else{
                newArray.add(obj);
            }
        }
        FileWriter.write(Constants.DUES_FILE, newArray.toString());
    }
    public void updateValueInDuesFile(String id, String amt){
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        Main.updateHistory(Float.parseFloat(amt), "paid", id);
        for(int i=0;i<array.size();i++){
            JSONObject obj = (JSONObject) array.get(i);
            String cId = obj.get("customerId").toString();
        
            if(cId.equals(id)){
                float dues = Float.parseFloat(obj.get("dues").toString());
                if(Float.parseFloat(amt) >= dues || Math.abs(Float.parseFloat(amt)-dues)<1){
                    removeFromDuesList(id);
                    if(Math.abs(Float.parseFloat(amt)-dues) > 1){
                        AlertMessage msg = new AlertMessage("<html>Dues cleared..!!<br><br>Please return "+(Float.parseFloat(amt) - dues)+"</html>");
                    }else{
                        AlertMessage msg = new AlertMessage("Dues cleared..!!");
                    }
                }else{
                    updateDuesList(id, Float.parseFloat(amt));
                    AlertMessage msg = new AlertMessage("<html>Dues updated..!!<br><br>Rs "+(dues-Float.parseFloat(amt))+"  more is left as dues</html>");
                }
                duesModel.setNumRows(0);
                fillTable();
            }
        }
    }

    public void removeFromDuesList(String id){
        JSONArray newArray = new JSONArray();
        JSONArray array = JsonClass.getJsonArray(Constants.DUES_FILE);
        for(int i=0;i<array.size();i++){
            JSONObject obj = (JSONObject) array.get(i);
            String gotId = obj.get("customerId").toString();
            if(!gotId.equals(id)){
                newArray.add(obj);
            }
        }
        FileWriter.write(Constants.DUES_FILE, newArray.toString());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel amountPanel;
    private javax.swing.JTextField amountTv;
    private javax.swing.JTextField changeDateTv;
    private javax.swing.JTextField discAmtField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel seachByLabel;
    private javax.swing.JComboBox searchByCombo;
    private javax.swing.JTextField searchedKeyText;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
