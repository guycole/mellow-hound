package net.braingang.mellow.hound.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.braingang.houndlib.Demo;
import net.braingang.mellow.hound.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("zzzzzzzzz");

        Demo demo = new Demo();
        demo.method1();
    }
}
