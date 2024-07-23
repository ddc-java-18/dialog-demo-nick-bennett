package edu.cnm.deepdive.dialogdemo.service;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.dialogdemo.model.Note;
import edu.cnm.deepdive.dialogdemo.model.dao.NoteDao;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {

  private final NoteDao noteDao;
  private final ExecutorService executor;

  private NotesRepository() {
    noteDao = NotesDatabase.getInstance().getNoteDao();
    executor = Executors.newSingleThreadExecutor();
  }

  public static NotesRepository getInstance() {
    return Holder.INSTANCE;
  }

  public LiveData<List<Note>> getNotes() {
    return noteDao.selectAll();
  }

  public void save(Note note) {
    executor.execute(() -> {
      if (note.getId() == 0) {
        noteDao.insert(note);
      } else {
        noteDao.update(note);
      }
    });
  }

  public LiveData<Note> getNote(long noteId) {
    return noteDao.selectById(noteId);
  }

  private static class Holder {

    private static final NotesRepository INSTANCE = new NotesRepository();

  }

}
