package Capteurs;

import Capteurs.tools.interfaces.IGyroscopeListener;
import Capteurs.tools.interfaces.IOrientationListener;
import Capteurs.tools.interfaces.ISensorManager;
import android.hardware.Sensor;
/**
 * Pour ajouter un capteur dans l'application il suffit d'ajouter une classe comme celle-ci, le reste est générique
 * @author benoît caruso
 */
public class Orientation extends Capteur {

    /**
     * @param sensibility ( 0 plus rapide - 3 plus lent )
     * @param sensorManager
     */
    public Orientation(int sensibility, ISensorManager sensorManager){
        super(sensorManager, Sensor.TYPE_ORIENTATION, sensibility);
        //On indique le type de listener qu'il faut. Pour la fonction subscribe, la classe générique Capteur s'en occupe
        this.listenerType = IOrientationListener.class.getCanonicalName();
    }

}
