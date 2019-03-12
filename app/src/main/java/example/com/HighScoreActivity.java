package example.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;



public class HighScoreActivity extends AppCompatActivity {

    private TextView mScoreView;
    private int score = 0;
    private TextView highscoreview_text;
    private HashMap<String, Integer> highScoreList = new HashMap<>();
    private String text = "";
    private String nameCurrentPlayer = "Current player";
    private Button mQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        onBackPressed();
        highscoreview_text = (TextView) findViewById(R.id.highscoreview_text);
        score = getIntent().getExtras().getInt("score");
        mQuit = (Button)findViewById(R.id.quit);

        //Start of Button Listener for quit
        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //My logic for button quit goes here
                askForUserInputToQuit();
            }
        });
        //End of Button Listener for quit

        askForUserInput();
    }

    //Asks for name of user
    private void askForUserInput(){
        AlertDialog.Builder builder =   new AlertDialog.Builder(this);
        builder.setTitle("You have made it to highscore! ");

        // Set the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Sets the ok button
        // sets score & prints highscores
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                nameCurrentPlayer = input.getText().toString();
                setScore();
                printHighScores();
            }
        });
        builder.show();
    }

    //puts some players in the highscore list and adds the current player
    private void setScore(){
        mScoreView = (TextView) findViewById(R.id.score);
        mScoreView.setText("" + score);
        highScoreList.put("Bert",1);
        highScoreList.put("Anna", 2);
        //set name of current player
            highScoreList.put(nameCurrentPlayer, score);
    }

    //sorts highscore, highest first
    public void sortHighScores() {
        Object[] a = this.highScoreList.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
           String key = ((Map.Entry<String, Integer>) e).getKey();
           int value = ((Map.Entry<String, Integer>) e).getValue();
          this.text += "\n"+(((Map.Entry<String, Integer>) e).getKey() + " : "
                   + ((Map.Entry<String, Integer>) e).getValue()) +"\n";
        }
    }

    //Dialog asks user to quit
    private void askForUserInputToQuit(){
        AlertDialog.Builder builder =   new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to exit?");

        //sets the yes and no button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Exits the app
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Does nothing
            }
        });
        builder.show();
    }

    //prints out the name and score of the players
    public void printHighScores() {
       sortHighScores();
        highscoreview_text.setText(text);
    }
    @Override
    //Back button on app leads user to home
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
