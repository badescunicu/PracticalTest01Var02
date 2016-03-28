package ro.pub.cs.systems.eim.practicaltest01var02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PracticalTest02Var02SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_var02_secondary);

        Intent intent = getIntent();
        if (intent != null) {
            String toCompute = intent.getStringExtra("string_to_compute");
            int result = 0;
            for (String elem : toCompute.split("\\+")) {
                result += Integer.parseInt(elem);
            }

            intent.putExtra("computed_result", result);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
