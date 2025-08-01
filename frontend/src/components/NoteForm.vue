<template>
  <form @submit.prevent="createNote">
    <div>
      <label>オーナー</label>
      <input v-model="owner" required />
    </div>
    <div>
      <label>タイトル</label>
      <input v-model="title" required maxlength="255" />
    </div>
    <div>
      <label>内容</label>
      <textarea v-model="content" required></textarea>
    </div>
    <button type="submit" :disabled="loading">
      {{ loading ? '作成中...' : 'ノートを作成' }}
    </button>
    <p v-if="error" class="error">{{ error }}</p>
  </form>
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
    async createNote() {
      this.error = ''
      this.loading = true
      try {
        const payload = {
          owner: this.owner,
          title: this.title,
          content: this.content
        }
		console.log('DEBUG payload:', payload);
		// axios は相対パス
　　　　const res = await axios.post('/api/notes', payload);

        this.$emit('created', res.data);
        this.owner = this.title = this.content = '';
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
.error { color: red; margin-top: 8px; }
form > div { margin-bottom: 12px; }
</style>
