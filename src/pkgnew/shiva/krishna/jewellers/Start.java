/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarfraz
 */
public class Start {

    static String path;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       path = System.getProperty("user.home");
        //try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Activation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Activation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Activation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Activation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
            //</editor-fold>

        String expiryDate = "01/01/1950 00:00";

        File f = new File(Constants.APPLICATION_DATA);
        JSONArray array = JsonClass.getJsonArray(Constants.APPLICATION_DATA);
        JSONParser parser = new JSONParser();
        if (f.exists() && array.size() > 0) {
            try {
                Object o = parser.parse(array.get(0).toString());
                JSONObject obj = (JSONObject) o;
                expiryDate = obj.get("aaaaa").toString();
            } catch (ParseException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }

            expiryDate = decode(expiryDate);
            System.out.println(expiryDate);

                //String expiryDate = Helper.getExpiryDate();
            //System.out.print(expiryDate+"jh");
            if (DateCompare.isDatePassed(expiryDate)) {
                ExpiredDateMessage msg = new ExpiredDateMessage();
            } else {
                
                PasswordCheck pc = new PasswordCheck();
                //ManageItems mi = new ManageItems();

            }
        } else {
            
            Activation act = new Activation();
        }
        /*} catch (UnsupportedEncodingException ex) {
         Logger.getLogger(Activation.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
         Logger.getLogger(Activation.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public static String decode(String s) {
        String r = "";
        try {
            byte[] decoded;

            decoded = DatatypeConverter.parseBase64Binary(s);
            r = new String(decoded, "UTF-8");;

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Activation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
}
