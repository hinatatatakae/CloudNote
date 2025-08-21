<template>
  <form class="note-form" @submit.prevent="createNewNote">
    <div class="field">
      <label for="title">タイトル</label>
      <input
        id="title"
        v-model="title"
        type="text"
        placeholder="タイトルを入力"
        required
      />
    </div>

    <div class="field">
      <label for="content">本文</label>
      <textarea
        id="content"
        v-model="content"
        placeholder="新しいノートを入力"
        required
      ></textarea>
    </div>

    <button type="submit" :disabled="loading" class="btn-primary">
      <span v-if="loading">作成中…</span>
      <span v-else>作成</span>
    </button>

    <p v-if="error" class="error">{{ error }}</p>
  </form>
</template>

<script>
import axios from 'axios'

export default {
  name: 'NoteForm',
  data() {
    return {
      title: '',
      content: '',
      loading: false,
      error: ''
    }
  },
  methods: {
    async createNewNote() {
      this.loading = true
      this.error = ''
      try {
        const payload = { title: this.title, content: this.content }
        const res = await axios.post('/notes', payload)
        this.$emit('noteCreated', res.data)
        this.title = ''
        this.content = ''
      } catch (e) {
        this.error = e.response?.data?.message || 'ノートの作成に失敗しました'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.note-form {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}
.field {
  margin-bottom: 16px;
}
label {
  display: block;
  margin-bottom: 6px;
  font-weight: bold;
}
input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
}
textarea {
  min-height: 120px;
  resize: vertical;
}
.error {
  color: #c33;
  margin-top: 8px;
}
.btn-primary {
  background: linear-gradient(135deg, #42b983, #3aa276);
  color: white;
  border: none;
  padding: 10px 18px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
