#import <Flutter/Flutter.h>

@interface  FlutterDataBufferLayer : NSObject

@property (nonatomic, class) NSString *session;
@property (nonatomic, class) NSString *userName;
@property (nonatomic, class) NSString *userCover;
@property (nonatomic, class) NSString *userSexType;
@property (nonatomic, class) NSString *machine;
@property (nonatomic, class) NSString *qqGroupNum;
@property (nonatomic, class) NSString *feedbackAppId;

@end

@interface FlutterdatapluginPlugin : NSObject<FlutterPlugin>
@end
