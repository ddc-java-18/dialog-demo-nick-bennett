package edu.cnm.deepdive.dialogdemo.viewmodel;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.dialogdemo.model.Note;
import edu.cnm.deepdive.dialogdemo.service.NotesRepository;
import java.util.List;

public class NotesViewModel extends AndroidViewModel {

  private final NotesRepository repository;
  private final MutableLiveData<Uri> imageUri;
  private final MutableLiveData<Long> noteId;
  private final LiveData<Note> note;

  private Uri pendingImageUri;

  public NotesViewModel(@NonNull Application application) {
    super(application);
    repository = NotesRepository.getInstance();
    imageUri = new MutableLiveData<>();
    noteId = new MutableLiveData<>();
    note = Transformations.switchMap(noteId, repository::getNote);
  }

  public LiveData<Uri> getImageUri() {
    return imageUri;
  }

  public void setPendingImageUri(Uri pendingImageUri) {
    this.pendingImageUri = pendingImageUri;
  }

  public void confirmImageCapture(boolean captured) {
    imageUri.setValue(captured ? pendingImageUri : null);
  }

  public LiveData<List<Note>> getNotes() {
    return repository.getNotes();
  }

  public LiveData<Note> getNote() {
    return note;
  }

  public void fetchNote(long id) {
    noteId.setValue(id);
  }
  
  public void addNote(Note note) {
    repository.save(note);
    imageUri.setValue(null);
  }

}
