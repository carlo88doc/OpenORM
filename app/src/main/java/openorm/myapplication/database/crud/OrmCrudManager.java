package openorm.myapplication.database.crud;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import openorm.myapplication.core.AnnotationManager;
import openorm.myapplication.core.OrmObject;
import openorm.myapplication.utils.Utils;

/**
 * Created by carlo on 22/01/15.
 */
public class OrmCrudManager {

    public static void createTable(String tableName, List<OrmObject> fields) {
        if (Utils.isNotNullOrEmpty(tableName) && Utils.isNotNullOrEmpty(fields)) {

            Collections.sort(fields);

            String sqlCreateTable = "CREATE TABLE " + tableName + " (";

            for (int i = 0; i < fields.size(); i++) {
                OrmObject field = fields.get(i);

                if (field != null && Utils.isNotNullOrEmpty(field.fieldName()) && field.fieldType() != null) {
                    String type = AnnotationManager.getFieldTypeString(field.fieldType());

                    if (field.isForeignKey() && field.foreignValues() != null) {

                        List<String> foreignValues = Arrays.asList(field.foreignValues());

                        if (Utils.isNotNullOrEmpty(foreignValues) && foreignValues.size() == 2) {
                            sqlCreateTable += " FOREIGN KEY(" + field.fieldName() + ")" +
                                    " REFERENCES " + foreignValues.get(0) + "(" + foreignValues.get(1) + ") ";
                        }

                    } else {
                        sqlCreateTable += " " + field.fieldName() + " " + type + " ";

                        if (field.isPrimaryKey()) {
                            sqlCreateTable += "PRIMARY KEY ";
                        }
                    }
                    if (i < fields.size() - 1) {
                        sqlCreateTable += ",";
                    }
                }
            }

            sqlCreateTable += ")";
        }

        //TODO execute query
        System.out.println();
    }

    public static void insertOrUpdate(String tableName, List<OrmObject> fields) {

    }
}
