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

    public static final float ANGLE_1 = 45;
    public static final float ANGLE_2 = 30;

    @Override
    public void onSensorChanged(SensorEvent arg){

        double leftPower = 0., rightPower = 0.;

        if(arg.values[1] > ANGLE_1) arg.values[1] = ANGLE_1;
        if(arg.values[1] < -ANGLE_1) arg.values[1] = -ANGLE_1;
        if(arg.values[2] > ANGLE_2) arg.values[2] = ANGLE_2;
        if(arg.values[2] < -ANGLE_2) arg.values[2] = -ANGLE_2;

        if(arg.values[2] > -8 && arg.values[2] < 8) // Rotation sur place
        {
            double orient = (arg.values[1]/ANGLE_1) * 100;

            leftPower = -orient;
            rightPower = orient;
        }
        else
        {
            double accel = (arg.values[2]/ANGLE_2) * 100;
            double orient = (arg.values[1]/ANGLE_1) * 50;

            leftPower = accel;
            rightPower = accel;

            if(arg.values[1] > 0) // on tourne vers la droite
                leftPower -= orient;
            else
                rightPower += orient; // + pcq orient sera negatif
        }

        if(leftPower > 100) leftPower = 100;
        if(leftPower < -100) leftPower = -100;
        if(rightPower > 100) rightPower = 100;
        if(rightPower < -100) rightPower = -100;

        arg.values[0] =  (float)leftPower;
        arg.values[1] =  (float)rightPower;

        super.onSensorChanged(arg);
    }
}
