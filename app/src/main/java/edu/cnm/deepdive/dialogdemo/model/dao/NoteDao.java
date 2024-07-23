package edu.cnm.deepdive.dialogdemo.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.dialogdemo.model.Note;
import java.util.List;

@Dao
public interface NoteDao {
  
  @Insert
  void insert(Note note);
  
  @Update
  void update(Note note);
  
  @Query("SELECT * FROM note ORDER BY note_id ASC")
  LiveData<List<Note>> selectAll();
  
  @Query("SELECT * FROM note WHERE note_id = :noteId")
  LiveData<Note> selectById(long noteId);
  
}
