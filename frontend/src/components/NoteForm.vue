<template>
  <section class="note-form card">
    <h2 class="section-title">ノートを作成</h2>

    <form @submit.prevent="createNewNote">
      <div class="field">
        <label for="owner">オーナー</label>
        <input
          id="owner"
          v-model="owner"
          required
          placeholder="オーナー名を入力"
        />
      </div>

      <div class="field">
        <label for="title">タイトル</label>
        <input
          id="title"
          v-model="title"
          required
          maxlength="255"
          placeholder="タイトルを入力"
        />
      </div>

      <div class="field">
        <label for="content">内容</label>
        <textarea
          id="content"
          v-model="content"
          required
          placeholder="内容を入力"
        ></textarea>
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
    return {
      owner: '',
      title: '',
      content: '',
      loading: false,
      error: ''
    }
  },
  methods: {
    async createNewNote() {
      this.error = ''
      this.loading = true
      try {
        const payload = {
          owner: this.owner,
          title: this.title,
          content: this.content
        }
        const res = await axios.post('/api/notes', payload)
        this.$emit('created', res.data)
        this.owner = this.title = this.content = ''
      } catch (e) {
        this.error = e.response?.data?.message || '作成に失敗しました'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.card {
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  padding: 24px;
}

.section-title {
  margin-bottom: 16px;
  font-size: 1.25rem;
  color: #222;
  text-align: center;
}

.field {
  margin-bottom: 16px;
}

.field label {
  display: block;
  margin-bottom: 6px;
  color: #555;
  font-size: 0.9rem;
}

.field input,
.field textarea {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

.field input:focus,
.field textarea:focus {
  outline: none;
  border-color: #3b82f6;
}

.field textarea {
  min-height: 100px;
  resize: vertical;
}

.btn-submit {
  display: block;
  width: 100%;
  background-color: #3b82f6;
  color: #fff;
  padding: 12px 0;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-submit:hover:not(:disabled) {
  background-color: #2563eb;
}

.btn-submit:disabled {
  background-color: #93c5fd;
  cursor: not-allowed;
}

.error {
  margin-top: 12px;
  color: #e11d48;
  text-align: center;
  font-size: 0.9rem;
}
</style>
