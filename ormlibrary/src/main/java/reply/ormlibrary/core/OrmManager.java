package reply.ormlibrary.core;

import android.content.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import reply.ormlibrary.database.crud.OrmCrudManager;
import reply.ormlibrary.utils.Utils;

/**
 * Created by carlo on 01/01/15.
 */
public class OrmManager {

    private static OrmManager _instance;
    private OrmCrudManager mCrudManager;

    private Context mContext;
    private String mDatabaseName;


    public static OrmManager getInstance(Context context) {
        String databaseName = context.getPackageName().concat("_db");
        return getInstance(context, databaseName);
    }

    public static OrmManager getInstance(Context context, String databaseName) {
        if (_instance == null) {
            _instance = new OrmManager();
            _instance.mContext = context;

            String dbNameNormalized = databaseName.replace(".", "");
            _instance.mDatabaseName = dbNameNormalized;

            _instance.mCrudManager = OrmCrudManager.getInstance(context, dbNameNormalized);
        }

        return _instance;
    }

    public void createTable(Object object) {
        if (object != null) {
            if (object instanceof Collection) {
                for (Object o : (Collection) object) {
                    executeCreateTable(o);
                }
            } else {
                executeCreateTable(object);
            }
        }
    }


//    public void update(Object object) {
//        Annotation annotationTable = object.getClass().getAnnotation(AnnotationManager.OrmTable.class);
//        String tableName = (String) getValue(annotationTable, "tableName");
//
//        HashMap<String, Object> mapValues = generateObjectValues(object);
//
//        mCrudManager.update(tableName, mapValues);
//    }
//
//    public void insert(Object object) {
//        Annotation annotationTable = object.getClass().getAnnotation(AnnotationManager.OrmTable.class);
//        String tableName = (String) getValue(annotationTable, "tableName");
//
//        HashMap<String, Object> mapValues = generateObjectValues(object);
//
//        mCrudManager.insert(tableName, mapValues);
//    }

    public void insertOrUpdate(Object object) {
        Annotation annotationTable = object.getClass().getAnnotation(AnnotationManager.OrmTable.class);
        String tableName = (String) getValue(annotationTable, "tableName");

        HashMap<String, Object> mapValues = generateObjectValues(object);

        mCrudManager.insertOrUpdate(tableName, mapValues);
    }

    private void executeCreateTable(Object object) {
        Class clazz = object.getClass();
        String tableName;
        List<OrmObject> fields = new ArrayList<>();
        String fieldName;
        AnnotationManager.FieldType fieldType;
        boolean isPrimaryKey;
        boolean isForeignKey;
        String[] foreignValues;

        Annotation annotationTable = clazz.getAnnotation(AnnotationManager.OrmTable.class);
        tableName = (String) getValue(annotationTable, "tableName");

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field f : declaredFields) {
            f.setAccessible(true);
            Annotation a = f.getAnnotation(AnnotationManager.OrmField.class);
            fieldName = (String) getValue(a, "fieldName");
            fieldType = (AnnotationManager.FieldType) getValue(a, "fieldType");
            isPrimaryKey = (Boolean) getValue(a, "isPrimaryKey");
            isForeignKey = (Boolean) getValue(a, "isForeignKey");
            foreignValues = (String[]) getValue(a, "foreignValues");


            fields.add(new OrmObject(fieldName, fieldType, isPrimaryKey, isForeignKey, foreignValues));
        }

        mCrudManager.createTable(tableName, fields);
    }

    private HashMap<String, Object> generateObjectValues(Object o) {
        HashMap<String, Object> map = null;

        if (o != null) {
            map = new HashMap<>();

            Field[] declaredFields = o.getClass().getDeclaredFields();

            for (Field f : declaredFields) {
                f.setAccessible(true);
                Annotation a = f.getAnnotation(AnnotationManager.OrmField.class);
                String fieldName = (String) getValue(a, "fieldName");
                Object fieldValue = null;

                try {
                    fieldValue = f.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (Utils.isNotNullOrEmpty(fieldName)) {
                    map.put(fieldName, fieldValue);
                }
            }
        }

        return map;
    }

    private static Object getValue(Annotation annotation, String fieldName) {
        Object value;

        try {
            value = annotation.annotationType().getMethod(fieldName).invoke(annotation);
        } catch (Exception e) {
            value = null;
        }

        return value;
    }


}
