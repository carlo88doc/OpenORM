package reply.ormlibrary.utils;

import java.util.Collection;

/**
 * Created by carlo on 22/01/15.
 */
public class Utils {

    public static boolean isNotNullOrEmpty(String s) {
        return (s != null && s.length() > 0);
    }

    public static boolean isNotNullOrEmpty(Collection coll) {
        return (coll != null && coll.size() > 0);
    }


}
