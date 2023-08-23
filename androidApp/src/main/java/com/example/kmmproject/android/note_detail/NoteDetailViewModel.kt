package com.example.kmmproject.android.note_detail

import androidx.lifecycle.*
import com.example.kmmproject.domain.note.Note
import com.example.kmmproject.domain.note.NoteDataSource
import com.example.kmmproject.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val noteTitle = savedStateHandle.getStateFlow(key = NOTE_TITLE, initialValue = "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow(key = IS_NOTE_TITLE_HINT_VISIBLE, initialValue = false)
    private val noteContent = savedStateHandle.getStateFlow(key = NOTE_CONTENT, initialValue = "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow(key = IS_NOTE_CONTENT_HINT_VISIBLE, initialValue = false)
    private val noteColor = savedStateHandle.getStateFlow(key = NOTE_COLOR, initialValue = Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NoteDetailState()
    )

    private val _hasNoteBeenSaved = MutableStateFlow(value = false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>(key = "noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(id = existingNoteId)?.let { note ->
                    savedStateHandle[NOTE_TITLE] = note.title
                    savedStateHandle[NOTE_CONTENT] = note.content
                    savedStateHandle[NOTE_COLOR] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[NOTE_TITLE] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[NOTE_CONTENT] = text
    }

    fun onNoteTitleTextFocused(isFocused: Boolean) {
        savedStateHandle[IS_NOTE_TITLE_HINT_VISIBLE] = isFocused
    }

    fun onNoteContentTextFocused(isFocused: Boolean) {
        savedStateHandle[IS_NOTE_CONTENT_HINT_VISIBLE] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                note = Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

    companion object {
        const val NOTE_TITLE = "noteTitle"
        const val IS_NOTE_TITLE_HINT_VISIBLE = "isNoteTitleHintVisible"
        const val NOTE_CONTENT = "noteContent"
        const val IS_NOTE_CONTENT_HINT_VISIBLE = "isNoteContentHintVisible"
        const val NOTE_COLOR = "noteColor"
    }
}