package org.me.bluestorm.Capteurs.tools.interfaces;

import android.hardware.SensorEventListener;

/**
 * @author benoît caruso
 */
public interface ICapteur extends SensorEventListener {
    /** Ajoute un listener au capteur */
    public void subscribe(ISensorListener listener);
    /** Supprime le listener du capteur */
    public void unsubscribe(ISensorListener listener);
    /** met en pause le capteur. par ex pour pause dans appli */
    public void pauseSensor();
    /** remet en marche le capteur */
    public void resumeSensor();
    /** etat du capteur */
    public boolean isOn();
}
