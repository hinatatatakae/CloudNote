package com.hinata.cloudnote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hinata.cloudnote.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    // あるユーザーのノート一覧を取得（任意）
    List<Note> findByOwner(String owner);
    
    // タグに指定ワードを含むノートを検索（例：%学習%）
    List<Note> findByTagsContaining(String tag);
}

