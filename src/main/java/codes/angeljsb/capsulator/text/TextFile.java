/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.text;

import codes.angeljsb.capsulator.BaseFile;
import codes.angeljsb.capsulator.ReadableFile;
import codes.angeljsb.capsulator.util.Capsule;

/**
 * Representa un archivo de texto en cualquiera de sus formatos
 *
 * @author Angel
 */
public class TextFile implements ReadableFile<String> {

    private final BaseFile baseFile;
    private final Capsule<String> capsule = new Capsule();

    /**
     * Crea un objeto text file representando un archivo
     *
     * @param baseFile El archivo al que estará anclado este objeto
     */
    public TextFile(BaseFile baseFile) {
        this.baseFile = baseFile;
    }

    @Override
    public BaseFile getBaseFile() {
        return this.baseFile;
    }

    @Override
    public Capsule getCapsule() {
        return this.capsule;
    }

    @Override
    public String[] getAcceptedFormats() {
        return new String[]{"*"};
    }

    @Override
    public String fileRead() {
        return TextLoader.read(this.baseFile);
    }

    @Override
    public void fileWrite(String content) {
        TextLoader.write(this.baseFile, content);
    }

}
