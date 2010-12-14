/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.androidapplication;

import Capteurs.Capteurs;
import Capteurs.tools.interfaces.IOrientationListener;
import android.app.Activity;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 *
 * @author sangskaal
 */
public class MainActivity extends Activity implements IOrientationListener {
    private TextView hello ;
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.hello = new TextView(this);
        Capteurs sensorManager = new Capteurs((SensorManager) getSystemService(SENSOR_SERVICE));
        sensorManager.subscribeOrientation(this, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void getOrientation(SensorEvent event) {
        afficher("orientation : ");
        String str = null;
        for ( float value : event.values){
            str+=value+" ; ";
        }
        afficher(str);
    }

    private void afficher (String s){
        this.hello.setText(s);
        setContentView(hello);
    }
}


