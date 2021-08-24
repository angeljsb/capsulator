/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.capsulator.util;

import java.awt.Graphics2D;

/**
 * Representa una función que toma el objeto graphics de algún objeto y pinta
 * algo en él
 *
 * @author Angel
 */
@FunctionalInterface
public interface Painter {

    /**
     * Función que recibe los graficos del objeto y puede dibujar en él
     *
     * @param g Los graficos del objeto
     */
    public void paint(Graphics2D g);

}
