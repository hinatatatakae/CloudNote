<template>
  <form @submit.prevent="createNewNote">
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

    <button type="submit" :disabled="loading">
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
        // title も一緒に送信
        const payload = {
          title: this.title,
          content: this.content
        }
        const res = await axios.post('/notes', payload)
        this.$emit('noteCreated', res.data)

        // フォームをリセット
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
.field {
  margin-bottom: 12px;
}

input, textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.error {
  color: #c33;
  margin-top: 6px;
}
</style>
