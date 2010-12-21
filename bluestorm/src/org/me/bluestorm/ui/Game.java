package org.me.bluestorm.ui;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import org.me.bluestorm.Bluestorm;
import org.me.bluestorm.music.Partition;

/**
 *
 * @author jiboo
 */
public class Game extends LinearLayout implements OnClickListener {

    Bluestorm activity;
    Button cycleClaw;
    Button disconnect;
    Button tone;
    Button song;
    boolean clawState;

    ProgressBar battery;

    Thread updateThread;

    public Game(Bluestorm con) {
        super(con);
        activity = con;

        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        cycleClaw = new Button(con);
        cycleClaw.setText("Claw");
        cycleClaw.setOnClickListener(this);

        disconnect = new Button(con);
        disconnect.setText("Disconnect");
        disconnect.setOnClickListener(this);

        tone = new Button(con);
        tone.setText("Beep");
        tone.setOnClickListener(this);

        song = new Button(con);
        song.setText("Song");
        song.setOnClickListener(this);

        battery = new ProgressBar(con, null, android.R.attr.progressBarStyleHorizontal);

        addView(cycleClaw);
        addView(disconnect);
        addView(tone);
        addView(song);
        addView(battery);
    }

    public void start() {
        updateThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true)
                    {
                        battery.setProgress((int)(activity.getNxt().getBatteryLevel() * 100));
                        // Mettre a jour les autres capteurs
                        sleep(500);
                    }
                }
                catch(Exception e) {
                    Log.d("bluestorm", "Caught in game update thread", e);
                }
            }
        };
        updateThread.start();
    }

    @Override
    protected void finalize() throws Throwable
    {
      updateThread.stop();
      super.finalize(); //not necessary if extending Object.
    }

    public void onClick(View arg) {
        try {
            if(arg == cycleClaw) {
                if(clawState)
                    activity.getNxt().closeClaw();
                else
                    activity.getNxt().openClaw();
                clawState = !clawState;
            }
            else if(arg == disconnect) {
                activity.stop();
            }
            else if(arg == tone) {
                activity.getNxt().emitTone(400, 1000);
            }
            else if(arg == song) {
                Partition.jinglebells.play(activity.getNxt());
            }
        }
        catch(Exception e) {
            Log.e("bluestorm", "Error while cycling claw", e);
            activity.alert(e.getMessage());
        }
    }
}
