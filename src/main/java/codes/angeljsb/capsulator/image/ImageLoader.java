/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.image;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import codes.angeljsb.capsulator.BaseFile;

/**
 * Clase con metodos estaticos para aplicar operaciones pesadas a imagenes.
 * Estas operaciones pueden ser de carga, redimención u modificación.
 *
 * @author Angel
 */
public class ImageLoader {

    /**
     * Determina si el tamaño de la imagen es igual al solicitado para la
     * redimensión
     *
     * @param image La imagen
     * @param width El ancho
     * @param height El alto
     * @return Si el tamaño de la imagen es igual al solicitado
     */
    private static boolean sizeEquals(BufferedImage image, int width, int height) {
        return (image.getWidth() == width || width < 0)
                && (image.getHeight() == height || height < 0);
    }

    /**
     * Determina si un archivo corresponde a uno de los siguientes formatos de
     * imagenes reconocidos:
     * <ul>
     * <li>jpg</li>
     * <li>png</li>
     * <li>gif</li>
     * <li>bmp</li>
     * </ul>
     *
     * @param file El archivo a comprobar
     * @return si corresponde a uno de los formatos.
     */
    public static boolean isImageFile(File file) {
        if(file.isDirectory()) return false;
        String name = file.getName();
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        return suffix.equalsIgnoreCase("jpg")
                || suffix.equalsIgnoreCase("jpeg")
                || suffix.equalsIgnoreCase("gif")
                || suffix.equalsIgnoreCase("png")
                || suffix.equalsIgnoreCase("bmp");
    }

    /**
     * Función para evitar el uso de imagenes de tipo BufferedImage.TYPE_CUSTOM
     * las cuales vuelven lenta la renderización.
     *
     * @param verify La imagen a verificar su tipo
     *
     * @return Si el tipo no es BufferedImage.TYPE_CUSTOM, se devuelve la misma
     * imagen que se ingresó. Sino, se devuelve una copia de tipo
     * BufferedImage.TYPE_INT_ARGB
     */
    public static BufferedImage validImageType(BufferedImage verify) {
        if (verify.getType() != BufferedImage.TYPE_CUSTOM) {
            return verify;
        }
        BufferedImage validType = new BufferedImage(verify.getWidth(), verify.getHeight(), BufferedImage.TYPE_INT_ARGB);
        validType.getGraphics().drawImage(verify, 0, 0, null);
        return validType;
    }

    /**
     * Redimensiona una BufferedImage y se asegura de que el resultado sea otra
     * BufferedImage del tamaño solicitado. La redimensión se hace con suavisado
     * de forma predeterminada.<br><br>
     *
     * Si se añade un número negativo en ancho o en alto, se redimensionará de
     * manera que se conserve la escala original. Si se hace esto en ambos, no
     * se hará nada.
     *
     * @param image La imagen a redimensionar
     * @param width El nuevo ancho que se busca
     * @param height El nuevo alto que se busca
     * @return La imagen redimensionada
     */
    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        if (sizeEquals(image, width, height)) {
            return image;
        }
        BufferedImage temp;
        Image img;
        img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        temp = new BufferedImage(img.getWidth(null), img.getHeight(null), image.getType());
        temp.getGraphics().drawImage(img, 0, 0, null);
        return temp;
    }

    /**
     * Carga una imagen desde un archivo.<br><br>
     *
     * De haber algún error en la lectura del archivo, se imprime el mensaje en
     * la salida normal de errores y se devuelve una imagen oscura de tamaño 10
     * x 10.
     *
     * @param file El archivo de la imagen
     * @return La imagen leída
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage readed;
        try {

            readed = ImageIO.read(file);
            readed = validImageType(readed);

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            readed = new BufferedImage(10, 10, 1);
        }
        return readed;
    }

    /**
     * Escribe una imagen en un archivo
     *
     * @param image La imagen a escribir
     * @param file El archivo en el que escribir la imagen
     */
    public static void writeImage(BufferedImage image, BaseFile file) {
        try {

            ImageIO.write(image, file.getSuffix().substring(1), file);

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Obtiene el tamaño de la imagen contenida en un archivo sin necesidad de
     * cargar la imagen completa, leyendo las cabeceras del archivo
     *
     * @param image El archivo de imagen
     * @return El tamaño de la imagen o un tamaño 0 x 0 si no lo encuentra
     */
    public static Dimension getSize(File image) {
        try (ImageInputStream in = ImageIO.createImageInputStream(image)) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    return new Dimension(reader.getWidth(0), reader.getHeight(0));
                } finally {
                    reader.dispose();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        return new Dimension(0, 0);
    }
}
