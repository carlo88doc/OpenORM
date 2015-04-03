package openorm.myapplication.database.crud;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import openorm.myapplication.core.AnnotationManager;
import openorm.myapplication.core.OrmObject;
import openorm.myapplication.database.OrmOpenHelper;
import openorm.myapplication.utils.Utils;

/**
 * Created by carlo on 22/01/15.
 */
public class OrmCrudManager {

    public static void createTable(String tableName, List<OrmObject> fields, Context context) {
        if (Utils.isNotNullOrEmpty(tableName) && Utils.isNotNullOrEmpty(fields)) {

            List<String> foreignSql = null;
            Collections.sort(fields);

            String sqlCreateTable = "CREATE TABLE " + tableName + " (";

            for (int i = 0; i < fields.size(); i++) {
                OrmObject field = fields.get(i);

                if (field != null && Utils.isNotNullOrEmpty(field.fieldName()) && field.fieldType() != null) {
                    String type = AnnotationManager.getFieldTypeString(field.fieldType());

                    sqlCreateTable += " " + field.fieldName() + " " + type + " ";
                    if (field.isPrimaryKey()) {
                        sqlCreateTable += "PRIMARY KEY ";
                    }
                    if (i < fields.size() - 1) {
                        sqlCreateTable += ",";
                    }

                    if (field.isForeignKey() && field.foreignValues() != null) {

                        if (foreignSql == null) foreignSql = new ArrayList<>();

                        List<String> foreignValues = Arrays.asList(field.foreignValues());

                        if (Utils.isNotNullOrEmpty(foreignValues) && foreignValues.size() == 2) {
                            foreignSql.add(" FOREIGN KEY(" + field.fieldName() + ")" +
                                    " REFERENCES " + foreignValues.get(0) + "(" + foreignValues.get(1) + ") ");
                        }
                    }
                }
            }

            if (Utils.isNotNullOrEmpty(foreignSql)) {
                sqlCreateTable += ",";
                for (int i = 0; i < foreignSql.size(); i++) {
                    sqlCreateTable += foreignSql.get(i);

                    if (i < foreignSql.size() - 1) {
                        sqlCreateTable += ",";
                    }

                }
            }

            sqlCreateTable += ")";

            OrmOpenHelper.getInstance(context, "DB_PROVA").execQuery(sqlCreateTable);
        }
    }

    public static void insertOrUpdate(String tableName, List<OrmObject> fields) {

    }
}
