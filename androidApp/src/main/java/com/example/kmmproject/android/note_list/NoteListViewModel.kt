package com.example.kmmproject.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmproject.domain.note.Note
import com.example.kmmproject.domain.note.NoteDataSource
import com.example.kmmproject.domain.note.SearchNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()

    private val notes = savedStateHandle.getStateFlow(key = "notes", initialValue = emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(key = "searchText", initialValue = "")
    private val isSearchActive = savedStateHandle.getStateFlow(key = "isSearchActive", initialValue = false)

    val state = combine(
        flow = notes,
        flow2 = searchText,
        flow3 = isSearchActive
    ) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes = notes, query = searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = NoteListState()
    )

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id = id)
            loadNotes()
        }
    }
}