package org.me.bluestorm.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.me.bluestorm.Bluestorm;

/**
 *
 * @author jiboo
 */
public class Home extends LinearLayout implements OnClickListener {

    Bluestorm activity;
    TextView text;
    Button connexion;

    public Home(Bluestorm con) {
        super(con);
        activity = con;

        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        connexion = new Button(con);
        connexion.setText("Connexion");
        connexion.setOnClickListener(this);

        text = new TextView(con);
        text.setText("Vous devez avoir le NXT associé, et son nom doit être \"NXT\"");

        addView(text);
        addView(connexion);
    }
    
    public void onClick(View arg) {
        if(arg == connexion) {
            Log.d("bluestorm", "Connexion button clicked!");
            activity.demarrerConnexion();
        }
    }
}
