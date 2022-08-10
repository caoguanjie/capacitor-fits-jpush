package cn.fits.plugins.jpush;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CapacitorPlugin(name = "FitsPush")
public class FitsPushPlugin extends Plugin {

    private static final List<String> IGNORED_EXTRAS_KEYS = Arrays.asList("profile");

    private FitsPush implementation = new FitsPush();
    public static Intent jpushIntent = null;
    public static String title = "title";
    public static String alert = "alert";
    public static Map<String, Object> extras = null;
    @Override
    public void load() {
        super.load();
        handlePushOpen();
    }

    //解析通知栏消息额外参数
    public static Map<String, Object> getNotificationExtras(Intent intent) {
        extras = new HashMap<String, Object>();
        Map<String, Object> extrasMap = new HashMap<String, Object>();
        for (String key : intent.getExtras().keySet()) {
            if (!IGNORED_EXTRAS_KEYS.contains(key)) {
//                if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//                    extrasMap.put(key, intent.getIntExtra(key, 0));
//                } else {
                    extrasMap.put(key, intent.getStringExtra(key));
//                }
            }
        }
        return extrasMap;
    }

    private void handlePushOpen() {
        try {
            Intent intent = getActivity().getIntent();
            Log.d("HJT", "执行获取意图" + intent.toString());
            if (intent == null) {
                return;
            }
            jpushIntent = intent;
            if(getActivity().getIntent().getExtras() != null){
                extras = getNotificationExtras(intent);
                Log.d("HJT", "点击后拿到额外参数" + extras.toString());
            }

            String pushData = null;
            if (getActivity().getIntent().getData() != null) {
                pushData = getActivity().getIntent().getData().toString();
                Log.d("HJT", "1监听" + pushData);
            }
            Gson gson = new Gson();
            gson.toJson(getActivity().getIntent().getData());

            if (TextUtils.isEmpty(pushData) && getActivity().getIntent().getExtras() != null) {
                Log.d("HJT", "2监听" + getActivity().getIntent().getExtras().toString());
                String datakey = getActivity().getIntent().getExtras().getString("datakey");
                String scene = getActivity().getIntent().getExtras().getString("scene");
                String sendtime = getActivity().getIntent().getExtras().getString("sendtime");
                String user = getActivity().getIntent().getExtras().getString("user");
                Log.d("HJT", "3监听当前推送数据：datakey=" + datakey + "，scene="+scene+"，sendtime="+sendtime+"，user="+user);
            }
            if (TextUtils.isEmpty(pushData)) {
                return;
            }
            JSONObject jsonObject = new JSONObject(pushData);
            Log.d("HJT", "4监听" + jsonObject.toString());
            // 推送消息附加字段
            // String extras = jsonObject.optString("n_extras");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
