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
import android.view.View;
import android.widget.Toast;
import org.me.bluestorm.Capteurs.Capteurs;
import org.me.bluestorm.Capteurs.tools.interfaces.IVirtualSensorWheelPowerListener;
import org.me.bluestorm.ui.Game;
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
    Home home;
    Game game;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        try {
            capteurs = new Capteurs((SensorManager) getSystemService(SENSOR_SERVICE));
            nxt = new Nxt();
            home = new Home(this);
            game = new Game(this);

            setContentView(home);
        }
        catch(Exception e) {
            alert(e.getMessage());
            Log.e("bluestorm", "Exception caught in onCreate:", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            if(nxt != null) {
                if(nxt.isConnected()) {
                    nxt.stop();
                    nxt.close();
                }
            }
        }
        catch(Exception e) {
            alert(e.getMessage());
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
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Bluestorm.this, pMessage,  Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeView(final View v) {
        runOnUiThread(new Runnable() {
            public void run() {
                setContentView(v);
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
        progress = ProgressDialog.show(this, "Veuillez patienter...", "Connexion en cours...", true, false);
        new Thread() {
            @Override
            public void run() {
                try {
                    nxt.connect();


                    Partition jinglebells = new Partition(new Note[] {
                        new Note(Note.DO, 1000, 8),
                        new Note(Note.DO, 1000, 8)
                    });

                    Bluestorm.this.changeView(game);
                }
                catch(Exception e) {
                    Log.e("bluestorm", "Erreur lors de la connexion", e);
                    alert(e.getMessage());
                }
                progress.dismiss();
            }
        }.start();
    }

    public void stop() {
        nxt.close();
        Bluestorm.this.changeView(home);
    }

    /**
     *
     */
    public INxt getNxt() {
        return nxt;
    }
}
