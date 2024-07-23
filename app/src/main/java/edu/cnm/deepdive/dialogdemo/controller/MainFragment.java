package edu.cnm.deepdive.dialogdemo.controller;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import edu.cnm.deepdive.dialogdemo.adapter.NotesAdapter;
import edu.cnm.deepdive.dialogdemo.databinding.FragmentMainBinding;
import edu.cnm.deepdive.dialogdemo.viewmodel.NotesViewModel;

public class MainFragment extends Fragment {

  private FragmentMainBinding binding;
  private NotesViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentMainBinding.inflate(inflater, container, false);
    binding.showEditFragment.setOnClickListener((v) ->
        Navigation.findNavController(binding.getRoot())
            .navigate(MainFragmentDirections.navigateToEditFragment()));
    // TODO: 7/15/24 Attach listeners to widgets in binding fields.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
    viewModel
        .getNotes()
        .observe(getViewLifecycleOwner(), (notes) -> {
          NotesAdapter adapter = new NotesAdapter(requireContext(), notes, (v, note, position) -> {
            Log.d(getClass().getSimpleName(), "Note clicked: " + note.getComment());
            Navigation.findNavController(binding.getRoot()).navigate(MainFragmentDirections.navigateToEditFragment()
                .setNoteId(note.getId()));
          });
          binding.notes.setAdapter(adapter);
        });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}
