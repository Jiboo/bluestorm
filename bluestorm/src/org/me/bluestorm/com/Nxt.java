/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm.com;
import android.bluetooth.*;
import android.util.Log;
import java.io.IOException;
import java.util.Formatter;
import java.util.UUID;

//#define assertex(CODE, MESSAGE)  try { CODE; } catch(Exception e) { Log.e("bluestorm", MESSAGE); }

/**
 *
 * @author root
 */
public class Nxt implements INxt {
    BluetoothAdapter adapter = null;
    BluetoothDevice dev = null;
    BluetoothSocket sock = null;

    boolean paired = false;

    public Nxt() throws IOException {
        adapter = BluetoothAdapter.getDefaultAdapter();

        for(BluetoothDevice tmp : adapter.getBondedDevices()) {
            if(tmp.getName().equals("NXT")) {
                dev = tmp;
                paired = true;
                Log.d("bluestorm", "NXT trouvé: " + tmp.getAddress());
            }
        }
    }

    public boolean isPaired() throws IOException {
        return paired;
    }

    public boolean isConnected() throws IOException {
        return sock.getOutputStream() != null;
    }

    public void connect() throws Exception {
        if(dev == null) throw new Exception("Pas de NXT associé!");

        Log.d("bluestorm", "Ouverture de la socket");
        try { sock = dev.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")); }
        catch(Exception e) { throw new Exception("Impossible d'ouvrir la socket, avez vous activé le bluetooth sur votre téléphone?"); }
        if(sock == null) throw new Exception("Problème lors de la création de la socket");

        Log.d("bluestorm", "Connexion...");
        try { sock.connect(); }
        catch(Exception e) { throw new Exception("Impossible de se connecter au robot, verifier qu'il soit allumé et que le bluetooth soit activé"); }

        setInputMode((byte)0, SENSOR_SWITCH, SENSOR_BOOLEANMODE);
    }

    public void close() {
        if(sock != null) {
            try { sock.close(); }
            catch(Exception e) { Log.e("bluestorm", "deconnexion impossible, lol"); }
        }
    }

    private void send(byte[] ba) throws IOException {
        sock.getOutputStream().write(ba);
        sock.getOutputStream().flush();

        StringBuilder sb = new StringBuilder();
        Formatter formater = new Formatter(sb);
        for(byte b : ba)
            formater.format("%2x ", b);
        Log.d("bluestorm", "Trame envoyée: " + sb.toString());
    }

    private byte[] read() throws IOException {
        byte[] size_header = new byte[2];
        if(sock.getInputStream().read(size_header) == -1) throw new IOException("Erreur lors de la lecture de l'entete");
        int size = size_header[0] + (size_header[1]<<8);

        byte[] ba = new byte[size];
        if(sock.getInputStream().read(ba) == -1) throw new IOException("Erreur lors de la lecture de la trame");

        StringBuilder sb = new StringBuilder();
        Formatter formater = new Formatter(sb);
        for(byte b : ba)
            formater.format("%2x ", b);
        Log.d("bluestorm", "Trame lu: " + sb.toString());

        return ba;
    }

    private void keepAlive() throws IOException {
        byte[] ba = {
            (byte)0x02,
            (byte)0x00,
            (byte)0x80, // Pas de réponse
            (byte)0x0D, // KeepAlive
        };
        send(ba);
    }

    public static final byte STATIC_SPEED = 100;

    public static final byte PORT_A = 0x00;
    public static final byte PORT_B = 0x01;
    public static final byte PORT_C = 0x02;

    public static final byte PORT_ALL = (byte)0xFF;

    public static final byte OUTPUT_MODE_MOTORON = 0x01;
    public static final byte OUTPUT_MODE_BRAKE = 0x02;
    public static final byte OUTPUT_MODE_REGULATED = 0x04;

    public static final byte REGUL_MODE_IDLE = 0x00;
    public static final byte REGUL_MODE_SPEED = 0x01;
    public static final byte REGUL_MODE_SYNC = 0x02;

    public static final byte RUN_STATE_IDLE = 0x00;
    public static final byte RUN_STATE_RAMPUP = 0x10;
    public static final byte RUN_STATE_RUNNING = 0x20;
    public static final byte RUN_STATE_RAMPDOWN = 0x40;

    public static final long LIMIT_FOREVER = 0;

    private void setOutputState(byte port, byte power, byte output_mode, byte regul_mode, byte turn_ratio, byte run_state, long limit) throws IOException {
        byte[] ba = {
            (byte)0x0c,
            (byte)0x00,
            (byte)0x80, // Pas de réponse
            (byte)0x04, // SetOutputState
            port,
            power,
            output_mode,
            regul_mode,
            turn_ratio,
            run_state,
            (byte)(limit & 0xFF), (byte)(limit>>8 & 0xFF), (byte)(limit>>16 & 0xFF), (byte)(limit>>24 & 0xFF),
        };
        send(ba);
    }

    public static final byte SENSOR_NONE = 0x00;
    public static final byte SENSOR_SWITCH = 0x01;
    public static final byte SENSOR_TEMPERATURE = 0x02;
    public static final byte SENSOR_REFLECTION = 0x03;
    public static final byte SENSOR_ANGLE = 0x04;
    public static final byte SENSOR_LIGHT_ACTIVE = 0x05;
    public static final byte SENSOR_LIGHT_INACTIVE = 0x06;
    public static final byte SENSOR_SOUND_DB = 0x07;
    public static final byte SENSOR_SOUND_DBA = 0x08;
    public static final byte SENSOR_CUSTOM = 0x09;
    public static final byte SENSOR_LOWSPEED = 0x0A;
    public static final byte SENSOR_LOWSPEED_9V = 0x0B;
    public static final byte SENSOR_NO_OF_SENSOR_TYPES = 0x0C;

    public static final byte SENSOR_RAWMOD = 0x00;
    public static final byte SENSOR_BOOLEANMODE = 0x20;
    public static final byte SENSOR_TRANSITIONCNTMODE = 0x40;
    public static final byte SENSOR_PERIODCOUNTERMODE = 0x60;
    public static final byte SENSOR_PCTFULLSCALEMODE = (byte)0x80;
    public static final byte SENSOR_CELCIUSMODE = (byte)0xA0;
    public static final byte SENSOR_FAHRENHEITMODE = (byte)0xC0;
    public static final byte SENSOR_ANGLESTEPSMODE = (byte)0xE0;
    public static final byte SENSOR_SLOPEMASK = (byte)0x1F;
    public static final byte SENSOR_MODEMASK = (byte)0xE0;

    private void setInputMode(byte port, byte sensor_type, byte sensor_mode) throws IOException {
        byte[] ba = {
            (byte)0x05,
            (byte)0x00,
            (byte)0x80, // Pas de réponse
            (byte)0x05, // SetInputMode
            port,
            sensor_type,
            sensor_mode
        };
        send(ba);
    }

    private void getInputValues(byte port) throws IOException {
        byte[] ba = {
            (byte)0x03,
            (byte)0x00,
            (byte)0x00, // Réponse
            (byte)0x07, // GetInputValues
            port
        };
        send(ba);
    }

    public void setSpeed(byte pLeftSpeed, byte pRightSpeed) throws IOException {
        setOutputState(PORT_A, (byte)(pRightSpeed), OUTPUT_MODE_MOTORON, REGUL_MODE_IDLE, (byte)(0), RUN_STATE_RUNNING, 0L);
        setOutputState(PORT_B, (byte)(pLeftSpeed), OUTPUT_MODE_MOTORON, REGUL_MODE_IDLE, (byte)(0), RUN_STATE_RUNNING, 0L);
    }

    public void stop() throws IOException {
        setOutputState(PORT_ALL, (byte)(0), OUTPUT_MODE_BRAKE, REGUL_MODE_IDLE, (byte)(0), RUN_STATE_IDLE, 0L);
    }

    public void goForward() throws IOException {
        setSpeed(STATIC_SPEED, STATIC_SPEED);
    }

    public void goBackward() throws IOException {
        setSpeed((byte)(-STATIC_SPEED), (byte)(-STATIC_SPEED));
    }

    public void turnLeft() throws IOException {
        setSpeed(STATIC_SPEED, (byte)(-STATIC_SPEED));
    }

    public void turnRight() throws IOException {
        setSpeed((byte)(-STATIC_SPEED), STATIC_SPEED);
    }

    public void openClaw() throws IOException, InterruptedException {
        setOutputState(PORT_C, (byte)(STATIC_SPEED), OUTPUT_MODE_MOTORON, REGUL_MODE_IDLE, (byte)(0), RUN_STATE_RUNNING, 0L);
        Thread.sleep(50);
        setOutputState(PORT_C, (byte)(0), OUTPUT_MODE_BRAKE, REGUL_MODE_IDLE, (byte)(0), RUN_STATE_IDLE, 0L);
    }

    public void closeClaw() throws IOException, InterruptedException {
        setOutputState(PORT_C, (byte)(-80), OUTPUT_MODE_MOTORON, REGUL_MODE_IDLE, (byte)(0), RUN_STATE_RUNNING, 0L);
    }

    public boolean gotBall() throws IOException {
        getInputValues((byte)0);
        byte[] rep = read();

        assert(rep[0] == (byte)0x2 && rep[1] == (byte)0x7 && rep[2] == (byte)0x0);

        return rep[12] == 1;
    }

    public short getObstacleDistance() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getBatteryLevel() throws IOException {
        byte[] ba = {
            (byte)0x02,
            (byte)0x00,
            (byte)0x00, // Réponse
            (byte)0x0B, // BatteryLevel
        };
        send(ba);

        byte[] rep = read();

        assert(rep[0] == (byte)0x2 && rep[1] == (byte)0xB && rep[2] == (byte)0x0);
        return (rep[3] + (rep[4]<<8)) / 9000.0;
    }

    public void emitTone(int pFreq, int pDur) throws IOException, InterruptedException {
        byte[] ba = {
            (byte)0x06,
            (byte)0x00,
            (byte)0x80, // Pas de réponse
            (byte)0x03, // Emit tone
            (byte)(pFreq & (short)0xFF), (byte)(pFreq>>8 & (short)0xFF), // Freq (UWORD)
            (byte)(pDur & (short)0xFF), (byte)(pDur>>8 & (short)0xFF) // Dur en ms (UWORD)
        };
        send(ba);
        Thread.sleep(pDur);
    }
}