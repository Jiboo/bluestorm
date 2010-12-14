/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Capteurs.tools.interfaces;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

/**
 * Interface du gestionnaire de capteurs
 * @author benoît caruso
 */
public interface ISensorManager {
    Sensor getDefaultSensor(int type);
    void registerListener(SensorEventListener listener, Sensor sensor, int delay);
}
