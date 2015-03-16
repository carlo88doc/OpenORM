package openorm.myapplication;

import openorm.myapplication.core.AnnotationManager;

/**
 * Created by carlo on 01/01/15.
 */
@AnnotationManager.OrmTable(tableName = "table_test")
public class Test {

    @AnnotationManager.OrmField(fieldName = "id",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT,
            isPrimaryKey = true)
    private String id;

    @AnnotationManager.OrmField(fieldName = "fk_other",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT,
            isForeignKey = true,
            foreignValues = {"other_table", "id_other_table"})
    private String fkTest;

    @AnnotationManager.OrmField(fieldName = "field_description",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT)
    private String description;


    public void setId(String id) {
        this.id = id;
    }

    public void setFkTest(String fkTest) {
        this.fkTest = fkTest;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
