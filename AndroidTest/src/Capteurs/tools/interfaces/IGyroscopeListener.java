package Capteurs.tools.interfaces;

import android.hardware.SensorEvent;

/**
 * Si une classe veut écouter le gyroscope elle doit implémenter cette interface
 * @author benoît caruso
 */
public interface IGyroscopeListener extends ISensorListener{
    void getGyroscope(SensorEvent event);
}
