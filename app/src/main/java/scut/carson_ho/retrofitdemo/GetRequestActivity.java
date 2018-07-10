package scut.carson_ho.retrofitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRequestActivity extends AppCompatActivity {

    TextView tv;
    EditText et;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.oText);
        bt = (Button) findViewById(R.id.translate);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
                new Thread(new SocketTest()).start();
            }
        });
        this.registerForContextMenu(tv);
//        request();
    }

    public void request() {

        String str = et.getText().toString();
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation1> call = request.getCall((str.isEmpty() ? "hello world" : str));

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation1>() {
            //请求成功时候的回调
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                //请求处理,输出结果
                String src = response.body().getTranslateResult().get(0).get(0).getSrc();
                String target = response.body().getTranslateResult().get(0).get(0).getTgt();
                System.out.println(src + "，翻译为中文是：" + target);
//                Toast.makeText(GetRequestActivity.this, src + ":" + target, Toast.LENGTH_LONG).show();
                response.body().show();
                tv.setText(target);
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<Translation1> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }

    class SocketTest implements Runnable {
        private String host = "192.168.51.75";
        private int port = 3000;
        private Socket socket;

        @Override
        public void run() {
            try {
                socket = new Socket(host, port);
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg = et.getText().toString();

                InputStream in_code = new ByteArrayInputStream(msg.getBytes());
//                ByteArrayInputStream localReader = new ByteArrayInputStream(msg.getBytes());
//                int flag;
                BufferedReader bfr = new BufferedReader(new InputStreamReader(in_code));
                String flag;
                while ((flag = bfr.readLine()) != null) {
                    pw.println(flag);
                    System.out.println(br.readLine());
                    if (msg.equals("bye"))
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private PrintWriter getWriter(Socket socket) throws IOException {
            OutputStream socketOut = socket.getOutputStream();
            return new PrintWriter(socketOut, true);
        }

        private BufferedReader getReader(Socket socket) throws IOException {
            InputStream socketIn = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(socketIn));
        }
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("你想干啥？");
//        item_menu.setHeaderIcon(R.drawable.);
        menu.add(0, 0, Menu.NONE, "post");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, PostRequestActivity.class));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}

