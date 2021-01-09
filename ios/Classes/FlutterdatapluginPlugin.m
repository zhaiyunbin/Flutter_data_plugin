#import "FlutterdatapluginPlugin.h"

#if __has_include("UIDevice+MCC_Addition.h")
#import "UIDevice+MCC_Addition.h"
#else
#define FlutterDataBufferLayerEmpty false
#endif

@implementation FlutterDataBufferLayer

#define FlutterDataBufferLayerKey(key)  [NSString stringWithFormat:@"FlutterDataBufferLayer_%@", key]
static inline void _cache(id value, NSString *key) {
    [[NSUserDefaults standardUserDefaults] setObject:value forKey:FlutterDataBufferLayerKey(key)];
}

static inline id _obForKey(NSString *key) {
   return [[NSUserDefaults standardUserDefaults] objectForKey:FlutterDataBufferLayerKey(key)];
}

+ (void)setSession:(NSString *)session {
    NSData *token = [session dataUsingEncoding:NSUTF8StringEncoding];
    NSString *authorization = [NSString stringWithFormat:@"session_%@", [token base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength]];
    _cache(authorization, @"session");
}

+ (NSString *)session {
    NSString *ret = _obForKey(@"session");
    NSLog(@"🔥%@",ret);
    return ret;
}

+ (void)setQqGroupNum:(NSString *)qqGroupNum{
    _cache(qqGroupNum, @"qqGroupNum");
}
///反馈模块QQ群
+ (NSString *)qqGroupNum {
   return _obForKey(@"qqGroupNum");
}

///反馈模块APPID
+ (void)setFeedbackAppId:(NSString *)feedbackAppId{
       _cache(feedbackAppId, @"feedbackAppId");
}
+ (NSString *)feedbackAppId{
     return _obForKey(@"feedbackAppId");
}

+ (void)setMachine:(NSString *)machine {
    _cache(machine, @"machine");
}

+ (NSString *)machine {

#ifdef FlutterDataBufferLayerEmpty
    return @"";
#else
    return [UIDevice mcc_getDeviceId];
#endif
//    return _obForKey(@"machine");
}

+ (void)setUserName:(NSString *)userName {
    _cache(userName, @"userName");
}

+ (NSString *)userName {
    return _obForKey(@"userName");
}

+ (void)setUserCover:(NSString *)userCover {
    _cache(userCover, @"userCover");
}

+ (NSString *)userCover {
    return _obForKey(@"userCover");
}

+ (void)setUserSexType:(NSString *)userSexType {
    _cache(userSexType, @"userSexType");
}

+ (NSString *)userSexType {
    return _obForKey(@"userSexType");
}

@end

@implementation FlutterdatapluginPlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"flutterdataplugin"
                                     binaryMessenger:[registrar messenger]];
    FlutterdatapluginPlugin* instance = [[FlutterdatapluginPlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
        return;
    }
    if ([@"getSession" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.session);
        return;
    }
    if ([@"getUserName" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.userName);
        return;
    }
    if ([@"getUserCover" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.userCover);
        return;
    }
    if ([@"getSexType" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.userSexType);
        return;
    }
    if ([@"getMachine" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.machine);
        return;
    }
    if ([@"getAppId" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.feedbackAppId);
        return;
    }
    if ([@"getQqGroupNum" isEqualToString:call.method]) {
        result(FlutterDataBufferLayer.qqGroupNum);
        return;
    }

    result(@"");
}

@end
