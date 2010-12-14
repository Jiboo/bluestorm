package Capteurs.tools.interfaces;

import android.hardware.SensorEvent;

/**
 * @author benoît caruso
 */
public interface IOrientationListener extends ISensorListener{
    void getOrientation(SensorEvent event);
}
