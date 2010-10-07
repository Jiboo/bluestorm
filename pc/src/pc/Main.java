/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pc;

import lejos.nxt.*;

/**
 *
 * @author Jiboo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        TouchSensor touch = new TouchSensor(SensorPort.S1);

        while(! touch.isPressed())
        {
                try {
                        Thread.sleep(10);
                } catch (InterruptedException ie) {}
        }
    }

}
