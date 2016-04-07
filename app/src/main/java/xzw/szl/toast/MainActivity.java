package xzw.szl.toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import xzw.szl.toast.library.SimpleWindowToast;

public class MainActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleWindowToast.showToast(getApplicationContext(), "I have click " + (++count) + " time(s)");
            }
        });
    }

}
