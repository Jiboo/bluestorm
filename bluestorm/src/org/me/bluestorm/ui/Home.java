package org.me.bluestorm.ui;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author jiboo
 */
public class Home extends LinearLayout
{
    TextView text;
    Button connexion;

    public Home(Context con)
    {
        super(con);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        this.connexion = new Button(con);
        this.connexion.setText("Connexion");

        this.text = new TextView(con);
        this.text.setText("Vous devez avoir le NXT associé, et son nom doit être \"NXT\"");

        this.addView(this.text);
        this.addView(this.connexion);
    }
}
