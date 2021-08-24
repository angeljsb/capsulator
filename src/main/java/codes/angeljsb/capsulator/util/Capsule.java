/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.util;

import java.util.function.Consumer;

/**
 * Capsula que contiene un objeto (content) y acepta efectos secundarios al
 * cambiar dicho objeto
 *
 * @author Angel
 * @param <T> El tipo de objeto a guardar en la capsula
 */
public class Capsule<T> {

    private T content = null;
    private Consumer<T> onChange = null;

    /**
     * Devuelve el contenido de la capsula
     *
     * @return El objeto guardado actual
     */
    public T getContent() {
        return content;
    }

    /**
     * Cambia el contenido de la capsula y activa los efectos secundarios
     *
     * @param content El contenido a guardar
     */
    public void setContent(T content) {
        boolean dif = this.content != null && !this.content.equals(content);
        this.content = content;
        if (dif) {
            this.dispatchChange();
        }
    }

    /**
     * Devuelve el objeto con la función que se activa al cambiar el contenido
     * de la capsula
     *
     * @return El evento
     */
    public Consumer<T> getOnChange() {
        return onChange;
    }

    /**
     * Cambia el evento que se activa al cambiar el contenido de la capsula
     *
     * @param onChange El nuevo evento a activar
     */
    public void setOnChange(Consumer<T> onChange) {
        this.onChange = onChange;
    }

    /**
     * Activa el evento onChange
     */
    public void dispatchChange() {
        if (this.onChange != null) {
            this.onChange.accept(this.content);
        }
    }

}
