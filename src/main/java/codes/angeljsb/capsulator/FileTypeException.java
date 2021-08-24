/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator;

/**
 * Representa un error en el tipo de archivo. Es decir, sucede cuando se trata
 * de usar un archivo para una acción que requiere otro formato, encriptado o
 * tipo
 *
 * @author Angel
 */
public class FileTypeException extends RuntimeException {

    /**
     * Creates a new instance of <code>FileTypeException</code> without detail
     * message.
     */
    public FileTypeException() {
    }

    /**
     * Constructs an instance of <code>FileTypeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FileTypeException(String msg) {
        super(msg);
    }
}
