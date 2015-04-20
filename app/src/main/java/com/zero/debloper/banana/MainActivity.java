package com.zero.debloper.banana;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.zero.debloper.banana.R;

/**
 * Created by debloper on 20/4/15.
 */
public class MainActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();// "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    private void setToggleButtonState()
    {
        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggle_button);
        TextReader textReader = TextReader.getTextReader();
        if(textReader != null)
            toggleButton.setChecked(true);
        else
            toggleButton.setChecked(false);

    }

    public void onToggleButtonClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable TextReader
            Log.v(TAG, "ToggleButtonClicked START");
            TextReader.initTextReader(getApplicationContext());
        } else {
            // Disable TextReader
            Log.v(TAG, "ToggleButtonClicked STOP");
            TextReader.getTextReader().destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setToggleButtonState();
    }

    public void onCheckButtonClicked(View view) {
        Log.v(TAG, "CheckButtonClicked");
        TextReader textReader = TextReader.getTextReader();
        if (textReader != null)
            textReader.check();
    }
}
