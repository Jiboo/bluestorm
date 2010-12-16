/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bluestorm;

import android.util.Log;
import java.io.IOException;

/**
 *
 * @author jiboo
 */
public class Partition {    
    public Note[] notes;

    public int tempo;

    public Partition(int t, Note[] pNotes) {
        notes = pNotes;
        tempo = t;
    }

    public void play(final INxt nxt) throws IOException
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    for(Note note : notes)
                    {
                        double temps = (note.delay / (double)tempo) * 60000.0;
                        nxt.emitTone((int)(note.note * (double)note.mul), (int)((temps/4.)*3.));
                        Thread.sleep((int)temps/4);
                    }
                }
                catch(Exception e)
                {
                    Log.e("bluestorm", "erreur lors de la lecture de la partition", e);
                }
            }
        }.start();
    }
}
