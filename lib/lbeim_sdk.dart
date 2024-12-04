import 'lbeim_sdk_platform_interface.dart';

class LbeimSdk {
  Future<void> initSdk() async {
    await LbeimSdkPlatform.instance.initSdk();
  }
}
