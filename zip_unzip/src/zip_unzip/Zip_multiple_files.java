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
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Arjun
 */
public class Zip_multiple_files {
    public static void main(final String[] args) throws IOException {
        final List<String> srcFiles = Arrays.asList("test1.txt", "test2.txt");
        final FileOutputStream fos = new FileOutputStream("compressed.zip");
        final ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (final String srcFile : srcFiles) {
            final File fileToZip = new File(srcFile);
            final FileInputStream fis = new FileInputStream(fileToZip);
            final ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            final byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }

}
