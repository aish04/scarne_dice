package com.example.android.scarnedice;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvComputerScore, tvStatus, tvComputerOverallScore, tvPlayerTurnScore, tvPlayerOverallScore;
    private ImageView ivDiceFace;
    private Button btHold, btRoll, btReset;

    private int[] diceFaces = {R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6};
    private int computerTurnScore,
            computerOverallScore,
            playerTurnScore,
            playerOverallScore;
    private Handler handler;
    private Runnable runnable;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPlayerTurnScore = (TextView) findViewById(R.id.tvTurnScore);
        tvPlayerOverallScore = (TextView) findViewById(R.id.tvPlayerOverallScore);
        tvComputerOverallScore = (TextView) findViewById(R.id.tvComputerOverallScore);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        ivDiceFace = (ImageView) findViewById(R.id.ivDiceFace);
        btHold = (Button) findViewById(R.id.btHold);
        btRoll = (Button) findViewById(R.id.btRoll);
        btReset = (Button) findViewById(R.id.btReset);

        btHold.setOnClickListener(this);
        btRoll.setOnClickListener(this);
        btReset.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btHold:
                hold();
                break;
            case R.id.btRoll:
                roll();
                break;
            case R.id.btReset:
                reset();

                break;
        }
    }

    private void roll() {

        btHold.setEnabled(true);
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        int rollNumber = getDiceFaceNumber();
        Toast.makeText(this, "Rolled:" + rollNumber, Toast.LENGTH_SHORT).show();
        if (rollNumber != 1) {
            playerTurnScore += rollNumber;
            tvPlayerTurnScore.setText("Your turn score is:" + playerTurnScore);
        } else {
            playerTurnScore = 0;
            tvPlayerTurnScore.setText("Your turn score is:" + playerTurnScore);
            computerTurn();
        }
    }

    private int getDiceFaceNumber() {
        Random random = new Random();
        int i = random.nextInt(6);
        ivDiceFace.setImageResource(diceFaces[i]);
        return i + 1;

    }

    private void playerTurn() {
        resetPlayerTurnScore();
        Toast.makeText(this, "Your Turn", Toast.LENGTH_SHORT).show();
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
    }

    private void hold() {

        playerOverallScore += playerTurnScore;
        playerTurnScore = 0;
        tvPlayerOverallScore.setText("Your overall score:" + playerOverallScore);
        computerTurn();
    }

    private void reset() {
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
        playerOverallScore = 0;
        computerOverallScore = 0;
        playerTurnScore = 0;
        computerTurnScore = 0;
        tvComputerOverallScore.setText("Computer's overall score: " + computerOverallScore);
        tvPlayerOverallScore.setText("Player's overall score:" + playerOverallScore);
        tvPlayerTurnScore.setVisibility(View.INVISIBLE);
        ivDiceFace.setImageResource(diceFaces[0]);
        resetPlayerTurnScore();

    }

    private void resetPlayerTurnScore() {

        playerTurnScore = 0;
        tvPlayerTurnScore.setText("");
    }

    private void computerTurn() {

        Toast.makeText(this, "Computer's Turn", Toast.LENGTH_SHORT).show();
        disableButtons();
        resetPlayerTurnScore();
        btRoll.setEnabled(false);
        btHold.setEnabled(false);
        final Random random = new Random();
        while (true) {

            int rollNumber = getDiceFaceNumber();
            Toast.makeText(this, "Rolled:" + rollNumber, Toast.LENGTH_SHORT).show();
            if (rollNumber != 1) {
                computerTurnScore += rollNumber;
                boolean hold = random.nextBoolean();
                if (hold) {
                    computerOverallScore += computerTurnScore;
                    break;
                }
            } else {
                computerTurnScore = 0;
                break;
            }

        }
        tvComputerOverallScore.setText("Computer overall score:" + computerOverallScore);
        playerTurn();


    }

    private void disableButtons() {
        btRoll.setEnabled(false);
        btHold.setEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.scarnedice/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.scarnedice/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    }

