package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private GridLayout gridContainer;

    private TextView[][] textViewGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        gridContainer = (GridLayout)findViewById(R.id.container_grid);
        textViewGrid = new TextView[9][9];

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                TextView temp = new TextView(this);

                // Set styles for text views
                temp.setBackgroundColor(getResources().getColor(R.color.gridTextViewBackground));
                temp.setGravity(1);
                temp.setWidth(100);
                temp.setHeight(100);



                textViewGrid[i][j] = temp;
                gridContainer.addView(temp);
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


	}



}
