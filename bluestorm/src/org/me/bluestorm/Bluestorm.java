/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import org.me.bluestorm.Capteurs.Capteurs;
import org.me.bluestorm.ui.Home;

/**
 *
 * @author jiboo
 */
public class Bluestorm extends Activity
{
    Nxt nxt;
<<<<<<< HEAD
    Capteurs capteurs;
    
=======

    public void applySpeed(float[] orientation) throws IOException
    {
        double leftPower, rightPower;

        double accel = (orientation[2]/50.) * 100;
        double orient = (orientation[1]/50.) * 50;

        leftPower = accel;
        rightPower = accel;

        if(orientation[1] > 0) {
            leftPower -= orient;
        } else {
            rightPower += orient;
        }

        if(leftPower > 100) leftPower = 100;
        if(leftPower < -100) leftPower = -100;
        if(rightPower > 100) rightPower = 100;
        if(rightPower < -100) rightPower = -100;

        Log.d("bluestorm", String.format("applySpeed: %3.0f %3.0f", leftPower, rightPower));
        this.nxt.setSpeed((byte)leftPower, (byte)rightPower);
    }

>>>>>>> 5c2f08cc25170fcb5810c85577e29843565bbcb7
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        try
        {
            //capteurs = new Capteurs((SensorManager) getSystemService(SENSOR_SERVICE));
            //this.nxt = new Nxt();

            this.setContentView(new Home(this));
        }
        catch(Exception e)
        {
            //TODO Afficher un message a l'utilisateur
            Log.e("bluestorm", "Exception caught in onCreate:", e);
        }
    }

    /*@Override
    public void onResume() {
        capteurs.subscribeOrientation(this, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        capteurs.subscribeOrientation(this, SensorManager.SENSOR_DELAY_NORMAL);
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
}
