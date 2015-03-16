package openorm.myapplication.database.crud;

import java.util.List;

import openorm.myapplication.core.OrmObject;
import openorm.myapplication.utils.Utils;

/**
 * Created by carlo on 22/01/15.
 */
public class OrmCrudManager {

    public static void createOrUpdateTable(String tableName, List<OrmObject> fields) {
        if (Utils.isNotNullOrEmpty(tableName) && Utils.isNotNullOrEmpty(fields)) {

//            complete table creation
        }
    }
}
