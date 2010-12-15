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
        arg0.values[0] = -1;
        double leftPower, rightPower;

        double accel = (orientation[2]/50.) * 100;
        double orient = (orientation[1]/50.) * 50;

        leftPower = accel;
        rightPower = accel;

        if(orientation[1] > 0) {
            leftPower -= orient;
        } else {
            rightPower += orient;
        }

        if(leftPower > 100) leftPower = 100;
        if(leftPower < -100) leftPower = -100;
        if(rightPower > 100) rightPower = 100;
        if(rightPower < -100) rightPower = -100;

        super.onSensorChanged(arg0);
    }
}
