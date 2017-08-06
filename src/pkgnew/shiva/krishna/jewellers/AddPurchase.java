/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
public class AddPurchase extends javax.swing.JFrame {

    /**
     * Creates new form AddPurchase
     */
    // = { "Item 1", "Item 2", "Item 3", "Item 4" };
    DefaultComboBoxModel model;// = new javax.swing.DefaultComboBoxModel(s);
    int index;
    //String c[] = {"contracter 1","contracter 2"};
    DefaultComboBoxModel contractorModel;
    DefaultTableModel tableModel;
    String stockID[];
    String purchasePrice[];
    String salePrice[];
    String dispUnit[];
    boolean noItem = false;
    String s[];
    int flag = 0;
    int selected = 0;

    public AddPurchase() {

        // defining model for item combo box
        this.getContentPane().setBackground(java.awt.Color.decode("#0041C2"));
        File filePath = new File(Constants.STOCK_FILE);
        if (filePath.exists()) {
            JSONParser parser = new JSONParser();
            JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);
            s = new String[array.size()];
            stockID = new String[array.size()];
            purchasePrice = new String[array.size()];
            salePrice = new String[array.size()];
            dispUnit = new String[array.size()];

            for (int i = 0; i < array.size(); i++) {
                try {
                    Object o = parser.parse(array.get(i).toString());
                    JSONObject obj = (JSONObject) o;
                    //model.addRow(new Object[] {obj.get("id"),obj.get("name"),obj.get("purchasePrice"),obj.get("salePrice"), obj.get("unit")});
                    s[i] = obj.get("name").toString();
                    stockID[i] = obj.get("id").toString();
                    purchasePrice[i] = obj.get("purchasePrice").toString();
                    salePrice[i] = obj.get("salePrice").toString();
                    dispUnit[i] = obj.get("unit").toString();
                    //purchasePriceField.setText(purchasePrice[i]);

                } catch (ParseException ex) {
                    Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
                }

                //model.addRow(new Object[] {"one","two","three","four","five"});
            }

        } else {
            noItem = true;

        }

        //defining model for contractor combo box
        File contractorFilepath = new File(Constants.CONTRACTOR_FILE);
        if (contractorFilepath.exists()) {
            JSONParser parser = new JSONParser();

            JSONArray array = JsonClass.getJsonArray(Constants.CONTRACTOR_FILE);
            String s[] = new String[array.size()];

            for (int i = 0; i < array.size(); i++) {
                try {
                    Object o = parser.parse(array.get(i).toString());
                    JSONObject obj = (JSONObject) o;
                    //model.addRow(new Object[] {obj.get("id"),obj.get("name"),obj.get("purchasePrice"),obj.get("salePrice"), obj.get("unit")});
                    s[i] = obj.get("name").toString();
                } catch (ParseException ex) {
                    Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
                }

                //model.addRow(new Object[] {"one","two","three","four","five"});
            }
            contractorModel = new DefaultComboBoxModel(s);

        } else {
            String s[] = {"No Contractor"};
            contractorModel = new DefaultComboBoxModel(s);
        }

