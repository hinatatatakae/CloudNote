<template>
  <div class="note-page">
    <header class="page-header">
      <h1>📝 ノート管理</h1>
    </header>

    <div class="page-content">
      <!-- 左：作成フォーム -->
      <NoteForm @created="onCreated" />

      <!-- 右：ノート一覧 -->
      <NoteList :notes="notes" />
    </div>
  </div>
</template>

<script>
import NoteForm from '@/components/NoteForm.vue'
import NoteList from '@/components/NoteList.vue'
import axios from 'axios'

export default {
  name: 'NotePage',
  components: { NoteForm, NoteList },
  data() {
    return { notes: [] }
  },
  async created() {
    try {
      const res = await axios.get('/api/notes')
      this.notes = res.data
    } catch (err) {
      console.error('ノート取得エラー:', err)
    }
  },
  methods: {
    onCreated(newNote) {
      this.notes.unshift(newNote)
    }
  }
}
</script>

<style scoped>
.note-page {
  max-width: 960px;
  margin: 0 auto;
  padding: 24px 16px;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}

.page-header {
  text-align: center;
  margin-bottom: 32px;
}

.page-header h1 {
  font-size: 2rem;
  margin: 0;
}

.page-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

@media (max-width: 768px) {
  .page-content {
    grid-template-columns: 1fr;
  }
}
</style>
