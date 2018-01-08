package com.example.irfandavalsab.newboston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Irfandavalsab on 2/9/2015.
 */

public class Irfan extends Activity {

    int counter = 0;
    Button add, remove;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irfan);

        add = (Button) findViewById(R.id.bAdds);
        remove = (Button) findViewById(R.id.bRemoves);
        display = (TextView) findViewById(R.id.tvDisplay);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                display.setText("Count is " +counter);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                display.setText("Count is " +counter);
            }
        });
    }
}
