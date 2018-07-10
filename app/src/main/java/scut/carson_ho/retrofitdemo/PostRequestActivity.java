package scut.carson_ho.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Carson_Ho on 17/3/21.
 */
public class PostRequestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request();
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);
        Call<Translation1> call = request.getCall("I love you");
        call.enqueue(new Callback<Translation1>() {
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                if (response.body() != null) {
                    String src = response.body().getTranslateResult().get(0).get(0).getSrc();
                    String target = response.body().getTranslateResult().get(0).get(0).getTgt();
                    System.out.println(src + "，翻译为中文是：" + target);
                    Toast.makeText(PostRequestActivity.this, src + ":" + target, Toast.LENGTH_LONG).show();
                }
                System.out.println("success");
            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {
                Toast.makeText(PostRequestActivity.this, "网络连接错误！", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //四个参数的含义。1，group的id,2,item的id,3,是否排序，4，将要显示的内容
//        item_menu.add(0, 1, 0, "菜单一");
//        item_menu.add(0, 2, 0, "菜单二");
//        item_menu.add(0, 3, 0, "菜单三");
//        item_menu.add(0, 4, 0, "菜单四");
//        SubMenu sub = item_menu.addSubMenu("子菜单");
//        sub.add(0, 5, 0, "子菜单一");
//        sub.add(0, 6, 0, "子菜单二");
//        sub.add(0, 7, 0, "子菜单三");

        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_one:
                startActivity(new Intent(this, GetRequestActivity.class));
                break;
            case R.id.group_two:
                Toast.makeText(PostRequestActivity.this, "菜单二", Toast.LENGTH_SHORT).show();
                break;
            case R.id.group_three:
                Toast.makeText(PostRequestActivity.this, "菜单三", Toast.LENGTH_SHORT).show();
                break;
            case R.id.child_one:
                Toast.makeText(PostRequestActivity.this, "子菜单一", Toast.LENGTH_SHORT).show();
                break;
            case R.id.child_two:
                Toast.makeText(PostRequestActivity.this, "子菜单二", Toast.LENGTH_SHORT).show();
                break;
            case R.id.child_three:
                Toast.makeText(PostRequestActivity.this, "子菜单三", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}