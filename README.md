# CountDownTimerView
android自定义倒计时短信发送视图
使用处理程序为了防止内存泄漏使用了弱引用，优点是在活动中退出销毁时倒计时依然进行重新创建进入活动会实时显示倒计时并重新倒计时。 getmessage.setActivity（this）;
        if（getmessage.getI（）<60）{
            if（getmessage.getI（）> 0）{
                getmessage.onStart（getmessage）;
            }其他{
                getmessage.setText（“重新获取”）;
            }
        }
在活动上创建中添加代码。getmessage是自定义控件实例化对象。
