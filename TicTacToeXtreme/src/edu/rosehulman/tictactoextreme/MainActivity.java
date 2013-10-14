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
<<<<<<< HEAD
		mButtons = new ArrayList<Button>();
		for (int id : BUTTON_IDS) {
			Button button = (Button) findViewById(id);
			button.setOnClickListener(this);
			mButtons.add(button);
		}
=======

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                this.mButtons[i][j] = (Button) findViewById(BUTTON_IDS[i][j]);
                this.mButtons[i][j].setText(R.string.Empty_String);
                this.mButtons[i][j].setOnClickListener(this);
            }
        }

>>>>>>> df17389cee0386cbe5aceced8643f4acedf209cc
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button11:
			mButtons.get(0).setText(R.string.X_string);
			mButtons.get(0).setClickable(false);
			break;
		case R.id.button12:
			mButtons.get(1).setText(R.string.X_string);
			mButtons.get(1).setClickable(false);
			break;
		case R.id.button13:
			mButtons.get(2).setText(R.string.X_string);
			mButtons.get(2).setClickable(false);
			break;
		case R.id.button14:
			mButtons.get(3).setText(R.string.X_string);
			mButtons.get(3).setClickable(false);
			break;
		case R.id.button15:
			mButtons.get(4).setText(R.string.X_string);
			mButtons.get(4).setClickable(false);
			break;
		case R.id.button16:
			mButtons.get(5).setText(R.string.X_string);
			mButtons.get(5).setClickable(false);
			break;
		case R.id.button17:
			mButtons.get(6).setText(R.string.X_string);
			mButtons.get(6).setClickable(false);
			break;
		case R.id.button18:
			mButtons.get(7).setText(R.string.X_string);
			mButtons.get(7).setClickable(false);
			break;
		case R.id.button19:
			mButtons.get(8).setText(R.string.X_string);
			mButtons.get(8).setClickable(false);
			break;
		case R.id.button21:
			if (mButtons.get(0).isClickable()) {

			} else {
				mButtons.get(9).setText(R.string.X_string);
				mButtons.get(9).setClickable(false);
			}

		case R.id.button22:
			if (mButtons.get(1).isClickable()) {

			} else {
				mButtons.get(10).setText(R.string.X_string);
				mButtons.get(10).setClickable(false);
			}
		case R.id.button23:
			if (mButtons.get(2).isClickable()) {

			} else {
				mButtons.get(11).setText(R.string.X_string);
				mButtons.get(11).setClickable(false);
			}
		case R.id.button24:
			if (mButtons.get(3).isClickable()) {

			} else {
				mButtons.get(12).setText(R.string.X_string);
				mButtons.get(12).setClickable(false);
			}
		case R.id.button25:
			if (mButtons.get(4).isClickable()) {

			} else {
				mButtons.get(13).setText(R.string.X_string);
				mButtons.get(13).setClickable(false);
			}
		case R.id.button26:
			if (mButtons.get(5).isClickable()) {

			} else {
				mButtons.get(14).setText(R.string.X_string);
				mButtons.get(14).setClickable(false);
			}
		case R.id.button27:
			if (mButtons.get(6).isClickable()) {

			} else {
				mButtons.get(15).setText(R.string.X_string);
				mButtons.get(15).setClickable(false);
			}
		case R.id.button28:
			if (mButtons.get(7).isClickable()) {

			} else {
				mButtons.get(16).setText(R.string.X_string);
				mButtons.get(16).setClickable(false);
			}
		case R.id.button29:
			if (mButtons.get(8).isClickable()) {

			} else {
				mButtons.get(17).setText(R.string.X_string);
				mButtons.get(17).setClickable(false);
			}
		case R.id.button31:
			if (mButtons.get(9).isClickable()) {

			} else {
				mButtons.get(18).setText(R.string.X_string);
				mButtons.get(18).setClickable(false);
			}
		case R.id.button32:
			if (mButtons.get(10).isClickable()) {

			} else {
				mButtons.get(19).setText(R.string.X_string);
				mButtons.get(19).setClickable(false);
			}
		case R.id.button33:
			if (mButtons.get(11).isClickable()) {

			} else {
				mButtons.get(20).setText(R.string.X_string);
				mButtons.get(20).setClickable(false);
			}
		case R.id.button34:
			if (mButtons.get(12).isClickable()) {

			} else {
				mButtons.get(21).setText(R.string.X_string);
				mButtons.get(21).setClickable(false);
			}
		case R.id.button35:
			if (mButtons.get(13).isClickable()) {

			} else {
				mButtons.get(22).setText(R.string.X_string);
				mButtons.get(22).setClickable(false);
			}
		case R.id.button36:
			if (mButtons.get(14).isClickable()) {

			} else {
				mButtons.get(23).setText(R.string.X_string);
				mButtons.get(23).setClickable(false);
			}
		case R.id.button37:
			if (mButtons.get(15).isClickable()) {

			} else {
				mButtons.get(24).setText(R.string.X_string);
				mButtons.get(24).setClickable(false);
			}
		case R.id.button38:
			if(mButtons.get(16).isClickable()){
				
			} else {
				mButtons.get(25).setText(R.string.X_string);
				mButtons.get(25).setClickable(false);
			}
		case R.id.button39:
			if(mButtons.get(17).isClickable()){
				
			} else {
				mButtons.get(26).setText(R.string.X_string);
				mButtons.get(26).setClickable(false);
			}
		case R.id.button41:
			if(mButtons.get(18).isClickable()){
				
			} else {
				mButtons.get(27).setText(R.string.X_string);
				mButtons.get(27).setClickable(false);
			}
		case R.id.button42:
			if(mButtons.get(19).isClickable()){
				
			} else {
				mButtons.get(28).setText(R.string.X_string);
				mButtons.get(28).setClickable(false);
			}
		case R.id.button43:
			if(mButtons.get(20).isClickable()){
				
			} else {
				mButtons.get(29).setText(R.string.X_string);
				mButtons.get(29).setClickable(false);
			}
		case R.id.button44:
			if(mButtons.get(21).isClickable()){
				
			} else {
				mButtons.get(30).setText(R.string.X_string);
				mButtons.get(30).setClickable(false);
			}
		case R.id.button45:
			if(mButtons.get(22).isClickable()){
				
			} else {
				mButtons.get(31).setText(R.string.X_string);
				mButtons.get(31).setClickable(false);
			}
		case R.id.button46:
			if(mButtons.get(23).isClickable()){
				
			} else {
				mButtons.get(32).setText(R.string.X_string);
				mButtons.get(32).setClickable(false);
			}
		case R.id.button47:
			if(mButtons.get(24).isClickable()){
				
			} else {
				mButtons.get(33).setText(R.string.X_string);
				mButtons.get(33).setClickable(false);
			}
		case R.id.button48:
			if(mButtons.get(25).isClickable()){
				
			} else {
				mButtons.get(34).setText(R.string.X_string);
				mButtons.get(34).setClickable(false);
			}
		case R.id.button49:
			if(mButtons.get(26).isClickable()){
				
			} else {
				mButtons.get(35).setText(R.string.X_string);
				mButtons.get(35).setClickable(false);
			}
		case R.id.button51:
			if(mButtons.get(27).isClickable()){
				
			} else {
				mButtons.get(36).setText(R.string.X_string);
				mButtons.get(36).setClickable(false);
			}
		case R.id.button52:
			if(mButtons.get(28).isClickable()){
				
			} else {
				mButtons.get(37).setText(R.string.X_string);
				mButtons.get(37).setClickable(false);
			}
		case R.id.button53:
			if(mButtons.get(29).isClickable()){
				
			} else {
				mButtons.get(38).setText(R.string.X_string);
				mButtons.get(38).setClickable(false);
			}
		case R.id.button54:
			if(mButtons.get(30).isClickable()){
				
			} else {
				mButtons.get(39).setText(R.string.X_string);
				mButtons.get(39).setClickable(false);
			}
		case R.id.button55:
			if(mButtons.get(31).isClickable()){
				
			} else {
				mButtons.get(40).setText(R.string.X_string);
				mButtons.get(40).setClickable(false);
			}
		case R.id.button56:
			if(mButtons.get(32).isClickable()){
				
			} else {
				mButtons.get(41).setText(R.string.X_string);
				mButtons.get(41).setClickable(false);
			}
		case R.id.button57:
			if(mButtons.get(33).isClickable()){
				
			} else {
				mButtons.get(42).setText(R.string.X_string);
				mButtons.get(42).setClickable(false);
			}
		case R.id.button58:
			if(mButtons.get(34).isClickable()){
				
			} else {
				mButtons.get(43).setText(R.string.X_string);
				mButtons.get(43).setClickable(false);
			}
		case R.id.button59:
			if(mButtons.get(35).isClickable()){
				
			} else {
				mButtons.get(44).setText(R.string.X_string);
				mButtons.get(44).setClickable(false);
			}
		case R.id.button61:
			if(mButtons.get(36).isClickable()){
				
			} else {
				mButtons.get(45).setText(R.string.X_string);
				mButtons.get(45).setClickable(false);
			}
		case R.id.button62:
			if(mButtons.get(37).isClickable()){
				
			} else {
				mButtons.get(46).setText(R.string.X_string);
				mButtons.get(46).setClickable(false);
			}
		case R.id.button63:
			if(mButtons.get(38).isClickable()){
				
			} else {
				mButtons.get(47).setText(R.string.X_string);
				mButtons.get(47).setClickable(false);
			}
		case R.id.button64:
			if(mButtons.get(39).isClickable()){
				
			} else {
				mButtons.get(48).setText(R.string.X_string);
				mButtons.get(48).setClickable(false);
			}
		case R.id.button65:
			if(mButtons.get(40).isClickable()){
				
			} else {
				mButtons.get(49).setText(R.string.X_string);
				mButtons.get(49).setClickable(false);
			}
		case R.id.button66:
			if(mButtons.get(41).isClickable()){
				
			} else {
				mButtons.get(50).setText(R.string.X_string);
				mButtons.get(50).setClickable(false);
			}
		case R.id.button67:
			if(mButtons.get(42).isClickable()){
				
			} else {
				mButtons.get(51).setText(R.string.X_string);
				mButtons.get(51).setClickable(false);
			}
		case R.id.button68:
			if(mButtons.get(43).isClickable()){
				
			} else {
				mButtons.get(52).setText(R.string.X_string);
				mButtons.get(52).setClickable(false);
			}
		case R.id.button69:
			if(mButtons.get(44).isClickable()){
				
			} else {
				mButtons.get(53).setText(R.string.X_string);
				mButtons.get(53).setClickable(false);
			}
		case R.id.button71:
			if(mButtons.get(45).isClickable()){
				
			} else {
				mButtons.get(54).setText(R.string.X_string);
				mButtons.get(54).setClickable(false);
			}
		case R.id.button72:
			if(mButtons.get(46).isClickable()){
				
			} else {
				mButtons.get(55).setText(R.string.X_string);
				mButtons.get(55).setClickable(false);
			}
		case R.id.button73:
			if(mButtons.get(47).isClickable()){
				
			} else {
				mButtons.get(56).setText(R.string.X_string);
				mButtons.get(56).setClickable(false);
			}
		case R.id.button74:
			if(mButtons.get(48).isClickable()){
				
			} else {
				mButtons.get(57).setText(R.string.X_string);
				mButtons.get(57).setClickable(false);
			}
		case R.id.button75:
			if(mButtons.get(49).isClickable()){
				
			} else {
				mButtons.get(58).setText(R.string.X_string);
				mButtons.get(58).setClickable(false);
			}
		case R.id.button76:
			if(mButtons.get(50).isClickable()){
				
			} else {
				mButtons.get(59).setText(R.string.X_string);
				mButtons.get(59).setClickable(false);
			}
		case R.id.button77:
			if(mButtons.get(51).isClickable()){
				
			} else {
				mButtons.get(60).setText(R.string.X_string);
				mButtons.get(60).setClickable(false);
			}
		case R.id.button78:
			if(mButtons.get(52).isClickable()){
				
			} else {
				mButtons.get(61).setText(R.string.X_string);
				mButtons.get(61).setClickable(false);
			}
		case R.id.button79:
			if(mButtons.get(53).isClickable()){
				
			} else {
				mButtons.get(62).setText(R.string.X_string);
				mButtons.get(62).setClickable(false);
			}
		case R.id.button81:
			if(mButtons.get(54).isClickable()){
				
			} else {
				mButtons.get(63).setText(R.string.X_string);
				mButtons.get(63).setClickable(false);
			}
		case R.id.button82:
			if(mButtons.get(55).isClickable()){
				
			} else {
				mButtons.get(64).setText(R.string.X_string);
				mButtons.get(64).setClickable(false);
			}
		case R.id.button83:
			if(mButtons.get(56).isClickable()){
				
			} else {
				mButtons.get(65).setText(R.string.X_string);
				mButtons.get(65).setClickable(false);
			}
		case R.id.button84:
			if(mButtons.get(57).isClickable()){
				
			} else {
				mButtons.get(66).setText(R.string.X_string);
				mButtons.get(66).setClickable(false);
			}
		case R.id.button85:
			if(mButtons.get(58).isClickable()){
				
			} else {
				mButtons.get(67).setText(R.string.X_string);
				mButtons.get(67).setClickable(false);
			}
		case R.id.button86:
			if(mButtons.get(59).isClickable()){
				
			} else {
				mButtons.get(68).setText(R.string.X_string);
				mButtons.get(68).setClickable(false);
			}
		case R.id.button87:
			if(mButtons.get(60).isClickable()){
				
			} else {
				mButtons.get(69).setText(R.string.X_string);
				mButtons.get(69).setClickable(false);
			}
		case R.id.button88:
			if(mButtons.get(61).isClickable()){
				
			} else {
				mButtons.get(70).setText(R.string.X_string);
				mButtons.get(70).setClickable(false);
			}
		case R.id.button89:
			if(mButtons.get(62).isClickable()){
				
			} else {
				mButtons.get(71).setText(R.string.X_string);
				mButtons.get(71).setClickable(false);
			}
		case R.id.button91:
			if(mButtons.get(63).isClickable()){
				
			} else {
				mButtons.get(72).setText(R.string.X_string);
				mButtons.get(72).setClickable(false);
			}
		case R.id.button92:
			if(mButtons.get(64).isClickable()){
				
			} else {
				mButtons.get(73).setText(R.string.X_string);
				mButtons.get(73).setClickable(false);
			}
		case R.id.button93:
			if(mButtons.get(65).isClickable()){
				
			} else {
				mButtons.get(74).setText(R.string.X_string);
				mButtons.get(74).setClickable(false);
			}
		case R.id.button94:
			if(mButtons.get(66).isClickable()){
				
			} else {
				mButtons.get(75).setText(R.string.X_string);
				mButtons.get(75).setClickable(false);
			}
		case R.id.button95:
			if(mButtons.get(67).isClickable()){
				
			} else {
				mButtons.get(76).setText(R.string.X_string);
				mButtons.get(76).setClickable(false);
			}
		case R.id.button96:
			if(mButtons.get(68).isClickable()){
				
			} else {
				mButtons.get(77).setText(R.string.X_string);
				mButtons.get(77).setClickable(false);
			}
		case R.id.button97:
			if(mButtons.get(69).isClickable()){
				
			} else {
				mButtons.get(78).setText(R.string.X_string);
				mButtons.get(78).setClickable(false);
			}
		case R.id.button98:
			if(mButtons.get(70).isClickable()){
				
			} else {
				mButtons.get(79).setText(R.string.X_string);
				mButtons.get(79).setClickable(false);
			}
		case R.id.button99:
			if(mButtons.get(71).isClickable()){
				
			} else {
				mButtons.get(80).setText(R.string.X_string);
				mButtons.get(80).setClickable(false);
			}
		}
	}

	private boolean isWinner() {
		return false;
	}

<<<<<<< HEAD
	private boolean isOccupied(Button button) {
		return false;
=======

>>>>>>> df17389cee0386cbe5aceced8643f4acedf209cc
	}

}
