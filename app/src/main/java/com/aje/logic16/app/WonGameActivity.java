package com.aje.logic16.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.aje.logic16.app.GameLogic.GameScore;
import com.aje.logic16.app.HighScoreLogic.highScore;
import com.aje.logic16.app.serverApi.api;


import org.w3c.dom.Text;

/**
 * Created by Engin on 16.04.14.
 */
public class WonGameActivity extends Activity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.won_screen);
        TextView scoreText = (TextView) findViewById(R.id.actualScore);
        int score = GameScore.getInstance().getScore();
        scoreText.setText(new Integer(score).toString());
        EditText personName = (EditText)findViewById(R.id.namePerson);
        final Button addHighscore = (Button) findViewById(R.id.addHighscoreButton);

        personName.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    addHighscore.setText(R.string.add_highscore);
                } else
                {
                    addHighscore.setText(R.string.hi_back_to_start);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    public void add_highscore(View view) {

        EditText name = (EditText) findViewById(R.id.namePerson);
        String insertedName = name.getText().toString();

        if (insertedName.length() > 0)
        {
            TextView score = (TextView) findViewById(R.id.actualScore);

            api myApi = new api();
            myApi.addScore(new highScore(insertedName, GameScore.getInstance().getScore()));
        }

        Intent intent = new Intent(this,StartPage.class);
        startActivity(intent);
    }

    public void continue_game(View view) {
        Intent intent = new Intent(this,GameScreen.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(getBaseContext(),StartPage.class);
                            startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You want go to start page?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
