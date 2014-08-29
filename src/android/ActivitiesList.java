package uk.ac.edina.mobile.activitieslist;

import java.util.Iterator;
import java.util.List;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class ActivitiesList extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("byIntent")) {
            String intentStr = args.getString(0);
            this.listByIntent(intentStr, callbackContext);
            return true;
        }
        return false;
    }

    /* List the activities associated to an intent */
    private void listByIntent(String intentStr, CallbackContext callbackContext) {
        if (intentStr != null && intentStr.length() > 0) {

            Context ctx = this.cordova.getActivity().getApplicationContext();
            PackageManager pm = ctx.getPackageManager();

            Intent intent = new Intent(intentStr);

            try {
                List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                Iterator<ResolveInfo> it = list.iterator();

                JSONArray jsonArray = new JSONArray();
                while(it.hasNext()){
                    ResolveInfo resolveInfo = it.next();
                    JSONObject jsonActivity = new JSONObject();
                    jsonActivity.put("package", resolveInfo.activityInfo.packageName);
                    jsonActivity.put("name", resolveInfo.activityInfo.loadLabel(pm).toString());
                    jsonArray.put(jsonActivity);
                }
                callbackContext.success(jsonArray);
            }
            catch(Exception e) {
                callbackContext.error(e.toString());
            }
        } else {
            callbackContext.error("An intent name is required");
        }
    }
}
