/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import codes.angeljsb.capsulator.BaseFile;

/**
 * Clase con funciones estaticas para manejar archivos de texto
 *
 * @author Angel
 */
public class TextLoader {

    /**
     * Lee todo el texto en un archivo
     *
     * @param file El archivo a leer
     * @return El texto en el archivo
     */
    public static String read(BaseFile file) {
        try (BufferedReader reader = file.getReader()) {
            String total = "";
            String line = reader.readLine();
            while (line != null) {
                total += line;
                line = reader.readLine();
            }
            return total;
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Escribe un string en un archivo
     *
     * @param file El archivo a escribir
     * @param txt El texto a escribir en el archivo
     */
    public static void write(BaseFile file, String txt) {
        try (BufferedWriter writer = file.getWriter()) {
            writer.write(txt);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

}
