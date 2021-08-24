/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.util;

import java.util.ArrayList;
import java.util.Collection;
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
    private ArrayList<Consumer<T>> changeListeners = new ArrayList();

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
     * Añade un agente de escucha que será ejecutado al cambiar el 
     * contenido de la capsula
     * 
     * @param listener El agente de escucha
     */
    public void addChangeListener(Consumer<T> listener) {
        this.changeListeners.add(listener);
    }
    
    /**
     * Añade varios agentes de escucha que serán ejecutados al cambiar el
     * contenido de la capsula
     * 
     * @param listeners Los agentes de escucha
     */
    public void addChangeListeners(Consumer<T>... listeners) {
        for(Consumer<T> listener : listeners){
            addChangeListener(listener);
        }
    }
    
    /**
     * Añade varios agentes de escucha que serán ejecutados al cambiar el
     * contenido de la capsula
     * 
     * @param listeners Los agentes de escucha
     */
    public void addChangeListeners(Collection<Consumer<T>> listeners) {
        this.changeListeners.addAll(listeners);
    }
    
    /**
     * Remueve un agente de escucha para que deje de ejecutarse al cambiar
     * el contenido de la capsula
     * 
     * @param listener El agente de escucha
     */
    public void removeChangeListener(Consumer<T> listener) {
        this.changeListeners.remove(listener);
    }
    
    /**
     * Remueve los agentes de escucha para que dejen de ejecutarse al cambiar
     * el contenido de la capsula
     * 
     * @param listeners los agentes de escucha a remover
     */
    public void removeAllChangeListeners(Collection<Consumer<T>> listeners) {
        this.changeListeners.removeAll(listeners);
    }
    
    /**
     * Remueve todos los agentes que están actualmente a la
     * escucha de esta capsula
     */
    public void removeAllChangeListeners() {
        this.changeListeners.clear();
    }

    /**
     * Activa el evento onChange
     */
    public void dispatchChange() {
        this.changeListeners.forEach(this::execChangeListener);
    }
    
    private void execChangeListener(Consumer<T> listener) {
        listener.accept(this.getContent());
    }

}
