import 'package:flutter_test/flutter_test.dart';
import 'package:lbeim_sdk/lbeim_sdk.dart';
import 'package:lbeim_sdk/lbeim_sdk_platform_interface.dart';
import 'package:lbeim_sdk/lbeim_sdk_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockLbeimSdkPlatform
    with MockPlatformInterfaceMixin
    implements LbeimSdkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
  
  @override
  Future<void> initSdk() {
    // TODO: implement initSdk
    throw UnimplementedError();
  }
}

void main() {
  final LbeimSdkPlatform initialPlatform = LbeimSdkPlatform.instance;

  test('$MethodChannelLbeimSdk is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelLbeimSdk>());
  });

  test('getPlatformVersion', () async {
    LbeimSdk lbeimSdkPlugin = LbeimSdk();
    MockLbeimSdkPlatform fakePlatform = MockLbeimSdkPlatform();
    LbeimSdkPlatform.instance = fakePlatform;

    // expect(await lbeimSdkPlugin.getPlatformVersion(), '42');
  });
}
