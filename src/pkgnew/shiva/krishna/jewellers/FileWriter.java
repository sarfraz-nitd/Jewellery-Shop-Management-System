/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sarfraz
 */
public class FileWriter {
    
    public static void write(String path, String s){
        try{
            File filePath = new File(path);
            filePath.getParentFile().mkdirs();
            FileOutputStream os = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            Writer w = new BufferedWriter(osw);
            w.write(s);
            w.close();
           
        }catch(IOException e){
            System.err.println("Problem writing to the file ");
        }
    }
    
    public static String read(String path){
        StringBuilder sb=null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            sb = new StringBuilder();
            String line = br.readLine();
            
            while(line!= null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String s = sb.toString();
        return s;
    }
    
}
