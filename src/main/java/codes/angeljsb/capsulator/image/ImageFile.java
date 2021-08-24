/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import codes.angeljsb.capsulator.BaseFile;
import codes.angeljsb.capsulator.FileTypeException;
import codes.angeljsb.capsulator.ReadableFile;
import codes.angeljsb.capsulator.util.Capsule;
import codes.angeljsb.capsulator.util.Painter;

/**
 * Clase que hace referencia a un archivo que contiene una imagen y facilita su
 * lectura y uso.
 *
 * @author Angel
 */
public class ImageFile implements ReadableFile<BufferedImage> {

    /**
     * Los formatos aceptados por esta clase
     */
    public static final String[] ACCEPTED_FORMATS = new String[]{
        "png", "jpg", "jpeg", "gif", "bmp"
    };

    private final Capsule<BufferedImage> capsule = new Capsule();
    private final BaseFile baseFile;

    /**
     * Crea un objeto ImageFile anidado a un archivo baseFile
     *
     * @param baseFile El archivo de la imagen
     */
    public ImageFile(BaseFile baseFile) {
        if (!baseFile.compareFormat(ACCEPTED_FORMATS)) {
            throw new FileTypeException("ImageFile solo acepta archivos"
                    + " en formatos png, jpg, gif o bmp");
        }
        this.baseFile = baseFile;
    }

    /**
     * Redimensiona la imagen contenida en el objeto. Si el ancho o el alto son
     * numeros negativos, se ajustará al lado con valor positivo y se mantendrá
     * la escala
     *
     * @param width El nuevo ancho
     * @param height El nuevo alto
     */
    public void resize(int width, int height) {
        this.setContent(
                ImageLoader.resizeImage(this.get(), width, height)
        );
    }

    /**
     * Redimensiona la imagen contenida al ancho solicitado manteniendo la
     * escala actual
     *
     * @param width El ancho que se quiere
     */
    public void resizeToWidth(int width) {
        this.resize(width, -1);
    }

    /**
     * Redimensiona la imagen para que el ancho esté en un rango especificado.
     * Si el ancho de la imagen es menor que el minimo se redimensiona al minimo
     * y si es mayor que el maximo se redimensiona al maximo. De estar ya dentro
     * del rango no ocurre nada.
     *
     * @param min El minimo
     * @param max El maximo
     */
    public void resizeToWidth(int min, int max) {
        BufferedImage content = this.get();
        int width = content.getWidth();
        if (width < min) {
            this.resizeToWidth(min);
        } else if (width > max) {
            this.resizeToWidth(max);
        }
    }

    /**
     * Redimensiona la imagen contenida al alto solicitado manteniendo la escala
     * actual
     *
     * @param height El alto que se quiere
     */
    public void resizeToHeight(int height) {
        this.resize(-1, height);
    }

    /**
     * Redimensiona la imagen para que el alto esté en un rango especificado. Si
     * el alto de la imagen es menor que el minimo se redimensiona al minimo y
     * si es mayor que el maximo se redimensiona al maximo. De estar ya dentro
     * del rango no ocurre nada.
     *
     * @param min El minimo
     * @param max El maximo
     */
    public void resizeToHeight(int min, int max) {
        BufferedImage content = this.get();
        int height = content.getHeight();
        if (height < min) {
            this.resizeToHeight(min);
        } else if (height > max) {
            this.resizeToHeight(max);
        }
    }

    /**
     * Función que obtiene un objeto painter y mediante este pinta algo en la
     * imagen
     *
     * @param painter La función que recibe los graficos de la imagen y puede
     * pintar en ella
     */
    public void paint(Painter painter) {
        painter.paint(this.get().createGraphics());
        this.getCapsule().dispatchChange();
    }
    
    /**
     * Obtiene el tamaño de la imagen contenida por el archivo.
     * Ignora el contenido de la capsula.
     * 
     * @return El tamaño de la imagen en el archivo
     */
    public Dimension getSizeFromFile() {
        return ImageLoader.getSize(this.baseFile);
    }
    
    /**
     * Obtiene el tamaño de la imagen.
     * <p>
     * Si hay una imagen contenida actualmente por el objeto, devuelve el
     * tamaño de esa imagen. Sino devuelve el tamaño de la imagen
     * contenida en el archivo
     * </p>
     * 
     * @return El tamaño de la imagen contenida
     */
    public Dimension getSize() {
        if(this.isLoaded()){
            BufferedImage content = this.getContent();
            return new Dimension(content.getWidth(), content.getHeight());
        }
        return this.getSizeFromFile();
    }
    
    /**
     * Devuelve el ancho de la imagen contenida. De no haber imagen
     * contenida, busca el ancho de la imagen en el archivo.
     * 
     * @return El ancho de la imagen o {@code 0} si no lo encuentra 
     */
    public int getWidth() {
        return this.getSize().width;
    }
    
    /**
     * Devuelve el alto de la imagen contenida. De no haber imagen
     * contenida, busca el alto de la imagen en el archivo.
     * 
     * @return El alto de la imagen o {@code 0} si no lo encuentra 
     */
    public int getHeight() {
        return this.getSize().height;
    }

    @Override
    public BaseFile getBaseFile() {
        return this.baseFile;
    }

    @Override
    public Capsule<BufferedImage> getCapsule() {
        return this.capsule;
    }

    @Override
    public BufferedImage fileRead() {
        return ImageLoader.loadImage(this.getBaseFile());
    }

    @Override
    public void fileWrite(BufferedImage content) {
        ImageLoader.writeImage(content, this.getBaseFile());
    }

    @Override
    public String[] getAcceptedFormats() {
        return ACCEPTED_FORMATS;
    }

}
