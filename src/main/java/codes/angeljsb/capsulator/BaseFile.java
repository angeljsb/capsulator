/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

/**
 * Clase base que representa un archivo. Se diferencia de java.io.File por el
 * hecho de que no puede ser un directorio y tiene varias funciones especificas
 * para archivos
 *
 * @author Angel
 */
public class BaseFile extends File {

    /**
     * Crea un objeto que apunta a la ruta pasada, así sea absoluta o relativa
     *
     * @param path La ruta del archivo
     */
    public BaseFile(String path) {
        super(path);
        if (new File(path).isDirectory()) {
            throw new FileTypeException("BaseFile no puede ser un directorio");
        }
    }

    /**
     * Crea un objeto que apunta al archivo hijo de un directorio padre
     *
     * @param parent La ruta del directorio donde se encuentra el archivo a
     * controlar
     * @param child El nombre del archivo hijo del directorio {@code parent}
     */
    public BaseFile(String parent, String child) {
        super(parent, child);
        if (new File(parent, child).isDirectory()) {
            throw new FileTypeException("BaseFile no puede ser un directorio");
        }
    }

    /**
     * Crea un objeto que apunta al archivo hijo de un directorio padre
     *
     * @param parent El directorio padre
     * @param child El nombre del archivo hijo del directorio padre
     */
    public BaseFile(File parent, String child) {
        super(parent, child);
        if (new File(parent, child).isDirectory()) {
            throw new FileTypeException("BaseFile no puede ser un directorio");
        }
    }

    /**
     * Crea un objeto base file a partir del URI del archivo
     *
     * @param uri El URI del archivo
     */
    public BaseFile(URI uri) {
        super(uri);
        if (new File(uri).isDirectory()) {
            throw new FileTypeException("BaseFile no puede ser un directorio");
        }
    }

    /**
     * Crea un objeto base file para un archivo File
     *
     * @param file El archivo
     */
    public BaseFile(File file) {
        super(file.getParent(), file.getName());
        if (file.isDirectory()) {
            throw new FileTypeException("BaseFile no puede ser un directorio");
        }
    }

    /**
     * Verifica si la extensión del archivo coincide con alguno de los formatos
     * solicitados.<br>
     * El formato puede ser pasado con el punto inicial <code>.png</code> o sin
     * este <code>png</code>, archivos de dicha extensión coincidirán de
     * cualquiera de los dos modos.<br>
     * El comodín {@code *} siendo pasado en el arreglo de formatos, provocará
     * que siempre se retorne true
     *
     * @param formatos Los formatos de archivo buscados
     * @return Si la extensión del archivo coincide con alguno de los formatos
     */
    public boolean compareFormat(String... formatos) {
        String suffix = this.getSuffix();
        for (String formato : formatos) {
            if (formato.equals("*") || formato.equals(".*")) {
                return true;
            }
            String puntuado = formato.startsWith(".")
                    ? formato
                    : ".".concat(formato);
            if (suffix.equalsIgnoreCase(puntuado)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el sufijo/formato del archivo.
     *
     * @return El sufijo del archivo. Es decir, el texto después del último
     * punto (.)
     */
    public String getSuffix() {
        int index = this.getName().lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return this.getName().substring(index);
    }

    /**
     * Obtiene un InputStream para leer el archivo
     *
     * @return un Input stream para leer este archivo o null si el archivo no se
     * puede leer
     * @throws FileNotFoundException Si el archivo no existe
     */
    public InputStream getInputStream() throws FileNotFoundException {
        if (!this.canRead()) {
            return null;
        }
        return new FileInputStream(this);
    }

    /**
     * Obtiene un OutputStream para escribir el archivo
     *
     * @return un output stream para escribir este archivo o null si el archivo
     * no se puede escribir
     * @throws FileNotFoundException Si el archivo no existe
     */
    public OutputStream getOutputStream() throws FileNotFoundException {
        if (!this.canWrite()) {
            return null;
        }
        return new FileOutputStream(this);
    }

    /**
     * Obtiene el reader para leer este archivo
     *
     * @return Un reader para leer el archivo o null si el archivo no se puede
     * leer
     * @throws FileNotFoundException Si el archivo no existe
     */
    public BufferedReader getReader() throws FileNotFoundException {
        InputStream is = this.getInputStream();
        if (is == null) {
            return null;
        }
        return new BufferedReader(new InputStreamReader(is));
    }

    /**
     * Obtiene el writer para escribir este archivo
     *
     * @return Un writer para escribir el archivo o null si el archivo no se
     * puede escribir
     * @throws FileNotFoundException Si el archivo no existe
     */
    public BufferedWriter getWriter() throws FileNotFoundException {
        OutputStream os = this.getOutputStream();
        if (os == null) {
            return null;
        }
        return new BufferedWriter(new OutputStreamWriter(os));
    }

    @Override
    public boolean mkdirs() {
        System.err.println("Operación no disponible para este tipo de "
                + "archivo");
        return false;
    }

    @Override
    public boolean mkdir() {
        System.err.println("Operación no disponible para este tipo de "
                + "archivo");
        return false;
    }

    @Override
    public File[] listFiles(FileFilter filter) {
        return null;
    }

    @Override
    public File[] listFiles(FilenameFilter filter) {
        return null;
    }

    @Override
    public File[] listFiles() {
        return null;
    }

    @Override
    public String[] list(FilenameFilter filter) {
        return null;
    }

    @Override
    public String[] list() {
        return null;
    }

    /**
     * Los archivos manejados por esta clase no pueden ser directorios.
     *
     * @return false
     */
    @Override
    public boolean isDirectory() {
        return false;
    }

    /**
     * Abre el archivo en el programa por defecto para abrir este tipo de
     * archivos según el sistema operativo
     */
    public void open() {
        try {
            Desktop.getDesktop().open(this);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Abre el archivo en el programa por defecto para editar este tipo de
     * archivo
     */
    public void openInEditor() {
        try {
            Desktop.getDesktop().edit(this);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    /**
     * Abre el archivo en el navegador por defecto
     */
    public void openInBrowser() {
        try {
            Desktop.getDesktop().browse(this.toURI());
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

}
