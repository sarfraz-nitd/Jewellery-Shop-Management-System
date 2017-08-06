/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
public class SaleItemNew extends javax.swing.JFrame {

    String myPhone = "";
    String myTin = "";
    /**
     * Creates new form SaleItemNew
     */
    /*
     new javax.swing.table.DefaultTableModel(
     new Object [][] {

     },
     new String [] {
     "Sr No.", "Item Name", "Quantity", "Caret", "Price/Unit", "Unit Of Measurement", "Labout Cost / Unit", "Total"
     }
     )
     */
    JSONArray array = new JSONArray();
    float finalBill = 0;
    int index;
    boolean noItem = false;
    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultComboBoxModel itemModel = new DefaultComboBoxModel();
    String dispUnit[];
    String availableQty[];
    String salePrice[];
    String itemCarret[];
    String stockID[];

    //new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" })
    public SaleItemNew() {
        initComponents();
        JSONArray parray = JsonClass.getJsonArray(Constants.PREFERENCES_FILE);
        for (int i = 0; i < parray.size(); i++) {
            JSONParser parser = new JSONParser();
            try {
                Object o = parser.parse(parray.get(i).toString());
                JSONObject obj = (JSONObject) o;
                myPhone = obj.get("phone").toString();
                myTin = obj.get("tin").toString();
            } catch (ParseException ex) {
                Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        File filePath = new File(Constants.STOCK_FILE);
        if (filePath.exists()) {
            JSONParser parser = new JSONParser();

            JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);
            String s[] = new String[array.size()];
            dispUnit = new String[array.size()];
            availableQty = new String[array.size()];
            salePrice = new String[array.size()];
            itemCarret = new String[array.size()];
            stockID = new String[array.size()];

            for (int i = 0; i < array.size(); i++) {
                try {
                    Object o = parser.parse(array.get(i).toString());
                    JSONObject obj = (JSONObject) o;
                    //model.addRow(new Object[] {obj.get("id"),obj.get("name"),obj.get("purchasePrice"),obj.get("salePrice"), obj.get("unit")});
                    //s[i] = obj.get("name").toString();
                    itemModel.addElement(obj.get("name"));
                    dispUnit[i] = obj.get("unit").toString();
                    availableQty[i] = obj.get("remainingPieces").toString();
                    salePrice[i] = obj.get("salePrice").toString();
                    itemCarret[i] = obj.get("carret").toString();
                    stockID[i] = obj.get("id").toString();
                } catch (ParseException ex) {
                    Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
                }

                //model.addRow(new Object[] {"one","two","three","four","five"});
            }
            //model = new DefaultComboBoxModel(s);
            availableTv.setText(availableQty[index]);
            salePriceField.setText(salePrice[index]);
            unitTv.setText(dispUnit[index]);
            sideUnitTv.setText(dispUnit[index]);
            unitSaleField.setText("/ " + dispUnit[index]);
            labourCostFieldUnit.setText("/ " + dispUnit[index]);
            SaleItemCarret.setText(itemCarret[index]);

        } else {
            //String s[] = {"No item in the stock"};
            noItem = true;
            itemModel.addElement("No Items in the stock");
        }

        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jTextField1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        SaleItemName = new javax.swing.JComboBox();
        availableTv = new javax.swing.JLabel();
        salePriceField = new javax.swing.JTextField();
        unitTv = new javax.swing.JLabel();
        BillDate = new datechooser.beans.DateChooserCombo();
        sideUnitTv = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        CustomerNameField = new javax.swing.JTextField();
        CustomerPhoneField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        CustomerAddField = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        SaleItemCarret = new javax.swing.JTextField();
        unitSaleField = new javax.swing.JLabel();
        LabourCostField = new javax.swing.JTextField();
        labourCostFieldUnit = new javax.swing.JLabel();
        saleItemQuantity = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        totalItemTv = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        finalBillTv = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

        jTextField1.setText("jTextField1");

        jLabel19.setText("jLabel19");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sale Item");
        setLocation(new java.awt.Point(470, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sale Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel2.setText("Date");

        jLabel3.setText("Item Name");

        jLabel4.setText("Quantity");

        jLabel5.setText("Available Quantity");

        jLabel6.setText("Sale Price");

        jLabel7.setText("Unit Of Measurement");

        SaleItemName.setModel(itemModel);
        SaleItemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaleItemNameActionPerformed(evt);
            }
        });

        availableTv.setText("0");

        unitTv.setText("N/A");

        BillDate.setCalendarPreferredSize(new java.awt.Dimension(350, 250));

        sideUnitTv.setText("N/A");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel8.setText("Customer's Details");

        jLabel9.setText("Name       :");

        jLabel15.setText("Phone No.  :");

        jLabel16.setText("Address      :");

        CustomerAddField.setColumns(20);
        CustomerAddField.setRows(5);
        jScrollPane2.setViewportView(CustomerAddField);

        jButton1.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jButton1.setText("Add Item To Bill");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setText("Labout Cost/Unit");

        jLabel18.setText("Carret");

        unitSaleField.setText("/ Unit");

        labourCostFieldUnit.setText("/ Unit");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                                .addComponent(jLabel18)
                                .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(unitTv, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(SaleItemName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BillDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(SaleItemCarret, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(saleItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sideUnitTv, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                                        .addComponent(availableTv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(unitSaleField)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(LabourCostField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(labourCostFieldUnit)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(15, 15, 15)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(CustomerNameField)
                                                .addComponent(CustomerPhoneField)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(165, 165, 165))))
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(BillDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(CustomerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel3))
                                .addComponent(SaleItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel18)
                                                .addComponent(SaleItemCarret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel4)
                                                .addComponent(sideUnitTv, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(saleItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel5)
                                                .addComponent(availableTv))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel6)
                                                .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(unitSaleField))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel17)
                                                .addComponent(LabourCostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labourCostFieldUnit))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel7)
                                                .addComponent(unitTv))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel15)
                                                .addComponent(CustomerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel16)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        tableModel = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Sr No.", "Item Name", "Quantity", "Caret", "Price/Unit", "Unit Of Measurement", "Labout Cost / Unit", "Total", "Remove"
                }
        );
        jTable1.setModel(tableModel);
        jTable1.setRowHeight(40);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(60);
        }
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel10.setText("Total Item :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel11.setText("Total Amount :");

        totalItemTv.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        totalItemTv.setText("0");

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel13.setText("Rs.");

        finalBillTv.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        finalBillTv.setText("0.00");

        jButton2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton3.setText("Save As PDF");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(120, 120, 120)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(totalItemTv, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(finalBillTv, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1)
                                                .addComponent(jSeparator1))))
                        .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(totalItemTv)
                                .addComponent(jLabel11)
                                .addComponent(jLabel13)
                                .addComponent(finalBillTv))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(jButton3))
                        .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String billDate = BillDate.getText();
        String itemName = SaleItemName.getSelectedItem().toString();
        String itemCarret = SaleItemCarret.getText();
        String quantity = saleItemQuantity.getText();
        String salePrice = salePriceField.getText();
        String labourCost = LabourCostField.getText();

        int sn = tableModel.getRowCount() + 1;

        if (noItem) {
            AlertMessage msg = new AlertMessage("No Item available in the stock");
        } else if (billDate.equals("")) {
            AlertMessage msg = new AlertMessage("Please select a suitable date");
        } else if (itemName.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Name of the product");
        } else if (itemCarret.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Carret of the product");
        } else if (quantity.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the quantity of the product");
        } else if (salePrice.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Sale Price of the product");
        } else if (labourCost.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Labour Cost of the product");
        } else if (!Helper.priceFormatCheck(salePrice)) {
            AlertMessage msg = new AlertMessage("Sale Price Format is not correct");
        } else if (!Helper.priceFormatCheck(labourCost)) {
            AlertMessage msg = new AlertMessage("Labour Cost Format is not correct");
        } else if (!Helper.priceFormatCheck(quantity)) {
            AlertMessage msg = new AlertMessage("Quantity Format is not correct");
        } else if (Float.parseFloat(availableQty[index]) < Float.parseFloat(quantity)) {
            AlertMessage msg = new AlertMessage("Availability is less than ordered quantity");
        } else {
            float p = Float.parseFloat(salePrice);
            float l = Float.parseFloat(labourCost);
            float q = Float.parseFloat(quantity);
            float total = (p + l) * q;
            finalBill += total;
            tableModel.addRow(new Object[]{Integer.toString(sn),
                itemName,
                quantity,
                itemCarret,
                salePrice,
                dispUnit[index],
                labourCost,
                Float.toString(total)});
            finalBillTv.setText(Float.toString(finalBill));
            totalItemTv.setText(Integer.toString(tableModel.getRowCount()));
            updateStock(quantity);

            HashMap map = new HashMap();
            map.put("sn", sn);
            map.put("itemName", itemName);
            map.put("quantity", quantity);
            map.put("carret", itemCarret);
            map.put("salePrice", salePrice);
            map.put("unit", dispUnit[index]);
            map.put("labourCost", labourCost);
            map.put("total", total);
            map.put("stockID", stockID[index]);
            map.put("billDate", billDate);
            addToJsonArray(map);
            //writeToSaleFile(); 
        }
    }

    public void addToJsonArray(HashMap map) {
        JSONObject obj = new JSONObject();
        obj.putAll(map);
        array.add(obj);

    }

    public void updateStock(String q) {
        File filePath = new File(Constants.STOCK_FILE);
        if (filePath.exists()) {
            JSONParser parser = new JSONParser();

            JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);
            JSONArray newArray = new JSONArray();

            for (int i = 0; i < array.size(); i++) {
                try {
                    Object o = parser.parse(array.get(i).toString());
                    JSONObject obj = (JSONObject) o;
                    //model.addRow(new Object[] {obj.get("id"),obj.get("name"),obj.get("purchasePrice"),obj.get("salePrice"), obj.get("unit")});
                    if (obj.get("id").toString().equals(Integer.toString(index + 1))) {
                        String r = obj.get("remainingPieces").toString();
                        float fr = Float.parseFloat(r);
                        fr -= Float.parseFloat(q);

                        obj.put("remainingPieces", fr);
                        availableQty[index] = Float.toString(fr);
                        availableTv.setText(availableQty[index]);
                        newArray.add(obj);
                    } else {
                        newArray.add(obj);
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
                }

                //model.addRow(new Object[] {"one","two","three","four","five"});
            }
            FileWriter.write(Constants.STOCK_FILE, newArray.toString());
        } else {
            AlertMessage msg = new AlertMessage("Please firts add Items to your stock..!!");
        }
    }

    private void SaleItemNameActionPerformed(java.awt.event.ActionEvent evt) {

        saleItemQuantity.setText("");
        LabourCostField.setText("");

        index = SaleItemName.getSelectedIndex();
        if (new File(Constants.STOCK_FILE).exists()) {
            availableTv.setText(availableQty[index]);
            salePriceField.setText(salePrice[index]);
            unitTv.setText(dispUnit[index]);
            sideUnitTv.setText(dispUnit[index]);
            unitSaleField.setText("/ " + dispUnit[index]);
            labourCostFieldUnit.setText("/ " + dispUnit[index]);
            SaleItemCarret.setText(itemCarret[index]);
        }

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String customerName = CustomerNameField.getText();
        String customerPhone = CustomerPhoneField.getText();
        String customerAddress = CustomerAddField.getText();

        JSONArray mainArray = JsonClass.getJsonArray(Constants.SALE_RECORD_FILE);
        JSONObject obj = new JSONObject();
        obj.put("customerName", customerName);
        obj.put("customerPhone", customerPhone);
        obj.put("customerAddress", customerAddress);
        obj.put("billDate", BillDate.getText());
        obj.put("totalAmount", finalBill);

        /*array.add(customerName);
         array.add(customerPhone);
         array.add(customerAddress);*/
        //array.add(obj);
        array.add(0, obj);

        mainArray.add(array);
        FileWriter.write(Constants.SALE_RECORD_FILE, mainArray.toString());

        printTheBill(array, customerName, customerPhone);
    }

    public void printTheBill(JSONArray array, String a, String b) {
        String path = "";
        System.out.println(array.toString());
        JSONParser parser = new JSONParser();
        Object o;
        try {
            Document document = new Document();
            if (a.length() >= 4 && b.length() >= 4) {
                path = Helper.getFilePath() + "\\" + a.substring(0, 4) + "_" + b.substring(0, 4) + "_bill.pdf";
            } else {
                path = Helper.getFilePath() + "\\" + a + "_" + b + "_bill.pdf";
            }

            PdfWriter.getInstance(document, new FileOutputStream(path));                    //change the name of the file according to the cudtomer id
            document.open();
            Paragraph shopName = new Paragraph("New Durga Krishna Jewellers", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD, BaseColor.BLACK));
            //shopName.setAlignment(Element.ALIGN_CENTER);
            Image image;
            float[] cw = new float[]{3f, 6f};
            PdfPTable shopNameTable = new PdfPTable(2);
            shopNameTable.setWidths(cw);
            shopNameTable.setWidthPercentage(100);

            PdfPCell cl;
            try {
                image = Image.getInstance("logo.png");
                image.scaleAbsolute(55, 40);
                cl = new PdfPCell(image);
                cl.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cl.disableBorderSide(Rectangle.BOX);
                shopNameTable.addCell(cl);
                //document.add(image);

            } catch (BadElementException ex) {
                Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            cl = new PdfPCell(shopName);
            cl.setHorizontalAlignment(Element.ALIGN_LEFT);
            cl.disableBorderSide(Rectangle.BOX);
            shopNameTable.addCell(cl);

            document.add(shopNameTable);

            Paragraph address = new Paragraph("In front of Uttar Bihar Grameen Bank, Benipatti, Madhubani.", FontFactory.getFont(FontFactory.TIMES, 10, Font.ITALIC, BaseColor.BLACK));
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);

            //adding image of the shop 
            /*try {
             Image image = Image.getInstance("heading.png");
             image.scaleAbsolute(530, 50);
             image.setAlignment(Element.ALIGN_CENTER);
             document.add(image);
             } catch (BadElementException ex) {
             Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
             Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            //adding header including TIN and date
            PdfPTable tbl = new PdfPTable(2);
            tbl.setWidthPercentage(100);
            PdfPCell cel = new PdfPCell(new Phrase("TIN/VAT - " + myTin, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            cel.setHorizontalAlignment(Element.ALIGN_LEFT);
            cel.disableBorderSide(Rectangle.BOX);
            tbl.addCell(cel);

            cel = new PdfPCell(new Phrase("Date - " + getDateFormat() + "    " + getTimeFormat(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cel);

            cel = new PdfPCell();
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cel);

            cel = new PdfPCell(new Phrase("Phone : " + myPhone, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cel);

            document.add(tbl);

            try {
                //adding customer details
                o = parser.parse(array.get(0).toString());
                JSONObject obj = (JSONObject) o;

                Paragraph cname = new Paragraph("Name -                      " + obj.get("customerName"), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(cname);
                Paragraph cphone = new Paragraph("Phone -                     " + obj.get("customerPhone"), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(cphone);
                Paragraph caddress = new Paragraph("Address -                  " + obj.get("customerAddress"), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(caddress);
            } catch (ParseException ex) {
                Logger.getLogger(SaleItemNew.class.getName()).log(Level.SEVERE, null, ex);
            }

            //adding horizontal line
            Paragraph p = new Paragraph();
            for (int i = 0; i < 130; i++) {
                p.add("-");
            }
            document.add(p);
            document.add(new Paragraph(" "));

            float[] columnWidths = new float[]{1f, 5f, 3f, 2f, 3f, 3f, 4f};
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(columnWidths);

            table.addCell("S No.");
            table.addCell("Item Name");
            table.addCell("Quantity");
            table.addCell("Carret");
            table.addCell("Price");
            table.addCell("Labour Cost");
            table.addCell("Amount");

            /*PdfPCell cell = new PdfPCell(new Paragraph("Title"));
             cell.setColspan(2);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(BaseColor.GREEN);
             table.addCell(cell);*/
            try {
                for (int i = 1; i < array.size(); i++) {
                    o = parser.parse(array.get(i).toString());
                    JSONObject obj = (JSONObject) o;

                    PdfPCell pc = new PdfPCell();

                    table.addCell(obj.get("sn").toString());
                    table.addCell(obj.get("itemName").toString());
                    table.addCell(obj.get("quantity") + " " + obj.get("unit"));
                    table.addCell(obj.get("carret").toString());
                    table.addCell(obj.get("salePrice").toString());
                    table.addCell(obj.get("labourCost") + "");
                    table.addCell(obj.get("total").toString());
                }
            } catch (ParseException ex) {
                Logger.getLogger(SaleItemNew.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (array.size() < 27) {
                PdfPCell c = new PdfPCell();
                for (int i = 0; i < 27 - array.size(); i++) {
                    table.addCell(Integer.toString(array.size() + i));
                    table.addCell(c);
                    table.addCell(c);
                    table.addCell(c);
                    table.addCell(c);
                    table.addCell(c);
                    table.addCell(c);
                }
            }

            /*table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");*/
            PdfPCell cell = new PdfPCell(new Paragraph("Total "));
            cell.setColspan(6);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            table.addCell(Float.toString(finalBill));

            //table.setSpacingBefore(30);
            document.add(table);

            //adding terms and conditions
            File file = new File(Constants.TERMS_AND_CONDITIONS_FILE);
            if (file.exists()) {
                document.add(new Paragraph("Terms and Conditions : ", FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
                document.add(new Paragraph(FileWriter.read(Constants.TERMS_AND_CONDITIONS_FILE), FontFactory.getFont(FontFactory.TIMES_ITALIC, 10)));
            }

            Paragraph sign = new Paragraph("Authorized Signature", FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
            sign.setAlignment(Element.ALIGN_RIGHT);
            document.add(sign);

            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(SaleItemNew.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaleItemNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        printWithPrinter(path);
        //Main.setBillAmt(finalBill);
        dispose();

    }

    public String getDateFormat() {
        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        return y.format(date);
    }

    public String getTimeFormat() {

        SimpleDateFormat y = new SimpleDateFormat("hh:mm a");
        Date date = Calendar.getInstance().getTime();
        return y.format(date);
    }

    public void printWithPrinter(String path) {
        //The desktop api can help calling other applications in our machine
        //and also many other features...
        Desktop desktop = Desktop.getDesktop();
        try {
            //desktop.print(new File("DocXfile.docx"));
            desktop.print(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify                     
    private datechooser.beans.DateChooserCombo BillDate;
    private javax.swing.JTextArea CustomerAddField;
    private javax.swing.JTextField CustomerNameField;
    private javax.swing.JTextField CustomerPhoneField;
    private javax.swing.JTextField LabourCostField;
    private javax.swing.JTextField SaleItemCarret;
    private javax.swing.JComboBox SaleItemName;
    private javax.swing.JLabel availableTv;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel finalBillTv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labourCostFieldUnit;
    private javax.swing.JTextField saleItemQuantity;
    private javax.swing.JTextField salePriceField;
    private javax.swing.JLabel sideUnitTv;
    private javax.swing.JLabel totalItemTv;
    private javax.swing.JLabel unitSaleField;
    private javax.swing.JLabel unitTv;
    // End of variables declaration                   
}
