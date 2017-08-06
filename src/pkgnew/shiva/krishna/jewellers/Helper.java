/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarfraz
 */
public class Helper {

    public static boolean priceFormatCheck(String s) {
        char a[] = s.toCharArray();
        int flag = 0;
        for (int i = 0; i < s.length(); i++) {
            if (!(a[i] == '0' || a[i] == '1' || a[i] == '2' || a[i] == '3' || a[i] == '4' || a[i] == '5' || a[i] == '6' || a[i] == '7' || a[i] == '8' || a[i] == '9' || a[i] == '.')) {
                flag = 1;
            }
        }
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (a[i] == '.') {
                c++;
            }
            if (c > 1) {
                return false;
            }
        }
        if (flag == 1) {
            return false;
        }
        return true;
    }

    public static String choseDir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            //System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            return chooser.getSelectedFile().toString();
        } else {
            //System.out.println("No Selection ");
            return "cancelled";
        }
    }

    public static boolean testInet(String site) {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress(site, 80);
        try {
            sock.connect(addr, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    public static String verifyKeyFromServer(String k, String date) throws MalformedURLException, UnsupportedEncodingException, IOException {
        String response = "";

        URL url = new URL(Constants.ACTIVATION_LINK);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("key", k);
        params.put("expiryDate", date + " 00:00");
        params.put("action", "activation");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;) {
            response += (char) c;
        }

        return response;
    }

    public static String getExpiryDate() {/*throws MalformedURLException, UnsupportedEncodingException, IOException{
         String response="";
        
         URL url = new URL("http://localhost/java/getDate.php");
         Map<String,Object> params = new LinkedHashMap<>();
         params.put("id", id);
         params.put("action", "expiryDate");
        
         StringBuilder postData = new StringBuilder();
         for (Map.Entry<String,Object> param : params.entrySet()) {
         if (postData.length() != 0) postData.append('&');
         postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
         postData.append('=');
         postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
         }
         byte[] postDataBytes = postData.toString().getBytes("UTF-8");

         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
         conn.setDoOutput(true);
         conn.getOutputStream().write(postDataBytes);

         Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

         for (int c; (c = in.read()) >= 0;){
         response+= (char)c;
         }*/


        String date = "01/01/1950 00:00";
        File filePath = new File(Constants.APPLICATION_DATA);

        if (filePath.exists()) {
            JSONParser parser = new JSONParser();

            JSONArray array = JsonClass.getJsonArray(Constants.APPLICATION_DATA);

            try {
                Object o = parser.parse(array.get(1).toString());
                JSONObject obj = (JSONObject) o;
                date = obj.get("date").toString();
            } catch (ParseException ex) {
                Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return date;
    }

    public static String updateLink(int source) {
        String response = Constants.ACTIVATION_LINK;
        try {

            URL url = new URL(Constants.UPDATE_LINK);
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("action", "update");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            for (int c; (c = in.read()) >= 0;) {
                response += (char) c;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public static String getPurchasePrice(String stockID) {
        String r = "0";

        JSONParser parser = new JSONParser();
        JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);

        for (int i = 0; i < array.size(); i++) {
            try {
                Object o = parser.parse(array.get(i).toString());
                JSONObject obj = (JSONObject) o;
                String id = obj.get("id").toString();
                if (id.equals(stockID)) {
                    r = obj.get("purchasePrice").toString();
                }
            } catch (ParseException ex) {
                Logger.getLogger(SearchSale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return r;
    }

    public static String getRemainingQuantity(String stockID) {
        String r = "0";
        JSONParser parser = new JSONParser();
        JSONArray array = JsonClass.getJsonArray(Constants.STOCK_FILE);

        for (int i = 0; i < array.size(); i++) {
            try {
                Object o = parser.parse(array.get(i).toString());
                JSONObject obj = (JSONObject) o;
                String id = obj.get("id").toString();
                if (id.equals(stockID)) {
                    r = obj.get("remainingPieces").toString();
                }
            } catch (ParseException ex) {
                Logger.getLogger(SearchSale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return r;
    }

    public static String getMD5value(String s) {
        String t = "";
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(s.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            t = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String getFilePath() {
        String path = "C:\\Shop";
        JSONArray array = JsonClass.getJsonArray(Constants.DB_FOLDER_PATH);
        JSONParser parser = new JSONParser();
        try {
            if (array.size() > 0) {
                Object o = parser.parse(array.get(0).toString());
                JSONObject obj = (JSONObject) o;
                path = obj.get("db").toString();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }

    public static boolean isInt(String s) {
        char c[] = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(c[i])) {
                return false;
            }
        }
        return true;
    }

    public static String sendMessageToDeveloper(String name, String phone, String email, String msg) throws MalformedURLException, UnsupportedEncodingException, ProtocolException, IOException {
        String response = "";

        URL url = new URL(Constants.SEND_TO_DEV_LINK);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("name", name);
        params.put("phone", phone);
        params.put("msg", msg);
        params.put("email", email);
        params.put("action", "sendToDev");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;) {
            response += (char) c;
        }

        return response;
    }
}
