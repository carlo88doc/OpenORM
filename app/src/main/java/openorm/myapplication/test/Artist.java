package openorm.myapplication.test;


import reply.ormlibrary.core.AnnotationManager;

/**
 * Created by carlo on 01/01/15.
 */
@AnnotationManager.OrmTable(tableName = "artists")
public class Artist {

    @AnnotationManager.OrmField(fieldName = "id",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT,
            isPrimaryKey = true)
    private String id;

    @AnnotationManager.OrmField(fieldName = "name",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT)
    private String name;

    @AnnotationManager.OrmField(fieldName = "surname",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT)
    private String surname;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
