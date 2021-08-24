/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;

/**
 * Una representación abstracta de la ruta de un directorio
 *
 * @author Angel
 */
public class Directory extends File {

    /**
     * Crea un objeto que apunta a la ruta pasada, así sea absoluta o relativa
     *
     * @param pathname La ruta del directorio
     */
    public Directory(String pathname) {
        super(pathname);
        if (new File(pathname).isFile()) {
            throw new FileTypeException("Las instancias de Directory deben "
                    + "apuntar a un directorio");
        }
    }

    /**
     * Crea un objeto que apunta al directorio hijo de un directorio padre
     *
     * @param parent La ruta del directorio donde se encuentra el archivo a
     * controlar
     * @param child El nombre del directorio hijo del directorio {@code parent}
     */
    public Directory(String parent, String child) {
        super(parent, child);
        if (new File(parent, child).isFile()) {
            throw new FileTypeException("Las instancias de Directory deben "
                    + "apuntar a un directorio");
        }
    }

    /**
     * Crea un objeto que apunta al directorio hijo de un directorio padre
     *
     * @param parent El directorio padre
     * @param child El nombre del directorio hijo
     */
    public Directory(File parent, String child) {
        super(parent, child);
        if (new File(parent, child).isFile()) {
            throw new FileTypeException("Las instancias de Directory deben "
                    + "apuntar a un directorio");
        }
    }

    /**
     * Crea un objeto directory a partir del URI del archivo
     *
     * @param uri El URI del directorio
     */
    public Directory(URI uri) {
        super(uri);
        if (new File(uri).isFile()) {
            throw new FileTypeException("Las instancias de Directory deben "
                    + "apuntar a un directorio");
        }
    }

    /**
     * Crea un objeto directory para un archivo File
     *
     * @param file El directorio
     */
    public Directory(File file) {
        super(file.getParent(), file.getName());
        if (file.isFile()) {
            throw new FileTypeException("Las instancias de Directory deben "
                    + "apuntar a un directorio");
        }
    }

    @Override
    public boolean canExecute() {
        return false;
    }

    @Override
    public boolean setExecutable(boolean executable) {
        return false;
    }

    @Override
    public boolean setExecutable(boolean executable, boolean ownerOnly) {
        return false;
    }

    @Override
    public boolean setReadable(boolean readable) {
        return false;
    }

    @Override
    public boolean setReadable(boolean readable, boolean ownerOnly) {
        return false;
    }

    @Override
    public boolean setWritable(boolean writable) {
        return false;
    }

    @Override
    public boolean setWritable(boolean writable, boolean ownerOnly) {
        return false;
    }

    @Override
    public boolean createNewFile() throws IOException {
        return false;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return this.exists();
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public boolean canRead() {
        return false;
    }

    /**
     * Devuelve todos los archivos que no sean carpetas en el directorio
     *
     * @return Un arreglo con los archivos del directorio
     */
    public BaseFile[] listBaseFiles() {
        return Stream.of(this.listFiles())
                .filter(file -> file.isFile())
                .map(BaseFile::new)
                .toArray(BaseFile[]::new);
    }

    /**
     * Devuelve los archivos en el directorio que coincidan con los formatos
     * recibidos
     *
     * @param formats Los formatos de los archivos a buscar
     * @return Un arreglo con todos los archivos en la carpeta que coincidan con
     * los formatos
     */
    public BaseFile[] listBaseFiles(String... formats) {
        return Stream.of(this.listFiles())
                .filter(file -> file.isFile())
                .map(BaseFile::new)
                .filter(base -> base.compareFormat(formats))
                .toArray(BaseFile[]::new);
    }

    /**
     * Devuelve todos los directorios hijos de este directorio
     *
     * @return Un arreglo con los directoros
     */
    public Directory[] listDirectories() {
        return Stream.of(this.listFiles())
                .filter(file -> file.isDirectory())
                .map(Directory::new)
                .toArray(Directory[]::new);
    }

    /**
     * Abre la carpeta en el explorador de archivos
     */
    public void open() {
        try {
            Desktop.getDesktop().open(this);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

}
