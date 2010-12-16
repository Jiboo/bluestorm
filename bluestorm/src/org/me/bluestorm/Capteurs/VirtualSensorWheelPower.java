/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm.Capteurs;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import org.me.bluestorm.Capteurs.tools.interfaces.ISensorManager;
import org.me.bluestorm.Capteurs.tools.interfaces.IVirtualSensorWheelPowerListener;

/**
 * Capteur virtuel
 * pour ce capteur on envoi en values[0] rightPower, la puissance de la roue de droite
 *                             values[1] leftPower,  la puissance de la roue de gauche
 * @author sangskaal
 */
public class VirtualSensorWheelPower extends Capteur{
    /**
     * @param sensibility ( 0 plus rapide - 3 plus lent )
     * @param sensorManager
     */
    public VirtualSensorWheelPower(int sensibility, ISensorManager sensorManager){
        super(sensorManager, Sensor.TYPE_ORIENTATION, sensibility);
        //On indique le type de listener qu'il faut. Pour la fonction subscribe, la classe générique Capteur s'en occupe
        this.listenerType = IVirtualSensorWheelPowerListener.class.getCanonicalName();
    }

    @Override
    public void onSensorChanged(SensorEvent arg0){
        float leftPower, rightPower;

        float accel  = ((arg0.values[2] / 50) * 100);
        float orient = ((arg0.values[1] / 50) * 50);

        leftPower = accel;
        rightPower = accel;

        if(arg0.values[1] > 0) {
            leftPower -= orient;
        } else {
            rightPower += orient;
        }

        //Saturation des valeurs à 100
        if(leftPower  >  100) leftPower  =  100;
        if(leftPower  < -100) leftPower  = -100;
        if(rightPower >  100) rightPower =  100;
        if(rightPower < -100) rightPower = -100;

        arg0.values[0] =  rightPower;
        arg0.values[1] =  leftPower;

        super.onSensorChanged(arg0);
    }
}
