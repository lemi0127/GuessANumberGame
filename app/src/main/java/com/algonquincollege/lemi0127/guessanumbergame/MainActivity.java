package com.algonquincollege.lemi0127.guessanumbergame;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;


public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    Random generator = new Random();
    int theNumber = generator.nextInt(1000);
    int guessCount = 0;
    int currentGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button guessButton = (Button) findViewById(R.id.guessButton);
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        final TextView userInput = (TextView) findViewById(R.id.userInput);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final TextView remainingGuesses = (TextView) findViewById(R.id.remainingGuesses);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessCount++;
                String guessInput = userInput.getText().toString();

               if(guessInput.equals( "") || !guessInput.matches("[0-9]+")) {
                   imageView.setImageResource(R.drawable.empty);
                   userInput.setError("Please enter a number between 1 - 1000");
               } else {
                   int currentGuess = Integer.valueOf(userInput.getText().toString());
                   guessButton.setVisibility(View.VISIBLE);

                   if(currentGuess < 1 || currentGuess > 1000){
                       imageView.setImageResource(R.drawable.minmax);
                       Toast.makeText(getApplicationContext(), "Please enter a number between 1 - 1000", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if(currentGuess < theNumber && guessCount <11){
                       imageView.setImageResource(R.drawable.toolow);
                       Toast.makeText(getApplicationContext(), "Too Low", Toast.LENGTH_SHORT).show();
                   }
                   if(currentGuess > theNumber && guessCount <11){
                       imageView.setImageResource(R.drawable.toohigh);
                       Toast.makeText(getApplicationContext(), "Too High", Toast.LENGTH_SHORT).show();
                   }
                   if(currentGuess == theNumber && guessCount <6){
                       imageView.setImageResource(R.drawable.superior);
                       Toast.makeText(getApplicationContext(), "Superior Win!", Toast.LENGTH_SHORT).show();
                       guessButton.setVisibility(View.GONE);
                   }
                   if(currentGuess == theNumber && guessCount <11){
                       imageView.setImageResource(R.drawable.win);
                       Toast.makeText(getApplicationContext(), "Excellent Win!", Toast.LENGTH_SHORT).show();
                       guessButton.setVisibility(View.GONE);
                   }
                   if(guessCount >= 1){
                       remainingGuesses.setText("Remaining Guesses:" + (10-guessCount));
                   }
                   if(guessCount > 11) {
                       imageView.setImageResource(R.drawable.toomany);
                       Toast.makeText(getApplicationContext(), "Out of guesses! Reset the game to play again!", Toast.LENGTH_SHORT).show();
                       guessButton.setVisibility(View.GONE);
                   }
               }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessCount = 0;
                remainingGuesses.setText("");
                theNumber = generator.nextInt(1000);
                userInput.setText("");
                imageView.setImageResource(R.drawable.main);
                guessButton.setVisibility(View.VISIBLE);
            }
        });

        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "Correct Number:" + theNumber, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show( getFragmentManager(), ABOUT_DIALOG_TAG );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
