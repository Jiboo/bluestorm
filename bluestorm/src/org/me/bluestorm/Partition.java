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
                        Log.d("partition", String.format("%5d %5d", (int)(note.note * (double)note.mul), (int)((temps/4.)*3.)));
                        nxt.emitTone((int)(note.note * (double)note.mul), (int)((temps/5.)*4.));

                        Thread.sleep((int)temps/5);
                    }
                }
                catch(Exception e)
                {
                    Log.e("bluestorm", "erreur lors de la lecture de la partition", e);
                }
            }
        }.start();
    }

    public static Partition victory = new Partition(200, new Note[] {
        new Note(Note.DO, Note.CROCHE, 16),
        new Note(Note.DO, Note.CROCHE, 16),
        new Note(Note.DO, Note.CROCHE, 16),
        new Note(Note.DO, Note.NOIRE, 16),

        new Note(Note.SOLSharp, Note.NOIRE, 15),
        new Note(Note.LASharp, Note.NOIRE, 15),
        new Note(Note.DO, Note.NOIRE, 16),
        new Note(Note.LASharp, Note.CROCHE, 15),
        new Note(Note.DO, Note.NOIRE, 16),
    });

    public static Partition jinglebells = new Partition(100, new Note[] {
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.NOIRE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.NOIRE, 16),

        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.SOL, Note.CROCHE, 16),
        new Note(Note.DO, Note.CROCHE, 16),
        new Note(Note.RE, Note.CROCHE, 16),
        new Note(Note.MI, Note.NOIRE, 16),

        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.RE, Note.CROCHE, 16),
        new Note(Note.RE, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.RE, Note.CROCHE, 16),
        new Note(Note.SOL, Note.NOIRE, 16),

        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.NOIRE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.NOIRE, 16),

        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.SOL, Note.CROCHE, 16),
        new Note(Note.DO, Note.CROCHE, 16),
        new Note(Note.RE, Note.CROCHE, 16),
        new Note(Note.MI, Note.NOIRE, 16),

        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.MI, Note.CROCHE, 16),
        new Note(Note.SOL, Note.CROCHE, 16),
        new Note(Note.SOL, Note.CROCHE, 16),
        new Note(Note.FA, Note.CROCHE, 16),
        new Note(Note.RE, Note.CROCHE, 16),
        new Note(Note.DO, Note.RONDE, 16),
    });
}
