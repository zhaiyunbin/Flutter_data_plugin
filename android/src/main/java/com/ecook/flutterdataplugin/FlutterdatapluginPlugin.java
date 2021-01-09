package com.ecook.flutterdataplugin;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterdatapluginPlugin */
public class FlutterdatapluginPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private Context mContext;
  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutterdataplugin");
    channel.setMethodCallHandler(this);
    mContext = flutterPluginBinding.getApplicationContext();
  }


  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutterdataplugin");
    channel.setMethodCallHandler(new FlutterdatapluginPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call,  Result result) {
    if (call.method.equals("getMachine")) {
      result.success(MachineManger.instance().getMachine(mContext));
    } else  if (call.method.equals("getSexType")) {
      result.success(MachineManger.instance().getSexType(mContext));
    } else  if (call.method.equals("getSession")) {
      result.success(MachineManger.instance().getSession(mContext));
    } else  if (call.method.equals("getUserName")) {
        result.success(MachineManger.instance().getUserName(mContext));
    } else  if (call.method.equals("getUserCover")) {
        result.success(MachineManger.instance().getUserCover(mContext));
    } else  if (call.method.equals("getAppId")) {
        result.success(MachineManger.instance().getAppId(mContext));
    } else  if (call.method.equals("getQqGroupNum")) {
        result.success(MachineManger.instance().getQQGroup(mContext));
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine( FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }


}
