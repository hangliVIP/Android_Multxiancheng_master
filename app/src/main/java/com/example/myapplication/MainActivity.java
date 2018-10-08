package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_TEXT = 1;

    private TextView textView;
    private Button changeText;

    // 消息异步处理机制
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){ // 在这里接收handle消息，handleMessage（）方法在主线程中处理
            switch (msg.what) {
                case UPDATE_TEXT:
                    // 在这里进行UI操作
                    textView.setText("更新数据");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        changeText = (Button) findViewById(R.id.change);
        changeText.setOnClickListener(this);  // 设置button点击监听
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change:
                // 创建子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 1.0版本，这样写，在子线程中进行UI操作会导致奔溃
                        // textView.setText("更新数据");
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);  // 将Message对象发送出去
                    }
                }).start();
                break;
                default:
                    break;
        }
    }
}
