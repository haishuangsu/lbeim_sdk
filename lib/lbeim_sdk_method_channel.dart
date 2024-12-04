import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'lbeim_sdk_platform_interface.dart';

/// An implementation of [LbeimSdkPlatform] that uses method channels.
class MethodChannelLbeimSdk extends LbeimSdkPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('lbeim_sdk');

  @override
  Future<void> initSdk() async {
    await methodChannel.invokeMethod('initSdk', {
      'lbeSign':
          'b184b8e64c5b0004c58b5a3c9af6f3868d63018737e68e2a1ccc61580afbc8f112119431511175252d169f0c64d9995e5de2339fdae5cbddda93b65ce305217700',
      'nickId': 'HermitK1',
      'nickName': 'HermitK1',
      'lbeIdentity': '441zy52mn2yy'
    });
  }
}
