/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarfraz
 */
public class JsonClass {
     public static String encoding(HashMap map){
        JSONObject obj = new JSONObject();
        obj.put("id", map.get("id"));
        obj.put("name", map.get("name"));
        obj.put("purchasePrice",map.get("pp"));
        obj.put("salePrice", map.get("sp"));
        obj.put("unit", map.get("unit"));
        obj.put("carret", map.get("carret"));
        obj.put("remainingPieces", map.get("remainingPieces"));
        
        return obj.toString();
    }
     
     public static String contractorEncoding(HashMap map){
         JSONObject obj = new JSONObject();
         obj.put("id", map.get("id"));
         obj.put("name", map.get("name"));
         obj.put("phone", map.get("phone"));
         obj.put("address", map.get("address"));
         
         
         return obj.toString();
     }
     
     public static String addPurchaseEncoding(HashMap map){
         JSONObject obj = new JSONObject();
         /*obj.put("id",map.get("id"));
         obj.put("date", map.get("date"));
         obj.put("name", map.get("name"));
         obj.put("qty", map.get("qty"));
         obj.put("contractor", map.get("contractor"));
         obj.put("purchasePrice", map.get("pp"));
         obj.put("salePrice", map.get("sp"));*/
         
         obj.putAll(map);
         
         return obj.toString();
     }
     
    public static JSONArray getJsonArray(String path){
        JSONArray array=null;
        JSONParser parser = new JSONParser();
        
        File file = new File(path);
        if(!file.exists()){
            return (new JSONArray());
        }
        
        String s = FileWriter.read(path);
        if(s.equals("")){
            return (new JSONArray());
        }
        
        Object obj;
         try {
             obj = parser.parse(s);
             array = (JSONArray)obj;
        
         } catch (ParseException ex) {
             Logger.getLogger(JsonClass.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        return array;
    }
}
