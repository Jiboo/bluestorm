package org.me.bluestorm.Capteurs;

import org.me.bluestorm.Capteurs.tools.interfaces.ISensorListener;
import org.me.bluestorm.Capteurs.tools.interfaces.ISensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Cette classe s'occupe de gérer les capteurs 
 * @author benoît caruso
 */
public class Capteurs implements ISensorManager{
    /**
     * Enum qui représente les types de capteurs dispo pour la méthode subscribe
     */
    public enum TypesCapteurs {
        orientation, vWhellPower
    }

    private Capteur gyroscope   = null;
    private Capteur orientation = null;
    private Capteur vWheelPower = null;

    private SensorManager sensorManager;


    // à appeler dans activity sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    public Capteurs(SensorManager sensorManager){
        this.sensorManager = sensorManager;
    }

    public void subscribe(TypesCapteurs type, ISensorListener subscribingListener, int sensibility){
        switch (type){
            case orientation :
                this.subscribeOrientation(subscribingListener, sensibility);
            break;
            case vWhellPower:
                this.subscribeVWheelPower(subscribingListener, sensibility);
            break;
            default : break;
        }
    }

    /**
     * @param sensibility défini dans SensorManager.SENSOR_DELAY_*
     */
    private void subscribeGyroscope(ISensorListener subscribingListener, int sensibility){
        //instanciation on demand
        if (this.gyroscope == null) {
            this.gyroscope = new Gyroscope(sensibility, this);
        }
        this.gyroscope.subscribe(subscribingListener);
    }
        /**
     * @param sensibility défini dans SensorManager.SENSOR_DELAY_*
     */
    private void subscribeOrientation(ISensorListener subscribingListener, int sensibility){
        //instanciation on demand
        if (this.orientation == null) {
            this.orientation = new Orientation(sensibility, this);
        }
        this.orientation.subscribe(subscribingListener);
    }
    /**
     * @param sensibility défini dans SensorManager.SENSOR_DELAY_*
     */
    private void subscribeVWheelPower(ISensorListener subscribingListener, int sensibility){
        //instanciation on demand
        if (this.vWheelPower == null) {
            this.vWheelPower = new VirtualSensorWheelPower(sensibility, this);
        }
        this.vWheelPower.subscribe(subscribingListener);
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
