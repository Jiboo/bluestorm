/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluetoothtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 *
 * @author jiboo
 */
public class MainActivity extends Activity {
    private Nxt nxt;
    private ProgressDialog connexion;

    class ConnectThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                Looper.prepare();

                if(MainActivity.this.nxt.connect())
                {
                    MainActivity.this.connexion.dismiss();
                        for(int lIndex = 0; lIndex < 10; lIndex++)
                        {
                            Log.d("bluetoothtest", "gotBall: " + MainActivity.this.nxt.gotBall());
                            sleep(2000);
                        }

                    Log.d("bluetoothtest", "Closing socket");
                    MainActivity.this.nxt.close();
                }
                else
                {
                    MainActivity.this.connexion.dismiss();
                    Log.d("bluetoothtest", "Connexion impossible, ou pas de NXT trouvé");
                }
            }
            catch(Exception e)
            {
                Log.d("bluetoothtest", Log.getStackTraceString(e));
            }

            Looper.loop();
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        nxt = new Nxt();

        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        
        Button b = new Button(this);
        b.setText("Connexion au NXT");

        layout.addView(b);
        setContentView(layout);

        if(!nxt.isPaired())
            Toast.makeText(MainActivity.this, "Le NXT n'est pas associé!", Toast.LENGTH_SHORT).show();

        b.setOnClickListener(new OnClickListener()
            {
                public void onClick(View arg0) {
                    MainActivity.this.connexion = ProgressDialog.show(MainActivity.this, "", "Connexion...", true);
                    (new ConnectThread()).start();
                }
            });
    }
}
