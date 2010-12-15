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
    Capteurs capteurs;
    
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
