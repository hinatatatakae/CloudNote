<!-- src/components/NoteForm.vue -->
<template>
  <section class="note-form card">
    <h2 class="section-title">ノートを作成</h2>
    <form @submit.prevent="createNewNote">
      <div class="field">
        <label for="title">タイトル</label>
        <input id="title" v-model="title" required maxlength="255" placeholder="タイトルを入力" />
      </div>
      <div class="field">
        <label for="content">内容</label>
        <textarea id="content" v-model="content" required placeholder="内容を入力"></textarea>
      </div>
      <button class="btn-submit" type="submit" :disabled="loading">
        {{ loading ? '作成中…' : 'ノートを作成' }}
      </button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </section>
</template>

<script>
import axios from 'axios'

export default {
  name: 'NoteForm',
  emits: ['created'],
  data() {
    return { title: '', content: '', loading: false, error: '' }
  },
  methods: {
    async createNewNote() {
      this.error = ''
      this.loading = true
      try {
        const res = await axios.post('/api/notes', { title: this.title, content: this.content })
        this.$emit('created', res.data)
        this.title = ''
        this.content = ''
      } catch (e) {
        this.error = e.response?.data?.message || '作成に失敗しました'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>
