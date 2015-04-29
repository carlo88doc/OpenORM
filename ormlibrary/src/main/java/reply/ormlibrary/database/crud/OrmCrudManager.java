package reply.ormlibrary.database.crud;

import android.content.ContentValues;
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

    private static OrmCrudManager _instance;
    private Context mContext;
    private String mDatabaseName;

    public static OrmCrudManager getInstance(Context context, String databaseName) {
        if (_instance == null) {
            _instance = new OrmCrudManager();
            _instance.mContext = context;
            _instance.mDatabaseName = databaseName;
        }

        return _instance;
    }

    public void createTable(String tableName, List<OrmObject> fields) {
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

            OrmOpenHelper.getInstance(mContext, mDatabaseName).execQuery(stringBuilder.toString());
        }
    }

    public void insert(String tableName, HashMap<String, Object> values) {

        if (values != null) {
            ContentValues contentValues = getContentValuesFromMapValues(values);

            if (contentValues != null) {
                OrmOpenHelper.getInstance(mContext, mDatabaseName).execInsert(tableName, null, contentValues);
            }
        }
    }

    public void update(String tableName, HashMap<String, Object> values) {

        String idColumn = "id";

        if (values != null && values.containsKey(idColumn)) {
            ContentValues contentValues = getContentValuesFromMapValues(values);

            if (contentValues != null) {
                String[] whereColumns = {idColumn};
                String[] whereClause = {(String) values.get(idColumn)};
                OrmOpenHelper.getInstance(mContext, mDatabaseName).execUpdate(tableName, contentValues, whereColumns, whereClause);
            }
        }
    }


    private ContentValues getContentValuesFromMapValues(HashMap<String, Object> map) {
        ContentValues contentValues = null;

        if (map != null) {
            contentValues = new ContentValues();

            for (String key : map.keySet()) {
                contentValues.put(key, (String) map.get(key));
            }
        }

        return contentValues;
    }

}
