package org.me.bluestorm.Capteurs;

import org.me.bluestorm.Capteurs.tools.interfaces.ISensorListener;
import org.me.bluestorm.Capteurs.tools.interfaces.ICapteur;
import org.me.bluestorm.Capteurs.tools.interfaces.ISensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Classe générique de capteur
 * @author benoît caruso
 */
public abstract class Capteur implements ICapteur{
    /** liste des capteurs abonnés aux changements du gyroscope */
    protected ArrayList<ISensorListener> subsribedListeners;
    /** type de listener qui doit écouter le capteur concret, à définir dans la classe fille */
    protected String listenerType = ISensorListener.class.getCanonicalName();
    /** type de capteur, défini dans classe fille, correspondance dans Sensor */
    protected int    sensorType   = -1;
    /** vitesse d'envoi des données du capteur, cf Sensor */
    protected int    sensibility  = -1;
    /** Manager des capteurs */
    protected ISensorManager sensorManager;
    /** permet de vérifier que le capteur fonctionne */
    private   boolean isOn = false;


    public Capteur(ISensorManager sensorManager, int sensorType, int sensibility){
        this.subsribedListeners = new ArrayList<ISensorListener>();
        this.sensorManager = sensorManager;
        this.sensorType    = sensorType;
        this.sensibility   = sensibility;
        //si la sensibilité n'a pas été définie on utilise la plus rapide
         this.registerSensor(this.sensorType, sensibility<0?SensorManager.SENSOR_DELAY_FASTEST:sensibility);
         this.isOn = true;
    }

    /**
     * Méthode générique d'abonnement au capteur
     * @param listener
     */
    public void subscribe(ISensorListener listener){
        if ( this.isListener(listener) ){
            this.subsribedListeners.add(listener);
        }
    }
    public void unsubscribe(ISensorListener listener){
        if (this.isListener(listener)){
            this.subsribedListeners.remove(listener);
        }
    }
    public void pauseSensor(){
        this.unRegisterSensor();
        this.isOn = false;
    }
    public void resumeSensor(){
        this.registerSensor(this.sensorType, this.sensibility);
        this.isOn = true;
    }
    public boolean isOn(){
        return this.isOn;
    }

    /**
     * Ici on enregistre le Capteur lui même comme listener pour rediriger le mécanisme d'evenements
     * @param type
     * @param delay
     */
    private void registerSensor( int type, int delay){
        Sensor sensor = this.sensorManager.getDefaultSensor(type);
        this.sensorManager.registerListener(this, sensor, delay);
    }
    private void unRegisterSensor(){
        this.sensorManager.unregisterListener(this);
    }
    /**
     * Dans cette méthode on vérifie que le listener implemente bien l'interface correspondant
     * au type réel du capteur, par exemple pour le capteur d'orientation le listener doit implémenter
     * ISensorListener
     * @param listener
     * @return
     */
    private boolean isListener(ISensorListener listener){
        //On boucle sur la liste des interfaces du listener
        for ( int i=0 ; i < listener.getClass().getInterfaces().length ; ++i){
            //Si listener implémente la bonne interface on l'ajoute puis on sort de la boucle, sinon on continue la boucle ou on sort de la méthode
            if ( listener.getClass().getInterfaces()[i].getCanonicalName().equals(this.listenerType) ){
                return true;
            }
        }
        return false;
    }

    public void onSensorChanged(SensorEvent arg0){
        try {
            //On récupère la/les méthode(s) de l'interface du listener
            Method[] methods = Class.forName(this.listenerType).getDeclaredMethods();
            //pour chaque méthode de l'interface abonnable
            for ( Method method : methods ){
                //Pour tous les abonnés
                for ( ISensorListener listener : this.subsribedListeners ){
                    //On appelle la méthode définie dans l'interface
                    method.invoke(listener, arg0);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Capteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onAccuracyChanged(Sensor arg0, int arg1){
        try {
            //On récupère la/les méthode(s) de l'interface du listener
            Method method = Class.forName(this.listenerType).getDeclaredMethod("onAccuracyChanged", Sensor.class, int.class);
                for ( ISensorListener listener : this.subsribedListeners ){
                    method.invoke(listener, arg0, arg1);
                }
        } catch (Exception ex) {
            Logger.getLogger(Capteur.class.getName()).log(Level.WARNING, null, ex);
        }
    }
}
