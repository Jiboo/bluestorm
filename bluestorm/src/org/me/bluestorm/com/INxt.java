package org.me.bluestorm.com;

import java.io.IOException;

/**
 *
 * @author Jiboo
 */
public interface INxt
{
    /**
     * @return vrai si le telephone à un NXT associé
     * @throws IOException
     */
    public boolean isPaired() throws IOException;

    /**
     * @return vrai si le telehpone est connecté au NXT
     * @throws IOException
     */
    public boolean isConnected() throws IOException;

    /**
     * @return Vrai si le capteur de lumière (sous le robot) detecte le sol (de l'obscuritée)
     * @throws IOException
     */
    public boolean hasFloor() throws IOException;

    /**
     * Demmare la connection vers le NXT
     * @return Vrai si la connexion à réussi
     * @throws IOException
     */
    public void connect() throws Exception;

    /**
     * Stop la connexion avec le robot
     */
    public void close();


    /**
     * Fonction de bas niveau pour régler la puissance des roues
     * @param pLeftSpeed puissance de la roue gauche [-100; 100]
     * @param pRightSpeed puissance de la roue droite [-100; 100]
     * @throws IOException
     */
    void setSpeed(byte pLeftSpeed, byte pRightSpeed) throws IOException;

    /**
     * Arréte tout mouvement du robot
     * @throws IOException
     */
    void stop() throws IOException;

    /**
     * Regle la vitesse des roues de façon à ce que le robot avance
     * @throws IOException
     */
    void goForward() throws IOException;

    /**
     * Regle la vitesse des roues de façon à ce que le robot recule
     * @throws IOException
     */
    void goBackward() throws IOException;

    /**
     * Regle la vitesse des roues de façon à ce que le robot tourne sur le même dans le sens anti-horraire
     * @throws IOException
     */
    void turnLeft() throws IOException;

    /**
     * Regle la vitesse des roues de façon à ce que le robot tourne sur le même dans le sens horraire
     * @throws IOException
     */
    void turnRight() throws IOException;

    /**
     * Ouvre la pince du robot (pause la thread pendant 0.5s)
     * @throws IOException
     * @throws InterruptedException
     */
    void openClaw() throws IOException, InterruptedException;

    /**
     * Ferme la pince du robot (pause la thread pendant 1s)
     * @throws IOException
     * @throws InterruptedException
     */
    void closeClaw() throws IOException, InterruptedException;

    /**
     * @return vrai si le capteur de pression est appuyé
     * @throws IOException
     */
    boolean gotBall() throws IOException;

    /**
     * @return la distance de l'obstacle en face du robot (en m)
     * @throws IOException
     */
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
    void emitTone(int pFreq, int pDuration) throws IOException, InterruptedException;

    /**
     * @return la puissance de chaque moteur, 0=A 1=B ...
     */
    int[] getMotors();
}