        initComponents();
        if (filePath.exists()) {
            purchasePriceField.setText(purchasePrice[0]);
            salePriceField.setText(salePrice[0]);
            unitTv.setText(dispUnit[0]);
            pPriceUnitTv.setText("/ " + dispUnit[0]);
            sPriceUnitTv.setText("/ " + dispUnit[0]);

            if (s.length == 0) {
                tableModel.addRow(new Object[]{"", "No Item in the stock"});
            } else {
                for (int i = 0; i < s.length; i++) {
                    tableModel.addRow(new Object[]{stockID[i], s[i]});

                }
            }

        }

        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (jTable1.getSelectedRow() != -1) {
                    mainTextField.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                    selected = 1;
                    String selectedId = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                    updateDetails(selectedId);
                }
            }
        });
        setVisible(true);
    }

    public void updateDetails(String selectedId) {

        quantity.setText("");
        purchasePercTv.setText("");
        MotiTv.setText("");
        fineMetalWtTv.setText("");
        //fineMetalNameTv.setText("");

        for (int i = 0; i < stockID.length; i++) {
            if (stockID[i].equals(selectedId)) {
                index = i;
                break;
            }
        }

        unitTv.setText(dispUnit[index]);
        pPriceUnitTv.setText("/ " + dispUnit[index]);
        sPriceUnitTv.setText("/ " + dispUnit[index]);
        purchasePriceField.setText(purchasePrice[index]);
        salePriceField.setText(salePrice[index]);

        JSONArray array = JsonClass.getJsonArray(Constants.PURCHASE_RECORD_FILE);
        for (int i = array.size() - 1; i >= 0; i--) {
            JSONObject obj = (JSONObject) array.get(i);
            String cid = obj.get("stockID").toString();
            if (cid.equals(selectedId)) {
                String wt = obj.get("quantity").toString();
                String purchPerc = obj.get("purchasePerc").toString();
                String motiWt = obj.get("motiWeight").toString();
                String metalWt = obj.get("fineMetalWeight").toString();
                String metalName = obj.get("fineMetalName").toString();

                quantity.setText(wt);
                purchasePercTv.setText(purchPerc);
                MotiTv.setText(motiWt);
                fineMetalWtTv.setText(metalWt);
                //fineMetalNameTv.setText(metalName);
                break;
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

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        contractorName = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        purchaseDate = new datechooser.beans.DateChooserCombo();
        jLabel6 = new javax.swing.JLabel();
        purchasePriceField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        salePriceField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        unitTv = new javax.swing.JLabel();
        pPriceUnitTv = new javax.swing.JLabel();
        sPriceUnitTv = new javax.swing.JLabel();
        mainTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        piecesTv = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        purchasePercTv = new javax.swing.JTextField();
        MotiTv = new javax.swing.JTextField();
        fineMetalWtTv = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        fineMetalNameTv = new javax.swing.JComboBox();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        jLabel8.setText("jLabel8");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Purchase(s)");
        setLocation(new java.awt.Point(400, 100));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Purchase Item Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Date");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Product ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Weight / Qty");

        quantity.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Contractor");

        contractorName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        contractorName.setModel(contractorModel);

        jButton1.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jButton1.setText("Save Item");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        purchaseDate.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    purchaseDate.setCalendarPreferredSize(new java.awt.Dimension(350, 250));
    purchaseDate.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));

    jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel6.setText("Purchase Price");

    purchasePriceField.setEditable(false);
    purchasePriceField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

    jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel7.setText("Sale Price");

    salePriceField.setEditable(false);
    salePriceField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

    jButton2.setText("Change");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    jButton3.setText("Change");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });

    unitTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    unitTv.setText("unit");

    pPriceUnitTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    pPriceUnitTv.setText("/ unit");

    sPriceUnitTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    sPriceUnitTv.setText("/ unit");

    mainTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    mainTextField.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            mainTextFieldKeyReleased(evt);
        }
    });

    tableModel = new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Product Id", "Product Name"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    };
    jTable1.setModel(tableModel);
    jTable1.setRowHeight(40);
    jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
    jTable1.getTableHeader().setReorderingAllowed(false);
    jScrollPane1.setViewportView(jTable1);
    if (jTable1.getColumnModel().getColumnCount() > 0) {
        jTable1.getColumnModel().getColumn(0).setMinWidth(150);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(150);
    }

    jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel9.setText("Pieces");

    piecesTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

    jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel10.setText("Piece");

    jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jLabel11.setText("Additional Informations");

    jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel12.setText("Purchase % ");

    jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel13.setText("Moti & Stone Weight ");

    jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel14.setText("Fine Metal Weight");

    purchasePercTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

    MotiTv.setFont(MotiTv.getFont().deriveFont((float)18));
    MotiTv.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            MotiTvKeyReleased(evt);
        }
    });

    fineMetalWtTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    fineMetalWtTv.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            fineMetalWtTvKeyReleased(evt);
        }
    });

    jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel15.setText("Fine Metal Name");

    fineMetalNameTv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    fineMetalNameTv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gold", "Silver", "Diamond"}));

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(28, 28, 28)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(purchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(mainTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(piecesTv, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(unitTv, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(purchasePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(pPriceUnitTv, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2))
                                .addComponent(contractorName, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(sPriceUnitTv, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton3))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(86, 86, 86)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(purchasePercTv, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(MotiTv, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(fineMetalWtTv, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(fineMetalNameTv, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGap(203, 203, 203)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(128, 128, 128)))))
                    .addGap(0, 8, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(22, 22, 22))))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jLabel1))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(unitTv)
                            .addComponent(jLabel4))
                        .addComponent(purchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jLabel3))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(mainTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(piecesTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel9))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(contractorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGap(15, 15, 15)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(purchasePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pPriceUnitTv)
                        .addComponent(jButton2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sPriceUnitTv)
                        .addComponent(jButton3))
                    .addGap(18, 18, 18)
                    .addComponent(jLabel11)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(purchasePercTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(MotiTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(fineMetalWtTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(fineMetalNameTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(32, 32, 32)
                    .addComponent(jButton1)
                    .addGap(83, 83, 83))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap(35, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(26, 26, 26))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(22, 22, 22)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(24, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String date = purchaseDate.getText();
        //String name = itemName.getSelectedItem().toString();
        String name = mainTextField.getText();
        String qty = quantity.getText();
        String contractor = contractorName.getSelectedItem().toString();
        String pp = purchasePriceField.getText();
        String sp = salePriceField.getText();
        String pieces = piecesTv.getText();
        String purchPerc = purchasePercTv.getText();
        String motiWt = MotiTv.getText();
        String fineMetalWt = fineMetalWtTv.getText();
        String fineMetalName = fineMetalNameTv.getSelectedItem().toString();

        if (selected == 0) {
            AlertMessage msg = new AlertMessage("No Item Selected");
        } else if (date.equals("")) {
            AlertMessage msg = new AlertMessage("Please provide suitable date");
        } else if (qty.equals("")) {
            AlertMessage msg = new AlertMessage("Please provide suitable quantity");
        } else if (pieces.equals("") || !Helper.isInt(pieces)) {
            AlertMessage msg = new AlertMessage("Please provide no. of pieces in correct format");
        } else if (pp.equals("")) {
            AlertMessage msg = new AlertMessage("Please provide purchase price");
        } else if (sp.equals("")) {
            AlertMessage msg = new AlertMessage("Please provide sale price");
        } else if (!Helper.priceFormatCheck(pp)) {
            AlertMessage msg = new AlertMessage("Purchase price format is not acceptable");
        } else if (!Helper.priceFormatCheck(sp)) {
            AlertMessage msg = new AlertMessage("Sale Price format is not acceptable");
        } else if (!Helper.priceFormatCheck(qty)) {
            AlertMessage msg = new AlertMessage("Quantity Format is not correct..");
        } else if (!Helper.priceFormatCheck(motiWt) || motiWt.equals("")) {
            AlertMessage msg = new AlertMessage("Moti & Stone Weight Format is not correct..");
        } else if (!Helper.priceFormatCheck(fineMetalWt) || fineMetalWt.equals("")) {
            AlertMessage msg = new AlertMessage("Fine Metal Weight Format is not correct..");
        } else if (Float.parseFloat(motiWt) > Float.parseFloat(qty) || Float.parseFloat(fineMetalWt) > Float.parseFloat(qty)) {
            AlertMessage msg = new AlertMessage("Fine Metal Weight or Moti Weight can't be greater than weight itself..");
        } else {

            JSONArray array = JsonClass.getJsonArray(Constants.PURCHASE_RECORD_FILE);

            int id = array.size() + 1;
            /*HashMap map = new HashMap();
             map.put("stockID", stockID[index]);
             map.put("unit", dispUnit[index]);
             map.put("id", id);
             map.put("date", date);
             map.put("name", name);
             map.put("quantity", qty);
             map.put("contractor", contractor);
             map.put("purchasePrice", pp);
             map.put("salePrice", sp);*/

            JSONObject obj = new JSONObject();
            obj.put("stockID", stockID[index]);
            obj.put("unit", dispUnit[index]);
            obj.put("id", id);
            obj.put("date", date);
            obj.put("name", name);
            obj.put("quantity", qty);
            obj.put("contractor", contractor);
            obj.put("purchasePrice", pp);
            obj.put("salePrice", sp);
            obj.put("pieces", pieces);
            obj.put("motiWeight", motiWt);
            obj.put("purchasePerc", purchPerc);
            obj.put("fineMetalWeight", fineMetalWt);
            obj.put("fineMetalName", fineMetalName);

            //String newRow = JsonClass.addPurchaseEncoding(map);
            array.add(obj);
            FileWriter.write(Constants.PURCHASE_RECORD_FILE, array.toString());
            AlertMessage msg = new AlertMessage("Item ' " + name + " ' added to record successfully..");

            addToStock(obj);
            super.dispose();

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void addToStock(JSONObject map) {
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
                    if (obj.get("id").toString().equals(map.get("stockID"))) {
                        String r = obj.get("remainingPieces").toString();
                        String w = obj.get("remainingWeight").toString();
                        float fr = Float.parseFloat(r);
                        fr += Float.parseFloat(map.get("pieces").toString());

                        float rw = Float.parseFloat(w);
                        rw += Float.parseFloat(map.get("quantity").toString());

                        obj.put("remainingPieces", fr);
                        obj.put("remainingWeight", rw);
                        obj.put("purchasePrice", map.get("purchasePrice"));
                        obj.put("salePrice", map.get("salePrice"));
                        obj.put("fineMetalName", map.get("fineMetalName"));
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
            AlertMessage msg = new AlertMessage("Please first add Item to your stock..!!");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        purchasePriceField.setEditable(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        salePriceField.setEditable(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mainTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mainTextFieldKeyReleased
        String keyword = mainTextField.getText();
        updateTable(keyword);
    }//GEN-LAST:event_mainTextFieldKeyReleased

    private void MotiTvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MotiTvKeyReleased
        if (Helper.priceFormatCheck(quantity.getText()) && Helper.priceFormatCheck(MotiTv.getText()) && !quantity.getText().equals("") && !MotiTv.getText().equals("")) {
            float wt = Float.parseFloat(quantity.getText());
            float motiWt = Float.parseFloat(MotiTv.getText());
            if (wt >= motiWt) {
                fineMetalWtTv.setText(Float.toString(wt - motiWt));
            }
        }
    }//GEN-LAST:event_MotiTvKeyReleased

    private void fineMetalWtTvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fineMetalWtTvKeyReleased
        if (Helper.priceFormatCheck(quantity.getText()) && Helper.priceFormatCheck(fineMetalWtTv.getText()) && !quantity.getText().equals("") && !fineMetalWtTv.getText().equals("")) {
            float wt = Float.parseFloat(quantity.getText());
            float metalWt = Float.parseFloat(fineMetalWtTv.getText());
            if (wt >= metalWt) {
                MotiTv.setText(Float.toString(wt - metalWt));
            }
        }
    }//GEN-LAST:event_fineMetalWtTvKeyReleased

    public void updateTable(String keyword) {
        selected = 0;
        tableModel.setNumRows(0);
        flag = 0;
        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        if (new File(Constants.STOCK_FILE).exists()) {
            for (int i = 0; i < s.length; i++) {
                if (s[i].toLowerCase().startsWith(keyword.toLowerCase())) {
                    flag = 1;
                    tableModel.addRow(new Object[]{stockID[i], s[i]});
                }
            }
        }
        if (flag == 0) {
            tableModel.addRow(new Object[]{"", "No match found"});

            Font f = new Font("Tahoma", Font.BOLD, 18);
            jTable1.setFont(f);
            jTable1.setForeground(new java.awt.Color(255, 102, 153));
        }
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MotiTv;
    private javax.swing.JComboBox contractorName;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private javax.swing.JComboBox fineMetalNameTv;
    private javax.swing.JTextField fineMetalWtTv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField mainTextField;
    private javax.swing.JLabel pPriceUnitTv;
    private javax.swing.JTextField piecesTv;
    private datechooser.beans.DateChooserCombo purchaseDate;
    private javax.swing.JTextField purchasePercTv;
    private javax.swing.JTextField purchasePriceField;
    private javax.swing.JTextField quantity;
    private javax.swing.JLabel sPriceUnitTv;
    private javax.swing.JTextField salePriceField;
    private javax.swing.JLabel unitTv;
    // End of variables declaration//GEN-END:variables

}
