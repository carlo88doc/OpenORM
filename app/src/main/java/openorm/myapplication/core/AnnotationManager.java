package openorm.myapplication.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnnotationManager {

    public enum FieldType {
        TYPE_INTEGER,
        TYPE_TEXT,
        TYPE_BLOB
    }


    @Retention(RetentionPolicy.RUNTIME)
    public @interface OrmField {
        String fieldName();

        FieldType fieldType();

        boolean isPrimaryKey() default false;

        boolean isForeignKey() default false;

        /**
         * Use the pattern "table1_ref_name", "id_table1_ref"
         * <p/>
         * Example:
         * foreignValues = {"table1", "table1_id")
         */
        String[] foreignValues() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface OrmTable {
        String tableName();
    }

    public static String getFieldTypeString(FieldType f) {
        switch (f) {
            case TYPE_TEXT:
                return "TEXT";
            case TYPE_INTEGER:
                return "INTEGER";
            case TYPE_BLOB:
                return "BLOB";
        }

        return null;
    }


}
