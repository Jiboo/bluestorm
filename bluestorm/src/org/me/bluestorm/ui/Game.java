package org.me.bluestorm.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.me.bluestorm.Bluestorm;

/**
 *
 * @author jiboo
 */
public class Game extends LinearLayout implements OnClickListener {

    Bluestorm activity;
    Button cycleClaw;
    Button disconnect;
    Button tone;
    boolean clawState;

    public Game(Bluestorm con) {
        super(con);
        activity = con;

        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        cycleClaw = new Button(con);
        cycleClaw.setText("Pince");
        cycleClaw.setOnClickListener(this);

        disconnect = new Button(con);
        disconnect.setText("Deconnexion");
        disconnect.setOnClickListener(this);

        tone = new Button(con);
        tone.setText("Bip");
        tone.setOnClickListener(this);

        addView(cycleClaw);
        addView(disconnect);
    }

    public void onClick(View arg) {
        if(arg == cycleClaw) {
            /*new Thread() {
                @Override
                public void run() {*/
                    try {
                        if(clawState)
                            activity.getNxt().closeClaw();
                        else
                            activity.getNxt().openClaw();
                        clawState = !clawState;
                    }
                    catch(Exception e) {
                        Log.e("bluestorm", "Error while cycling claw", e);
                        activity.alert(e.getMessage());
                    }
  /*              }
            }.start();*/
        }
        else if(arg == disconnect) {
            activity.stop();
        }
        else if(arg == tone) {
            try {
                activity.getNxt().emitTone(400, 1000);
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
