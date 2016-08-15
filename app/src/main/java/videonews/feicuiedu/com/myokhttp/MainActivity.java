package videonews.feicuiedu.com.myokhttp;

import android.os.Handler;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
 * OkHttp
 * 1.了解Okhttp
 * 2.掌握Call模型的运用
 * 基本使用
 * call
 * 拦截器
 * Okhttp是square出的一个Http通讯库，用于Java和android应用
 * Android6.0后将HttpClent从SDK中移除全面转向使用OkHttp
 *知名的第三方框架呢都使用OKHttp作为网络连接技术
 * Okhttp是一个Java库
 * 以前用的网络连接
 * HttpUrlConnectionJava提供的代码又点复杂
 * HttpClient（阿帕奇）使用比较简单
 * Okhttp使用环境是SDK2.3以上JDK1.7以上
 */
public class MainActivity extends AppCompatActivity {
    //Okhttp使用
    //1.库的依赖
    //2.运用（初始化，构建Call模型，执行）
    //请求 ---- 响应

    //Okhttp的特性：重写，重定向，重试等操作，使得你一个简单请求，会出现多个请求和响应
    //重写:保证正确性和传输效率，Okhttp会在发送你的请求之前重写它
    //              请求中缺失的头信息(host)
    //重定向:Okhttp会跟随新的URL,获取到最终的响应。
    //重试:由于网络状态不好会主动给我们重连
    //就因为以上的特性
    //使得你一个简单请求，会出现多个请求和响应
    //但是这一次操作，不管多少请求响应，都叫一个Call

    //Call
    //Call可以在任意线程取消
    //Call有两种方式来执行
    //(同步的执行方法)直接在当前线程执行
    //其它线程执行

    //可添加各种拦截器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        Request request =  new Request.Builder().url("http://www.baidu.com").build();
        //构建一个Call
        Call call =okHttpClient.newCall(request);
        //Call有两种方法执行
        //Request request = call.execute();//不推荐使用ANR
        call.enqueue(callback);
    }

    private Callback callback = new Callback() {
        //失败
        @Override
        public void onFailure(Call call, IOException e) {
            Toast.makeText(getApplicationContext(),"failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        //成功
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response==null){
                Toast.makeText(getApplicationContext(),"未知错误",Toast.LENGTH_SHORT).show();
                return;
            }
            //如果响应码是200-299就是成功
                if(response.isSuccessful()){
                //响应头
//                  Headers headers = response.headers();
//                    response.header("");
                    //响应体
                    ResponseBody body = response.body();
                    Log.d("TAG",body.string());
                    return;
                }
            Toast.makeText(getApplicationContext(),"code:"+response.code(),Toast.LENGTH_SHORT).show();
        }
    };
}
