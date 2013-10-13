package edu.rosehulman.tictactoextreme;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private List<Button> mButtons;
	private Button mNewButton;
	private Button mExitButton;
	private static final int[] BUTTON_IDS = { R.id.button11, R.id.button12,
			R.id.button13, R.id.button14, R.id.button15, R.id.button16,
			R.id.button17, R.id.button18, R.id.button19, R.id.button21,
			R.id.button22, R.id.button23, R.id.button24, R.id.button25,
			R.id.button26, R.id.button27, R.id.button28, R.id.button29,
			R.id.button31, R.id.button32, R.id.button33, R.id.button34,
			R.id.button35, R.id.button36, R.id.button37, R.id.button38,
			R.id.button39, R.id.button41, R.id.button42, R.id.button43,
			R.id.button44, R.id.button45, R.id.button46, R.id.button47,
			R.id.button48, R.id.button49, R.id.button51, R.id.button52,
			R.id.button53, R.id.button54, R.id.button55, R.id.button56,
			R.id.button57, R.id.button58, R.id.button59, R.id.button61,
			R.id.button62, R.id.button63, R.id.button64, R.id.button65,
			R.id.button66, R.id.button67, R.id.button68, R.id.button69,
			R.id.button71, R.id.button72, R.id.button73, R.id.button74,
			R.id.button75, R.id.button76, R.id.button77, R.id.button78,
			R.id.button79, R.id.button81, R.id.button82, R.id.button83,
			R.id.button84, R.id.button85, R.id.button86, R.id.button87,
			R.id.button88, R.id.button89, R.id.button91, R.id.button92,
			R.id.button93, R.id.button94, R.id.button95, R.id.button96,
			R.id.button97, R.id.button98, R.id.button99 };

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
		mButtons = new ArrayList<Button>();
		for (int id : BUTTON_IDS) {
			Button button = (Button) findViewById(id);
			button.setOnClickListener(this);
			if (id == 37 || id == 49 || id == 57 || id == 26 || id == 56) {
				button.setText(R.string.Mystery_PowerUp_String);
			} else {
				button.setText(R.string.Empty_String);
			}

			mButtons.add(button);
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
