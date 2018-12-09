/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zip_unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Arjun
 */
public class Unzip {
 
    public static void main(String[] args) {
        String zipFilePath = "/Users/pankaj/tmp.zip";
        
        String destDir = "/Users/pankaj/output";
        
        unzip(zipFilePath, destDir);
    }

    private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
     
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zinput = new ZipInputStream(fis);
            ZipEntry ze = zinput.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
              
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zinput.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
       
                zinput.closeEntry();
                ze = zinput.getNextEntry();
            }
            zinput.closeEntry();
            zinput.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}


