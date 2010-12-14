/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import org.me.bluestorm.Capteurs.Capteurs;
import org.me.bluestorm.Capteurs.tools.interfaces.IOrientationListener;

/**
 *
 * @author jiboo
 */
public class MainActivity extends Activity implements IOrientationListener
{
    Nxt nxt;

    public void applySpeed(float[] orientation) throws IOException
    {
        double leftPower, rightPower;

        double accel = (orientation[2]/50.) * 100;
        double orient = (orientation[1]/50.) * 50;

        leftPower = accel;
        rightPower = accel;

        if(orientation[1] > 0)
            leftPower -= orient;
        else
            rightPower += orient;

        if(leftPower > 100) leftPower = 100;
        if(leftPower < -100) leftPower = -100;
        if(rightPower > 100) rightPower = 100;
        if(rightPower < -100) rightPower = -100;

        Log.d("bluestorm", String.format("applySpeed: %3.0f %3.0f", leftPower, rightPower));
        this.nxt.setSpeed((byte)leftPower, (byte)rightPower);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        try
        {
            Capteurs sensorManager = new Capteurs((SensorManager) getSystemService(SENSOR_SERVICE));

            this.nxt = new Nxt();

            //TODO Utiliser un bouton pour permettre la connexion et demarrer l'ecoute sur le capteur d'orientation
            this.nxt.connect();

            sensorManager.subscribeOrientation(this, SensorManager.SENSOR_DELAY_NORMAL);
        }
        catch(Exception e)
        {
            //TODO Afficher un message a l'utilisateur
            Log.e("bluestorm", "Exception caught in onCreate:", e);
        }
    }

    /*@Override
    public void onResume() {
        sensorManager.subscribeOrientation(this, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        sensorManager.subscribeOrientation(this, SensorManager.SENSOR_DELAY_NORMAL);
    }*/

    @Override
    public void onDestroy() {
        try
        {
            if(this.nxt != null)
            {
                if(this.nxt.isConnected())
                {
                    this.nxt.stop();
                    this.nxt.close();
                }
            }
        }
        catch(Exception e)
        {
            //TODO Afficher un message a l'utilisateur
            Log.e("bluestorm", "Exception caught in onDestroy:", e);
        }
    }

    public void getOrientation(SensorEvent event) {
        try
        {
            this.applySpeed(event.values);
        }
        catch(Exception e)
        {
            //TODO Afficher un message a l'utilisateur
            Log.e("bluestorm", "Exception caught in getOrientation:", e);
        }
    }
}
