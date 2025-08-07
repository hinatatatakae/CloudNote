<template>
  <div>
    <h2>ノートを編集</h2>
    <form @submit.prevent="save">
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
        {{ loading ? '保存中...' : '保存' }}
      </button>
      <button @click.prevent="cancel">キャンセル</button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  props: ['id'],
  data() {
    return {
      owner: '',
      title: '',
      content: '',
      loading: false,
      error: ''
    }
  },
  async created() {
    try {
      const res = await axios.get(`/api/notes/${this.id}`)
      const note = res.data
      this.owner = note.owner
      this.title = note.title
      this.content = note.content
    } catch {
      this.error = 'ノートの読み込みに失敗しました'
    }
  },
  methods: {
    async save() {
      this.loading = true
      this.error = ''
      try {
        const payload = {
          owner: this.owner,
          title: this.title,
          content: this.content
        }
        await axios.put(`/api/notes/${this.id}`, payload)
        this.$router.push(`/notes/${this.id}`)
      } catch {
        this.error = '保存に失敗しました'
      } finally {
        this.loading = false
      }
    },
    cancel() {
      this.$router.back()
    }
  }
}
</script>

<style scoped>
.error { color: red; }
form > div { margin-bottom: 12px; }
button { margin-right: 8px; }
</style>
