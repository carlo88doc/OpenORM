package openorm.myapplication.core;

import android.content.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import openorm.myapplication.database.crud.OrmCrudManager;

import static openorm.myapplication.core.AnnotationManager.FieldType;

/**
 * Created by carlo on 01/01/15.
 */
public class OrmManager {

    static final String AUTHORITY = "com.cnh.mobiledealerconnect";
    static final String BASE_CONTENT_URI = "content://" + AUTHORITY + "/";

    private Context mContext;

    public static void register(Object object, Context context) {
        if (object != null) {
            if (object instanceof Collection) {
                for (Object o : (Collection) object) {
                    registerSingleObject(o, context);
                }
            } else {
                registerSingleObject(object, context);
            }
        }
    }

    private static void registerSingleObject(Object object, Context context) {
        Class clazz = object.getClass();
        String tableName;
        List<OrmObject> fields = new ArrayList<>();
        String fieldName;
        FieldType fieldType;
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
            fieldType = (FieldType) getValue(a, "fieldType");
            isPrimaryKey = (Boolean) getValue(a, "isPrimaryKey");
            isForeignKey = (Boolean) getValue(a, "isForeignKey");
            foreignValues = (String[]) getValue(a, "foreignValues");

            Object value = null;
            try {
                value = f.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            fields.add(new OrmObject(fieldName, fieldType, isPrimaryKey, isForeignKey, foreignValues, value));
        }

        OrmCrudManager.createTable(tableName, fields, context);
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
