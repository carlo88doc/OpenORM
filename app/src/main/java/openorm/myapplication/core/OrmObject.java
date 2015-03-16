package openorm.myapplication.core;

import java.lang.annotation.Annotation;

/**
 * Created by carlo on 01/01/15.
 */
public class OrmObject implements AnnotationManager.OrmField {
    private String dbFieldName;
    private AnnotationManager.FieldType dbFieldType;
    private boolean isPrimaryKey;
    private boolean isForeignKey;
    private String[] foreignValues;
    private Object value;

    public OrmObject() {
    }

    public OrmObject(String fieldName, AnnotationManager.FieldType fieldType, boolean isPrimaryKey, boolean isForeignKey, String[] foreignValues, Object value) {
        this.dbFieldName = fieldName;
        this.dbFieldType = fieldType;
        this.value = value;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.foreignValues = foreignValues;
    }

    @Override
    public String fieldName() {
        return dbFieldName;
    }

    @Override
    public AnnotationManager.FieldType fieldType() {
        return dbFieldType;
    }

    @Override
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    @Override
    public boolean isForeignKey() {
        return isForeignKey;
    }

    @Override
    public String[] foreignValues() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
