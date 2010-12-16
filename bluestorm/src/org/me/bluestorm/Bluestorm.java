/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import org.me.bluestorm.Capteurs.Capteurs;
import org.me.bluestorm.Capteurs.tools.interfaces.IVirtualSensorWheelPowerListener;
import org.me.bluestorm.ui.Home;

/**
 *
 * @author jiboo
 */
public class Bluestorm extends Activity implements IVirtualSensorWheelPowerListener
{
    Nxt nxt;
    Capteurs capteurs;
    ProgressDialog progress;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        try {
            capteurs = new Capteurs((SensorManager) getSystemService(SENSOR_SERVICE));
            this.nxt = new Nxt();

            this.setContentView(new Home(this));
        }
        catch(Exception e) {
            //TODO Afficher un message a l'utilisateur
            Log.e("bluestorm", "Exception caught in onCreate:", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            if(this.nxt != null) {
                if(this.nxt.isConnected()) {
                    this.nxt.stop();
                    this.nxt.close();
                }
            }
        }
        catch(Exception e) {
            //TODO Afficher un message a l'utilisateur
            Log.e("bluestorm", "Exception caught in onDestroy:", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        capteurs.subscribe(Capteurs.TypesCapteurs.vWhellPower, this, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();

        capteurs.unsubscribe(Capteurs.TypesCapteurs.vWhellPower, this);
    }

    // -------------------------------------------------------------------------
    // --- Evenements des capteurs
    // -------------------------------------------------------------------------

    public void getWheelPower(SensorEvent event) {
    }

    // -------------------------------------------------------------------------
    // --- Utilitaires de l'ui
    // -------------------------------------------------------------------------

    /**
     * Permet d'afficher un toast depuis un thread, trés utile.
     * Si vous voulez afficher un thread depuis l' ui (exemple: dans onCreate, onDestroy)
     * utiliser Toast.makeText dirrectement.
     *
     * http://stackoverflow.com/questions/3134683/android-toast-in-a-thread
     * @param pMessage le text du toast a afficher
     */
    public void alert(final String pMessage) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Bluestorm.this, pMessage,  Toast.LENGTH_SHORT).show();
            }
        });
    }

    // -------------------------------------------------------------------------
    // --- Evenements de l' ui
    // -------------------------------------------------------------------------

    /**
     * Initie la connexion au robot
     */
    public void demarrerConnexion() {
        progress = ProgressDialog.show(this, "Connexion...", "Veuillez patienter, connexion en cours...", true, false);
        new Thread() {
            @Override
            public void run() {
                try { nxt.connect();}
                catch(Exception e) {
                    Log.e("bluestorm", "Erreur lors de la connexion", e);
                    alert(e.getMessage());
                }
                progress.dismiss();
            }
        }.start();
    }
}
