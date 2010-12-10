/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluetoothtest;

import java.io.IOException;

/**
 *
 * @author Jiboo
 */
public interface INxt
{
    void setSpeed(byte pLeftSpeed, byte pRightSpeed) throws IOException;

    void stop() throws IOException;
    void goForward() throws IOException;
    void goBackward() throws IOException;
    void turnLeft() throws IOException;
    void turnRight() throws IOException;

    void openClaw() throws IOException, InterruptedException;
    void closeClaw() throws IOException, InterruptedException;

    boolean gotBall() throws IOException;
    short getObstacleDistance() throws IOException;

    /**
     * @return le niveau de la batterie [0.0-1.0]
     * @throws IOException erreur sur le socket
     */
    double getBatteryLevel() throws IOException;

    /**
     * Demande au NXT d'emmetre un beep
     * @param pFreq [200-14000] en Hz
     * @param pDuration en ms
     * @throws IOException erreur sur le socket
     */
    void emitTone(int pFreq, int pDuration) throws IOException;
}
