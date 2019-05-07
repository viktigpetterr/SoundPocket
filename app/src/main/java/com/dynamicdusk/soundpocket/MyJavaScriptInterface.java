package com.dynamicdusk.soundpocket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.io.Console;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fredricbillow on 2019-04-07.
 */

public class MyJavaScriptInterface {
    WebView webView;
    Context context;
    SoundPlayer soundPlayer;

    public MyJavaScriptInterface(WebView w, Context context, SoundPlayer soundPlayer) {
        this.webView = w;
        this.context = context;
        this.soundPlayer = soundPlayer;
    }


    @JavascriptInterface
    public void setSoundOn(boolean bool) {
        //alert("set soundOn to " + bool);
        if(bool) {
            soundPlayer.setSoundOn();
        } else {
            soundPlayer.setSoundOff();
        }
    }

    @JavascriptInterface
    public void setSound(int id) {
        System.out.println("---------------------------------setSoundOn");
        if(id == 1) {
            soundPlayer.setSound(SoundPlayer.SOUND_PEW_PEW);
            System.out.println("---------------------------------setSoundOn 1");
        } else if(id == 2) {
            soundPlayer.setSound(SoundPlayer.SOUND_PUNCH);
            System.out.println("---------------------------------setSoundOn 2");
        } else if (id == 3) {
            soundPlayer.setSound(SoundPlayer.SOUND_GUN_SHOT);
            System.out.println("---------------------------------setSoundOn 3");
        }
        //alert("not working");
        soundPlayer.playSound(-1);
    }



    @JavascriptInterface
    public void alert(String message) {
        new AlertDialog.Builder(context)
                .setTitle("SoundPocket")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @JavascriptInterface
    public void getTime() {
        Date currentTime = Calendar.getInstance().getTime();
        String strDate = currentTime.toString();
        runJavaScript("callbackTimeFromAndroid(\"" + strDate + "\")");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }






    private void runJavaScript(String jsFunction) {
        final String fJSFunction = jsFunction;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript("javascript: " + fJSFunction,
                        null);            }
        });
    }
}
