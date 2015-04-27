package reply.ormlibrary.database.crud;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import reply.ormlibrary.core.AnnotationManager;
import reply.ormlibrary.core.OrmObject;
import reply.ormlibrary.database.OrmOpenHelper;
import reply.ormlibrary.utils.Utils;


/**
 * Created by carlo on 22/01/15.
 */
public class OrmCrudManager {

    public static void createTable(String tableName, List<OrmObject> fields, Context context) {
        StringBuilder stringBuilder;
        if (Utils.isNotNullOrEmpty(tableName) && Utils.isNotNullOrEmpty(fields)) {

            List<String> foreignSql = null;
            Collections.sort(fields);

            stringBuilder = new StringBuilder("CREATE TABLE ");
            stringBuilder.append(tableName);
            stringBuilder.append(" (");

            for (int i = 0; i < fields.size(); i++) {
                OrmObject field = fields.get(i);

                if (field != null && Utils.isNotNullOrEmpty(field.fieldName()) && field.fieldType() != null) {
                    String type = AnnotationManager.getFieldTypeString(field.fieldType());

                    stringBuilder.append(" ");
                    stringBuilder.append(field.fieldName());
                    stringBuilder.append(" ");
                    stringBuilder.append(type);
                    stringBuilder.append(" ");


                    if (field.isPrimaryKey()) {
                        stringBuilder.append("PRIMARY KEY ");
                    }
                    if (i < fields.size() - 1) {
                        stringBuilder.append(",");
                    }

                    if (field.isForeignKey() && field.foreignValues() != null) {

                        if (foreignSql == null) foreignSql = new ArrayList<>();

                        List<String> foreignValues = Arrays.asList(field.foreignValues());

                        if (Utils.isNotNullOrEmpty(foreignValues) && foreignValues.size() == 2) {
                            StringBuilder stringBuilderForeignKeys = new StringBuilder(" FOREIGN KEY(");
                            stringBuilderForeignKeys.append(field.fieldName());
                            stringBuilderForeignKeys.append(") REFERENCES ");
                            stringBuilderForeignKeys.append(foreignValues.get(0));
                            stringBuilderForeignKeys.append("(");
                            stringBuilderForeignKeys.append(foreignValues.get(1));
                            stringBuilderForeignKeys.append(") ");

                            foreignSql.add(stringBuilderForeignKeys.toString());

//                            foreignSql.add(" FOREIGN KEY(" + field.fieldName() + ")" +
//                                    " REFERENCES " + foreignValues.get(0) + "(" + foreignValues.get(1) + ") ");
                        }
                    }
                }
            }

            if (Utils.isNotNullOrEmpty(foreignSql)) {
                stringBuilder.append(",");
                for (int i = 0; i < foreignSql.size(); i++) {
                    stringBuilder.append(foreignSql.get(i));

                    if (i < foreignSql.size() - 1) {
                        stringBuilder.append(",");
                    }

                }
            }

            stringBuilder.append(") ");

            OrmOpenHelper.getInstance(context, context.getPackageName() + "_db").execQuery(stringBuilder.toString());
        }
    }

    public static void insertTable(String tableName, HashMap<String, Object> values, Context context) {
        StringBuilder sql;

        if (tableName != null && values != null) {
            sql = new StringBuilder("INSERT INTO ");
            sql.append(tableName);

            StringBuilder stringBuilderColumns = new StringBuilder();
            StringBuilder stringBuilderValues = new StringBuilder();

            int i = 0;

            for (String key : values.keySet()) {
                stringBuilderColumns.append(key);
                //TODO replace with defaultValue
                Object value = values.get(key);
                stringBuilderValues.append(value != null ? value : "");
                i++;
                if (i < values.size()) {
                    stringBuilderColumns.append(",");
                    stringBuilderValues.append(",");
                }
            }

            sql.append(tableName);
            sql.append("(");
            sql.append(stringBuilderColumns);
            sql.append(") VALUES");
            sql.append(stringBuilderValues);

            OrmOpenHelper.getInstance(context, context.getPackageName() + "_db").execQuery(sql.toString());
        }
    }

    public static void updateTable(String tableName, HashMap<String, Object> values, Context context) {

//        OrmOpenHelper.getInstance(context, context.getPackageName() + "_db").e(sql.toString());
//        StringBuilder sql;
//        String idValue = (String)values.get("id");
//
//        sql = new StringBuilder("UPDATE ");
//        sql.append(tableName);
//
//        int i=0;
//
//        StringBuilder stringBuilderValues = new StringBuilder("SET ");
//
//        for (String key : values.keySet()){
//            String column = key;
//            //TODO replace with value formatted by type
//            String value = (String)values.get(key);
//            stringBuilderValues.append(column);
//            stringBuilderValues.append("=");
//            stringBuilderValues.append(value);
//
//            i++;
//            if (i<values.size()){
//                stringBuilderValues.append(",");
//            }
//        }
//
//        sql.append(" ");
//        sql.append(stringBuilderValues);
//        sql.append(" WHERE ID =");
    }
}
