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
import java.awt.Color;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarfraz
 */
public class SaleItem extends javax.swing.JFrame {

    String myPhone = "";
    String myTin = "";
    float vatPercentage = 1;
    float oldMetalCost = 0;
    float oldMetalCost2 = 0;
 
    JSONArray array = new JSONArray();
    float finalBill = 0;
    int index;
    boolean noItem = false;
    DefaultTableModel tableModel = new DefaultTableModel();

    DefaultTableModel stockItemModel;
    String dispUnit[];
    String availableQty[];
    String availableTotalWeight[];
    String weight;
    String salePrice[];
    String itemCarret[];
    String stockID[];
    String s[];
    String selectedId;
    int selected = 0;
    float adv=0;
    float duesA=0;

    //new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" })
    public SaleItem() {
        initComponents();
        this.getContentPane().setBackground(java.awt.Color.decode("#0041C2"));
        oldMetalPanel.setVisible(false);

        //dicountText.setVisible(false);
        discountField.setEnabled(false);
        //advText.setVisible(false);
        advInput.setEnabled(false);
        duesInp.setEnabled(false);
        JSONArray parray = JsonClass.getJsonArray(Constants.PREFERENCES_FILE);
        for (int i = 0; i < parray.size(); i++) {
            JSONParser parser = new JSONParser();
            try {
                Object o = parser.parse(parray.get(i).toString());
                JSONObject obj = (JSONObject) o;
                myPhone = obj.get("phone").toString();
                myTin = obj.get("tin").toString();
                vatPercentage = 0; //Float.parseFloat(obj.get("vat").toString()); 
            } catch (ParseException ex) {
                Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.println(myPhone+"    "+myTin+"end");

        File filePath = new File(Constants.STOCK_FILE);
        if (filePath.exists()) {
            JSONParser parser = new JSONParser();

            JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);
            s = new String[array.size()];
            dispUnit = new String[array.size()];
            availableQty = new String[array.size()];      //remaining  
            availableTotalWeight = new String[array.size()];
            salePrice = new String[array.size()];
            itemCarret = new String[array.size()];
            stockID = new String[array.size()];

            for (int i = 0; i < array.size(); i++) {
                try {
                    Object o = parser.parse(array.get(i).toString());
                    JSONObject obj = (JSONObject) o;
                    //model.addRow(new Object[] {obj.get("id"),obj.get("name"),obj.get("purchasePrice"),obj.get("salePrice"), obj.get("unit")});
                    s[i] = obj.get("name").toString();

                    dispUnit[i] = obj.get("unit").toString();
                    availableQty[i] = obj.get("remainingPieces").toString();
                    availableTotalWeight[i] = obj.get("remainingWeight").toString();
                    salePrice[i] = obj.get("salePrice").toString();
                    itemCarret[i] = obj.get("carret").toString();
                    stockID[i] = obj.get("id").toString();
                } catch (ParseException ex) {
                    Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
                }

                //model.addRow(new Object[] {"one","two","three","four","five"});
            }
            //model = new DefaultComboBoxModel(s);
            //availableTv.setText(availableTotalWeight[index] + " " + dispUnit[index] + " (" + availableQty[index] + " pcs)");
            // salePriceField.setText(salePrice[index]);
            //unitTv.setText(dispUnit[index]);
            unitTv.setText("Unit");
            unitSaleField.setText("/ unit");
            labourCostFieldUnit.setText("/ unit");
            SaleItemCarret.setText("");
            fillItemTable();

        } else {
            //String s[] = {"No item in the stock"};
            noItem = true;

        }

        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (jTable2.getSelectedRow() != -1) {
                    itemNameTv.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString());
                    selected = 1;
                    String selectedId = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
                    saleItemQuantity.setText("");
                    weightField.setText("");
                    LabourCostField.setText("");

                    //index = SaleItemName.getSelectedIndex();
                    for (int i = 0; i < stockID.length; i++) {
                        if (stockID[i].equals(selectedId)) {
                            index = i;
                            break;
                        }
                    }

                    if (new File(Constants.STOCK_FILE).exists()) {
                        //availableTv.setText(availableQty[index]);
                        availableTv.setText(availableTotalWeight[index] + " " + dispUnit[index] + " (" + availableQty[index] + " pcs)");
                        salePriceField.setText(salePrice[index]);
                        unitTv.setText(dispUnit[index]);
                        unitSaleField.setText("/ " + dispUnit[index]);
                        labourCostFieldUnit.setText("/ " + dispUnit[index]);
                        SaleItemCarret.setText(itemCarret[index]);
                        //setWeight(selectedId);
                    }
                }
            }
        });
        setVisible(true);
    }

    public void setWeight(String id) {
        JSONArray array = JsonClass.getJsonArray(Constants.PURCHASE_RECORD_FILE);
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String cid = obj.get("stockID").toString();
            if (cid.equals(id)) {
                weight = obj.get("quantity").toString();
                //weightTv.setText(weight);
            }
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
        availableTv = new javax.swing.JLabel();
        salePriceField = new javax.swing.JTextField();
        BillDate = new datechooser.beans.DateChooserCombo();
        unitTv = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        SaleItemCarret = new javax.swing.JTextField();
        unitSaleField = new javax.swing.JLabel();
        LabourCostField = new javax.swing.JTextField();
        labourCostFieldUnit = new javax.swing.JLabel();
        saleItemQuantity = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        itemNameTv = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        weightField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        CustomerNameField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        CustomerPhoneField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        CustomerAddField = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        finalBillTv = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        totalItemTv = new javax.swing.JLabel();
        discountCombo = new javax.swing.JCheckBox();
        discountField = new javax.swing.JTextField();
        dicountText = new javax.swing.JLabel();
        oldMetalPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        oldMetalWeight = new javax.swing.JTextField();
        oldMetalRate = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        purityPerc = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        oldMetalWeight2 = new javax.swing.JTextField();
        oldMetalRate2 = new javax.swing.JTextField();
        purityPerc2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        oldMetalName = new javax.swing.JCheckBox();
        oldMetalName2 = new javax.swing.JCheckBox();
        oldMetalCheckBox = new javax.swing.JCheckBox();
        advInput = new javax.swing.JTextField();
        advText = new javax.swing.JLabel();
        advanceCombo = new javax.swing.JCheckBox();
        duesCheckBox = new javax.swing.JCheckBox();
        dicountText1 = new javax.swing.JLabel();
        duesInp = new javax.swing.JTextField();

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
        setLocation(new java.awt.Point(270, 20));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sale Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Date");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Item Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Pieces");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Available Quantity");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Sale Price");

        availableTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        availableTv.setText("0");

        salePriceField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        salePriceField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salePriceFieldKeyPressed(evt);
            }
        });

        BillDate.setCalendarPreferredSize(new java.awt.Dimension(350, 250));
        BillDate.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));

        unitTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        unitTv.setText("N/A");

        addBtn.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        addBtn.setText("Add Item To Bill");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        addBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addBtnKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Labout Cost/Unit");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Carret");

        SaleItemCarret.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SaleItemCarret.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaleItemCarretKeyPressed(evt);
            }
        });

        unitSaleField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        unitSaleField.setText("/ Unit");

        LabourCostField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LabourCostField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabourCostFieldKeyPressed(evt);
            }
        });

        labourCostFieldUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labourCostFieldUnit.setText("/ Unit");

        saleItemQuantity.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saleItemQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saleItemQuantityKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Weight");

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        stockItemModel = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock Id", "Item Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        jTable2.setModel(stockItemModel);
        jTable2.setRowHeight(40);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(150);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        itemNameTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        itemNameTv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemNameTvKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemNameTvKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("pieces");

        weightField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        weightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                weightFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(addBtn)
                                .addGap(30, 30, 30))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(availableTv, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel18))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(SaleItemCarret, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BillDate, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                        .addComponent(itemNameTv)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(weightField))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(saleItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(unitTv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(LabourCostField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(unitSaleField, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                        .addComponent(labourCostFieldUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BillDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(itemNameTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(SaleItemCarret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(saleItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(unitTv, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(availableTv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(unitSaleField)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(LabourCostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labourCostFieldUnit))
                .addGap(18, 18, 18)
                .addComponent(addBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bill Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        tableModel = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr No.", "Item Name", "Weight","Pieces", "Caret", "Price/Unit",  "Labout Cost / Unit", "Total"
            }
        ){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false,  false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        jTable1.setModel(tableModel);
        jTable1.setRowHeight(50);
        jTable1.setFont(new Font("Tahoma",Font.BOLD,16));
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
        }
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel8.setText("Customer's Details");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Name      ");

        CustomerNameField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CustomerNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CustomerNameFieldKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Phone No.  ");

        CustomerPhoneField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CustomerPhoneField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CustomerPhoneFieldKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Address      ");

        CustomerAddField.setColumns(20);
        CustomerAddField.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        CustomerAddField.setRows(5);
        CustomerAddField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CustomerAddFieldKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(CustomerAddField);

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel11.setText("Total Amount :  Rs. ");

        finalBillTv.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        finalBillTv.setText("0.00");

        jButton2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel10.setText("Total Item :");

        totalItemTv.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        totalItemTv.setText("0");

        discountCombo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        discountCombo.setText("Discount");
        discountCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                discountComboItemStateChanged(evt);
            }
        });

        discountField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        dicountText.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dicountText.setText("Discount Amount : ");

        oldMetalPanel.setBackground(new java.awt.Color(153, 153, 255));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Weight            ");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Rate");

        oldMetalWeight.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oldMetalWeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldMetalWeightKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                oldMetalWeightKeyReleased(evt);
            }
        });

        oldMetalRate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oldMetalRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldMetalRateKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("gram");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("/ gram");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Purity %");

        purityPerc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        purityPerc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                purityPercKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Metal Name       ");

        oldMetalWeight2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oldMetalWeight2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldMetalWeight2KeyPressed(evt);
            }
        });

        oldMetalRate2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oldMetalRate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldMetalRate2KeyPressed(evt);
            }
        });

        purityPerc2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        purityPerc2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                purityPerc2KeyPressed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("gram");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("/ gram");

        oldMetalName.setText("Gold");
        oldMetalName.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                oldMetalNameStateChanged(evt);
            }
        });

        oldMetalName2.setText("Silver");
        oldMetalName2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                oldMetalName2StateChanged(evt);
            }
        });

        javax.swing.GroupLayout oldMetalPanelLayout = new javax.swing.GroupLayout(oldMetalPanel);
        oldMetalPanel.setLayout(oldMetalPanelLayout);
        oldMetalPanelLayout.setHorizontalGroup(
            oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(oldMetalPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(oldMetalPanelLayout.createSequentialGroup()
                        .addComponent(oldMetalWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(oldMetalPanelLayout.createSequentialGroup()
                        .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(oldMetalPanelLayout.createSequentialGroup()
                                .addComponent(oldMetalRate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(oldMetalPanelLayout.createSequentialGroup()
                                .addComponent(purityPerc, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel24))
                            .addComponent(oldMetalName))
                        .addGap(25, 25, 25)
                        .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(oldMetalPanelLayout.createSequentialGroup()
                                .addComponent(oldMetalName2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(oldMetalPanelLayout.createSequentialGroup()
                                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(purityPerc2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(oldMetalRate2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(oldMetalWeight2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        oldMetalPanelLayout.setVerticalGroup(
            oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(oldMetalPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(oldMetalName)
                    .addComponent(oldMetalName2))
                .addGap(24, 24, 24)
                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldMetalWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(oldMetalWeight2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel27)
                    .addComponent(oldMetalRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(oldMetalRate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(oldMetalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel28)
                    .addComponent(purityPerc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(purityPerc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        oldMetalCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        oldMetalCheckBox.setText("Old Metal Exchange");
        oldMetalCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                oldMetalCheckBoxItemStateChanged(evt);
            }
        });
        oldMetalCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                oldMetalCheckBoxStateChanged(evt);
            }
        });

        advInput.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        advText.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        advText.setText("Amount Paid       : ");

        advanceCombo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        advanceCombo.setText("Advance");
        advanceCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                advanceComboItemStateChanged(evt);
            }
        });

        duesCheckBox.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        duesCheckBox.setText("Dues If Any");
        duesCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                duesCheckBoxItemStateChanged(evt);
            }
        });

        dicountText1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dicountText1.setText("Dues Amount     :");

        duesInp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CustomerPhoneField)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CustomerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(oldMetalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157)
                        .addComponent(oldMetalCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(advanceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(discountCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(32, 32, 32))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(duesCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(advText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dicountText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(dicountText1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(advInput, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(duesInp, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(discountField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(finalBillTv, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(26, 26, 26)
                                    .addComponent(totalItemTv, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1044, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(oldMetalCheckBox))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(CustomerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(CustomerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oldMetalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(advInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(advText)
                            .addComponent(advanceCombo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dicountText)
                            .addComponent(discountCombo))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(duesInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dicountText1)
                                    .addComponent(duesCheckBox)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(finalBillTv))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(totalItemTv))))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        String billDate = BillDate.getText();
        //String itemName = SaleItemName.getSelectedItem().toString();
        String itemName = itemNameTv.getText();
        String itemCarret = SaleItemCarret.getText();
        String quantity = saleItemQuantity.getText();
        String salePrice = salePriceField.getText();
        String labourCost = LabourCostField.getText();
        String weight = weightField.getText();

        //
        int sn = tableModel.getRowCount() + 1;

        if (noItem) {
            AlertMessage msg = new AlertMessage("No Item available in the stock");
        } else if (billDate.equals("")) {
            AlertMessage msg = new AlertMessage("Please select a suitable date");
        } else if (itemName.equals("") || selected == 0) {
            AlertMessage msg = new AlertMessage("Please select the product from list");
        } else if (itemCarret.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Carret of the product");
        } else if (quantity.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the no.of pieces of the product");
        } else if (weight.equals("") || !Helper.priceFormatCheck(weight)) {
            AlertMessage msg = new AlertMessage("Weight format is not acceptable");
        } else if (salePrice.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Sale Price of the product");
        } else if (labourCost.equals("")) {
            AlertMessage msg = new AlertMessage("Please Enter the Labour Cost of the product");
        } else if (!Helper.priceFormatCheck(salePrice)) {
            AlertMessage msg = new AlertMessage("Sale Price Format is not correct");
        } else if (!Helper.priceFormatCheck(labourCost)) {
            AlertMessage msg = new AlertMessage("Labour Cost Format is not correct");
        } else if (!Helper.isInt(quantity)) {
            AlertMessage msg = new AlertMessage("Quantity Format is not correct, 'pieces' should be an integer");
        } else {
            float reqWt = Float.parseFloat(weight);
            float reqPc = Float.parseFloat(quantity);
            if (Float.parseFloat(availableTotalWeight[index]) < reqWt || Float.parseFloat(availableQty[index])  < reqPc) {
                AlertMessage msg = new AlertMessage("Availability is less than ordered quantity");
            } else if (reqWt <= 0) {
                AlertMessage msg = new AlertMessage("Please add some weight / pieces to add item to bill");
            } else {
                float p = Float.parseFloat(salePrice);
                float l = Float.parseFloat(labourCost);
                float q = Float.parseFloat(quantity);
                float wt = Float.parseFloat(weight);
                float total = (p + l) * wt;
                finalBill += total;
                tableModel.addRow(new Object[]{Integer.toString(sn),
                    itemName,
                    weight + " " + dispUnit[index],
                    quantity,
                    itemCarret,
                    salePrice,
                    labourCost,
                    Float.toString(total)});
                //finalBillTv.setText(Float.toString(finalBill + (finalBill / 100) * vatPercentage));
                finalBillTv.setText(Float.toString(finalBill));
                totalItemTv.setText(Integer.toString(tableModel.getRowCount()));
                updateStock(quantity, weight);

                HashMap map = new HashMap();
                map.put("sn",Float.toString(sn));
                map.put("itemName", itemName);
                map.put("weight", weight);
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
                availableTv.setText("");
                weightField.setText("");
                salePriceField.setText("");
                unitTv.setText("Unit");
                unitSaleField.setText("/ " + "unit");
                labourCostFieldUnit.setText("/ " + "unit");
                SaleItemCarret.setText("");
                //weightTv.setText("");
                itemNameTv.setText("");
                LabourCostField.setText("");
                saleItemQuantity.setText("");
            }
        }
    }//GEN-LAST:event_addBtnActionPerformed

    public void addToJsonArray(HashMap map) {
        JSONObject obj = new JSONObject();
        obj.putAll(map);
        array.add(obj);

    }

    public void updateStock(String q, String wt) {
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
                    if (obj.get("id").toString().equals(stockID[index])) {
                        String r = obj.get("remainingPieces").toString();
                        float fr = Float.parseFloat(r);
                        fr -= Float.parseFloat(q);
                        String w = obj.get("remainingWeight").toString();
                        float rw = Float.parseFloat(w);
                        rw -= Float.parseFloat(wt);

                        obj.put("remainingPieces", fr);
                        obj.put("remainingWeight", rw);
                        availableQty[index] = Float.toString(fr);
                        availableTotalWeight[index] = Float.toString(rw);
                        //availableTv.setText(availableQty[index]);
                        availableTv.setText(availableTotalWeight[index] + " " + dispUnit[index] + " (" + availableQty[index] + " pcs)");
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
            AlertMessage msg = new AlertMessage("Please first add Items to your stock..!!");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String customerName = CustomerNameField.getText();
        String customerPhone = CustomerPhoneField.getText();
        String customerAddress = CustomerAddField.getText();
        String billDate = BillDate.getText();
        float discountAmt = 0;

        if (customerName.equals("")) {
            AlertMessage msg = new AlertMessage("Please specify Customer Name");
        } else if (customerPhone.equals("")) {
            AlertMessage msg = new AlertMessage("Please specify Customer's Phone No.");
        } else if (customerAddress.equals("")) {
            AlertMessage msg = new AlertMessage("Please specify Customer's Address");
        } else if (tableModel.getRowCount() < 1) {
            AlertMessage msg = new AlertMessage("No Item in the Bill");
        } else {
            JSONArray mainArray = JsonClass.getJsonArray(Constants.SALE_RECORD_FILE);
            JSONObject obj = new JSONObject();
            obj.put("customerName", customerName);
            obj.put("customerPhone", customerPhone);
            obj.put("customerAddress", customerAddress);
            obj.put("billDate", billDate);
            //obj.put("totalAmount", finalBill * (1 + vatPercentage / 100));
            obj.put("totalAmount", finalBill);

            int custCount = mainArray.size() + 1;
            String customerUniqueId = "DKJ" + String.format("%05d", custCount);
            obj.put("customerID", customerUniqueId);

            if (discountCombo.isSelected()) {
                if (!Helper.priceFormatCheck(discountField.getText())) {
                    AlertMessage msg = new AlertMessage("Discount amount format is not acceptable");
                } else {
                    if (discountField.getText().equals("")) {
                        obj.put("discount", Integer.toString(0));
                    } else {
                        discountAmt = Float.parseFloat(discountField.getText());
                        obj.put("discount", discountAmt);
                    }
                }
            } else {
                obj.put("discount", Integer.toString(0));
            }
            
            if(advanceCombo.isSelected()){
                String ad = advInput.getText().toString();
                if(Helper.priceFormatCheck(ad) && !ad.isEmpty()){
                    adv = Float.parseFloat(ad);
                }
            }
            if(duesCheckBox.isSelected()){
                String dd = duesInp.getText().toString();
                if(!dd.isEmpty() && Helper.priceFormatCheck(dd)){
                    duesA = Float.parseFloat(dd);
                    Main.writeOnDuesFile(customerUniqueId, customerName, duesA, 10);
                }
            }

            /*array.add(customerName);
             array.add(customerPhone);
             array.add(customerAddress);*/
            //array.add(obj);
            array.add(0, obj);

            mainArray.add(array);

            FileWriter.write(Constants.SALE_RECORD_FILE, mainArray.toString());
            jButton2.setEnabled(false);

            if (oldMetalCheckBox.isSelected()) {
                if(oldMetalName.isSelected()){
                    String name = oldMetalName.getText().toString();
                    String wt = oldMetalWeight.getText();
                    String rate = oldMetalRate.getText();
                    String pPerc = purityPerc.getText();

                   

                    if (wt.equals("")) {
                        //AlertMessage msg = new AlertMessage("Please enter the weight of the Gold as old metal");
                    } else if (rate.equals("")) {
                        //AlertMessage msg = new AlertMessage("Please enter the rate of the Gold as old metal");
                    } else if (pPerc.equals("")) {
                        //AlertMessage msg = new AlertMessage("Please enter the purity Percentage of Gold as old metal");
                    } else if (!Helper.priceFormatCheck(wt)) {
                       // AlertMessage msg = new AlertMessage("Weight of Gold as the old metal is not acceptable");
                    } else if (!Helper.priceFormatCheck(rate)) {
                        //AlertMessage msg = new AlertMessage("Rate of Gold as old metal is not acceptable");
                    } else if (!Helper.priceFormatCheck(pPerc)) {
                       // AlertMessage msg = new AlertMessage("Purity Percentage of Gold as old metal is not acceptable format");
                    } else {
                        oldMetalCost = Float.parseFloat(wt) * (Float.parseFloat(pPerc) / 100) * Float.parseFloat(rate);
                        writeToOldMetalFile(customerName, customerUniqueId, name, wt, rate, pPerc, oldMetalCost);  
                    }
                }
                
                if(oldMetalName2.isSelected()){
                    String name = oldMetalName2.getText().toString();
                    String wt = oldMetalWeight2.getText();
                    String rate = oldMetalRate2.getText();
                    String pPerc = purityPerc2.getText();


                    if (wt.equals("")) {
                        //AlertMessage msg = new AlertMessage("Please enter the weight of Silver as old metal");
                    } else if (rate.equals("")) {
                        //AlertMessage msg = new AlertMessage("Please enter the rate of Silver as old metal");
                    } else if (pPerc.equals("")) {
                        //AlertMessage msg = new AlertMessage("Please enter the purity Percentage of Silver as old metal");
                    } else if (!Helper.priceFormatCheck(wt)) {
                        //AlertMessage msg = new AlertMessage("Weight of Silver as old metal is not acceptable");
                    } else if (!Helper.priceFormatCheck(rate)) {
                        //AlertMessage msg = new AlertMessage("Rate of Silver as old metal is not acceptable");
                    } else if (!Helper.priceFormatCheck(pPerc)) {
                        //AlertMessage msg = new AlertMessage("Purity Percentage of Silver as old metal is not acceptable format");
                    } else {
                        oldMetalCost2 = Float.parseFloat(wt) * (Float.parseFloat(pPerc) / 100) * Float.parseFloat(rate);
                        writeToOldMetalFile(customerName, customerUniqueId, name, wt, rate, pPerc, oldMetalCost2);  
                    }
                }
                
                
            }
                entryOnHistoryFile(customerName, customerPhone, customerUniqueId, customerAddress);

                printTheBill(array, customerName, customerPhone, customerUniqueId, discountAmt);
            

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public void writeToOldMetalFile(String cname, String cid, String mname, String mwt, String mrate, String purity, float mcost) {
        JSONArray array = JsonClass.getJsonArray(Constants.OLD_METAL_FILE);
        JSONObject obj = new JSONObject();
        obj.put("customerId", cid);
        obj.put("customerName", cname);
        obj.put("metalName", mname);
        obj.put("metalWeight", mwt);
        obj.put("metalRate", mrate);
        obj.put("metalPurity", purity);
        obj.put("unit", "gram");
        obj.put("totalCost", Float.toString(mcost));
        array.add(obj);
        FileWriter.write(Constants.OLD_METAL_FILE, array.toString());
    }

    public void entryOnHistoryFile(String name, String phone, String id, String address) {
        JSONArray mainArray = JsonClass.getJsonArray(Constants.TRANS_HISTORY_FILE);
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("customerName", name);
        obj.put("customerPhone", phone);
        obj.put("customerAddress", address);
        obj.put("customerId", id);
        array.add(obj);
        mainArray.add(array);

        FileWriter.write(Constants.TRANS_HISTORY_FILE, mainArray.toString());
    }
    private void discountComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_discountComboItemStateChanged
        if (discountCombo.isSelected()) {
            //dicountText.setVisible(true);
            discountField.setEnabled(true);
            discountField.requestFocus();
        } else {
            //dicountText.setVisible(false);
            discountField.setEnabled(false);
        }
    }//GEN-LAST:event_discountComboItemStateChanged

    private void itemNameTvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameTvKeyReleased
        availableTv.setText("0");
        salePriceField.setText("");
        unitTv.setText("Unit");
        unitSaleField.setText("/ " + "unit");
        labourCostFieldUnit.setText("/ " + "unit");
        SaleItemCarret.setText("");
        //weightTv.setText("");
        String name = itemNameTv.getText();
        updateItemTable(name);
        selected = 0;
    }//GEN-LAST:event_itemNameTvKeyReleased

    private void oldMetalCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_oldMetalCheckBoxStateChanged
     
    }//GEN-LAST:event_oldMetalCheckBoxStateChanged

    private void oldMetalCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_oldMetalCheckBoxItemStateChanged
        if (oldMetalCheckBox.isSelected()) {
            oldMetalPanel.setVisible(true);
            oldMetalWeight.setEnabled(false);
            oldMetalRate.setEnabled(false);
            purityPerc.setEnabled(false);
            oldMetalName.setSelected(false);
            
            oldMetalWeight2.setEnabled(false);
            oldMetalRate2.setEnabled(false);
            purityPerc2.setEnabled(false);
            oldMetalName2.setSelected(false);
        } else {
            oldMetalPanel.setVisible(false);
        }
    }//GEN-LAST:event_oldMetalCheckBoxItemStateChanged

    private void oldMetalNameStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_oldMetalNameStateChanged
        if(oldMetalName.isSelected()){
            oldMetalWeight.setEnabled(true);
            oldMetalRate.setEnabled(true);
            purityPerc.setEnabled(true);
        }else{
            oldMetalWeight.setEnabled(false);
            oldMetalRate.setEnabled(false);
            purityPerc.setEnabled(false);
        }
    }//GEN-LAST:event_oldMetalNameStateChanged

    private void oldMetalName2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_oldMetalName2StateChanged
         if(oldMetalName2.isSelected()){
            oldMetalWeight2.setEnabled(true);
            oldMetalRate2.setEnabled(true);
            purityPerc2.setEnabled(true);
        }else{
            oldMetalWeight2.setEnabled(false);
            oldMetalRate2.setEnabled(false);
            purityPerc2.setEnabled(false);
        }
    }//GEN-LAST:event_oldMetalName2StateChanged
    
    AlertMessage msg;
    private void oldMetalWeightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldMetalWeightKeyReleased
        /*String wt = oldMetalWeight.getText().toString(); 
        int flag=0;
        for (char c : wt.toCharArray())
            {
                if (!Character.isDigit(c) && c!='.') flag = 1;
            }
        if(flag==1) oldMetalWeight.setBackground(Color.red);
        else oldMetalWeight.setBackground(Color.white);*/
    }//GEN-LAST:event_oldMetalWeightKeyReleased

    private void advanceComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_advanceComboItemStateChanged
        if(advanceCombo.isSelected()){
            //advText.setVisible(true);
            advInput.setEnabled(true);
            advInput.requestFocus();
        }else{
            //advText.setVisible(false);
            advInput.setEnabled(false);
        }
    }//GEN-LAST:event_advanceComboItemStateChanged

    private void itemNameTvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameTvKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            itemNameTv.transferFocus();
        }
    }//GEN-LAST:event_itemNameTvKeyPressed

    private void SaleItemCarretKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaleItemCarretKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            SaleItemCarret.transferFocus();
        }        
    }//GEN-LAST:event_SaleItemCarretKeyPressed

    private void saleItemQuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saleItemQuantityKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            saleItemQuantity.transferFocus();
        }     // TODO add your handling code here:
    }//GEN-LAST:event_saleItemQuantityKeyPressed

    private void weightFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weightFieldKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            weightField.transferFocus();
        } 
    }//GEN-LAST:event_weightFieldKeyPressed

    private void salePriceFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salePriceFieldKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            salePriceField.transferFocus();
        } 
    }//GEN-LAST:event_salePriceFieldKeyPressed

    private void LabourCostFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabourCostFieldKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            LabourCostField.transferFocus();
        } 
    }//GEN-LAST:event_LabourCostFieldKeyPressed

    private void CustomerNameFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CustomerNameFieldKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            CustomerNameField.transferFocus();
        } 
    }//GEN-LAST:event_CustomerNameFieldKeyPressed

    private void CustomerPhoneFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CustomerPhoneFieldKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            CustomerPhoneField.transferFocus();
        } 
    }//GEN-LAST:event_CustomerPhoneFieldKeyPressed

    private void CustomerAddFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CustomerAddFieldKeyPressed
       if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            CustomerAddField.transferFocus();
        } 
    }//GEN-LAST:event_CustomerAddFieldKeyPressed

    private void oldMetalWeightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldMetalWeightKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            oldMetalWeight.transferFocus();
        } 
    }//GEN-LAST:event_oldMetalWeightKeyPressed

    private void oldMetalRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldMetalRateKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            oldMetalRate.transferFocus();
        } 
    }//GEN-LAST:event_oldMetalRateKeyPressed

    private void purityPercKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purityPercKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            purityPerc.transferFocus();
        } 
    }//GEN-LAST:event_purityPercKeyPressed

    private void oldMetalWeight2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldMetalWeight2KeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            oldMetalWeight2.transferFocus();
        } 
    }//GEN-LAST:event_oldMetalWeight2KeyPressed

    private void oldMetalRate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldMetalRate2KeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            oldMetalRate2.transferFocus();
        } 
    }//GEN-LAST:event_oldMetalRate2KeyPressed

    private void purityPerc2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purityPerc2KeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            purityPerc2.transferFocus();
        } 
    }//GEN-LAST:event_purityPerc2KeyPressed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            addBtn.doClick();
        } 
    }//GEN-LAST:event_addBtnKeyPressed

    private void duesCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_duesCheckBoxItemStateChanged
        if(duesCheckBox.isSelected()){
            duesInp.setEnabled(true);
        }else{
            duesInp.setEnabled(false);
        }
    }//GEN-LAST:event_duesCheckBoxItemStateChanged

    public void updateItemTable(String keyword) {
        stockItemModel.setNumRows(0);
        for (int i = 0; i < stockID.length; i++) {
            if (s[i].toLowerCase().trim().startsWith(keyword.toLowerCase().trim())) {
                stockItemModel.addRow(new Object[]{stockID[i], s[i]});
            }
        }
    }

    public void fillItemTable() {
        if (new File(Constants.STOCK_FILE).exists()) {
            for (int i = 0; i < stockID.length; i++) {
                stockItemModel.addRow(new Object[]{stockID[i], s[i]});
            }
        }
    }

    public void printTheBill(JSONArray array, String a, String b, String id, float discAmt) {
        String path = "";
        //System.out.println(array.toString());
        JSONParser parser = new JSONParser();
        Object o;
        try {
            Document document = new Document();
            path = Helper.getFilePath() + "\\" + id + "_bill.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(path));                    //change the name of the file according to the cudtomer id
            document.open();

           
            //shopName.setAlignment(Element.ALIGN_CENTER);
            Image image;
            float[] cw = new float[]{3f, 6f};
            PdfPTable shopNameTable = new PdfPTable(2);
            shopNameTable.setWidths(cw);
            shopNameTable.setWidthPercentage(100);

            PdfPCell cl;
            try {
                image = Image.getInstance(Constants.SHOP_LOGO_URL);
                image.scaleAbsolute(40, 40);
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
            Paragraph shopName = new Paragraph(Constants.SHOP_NAME, FontFactory.getFont(FontFactory.TIMES_BOLD, 22, Font.BOLD, BaseColor.BLACK));
            cl = new PdfPCell(shopName);
            
            cl.setHorizontalAlignment(Element.ALIGN_LEFT);
            cl.setVerticalAlignment(Element.ALIGN_CENTER);
            cl.disableBorderSide(Rectangle.BOX);
            shopNameTable.addCell(cl);
            
            document.add(shopNameTable);

            //document.add(shopName);
            Paragraph address = new Paragraph("             "+Constants.SHOP_ADDRESS, FontFactory.getFont(FontFactory.TIMES, 14, Font.ITALIC, BaseColor.BLACK));
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
            PdfPCell cel;
            
            /*if (!myTin.equals("")) {
                cel = new PdfPCell(new Phrase("TIN/VAT - " + myTin, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            } else {
                cel = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            }
            cel.setHorizontalAlignment(Element.ALIGN_LEFT);
            cel.disableBorderSide(Rectangle.BOX);
            tbl.addCell(cel);*/
            cel = new PdfPCell(new Phrase("Date - " + getDateFormat() + "    " + getTimeFormat(BillDate.getSelectedDate().getTime()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbl.addCell(cel);
            
            if (myPhone.equals("")) {
                cel = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            } else {
                cel = new PdfPCell(new Phrase("Phone : " + myPhone, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            }
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cel);
            /*Paragraph p = new Paragraph("Date - " + getDateFormat() + "    " + getTimeFormat(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10));
             Paragraph p1 = new Paragraph("Phone : 1234567890", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10));
             Phrase t = new Phrase();*/
            

           /* cel = new PdfPCell();
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cel);*/

            /*if (myPhone.equals("")) {
                cel = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            } else {
                cel = new PdfPCell(new Phrase("Phone : " + myPhone, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
            }
            cel.disableBorderSide(Rectangle.BOX);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbl.addCell(cel);*/

            document.add(tbl);

            try {
                //adding customer details
                o = parser.parse(array.get(0).toString());
                JSONObject obj = (JSONObject) o;

                Paragraph cid = new Paragraph("Customer Id -             " + id, FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(cid);
                Paragraph cname = new Paragraph("Name -                      " + obj.get("customerName"), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(cname);
                Paragraph cphone = new Paragraph("Phone -                     " + obj.get("customerPhone"), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(cphone);
                Paragraph caddress = new Paragraph("Address -                  " + obj.get("customerAddress"), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
                document.add(caddress);
            } catch (ParseException ex) {
                Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            //adding horizontal line
            Paragraph p = new Paragraph();
            for (int i = 0; i < 130; i++) {
                p.add("-");
            }
            document.add(p);
            document.add(new Paragraph(" "));

            float[] columnWidths = new float[]{1f, 5f, 3f, 3f, 2f, 3f, 3f, 4f};
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(columnWidths);

            table.addCell("S No.");
            table.addCell("Item Name");
            table.addCell("Weight");
            table.addCell("Quantity");
            table.addCell("Carret");
            table.addCell("Price/gram");
            table.addCell("Labour Cost/gram");
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

                    int len = obj.get("sn").toString().length();
                    table.addCell(obj.get("sn").toString().substring(0, len-2));
                    //table.addCell(Integer.toString(i));
                    table.addCell(obj.get("itemName").toString());
                    table.addCell(obj.get("weight") + " " + obj.get("unit"));
                    table.addCell(obj.get("quantity").toString());
                    table.addCell(obj.get("carret").toString());
                    table.addCell(obj.get("salePrice").toString());
                    table.addCell(obj.get("labourCost") + "");
                    table.addCell(obj.get("total").toString());
                }
            } catch (ParseException ex) {
                Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            int oldTicked = 0;
            if (oldMetalCheckBox.isSelected()) {
                oldTicked = 1;
                if (array.size() < 16) {
                    PdfPCell c = new PdfPCell(new Paragraph("1",FontFactory.getFont(FontFactory.TIMES,10, Font.ITALIC, BaseColor.WHITE)));
                    c.setUseVariableBorders(true);
                    c.setBorderColorTop(BaseColor.WHITE);
                    c.setBorderColorBottom(BaseColor.WHITE);
                    for (int i = 0; i < 16 - array.size(); i++) {
                        //table.addCell(Integer.toString(array.size() + i));
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                    }
                }
                PdfPCell c = new PdfPCell(new Paragraph("In Exchange"));
                c.setColspan(2);
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c);
                c = new PdfPCell(new Paragraph("Weight"));
                table.addCell(c);
                c = new PdfPCell(new Paragraph("Rate"));
                table.addCell(c);
                c = new PdfPCell(new Paragraph("Purity"));
                table.addCell(c);
                c = new PdfPCell();
                table.addCell(c);
                table.addCell(c);
                table.addCell(c);

                if(oldMetalName.isSelected()){
                    c = new PdfPCell(new Paragraph(oldMetalName.getText().toString()));
                    c.setColspan(2);
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(oldMetalWeight.getText() + " gram"));
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(oldMetalRate.getText() + " / gram"));
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(purityPerc.getText() + " %"));
                    table.addCell(c);
                    c = new PdfPCell();
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph("Cost "));
                    c.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(Float.toString(oldMetalCost)));
                    table.addCell(c);
                    finalBill -= oldMetalCost;
                }
                
                if(oldMetalName2.isSelected()){
                    c = new PdfPCell(new Paragraph(oldMetalName2.getText().toString()));
                    c.setColspan(2);
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(oldMetalWeight2.getText() + " gram"));
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(oldMetalRate2.getText() + " / gram"));
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(purityPerc2.getText() + " %"));
                    table.addCell(c);
                    c = new PdfPCell();
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph("Cost "));
                    c.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(c);
                    c = new PdfPCell(new Paragraph(Float.toString(oldMetalCost2)));
                    table.addCell(c);
                    finalBill -= oldMetalCost2;
                }
                
                
            } else {
                if (array.size() < 16) {
                    
                    PdfPCell c = new PdfPCell(new Paragraph("1",FontFactory.getFont(FontFactory.TIMES,10, Font.ITALIC, BaseColor.WHITE)));
                    c.setUseVariableBorders(true);
                    c.setBorderColorTop(BaseColor.WHITE);
                    c.setBorderColorBottom(BaseColor.WHITE);
                    for (int i = 0; i < 16 - array.size(); i++) {
                        //table.addCell(Integer.toString(array.size() + i));
                        table.addCell(c);                        
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                        table.addCell(c);
                    }
                }
            }

            /*table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("VAT");*/
            PdfPCell cell;
            /*PdfPCell cell = new PdfPCell(new Paragraph("VAT     "));
            cell.setColspan(7);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);*/
            
            //table.addCell(Float.toString((finalBill / 100) * vatPercentage));
            

            /*table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");*/
            if (discountCombo.isSelected()) {
                cell = new PdfPCell(new Paragraph("Discount     "));
                cell.setColspan(7);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                table.addCell(Float.toString(discAmt));
            }
            
            if(adv>0){
                cell = new PdfPCell(new Paragraph("Advanve Amount Paid Earlier     "));
                cell.setColspan(7);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                table.addCell(Float.toString(adv));
            }

            /*table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");
             table.addCell("");*/
            cell = new PdfPCell(new Paragraph("To Pay     "));
            cell.setColspan(7);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            //table.addCell(Float.toString(finalBill + (finalBill / 100) * vatPercentage - discAmt));
            table.addCell(Float.toString(finalBill - discAmt - adv));
            
            if(duesA>0){
                cell = new PdfPCell(new Paragraph("Dues     "));
                cell.setColspan(7);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                table.addCell(Float.toString(duesA));
            }
            
            
            document.add(table);
            Main.updateHistory(finalBill - discAmt - adv - duesA, "paid", id);
            
             //advance amount
            /*if(adv>0){
                Paragraph advance = new Paragraph("Advanve Amount Paid Earlier : Rs. "+adv , FontFactory.getFont(FontFactory.TIMES, 12, Font.ITALIC, BaseColor.BLACK));
                advance.setAlignment(Element.ALIGN_LEFT);
                document.add(advance);
            }*/

            //adding terms and conditions
            File file = new File(Constants.TERMS_AND_CONDITIONS_FILE);
            if (file.exists()) {
                document.add(new Paragraph("Terms and Conditions : ", FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
                document.add(new Paragraph(FileWriter.read(Constants.TERMS_AND_CONDITIONS_FILE), FontFactory.getFont(FontFactory.TIMES_ITALIC, 10)));
            }

            Paragraph sign = new Paragraph("Authorized Signature", FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
            sign.setAlignment(Element.ALIGN_RIGHT);
            document.add(sign);

            Paragraph msg = new Paragraph("THANKS FOR VISIT", FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
            msg.setAlignment(Element.ALIGN_CENTER);
            document.add(msg);
            
            //adding horizontal line
            Paragraph p1 = new Paragraph();
            for (int i = 0; i < 130; i++) {
                p1.add("-");
            }
            document.add(p1);
            
            Paragraph p2 = new Paragraph("This is a Computer Generated Invoice.",FontFactory.getFont(FontFactory.TIMES, 8, Font.ITALIC, BaseColor.BLACK));
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            Paragraph p3 = new Paragraph("Powered By : shopeasyweb.tk",FontFactory.getFont(FontFactory.TIMES, 12, Font.ITALIC, BaseColor.BLACK));   
            p3.setAlignment(Element.ALIGN_RIGHT);
            document.add(p3);
            
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        printWithPrinter(path);

        //Main.notifTv.setText("<html>Amount to pay :<br>      "+finalBill+"</html>");
        Main.setCustDetails(a, id);
        //Main.setBillAmt(finalBill + (finalBill / 100) * vatPercentage, discAmt);
        Main.setBillAmt(finalBill,adv, discAmt);

        dispose();
    }

    public String getDateFormat() {
        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        return y.format(date);
    }

    public String getTimeFormat(Date b) {

        SimpleDateFormat y = new SimpleDateFormat("hh:mm a");
        //Date date = Calendar.getInstance().getTime();
        return y.format(b);
    }
    static PDDocument document;
    public void printWithPrinter(String path) {
        try {
            //The desktop api can help calling other applications in our machine
            //and also many other features...
            /*Desktop desktop = Desktop.getDesktop();
            try {
            //desktop.print(new File("DocXfile.docx"));
            desktop.print(new File(path));
            } catch (IOException e) {
            e.printStackTrace();
            }*/
            document = PDDocument.load(new File(path));
            //get the printer name
            /*File f = new File(Constants.PRINTER_NAME_FILE);
            String s="";
            if(f.exists()){
                s = FileWriter.read(Constants.PRINTER_NAME_FILE).trim();
            }*/
            //PrintService myPrintService = findPrintService(s);
            findPrintService();
            
            
        } catch (IOException ex) {
            Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void printNow(int printerIndex){
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setPrintService(list.get(printerIndex));
            job.print();
        } catch (PrinterException ex) {
            Logger.getLogger(SaleItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static List<PrintService> list= new ArrayList<PrintService>();
    private static void findPrintService() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
         
        for (PrintService printService : printServices) {
            list.add(printService);
            
            /*if (printService.getName().trim().equals(printerName)) {
                return printService;
            }*/
        }
        //System.out.println(list);
        Printer p = new Printer(list,1);
        
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo BillDate;
    private javax.swing.JTextArea CustomerAddField;
    private javax.swing.JTextField CustomerNameField;
    private javax.swing.JTextField CustomerPhoneField;
    private javax.swing.JTextField LabourCostField;
    private javax.swing.JTextField SaleItemCarret;
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField advInput;
    private javax.swing.JLabel advText;
    private javax.swing.JCheckBox advanceCombo;
    private javax.swing.JLabel availableTv;
    private javax.swing.JLabel dicountText;
    private javax.swing.JLabel dicountText1;
    private javax.swing.JCheckBox discountCombo;
    private javax.swing.JTextField discountField;
    private javax.swing.JCheckBox duesCheckBox;
    private javax.swing.JTextField duesInp;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel finalBillTv;
    private javax.swing.JTextField itemNameTv;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labourCostFieldUnit;
    private javax.swing.JCheckBox oldMetalCheckBox;
    private javax.swing.JCheckBox oldMetalName;
    private javax.swing.JCheckBox oldMetalName2;
    private javax.swing.JPanel oldMetalPanel;
    private javax.swing.JTextField oldMetalRate;
    private javax.swing.JTextField oldMetalRate2;
    private javax.swing.JTextField oldMetalWeight;
    private javax.swing.JTextField oldMetalWeight2;
    private javax.swing.JTextField purityPerc;
    private javax.swing.JTextField purityPerc2;
    private javax.swing.JTextField saleItemQuantity;
    private javax.swing.JTextField salePriceField;
    private javax.swing.JLabel totalItemTv;
    private javax.swing.JLabel unitSaleField;
    private javax.swing.JLabel unitTv;
    private javax.swing.JTextField weightField;
    // End of variables declaration//GEN-END:variables
}
