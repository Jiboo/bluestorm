/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

/**
 *
 * @author jiboo
 */
public class Note {
        /*public static final double DO = 32.7;
        public static final double DOSharp = 34.6;
        public static final double RE = 36.7;
        public static final double RESharp = 38.9;
        public static final double MI = 41.2;
        public static final double FA = 43.7;
        public static final double FASharp = 46.2;
        public static final double SOL = 49;
        public static final double SOLSharp = 51.9;
        public static final double LA = 55;
        public static final double LASharp = 58.3;
        public static final double SI = 61.8;*/

        public static final double DO = 16.35;
        public static final double DOSharp = 17.32;
        public static final double RE = 18.35;
        public static final double RESharp = 19.45;
        public static final double MI = 20.60;
        public static final double FA = 21.83;
        public static final double FASharp = 23.12;
        public static final double SOL = 24.5;
        public static final double SOLSharp = 25.96;
        public static final double LA = 27.5;
        public static final double LASharp = 29.14;
        public static final double SI = 30.87;

        public static final double CROCHE = 0.5;
        public static final double NOIRE = 1;
        public static final double BLANCHE = 2;
        public static final double RONDE = 4;

        public double note;
        public double delay;
        public int mul;

        public Note(double n, double d, int m)
        {
            note = n; delay = d; mul = m;
        }
}
