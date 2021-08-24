/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator;

import java.io.File;
import codes.angeljsb.capsulator.util.Capsule;

/**
 * Define un archivo que se puede leer y del cual se puede obtener un objeto de
 * una clase en concreto
 *
 * @author Angel
 * @param <T> El tipo de objeto que se puede obtener de ese archivo
 */
public interface ReadableFile<T> {

    /**
     * Devuelve el archivo correspondiente a este objeto
     *
     * @return El archivo legible al que este objeto está anclado
     */
    public BaseFile getBaseFile();

    /**
     * Devuelve la capsula que guarda el contenido de este objeto. A esta se le
     * pueden añadir a su vez los efectos secundarios si se necesitan
     *
     * @return La capsula
     */
    public Capsule<T> getCapsule();

    /**
     * Devuelve el contenido actual de este objeto. El contenido es un objeto
     * que puede ser leído o escrito en el archivo por medio de los metodos
     * fileRead y fileWrite.<br>
     * El contenido actual puede ser el contenido del archivo o un contenido
     * añadido por el metodo setContent. O <code>null</code> si no se ha llamado
     * a read() ni a setContent()
     *
     * @return El contenido actual de este objeto
     */
    public default T getContent() {
        return this.getCapsule().getContent();
    }

    /**
     * Cambia el contenido del objeto. El contenido es un objeto que puede ser
     * leído o escrito en el archivo por medio de los metodos fileRead y
     * fileWrite.
     *
     * @param content El nuevo contenido que añadir al objeto
     */
    public default void setContent(T content) {
        this.getCapsule().setContent(content);
    }

    /**
     * Función que lee y retorna el contenido del archivo como un objeto del
     * formato correspondiente
     *
     * @return El contenido del archivo como un objeto
     */
    public T fileRead();

    /**
     * Escribe un objeto en el archivo correspondiente
     *
     * @param content El objeto a escribir
     */
    public void fileWrite(T content);

    /**
     * Lee el contenido del archivo y lo guarda en content para que después sea
     * fácilmente llamable con getContent()
     */
    public default void read() {
        this.setContent(this.fileRead());
    }

    /**
     * Escribe el contenido actual de este objeto en el archivo
     */
    public default void write() {
        this.fileWrite(this.getContent());
    }

    /**
     * Devuelve los formatos de archivos que pueden ser leidos y escritos por
     * esta clase
     *
     * @return Un arreglo con los formatos de archivos que pueden ser leídos por
     * esta clase
     */
    public String[] getAcceptedFormats();

    /**
     * Si el contenido existe lo obtiene, sin importar que este corresponda al
     * contenido del archivo o no. Si no hay contenido, carga el contenido desde
     * el archivo y luego lo devuelve
     *
     * @return El contenido actual del objeto o, de no haber contenido, el
     * contenido del archivo
     */
    public default T get() {
        if (!this.isLoaded()) {
            this.read();
        }
        return this.getContent();
    }

    /**
     * Devuelve el contenido a null y llama al garbage collector. El principal
     * objetivo de este metodo es ahorrar memoria
     */
    public default void nulliffy() {
        this.setContent(null);
        System.gc();
    }

    /**
     * Determina si existe contenido en este objeto
     *
     * @return Si exite contenido en este objeto
     */
    public default boolean isLoaded() {
        return this.getContent() != null;
    }

    /**
     * Devuelve el archivo correspondiente a este objeto como un objeto de tipo
     * File
     *
     * @return La ruta abstracta a la que está anclada este objeto
     */
    public default File getFile() {
        return this.getBaseFile();
    }

}
