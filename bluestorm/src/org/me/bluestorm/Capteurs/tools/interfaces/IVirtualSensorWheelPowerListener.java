/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm.Capteurs.tools.interfaces;

import android.hardware.SensorEvent;

/**
 *
 * @author sangskaal
 */
public interface IVirtualSensorWheelPowerListener extends ISensorListener{
    void getWheelPower(SensorEvent event);
}
