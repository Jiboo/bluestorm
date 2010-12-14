package Capteurs.tools.interfaces;

import android.hardware.SensorEventListener;

/**
 * @author benoît caruso
 */
public interface ICapteur extends SensorEventListener {
    public void subscribe(ISensorListener listener);
}
