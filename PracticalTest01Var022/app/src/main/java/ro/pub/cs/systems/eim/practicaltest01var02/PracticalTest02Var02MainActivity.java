package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02Var02MainActivity extends AppCompatActivity {

    private Button addButton, computeButton;
    private EditText nextTermEditText, allTermsEditText;
    private boolean firstNumber = true;
    private int cachedSum = 0;
    private boolean isDirty = false;
    private boolean alreadyRun = false;
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = null;
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcast_message");
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            Log.d("RECEIVER", message);
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.add_button:
                    String addString = nextTermEditText.getText().toString();
                    int numberToAdd;
                    try {
                        numberToAdd  = Integer.parseInt(addString);
                    } catch (NumberFormatException exc) {
                        Log.d("In clickListener", "Exception at number format");
                        exc.printStackTrace();
                        return;
                    }

                    if (firstNumber == true) {
                        allTermsEditText.setText(Integer.toString(numberToAdd));
                        firstNumber = false;
                    } else {
                        String allText = allTermsEditText.getText().toString();
                        allTermsEditText.setText(allText + "+" + Integer.toString(numberToAdd));
                    }
                    isDirty = true;
                    break;
                case R.id.compute_button:
                    if (isDirty == true) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest02Var02SecondaryActivity.class);
                        intent.putExtra("string_to_compute", allTermsEditText.getText().toString());
                        startActivityForResult(intent, 2016);
                    } else {
                        Toast.makeText(getApplicationContext(), "Result is cached: " + cachedSum, Toast.LENGTH_LONG).show();
                    }

                    break;
                }
            }

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_var02_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addButton = (Button)findViewById(R.id.add_button);
        addButton.setOnClickListener(buttonClickListener);
        computeButton = (Button)findViewById(R.id.compute_button);
        computeButton.setOnClickListener(buttonClickListener);
        nextTermEditText = (EditText)findViewById(R.id.next_term_edittext);
        allTermsEditText = (EditText)findViewById(R.id.all_terms_edittext);

        intentFilter.addAction("broadcast_message_action");

        isDirty = true;


        messageBroadcastReceiver =  new MessageBroadcastReceiver();


    }


    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int result = data.getIntExtra("computed_result", -1);
        isDirty = false;
        cachedSum = result;
        Toast.makeText(getApplicationContext(), "Result is: " + result, Toast.LENGTH_LONG).show();

        if (alreadyRun == false && cachedSum > 10) {
            alreadyRun = true;
            Intent intent = new Intent(getApplicationContext(), PracticalTest02Var02Service.class);
            intent.putExtra("key_sum", cachedSum);
            startService(intent);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        cachedSum = savedInstanceState.getInt("cached_sum");
        Toast.makeText(getApplicationContext(), "cachedSum is restored: " + cachedSum, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("cached_sum", cachedSum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test02_var02_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest02Var02Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
