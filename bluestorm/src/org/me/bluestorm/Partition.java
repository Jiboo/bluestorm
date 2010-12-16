/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

import java.io.IOException;

/**
 *
 * @author jiboo
 */
public class Partition {
    
    public enum Notes
    {
        DO,
        DOSharp,
        RE,
        RESharp,
        MI,
        FA,
        FASharp,
        SOL,
        LA,
        LASharp,
        SI     
    }

    public class Note
    {
        public final Notes note;
        public final int delay;
        public final int mul;

        public Note(Notes n, int d, int m)
        {
            note = n; delay = d; mul = m;
        }
    }

    public Note[] notes;

    public Partition(Note[] pNotes) {
        notes = pNotes;
    }

    public void play(INxt nxt) throws IOException
    {
        
    }
}
