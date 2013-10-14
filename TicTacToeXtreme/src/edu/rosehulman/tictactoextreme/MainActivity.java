package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button[][] mButtons = new Button[9][9];
	private Button mNewButton;
	private Button mExitButton;
	private static final int[][] BUTTON_IDS = {
            {R.id.button00,R.id.button01,R.id.button02,R.id.button03,R.id.button04,R.id.button05,R.id.button06,R.id.button07,R.id.button08},
            {R.id.button10,R.id.button11,R.id.button12,R.id.button13,R.id.button14,R.id.button15,R.id.button16,R.id.button17,R.id.button18},
            {R.id.button20,R.id.button21,R.id.button22,R.id.button23,R.id.button24,R.id.button25,R.id.button26,R.id.button27,R.id.button28},
            {R.id.button30,R.id.button31,R.id.button32,R.id.button33,R.id.button34,R.id.button35,R.id.button36,R.id.button37,R.id.button38},
            {R.id.button40,R.id.button41,R.id.button42,R.id.button43,R.id.button44,R.id.button45,R.id.button46,R.id.button47,R.id.button48},
            {R.id.button50,R.id.button51,R.id.button52,R.id.button53,R.id.button54,R.id.button55,R.id.button56,R.id.button57,R.id.button58},
            {R.id.button60,R.id.button61,R.id.button62,R.id.button63,R.id.button64,R.id.button65,R.id.button66,R.id.button67,R.id.button68},
            {R.id.button70,R.id.button71,R.id.button72,R.id.button73,R.id.button74,R.id.button75,R.id.button76,R.id.button77,R.id.button78},
            {R.id.button80,R.id.button81,R.id.button82,R.id.button83,R.id.button84,R.id.button85,R.id.button86,R.id.button87,R.id.button88},
    };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeGame();
		mNewButton = (Button) findViewById(R.id.New_Game_Button);
		mNewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initializeGame();

			}

		});

		mExitButton = (Button) findViewById(R.id.Exit_Button);
		mExitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}

		});
	}

	private void initializeGame() {

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                this.mButtons[i][j] = (Button) findViewById(BUTTON_IDS[i][j]);
                this.mButtons[i][j].setText(R.string.Empty_String);
                this.mButtons[i][j].setOnClickListener(this);
            }
        }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {


	}

}
