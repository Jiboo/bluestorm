/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm.Capteurs.tools.interfaces;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import org.me.bluestorm.Capteurs.Capteurs.TypesCapteurs;

/**
 * Interface du gestionnaire de capteurs
 * @author benoît caruso
 */
public interface ISensorManager {
    /** Permet de s'abonner a un capteur */
    void subscribe(TypesCapteurs type, ISensorListener subscribingListener, int sensibility);
    void unsubscribe(TypesCapteurs type, ISensorListener unsubscribingListener);
    /** Permet de mettre un capteur en "pause" pour economiser des ressources dans l'appli */
    void pauseCapteur(TypesCapteurs type);
    void resumeCapteur(TypesCapteurs type);

    Sensor getDefaultSensor(int type);
    void registerListener(SensorEventListener listener, Sensor sensor, int delay);
    void unregisterListener(SensorEventListener listener);
}
