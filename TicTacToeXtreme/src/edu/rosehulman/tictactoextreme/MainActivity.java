package edu.rosehulman.tictactoextreme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {
	private Button[][] mButtons = new Button[9][9];
	private ImageButton mPower1Player1;
	private ImageButton mPower2Player1;
	private ImageButton mPower1Player2;
	private ImageButton mPower2Player2;
	private Button mNewButton;
	private Button mExitButton;
	private static final int[] IMAGE_BUTTON_IDS = { R.drawable.ic_2xredo,
			R.drawable.ic_bomb, R.drawable.ic_uno_cards_reverseredo };
	private static final int[][] BUTTON_IDS = {
			{ R.id.button00, R.id.button01, R.id.button02, R.id.button03,
					R.id.button04, R.id.button05, R.id.button06, R.id.button07,
					R.id.button08 },
			{ R.id.button10, R.id.button11, R.id.button12, R.id.button13,
					R.id.button14, R.id.button15, R.id.button16, R.id.button17,
					R.id.button18 },
			{ R.id.button20, R.id.button21, R.id.button22, R.id.button23,
					R.id.button24, R.id.button25, R.id.button26, R.id.button27,
					R.id.button28 },
			{ R.id.button30, R.id.button31, R.id.button32, R.id.button33,
					R.id.button34, R.id.button35, R.id.button36, R.id.button37,
					R.id.button38 },
			{ R.id.button40, R.id.button41, R.id.button42, R.id.button43,
					R.id.button44, R.id.button45, R.id.button46, R.id.button47,
					R.id.button48 },
			{ R.id.button50, R.id.button51, R.id.button52, R.id.button53,
					R.id.button54, R.id.button55, R.id.button56, R.id.button57,
					R.id.button58 },
			{ R.id.button60, R.id.button61, R.id.button62, R.id.button63,
					R.id.button64, R.id.button65, R.id.button66, R.id.button67,
					R.id.button68 },
			{ R.id.button70, R.id.button71, R.id.button72, R.id.button73,
					R.id.button74, R.id.button75, R.id.button76, R.id.button77,
					R.id.button78 },
			{ R.id.button80, R.id.button81, R.id.button82, R.id.button83,
					R.id.button84, R.id.button85, R.id.button86, R.id.button87,
					R.id.button88 }, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeGame();
		isWinner();
		mPower1Player1 = (ImageButton) findViewById(R.id.Power1forPlayer1);
		mPower2Player1 = (ImageButton) findViewById(R.id.Power2forPlayer1);
		mPower1Player2 = (ImageButton) findViewById(R.id.Power1forPlayer2);
		mPower2Player2 = (ImageButton) findViewById(R.id.Power2forPlayer2);
		
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
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.mButtons[i][j] = (Button) findViewById(BUTTON_IDS[i][j]);
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
	public void onClick(View v) {
		int vID = v.getId();
		for (int i = 1; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (vID == BUTTON_IDS[0][j]) {
					this.mButtons[0][j].setText(R.string.X_string);
					this.mButtons[0][j].setClickable(false);
					return;
				}
				if (vID == BUTTON_IDS[i][j]
						&& !this.mButtons[i - 1][j].isClickable()) {
					if (this.mButtons[i][j].getText().equals(
							R.string.Mystery_PowerUp_String)) {
						landedOnPowerUp();
					}
					this.mButtons[i][j].setText(R.string.X_string);
					this.mButtons[i][j].setClickable(false);
				}
			}
		}
	}

	private void landedOnPowerUp() {
		Random rand = new Random();
		mPower1Player1.setBackgroundResource(IMAGE_BUTTON_IDS[rand.nextInt(2)]);
		

	}

	private void isWinner() {

		// check columns
		for (int j = 0; j < 9; j++) {
			for (int k = 0; k < 9; k++) {
				if (isOccupied(this.mButtons[j][k])
						&& this.mButtons[j][k].getText().equals(
								this.mButtons[j][k + 1].getText())
						&& this.mButtons[j][k].equals(this.mButtons[j][k + 2])) {
					this.mButtons[j][k].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
					this.mButtons[j][k + 1].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
					this.mButtons[j][k + 2].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
				}
				if (isOccupied(this.mButtons[j][k])
						&& this.mButtons[j][k].getText().equals(
								this.mButtons[j + 1][k].getText())
						&& this.mButtons[j][k].equals(this.mButtons[j + 2][k])) {
					this.mButtons[j][k].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
					this.mButtons[j + 1][k].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
					this.mButtons[j + 2][k].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
				}
				if (isOccupied(this.mButtons[j][k])
						&& this.mButtons[j][k].getText().equals(
								this.mButtons[j + 1][k + 1].getText())
						&& this.mButtons[j][k]
								.equals(this.mButtons[j + 2][k + 2])) {
					this.mButtons[j][k].setBackgroundColor(getResources()
							.getColor(R.color.X_button));
					this.mButtons[j + 1][k + 1]
							.setBackgroundColor(getResources().getColor(
									R.color.X_button));
					this.mButtons[j + 2][k + 2]
							.setBackgroundColor(getResources().getColor(
									R.color.X_button));
				}
			}
		}

	}

	private boolean isOccupied(Button button) {
		if (button.isClickable()) {
			return false;
		} else {
			return true;
		}

	}

}
