package us.sensornet.flutterapp.flutterapp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  String sharedText;

  private static final String TAG = "MainActivity";
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: Was called!");
    GeneratedPluginRegistrant.registerWith(this);
    Intent intent = getIntent();
    String action = intent.getAction();
    String type = intent.getType();

    if (Intent.ACTION_SEND.equals(action) && type != null) {
      Log.d(TAG, "Intent.Action_SEND was entered... ");
      if ("text/plain".equals(type)) {
        Log.d(TAG, "onCreate: handleSendText was called...");
        handleSendText(intent); // Handle text being sent

      }
    }

    new MethodChannel(getFlutterView(), "app.channel.shared.data").setMethodCallHandler(new MethodChannel.MethodCallHandler() {
      @Override
      public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        Log.d(TAG, "onMethodCall: was called");
        if (methodCall.method.contentEquals("getSharedText")) {
          Log.d(TAG, "onMethodCall: if entered...");
          result.success(sharedText);
          sharedText = null;
        }
      }
    });
  }


  void handleSendText(Intent intent) {
      Log.e(TAG, "handleSendText: was called...");
      sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
  }
}

