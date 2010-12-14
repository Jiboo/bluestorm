package Capteurs;

import Capteurs.tools.interfaces.ISensorListener;
import Capteurs.tools.interfaces.ISensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Cette classe s'occupe de gérer les capteurs 
 * @author benoît caruso
 */
public class Capteurs implements ISensorManager{
    private Capteur gyroscope = null;
    private Capteur orientation = null;
    
    private SensorManager sensorManager;


    // à appeler dans activity sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    public Capteurs(SensorManager sensorManager){
        this.sensorManager = sensorManager;
    }

    /**
     * @param sensibility défini dans SensorManager.SENSOR_DELAY_*
     */
    public void subscribeGyroscope(ISensorListener subscribingListener, int sensibility){
        //instanciation on demand
        if (this.gyroscope == null) {
            this.gyroscope = new Gyroscope(sensibility, this);
        }
        this.gyroscope.subscribe(subscribingListener);
    }
        /**
     * @param sensibility défini dans SensorManager.SENSOR_DELAY_*
     */
    public void subscribeOrientation(ISensorListener subscribingListener, int sensibility){
        //instanciation on demand
        if (this.orientation == null) {
            this.orientation = new Orientation(sensibility, this);
        }
        this.orientation.subscribe(subscribingListener);
    }

    /**
     * Permet de récupérer un capteur par défaut
     * @param type : par ex Sensor.TYPE_GYROSCOPE
     * @return un Capteur
     */
    public Sensor getDefaultSensor(int type){
        return this.sensorManager.getDefaultSensor(type);
    }

    /**
     * enregistre un listener pour un capteur donné
     * @param listener
     * @param sensor
     * @param delay
     */
    public void registerListener(SensorEventListener listener, Sensor sensor, int delay) {
        this.sensorManager.registerListener(listener , sensor, delay);
    }
}
