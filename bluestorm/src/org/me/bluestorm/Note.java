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
        public static final float DO = 0;
        public static final float DOSharp = 0;
        public static final float  RE = 0;
        public static final float RESharp = 0;
        public static final float MI = 0;
        public static final float FA = 0;
        public static final float FASharp = 0;
        public static final float SOL = 0;
        public static final float LA = 0;
        public static final float LASharp = 0;
        public static final float SI = 0;

        public float note;
        public int delay;
        public int mul;

        public Note(float n, int d, int m)
        {
            note = n; delay = d; mul = m;
        }
}
