/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table;

/**
 *
 * @author leandro.lima
 */
public class Utils {
    public static double stringToDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch(NumberFormatException ex) {
            return 0;
        }
    }
    
}
