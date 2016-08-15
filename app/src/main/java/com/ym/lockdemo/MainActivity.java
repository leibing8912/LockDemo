package com.ym.lockdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Bank bank = new Bank(2, 100);
        for (int i = 0;i<10;i++){
            // 转账
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bank.transfer(0, 1, 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            // 存钱
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bank.save(0,50);
                }
            }).start();
        }

    }
}
