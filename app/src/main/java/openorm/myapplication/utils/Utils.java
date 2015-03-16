package openorm.myapplication.utils;

import java.util.List;

/**
 * Created by carlo on 22/01/15.
 */
public class Utils {

    public static boolean isNotNullOrEmpty(String s) {
        return (s != null && s.length() > 0);
    }

    public static boolean isNotNullOrEmpty(List list) {
        return (list != null && list.size() > 0);
    }


}
