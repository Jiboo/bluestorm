/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;

/**
 *
 * @author jiboo
 */
public class MainActivity extends Activity
{
    // TODO Initialiser cette variable, et appeler connect sur un evenement IHM
    Nxt nxt;

    public void applySpeed(float[] orientation) throws IOException
    {
        double leftPower, rightPower;

        double accel = (orientation[2]/50.) * 100;
        double orient = (orientation[1]/50.) * 50;

        leftPower = accel;
        rightPower = accel;

        if(orientation[1] > 0)
            leftPower -= orient;
        else
            rightPower += orient;

        if(leftPower > 100) leftPower = 100;
        if(leftPower < -100) leftPower = -100;
        if(rightPower > 100) rightPower = 100;
        if(rightPower < -100) rightPower = -100;

        Log.d("bluestorm", String.format("%.2f %.2f %.2f\n%3.0f %3.0f\n%3.0f %3.0f", orientation[0], orientation[1], orientation[2], accel, orient, leftPower, rightPower));
        this.nxt.setSpeed((byte)leftPower, (byte)rightPower);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here        
    }

}
