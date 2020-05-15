package data;

import android.app.Application;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/1 0001.
 */
public class AppToast {
    private static Toast toast = null;  // Global Toast
    private static WeakReference<Application> app;
    public static void init(Application application) {
        app = new WeakReference<Application>(application);
    }
    public static void showToast(String resId) {

        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(app.get(), resId, Toast.LENGTH_SHORT);
        toast.show();
    }
}
