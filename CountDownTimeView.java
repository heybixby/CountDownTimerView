package com.juexing.payassistant.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


import androidx.annotation.NonNull;

import com.juexing.payassistant.activity.login.RegisterActivity;

import java.lang.ref.WeakReference;

//待优化为了保存倒计时状态线程延迟一秒关闭所有的子线程
//qq也是如此

public class CountDownTimeView extends androidx.appcompat.widget.AppCompatButton {
    private  static  volatile int i=60;
    //用来保存倒计时时间的中转变量
    private  static  volatile int j=0;
    private static TimeHandler handler;
//每次启动活动判断i是否为0来读取倒计时时间
    public static int getI() {
        return i;
    }
    //RegisterActivity是调用该控件的活动需要在oncreate中将对象传入
public void  setActivity(RegisterActivity activity){
       handler=new TimeHandler(activity);
}
    public static void setI(int i) {
        CountDownTimeView.i = i;
    }
    //防止多次重复点击发送设置的变量
    private  static  volatile boolean send=true;
    private  class TimeHandler extends  Handler {
        WeakReference<RegisterActivity> timeActivityWeakReference;
 public TimeHandler(RegisterActivity registerActivity){
     this.timeActivityWeakReference = new WeakReference<>(registerActivity);

 }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            RegisterActivity activity=timeActivityWeakReference.get();//获取活动
            switch (msg.what){
                case 1:
                    if(i>0){
                        activity.getmessage. setText(i+"s");
                    }else {

                    }
                    break;
                case 2:
                    activity.getmessage. setText("重新获取");
                    break;
                case 3:
                    i=j-1;
                    j=0;
                    send=false;
                    handler.postDelayed(runnable,1000);

            }
        }
    };
    public CountDownTimeView(Context context) {
        super(context);
    }

    public CountDownTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
//网络请求短信成功后调用
    public void onStart(CountDownTimeView view){
      if(send){
          if(i==0){
              i=60;
          }
          handler.postDelayed(runnable,1000);
          view.setText(i+"s");
          send=false;
      }else {
              //为了显示不突兀自动减去一秒
              view.setText(i-1+"s");
              j=i;
              //将i设置为0是将所有线程运行完毕关闭释放内存
              i=0;
              handler.removeCallbacksAndMessages(null);
              //因为线程调度随机性所以要延迟一秒发送保证子线程关闭
              Message message=Message.obtain();
              message.what=3;
              handler.sendMessageDelayed(message,1000);

//          i=j;

      }

    }
 //设置成静态保证唯一性
    public static Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(i>0){
                i--;
                Message message=new Message();
                message.what=1;
                handler.handleMessage(message);
                handler.postDelayed(runnable,1000);

            }else {
                if(j!=0){

                }else {
                    send=true;
                    Message message=new Message();
                    message.what=2;
                    handler.handleMessage(message);
                }

            }
        }
    };
}
