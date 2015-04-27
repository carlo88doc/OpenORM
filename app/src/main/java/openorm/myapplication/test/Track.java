package openorm.myapplication.test;

import java.util.List;

import reply.ormlibrary.core.AnnotationManager;


/**
 * Created by carlo on 01/01/15.
 */
@AnnotationManager.OrmTable(tableName = "tracks")
public class Track {

    @AnnotationManager.OrmField(fieldName = "id",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT,
            isPrimaryKey = true)
    private String id;

    @AnnotationManager.OrmField(fieldName = "title",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT)
    private String title;

    @AnnotationManager.OrmField(fieldName = "fk_artist",
            fieldType = AnnotationManager.FieldType.TYPE_TEXT,
            isForeignKey = true,
            foreignValues = {"artist", "id"})
    private String fkArtist;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFkArtist() {
        return fkArtist;
    }

    public void setFkArtist(String fkArtist) {
        this.fkArtist = fkArtist;
    }
}
