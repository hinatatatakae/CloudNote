package com.hinata.cloudnote.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    // ✅ すべてのノートを取得
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // ✅ ID指定でノートを取得
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
            .map(note -> ResponseEntity.ok(note))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Note createNewNote(@RequestBody Note note) {
    	System.out.println(">>> controller#POST called!");
        logger.info("▶▶▶ Received Note object: owner={}, title={}, content={}",
                    note.getOwner(), note.getTitle(), note.getContent());
        return noteRepository.save(note);
    }

    // -----------------------------------
    // PUT /api/notes/{id} : 更新
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
        @PathVariable Long id,
        @RequestBody Note incoming
    ) {
      return noteRepository.findById(id)
        .map(old -> {
          old.setOwner(incoming.getOwner());
          old.setTitle(incoming.getTitle());
          old.setContent(incoming.getContent());
          Note updated = noteRepository.save(old);
          return ResponseEntity.ok(updated);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // -----------------------------------
    // DELETE /api/notes/{id} : 削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
      if (!noteRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
      }
      noteRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }
}
