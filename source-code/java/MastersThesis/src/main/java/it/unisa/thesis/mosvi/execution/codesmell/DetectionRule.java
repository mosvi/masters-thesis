/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.thesis.mosvi.execution.codesmell;

import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;

/**
 *
 * @author fably
 */
public interface DetectionRule {
    boolean isSmelly(ComponentBean cb);
}
