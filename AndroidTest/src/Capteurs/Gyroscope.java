package Capteurs;

import Capteurs.tools.interfaces.IGyroscopeListener;
import Capteurs.tools.interfaces.ISensorManager;
import android.hardware.Sensor;
/**
 * Pour ajouter un capteur dans l'application il suffit d'ajouter une classe comme celle-ci, le reste est générique
 * @author benoît caruso
 */
public class Gyroscope extends Capteur {
    
    /**
     * @param sensibility ( 0 plus rapide - 3 plus lent )
     * @param sensorManager
     */
    public Gyroscope(int sensibility, ISensorManager sensorManager){
        super(sensorManager, Sensor.TYPE_GYROSCOPE, sensibility);
        //On indique le type de listener qu'il faut. Pour la fonction subscribe, la classe générique Capteur s'en occupe
        this.listenerType = IGyroscopeListener.class.getCanonicalName();
    }
}
