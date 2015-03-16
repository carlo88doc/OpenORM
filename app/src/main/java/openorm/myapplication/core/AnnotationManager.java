package openorm.myapplication.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by carlo on 01/01/15.
 */
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

        String[] foreignValues() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface OrmTable {
        String tableName();
    }


}
