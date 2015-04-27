package reply.ormlibrary.database.crud;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
//            String sqlCreateTable =  + tableName + " (";

            for (int i = 0; i < fields.size(); i++) {
                OrmObject field = fields.get(i);

                if (field != null && Utils.isNotNullOrEmpty(field.fieldName()) && field.fieldType() != null) {
                    String type = AnnotationManager.getFieldTypeString(field.fieldType());

                    stringBuilder.append(" ");
                    stringBuilder.append(field.fieldName());
                    stringBuilder.append(" ");
                    stringBuilder.append(type);
                    stringBuilder.append(" ");


//                    sqlCreateTable += " " + field.fieldName() + " " + type + " ";
                    if (field.isPrimaryKey()) {
                        stringBuilder.append("PRIMARY KEY ");
//                        sqlCreateTable += "PRIMARY KEY ";
                    }
                    if (i < fields.size() - 1) {
                        stringBuilder.append(",");
//                        sqlCreateTable += ",";
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
                stringBuilder.append(",");
//                sqlCreateTable += ",";
                for (int i = 0; i < foreignSql.size(); i++) {
                    stringBuilder.append(foreignSql.get(i));
//                    sqlCreateTable += foreignSql.get(i);

                    if (i < foreignSql.size() - 1) {
                        stringBuilder.append(",");
//                        sqlCreateTable += ",";
                    }

                }
            }

            stringBuilder.append(") ");
//            sqlCreateTable += ")";

            OrmOpenHelper.getInstance(context, context.getPackageName() + "_db").execQuery(stringBuilder.toString());


        }
    }

    public static void insertOrUpdate(String tableName, List<OrmObject> fields) {

    }
}
