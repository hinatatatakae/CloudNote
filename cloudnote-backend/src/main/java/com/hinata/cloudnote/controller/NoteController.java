package com.hinata.cloudnote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hinata.cloudnote.entity.Note;
import com.hinata.cloudnote.repository.NoteRepository;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    // ✅ すべてのノートを取得
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // ✅ ID指定でノートを取得
    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    // ✅ ノートを新規作成
    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    // ✅ ノートを削除
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
    }
}
