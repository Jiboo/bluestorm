package org.me.bluestorm.ui;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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

        addView(cycleClaw);
        addView(disconnect);
        addView(tone);
        addView(song);
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
                Log.d("bluestorm", String.format("%b", activity.getNxt().hasFloor()));
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
