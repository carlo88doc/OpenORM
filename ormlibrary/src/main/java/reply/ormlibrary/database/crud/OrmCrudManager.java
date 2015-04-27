package reply.ormlibrary.database.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public static void insertOrUpdate(String tableName, HashMap<String, Object> values, Context context) {

        if (values != null) {
            SQLiteDatabase db = OrmOpenHelper.getInstance(context, context.getPackageName() + "_db").getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            String idColumn = "id";
            String valueID = (String) values.get(idColumn);

            //TODO replace with current type
            for (String key : values.keySet()) {
                contentValues.put(key, (String) values.get(key));
            }

            if (valueID == null) {
                //insert
                db.insert(tableName, null, contentValues);
            } else {
                //update
                String[] whereClause = {valueID};
                db.update(tableName, contentValues, idColumn, whereClause);
            }

        }
    }

}
