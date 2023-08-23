package com.example.kmmproject.android.note_list

import com.example.kmmproject.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
