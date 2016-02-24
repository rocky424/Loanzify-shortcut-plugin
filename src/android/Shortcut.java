package ch.itomy.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.content.Intent;
import android.util.Log;
import android.content.res.Resources;
import java.lang.ClassNotFoundException;

public class Shortcut extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("add")) {

            String img = data.getString(0);
            String search = "base64,";

            img = img.substring(img.lastIndexOf(search) + search.length());
            Bitmap bmp = decodeBase64(img);

            Resources appR = cordova.getActivity().getResources();
            CharSequence txt = appR.getText(appR.getIdentifier("app_name",
            "string", cordova.getActivity().getPackageName()));


try {
            shortcutDel(txt.toString());
            shortcutAdd(txt.toString(),bmp);
            callbackContext.success();
     } catch (ClassNotFoundException e) {
            e.printStackTrace();

            callbackContext.error(e.getLocalizedMessage());

        }
         return true;
           

        } else if(action.equals("delete")){

             Resources appR = cordova.getActivity().getResources();
            CharSequence txt = appR.getText(appR.getIdentifier("app_name",
            "string", cordova.getActivity().getPackageName()));

            
try {
            shortcutDel(txt.toString());
             callbackContext.success();
     } catch (ClassNotFoundException e) {
            e.printStackTrace();
            callbackContext.error(e.getLocalizedMessage());
          
        }
        return true;

        }else {

            return false;

        }
    }
    public  Bitmap decodeBase64(String input) 
{
    byte[] decodedByte = Base64.decode(input, 0);
    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
}

private void shortcutAdd(String name, Bitmap bitmap)  throws ClassNotFoundException  {
    // Intent to be send, when shortcut is pressed by user ("launched")

        String packageName = cordova.getActivity().getPackageName();
        Intent launchIntent = cordova.getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();

    Intent shortcutIntent = new Intent(cordova.getActivity().getApplicationContext(), Class.forName(className));
    shortcutIntent.setAction("action_play");

    // Decorate the shortcut
    Intent addIntent = new Intent();
    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap);

    // Inform launcher to create shortcut
    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
    cordova.getActivity().getApplicationContext().sendBroadcast(addIntent);


}

private void shortcutDel(String name) throws ClassNotFoundException {
    // Intent to be send, when shortcut is pressed by user ("launched")
       String packageName = cordova.getActivity().getPackageName();
        Intent launchIntent = cordova.getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
    Intent shortcutIntent = new Intent(cordova.getActivity().getApplicationContext(), Class.forName(className));
    shortcutIntent.setAction("action_play");

    // Decorate the shortcut
    Intent delIntent = new Intent();
    delIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
    delIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

    // Inform launcher to remove shortcut
    delIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
    cordova.getActivity().getApplicationContext().sendBroadcast(delIntent);
  
}
}
