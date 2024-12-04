import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'lbeim_sdk_method_channel.dart';

abstract class LbeimSdkPlatform extends PlatformInterface {
  /// Constructs a LbeimSdkPlatform.
  LbeimSdkPlatform() : super(token: _token);

  static final Object _token = Object();

  static LbeimSdkPlatform _instance = MethodChannelLbeimSdk();

  /// The default instance of [LbeimSdkPlatform] to use.
  ///
  /// Defaults to [MethodChannelLbeimSdk].
  static LbeimSdkPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [LbeimSdkPlatform] when
  /// they register themselves.
  static set instance(LbeimSdkPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> initSdk() {
    throw UnimplementedError('initSdk() has not been implemented.');
  }
}
