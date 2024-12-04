package com.lbeim.lbeim_sdk

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import info.hermiths.lbesdk.LbeChatActivity
import info.hermiths.lbesdk.model.InitArgs
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** LbeimSdkPlugin */
class LbeimSdkPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel

    private lateinit var context: Context

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "lbeim_sdk")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "initSdk" -> {
                val args = call.arguments as MutableMap<*, *>
                println("embed initSdk args --->> $args")
                val initArgs = InitArgs(
                    lbeSign = args["lbeSign"].toString(),
                    nickId = args["nickId"].toString(),
                    nickName = args["nickName"].toString(),
                    lbeIdentity = args["lbeIdentity"].toString()
                )
                val intent = Intent(context, LbeChatActivity::class.java).putExtra(
                    "initArgs", Gson().toJson(initArgs)
                )
                context.startActivity(intent)
            }

            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}