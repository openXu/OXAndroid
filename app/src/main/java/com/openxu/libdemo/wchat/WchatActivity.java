package com.openxu.libdemo.wchat;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openxu.libdemo.R;
import com.openxu.libdemo.evenbus.MessageEvent;
import com.openxu.libdemo.view.CoordinatorActivity;
import com.openxu.libdemo.view.ViewListActivity;
import com.openxu.libdemo.wchat.bean.AccessToken;
import com.openxu.libdemo.wchat.bean.SendModulMsgBody;
import com.openxu.oxlib.adapter.CommandRecyclerAdapter;
import com.openxu.oxlib.adapter.ViewHolder;
import com.openxu.oxlib.base.BaseActivity;
import com.openxu.oxlib.utils.LogUtil;
import com.openxu.oxlib.utils.ToastAlone;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WchatActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> itemList;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        itemList = new ArrayList<>();
        itemList.add("发送模板消息");

        CommandRecyclerAdapter adapter = new CommandRecyclerAdapter<String>(this,
                R.layout.item_recycler, itemList){
            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.tv_name, str);
            }

            @Override
            public void onItemClick(String data, int position) {
                Intent intent = null;
                switch (position){
                    case 0:
                        getAccestoken();
                        break;
                }
                if(null!=intent)
                    startActivity(intent);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(itemList);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("OrganiseUnitID", "20e1516b-2032-11e7-98bc-000c29624c55");
       /* OkHttpUtils.post()
                .url("http://172.16.160.34:8002/WebApi/DataExchange/SendData/SHOP_Register_Cancel?datakey=00-00-00-00")
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtil.e(TAG, "==response==" + response);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }
                });
*/

    }

    private void getAccestoken(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.weixin.qq.com/cgi-bin/token?" +
                        "grant_type=client_credential" +
                        "&appid=wxb81b9e61b673330f" +
                        "&secret=9efa9f84315869426735c1caf8a11c8c")
                .build();
        Call call = mOkHttpClient.newCall(request);
        //异步请求enqueue(),通过回调获取到返回的数据，注意：回调中仍然是子线程
        call.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                ToastAlone.show("请求失败"+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AccessToken token = new Gson().fromJson(response.body().string(),AccessToken.class);
                LogUtil.w(TAG, "获取accestoken成功："+token);
                sendModulMsg(token.getAccess_token());
            }
        });
    }

    private String form_id = "1548816752885";
//    private String form_id = "1548816754210";
//    private String form_id = "1548816757931";

    private void sendModulMsg(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        // JSON或文件RequestBody
        SendModulMsgBody b = new SendModulMsgBody("oaL715Yl8FeGsTftskos3AMiqs-4",
                "929A_1iMrKQ9N0ezVHckK93fJYQjuv6rwvrydZJt7A4",
                "pages/note/canvas/canvas",
                form_id,
                "keyword1.DATA",
                new SendModulMsgBody.Data(new SendModulMsgBody.Keyword("339208499"),
                        new SendModulMsgBody.Keyword("339208499")) );

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = new Gson().toJson(b);
        LogUtil.w(TAG, "发送body:"+jsonStr);
        RequestBody jsonbody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+
                        accesstoken)
                .post(jsonbody)//设置post数据体
                .build();
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.w(TAG, "发送结果："+response.body().string());
            }
        });
    }

}
