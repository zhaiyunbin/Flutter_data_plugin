import 'dart:async';

import 'package:flutter/services.dart';

class Flutterdataplugin {
  static const MethodChannel _channel =
      const MethodChannel('flutterdataplugin');

  static Future<String> get deviceID async {
    final String version = await _channel.invokeMethod('getMachine');
    return version;
  }
  static Future<String> get appID async {
    final String appid = await _channel.invokeMethod('getAppId');
    return appid;
  }

  static Future<String> get qqGroupNum async {
    final String qqGroupNum = await _channel.invokeMethod('getQqGroupNum');
    return qqGroupNum;
  }

  static Future<String> get sexType async {
    final String sexType = await _channel.invokeMethod('getSexType');
    if(sexType == '0'){
      return 'woman';
    }else{
      return 'man';
    }
  }
  static Future<String> get session async {
    final String version = await _channel.invokeMethod('getSession');
    return version;
  }
  static Future<String> get userName async {
    final String userName = await _channel.invokeMethod('getUserName');
    return userName;
  }
  static Future<String> get userCover async {
    final String cover = await _channel.invokeMethod('getUserCover');
    return cover;
  }
}
