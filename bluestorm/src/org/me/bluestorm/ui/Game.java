package org.me.bluestorm.ui;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.me.bluestorm.Bluestorm;
import org.me.bluestorm.com.Nxt;
import org.me.bluestorm.music.Partition;

/**
 *
 * @author jiboo
 */
public class Game extends LinearLayout implements OnClickListener {

    Bluestorm activity;
    Button    cycleClaw;
    Button    disconnect;
    Button    tone;
    Button    song;

    TextView ball_label;
    CheckBox  ball;
    //etat du capteur d'intensité lumineuse
    CheckBox  floor;
    
    boolean clawState;

    TextView bat_label;
    ProgressBar battery;

    TextView motorA;
    TextView motorB;

    Thread updateThread;

    public Game(Bluestorm con) {
        super(con);
        activity = con;

        this.setOrientation(LinearLayout.VERTICAL);

        cycleClaw = new Button(con);
        cycleClaw.setText("Pince");
        cycleClaw.setOnClickListener(this);

        disconnect = new Button(con);
        disconnect.setText("Deconnection");
        disconnect.setOnClickListener(this);

        tone = new Button(con);
        tone.setText("Bip");
        tone.setOnClickListener(this);

        song = new Button(con);
        song.setText("Musique");
        song.setOnClickListener(this);

        ball = new CheckBox(activity);
        ball.setClickable(false);
        ball.setChecked(false);

        floor = new CheckBox(activity);
        floor.setClickable(false);
        floor.setChecked(false);

        battery = new ProgressBar(con, null, android.R.attr.progressBarStyleHorizontal);

        motorA = new TextView(con);
        motorB = new TextView(con);

        bat_label = new TextView(con);
        bat_label.setText("Batterie:");
        ball_label = new TextView(con);
        ball_label.setText("Pression:");

        LinearLayout row1 = new LinearLayout(con);
        row1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout row2 = new LinearLayout(con);
        row2.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout row3 = new LinearLayout(con);
        row3.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout row4 = new LinearLayout(con);
        row4.setOrientation(LinearLayout.HORIZONTAL);

        row1.addView(cycleClaw);
        row1.addView(disconnect);
        row1.addView(tone);
        row1.addView(song);
        
        row2.addView(bat_label);
        row2.addView(battery);

        row3.addView(ball_label);
        row3.addView(ball);
        
        row4.addView(motorA);
        row4.addView(motorB);

        addView(row1);
        addView(row2);
        addView(row3);
        addView(row4);
    }

    public void start() {
        updateThread = new Thread() {
            @Override
            public void run() {
                int count = 100;
                while(true) {
                    try {
                        // Les modules permettent d'eviter de faire trop de demande sur le bluetooth (send_read est synchronized)

                        if(count % 5 == 0) {
                            final boolean ball_val = activity.getNxt().gotBall();
                            final boolean floor_val = activity.getNxt().gotBall();
                            activity.runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    ball.setChecked(ball_val);
                                    floor.setChecked(floor_val);
                                }
                            });
                        }

                        if(count % 50 == 0) {
                            battery.setProgress((int)(activity.getNxt().getBatteryLevel() * 100));
                        }
                        
                        activity.runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                int[] motors = activity.getNxt().getMotors();
                                motorA.setText("Moteur A: " + String.format("%+3d", motors[Nxt.PORT_A]));
                                motorB.setText(" Moteur B: " + String.format("%+3d", motors[Nxt.PORT_B]));
                            }
                        });

                        count++;
                        sleep(100);
                    }
                    catch(InterruptedException e) {
                        break;
                    }
                    catch(Exception e) {
                        Log.d("bluestorm", "Caught in update thread", e);
                    }
                }
            }
        };
        updateThread.start();
    }

    public void stop() {
        if(updateThread != null)
            updateThread.interrupt();
        updateThread = null;
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
