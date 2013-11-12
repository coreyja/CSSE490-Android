package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by coreyja on 11/12/13.
 */
public class InitialActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        ((Button)findViewById(R.id.start_game_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.help_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpDialog();
            }
        });
    }

    private void openHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.help_dialog_title);
        builder.setMessage(R.string.help_dialog_message);

        builder.create().show();
    }
}