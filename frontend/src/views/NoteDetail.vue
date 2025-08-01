<template>
  <div v-if="note">
    <h2>{{ note.title }}</h2>
    <p>オーナー: {{ note.owner }}</p>
    <p>{{ note.content }}</p>

    <router-link :to="`/notes/${note.id}/edit`">
      <button>編集</button>
    </router-link>
    <button @click="remove">削除</button>
  </div>
  <div v-else-if="error">
    <p class="error">{{ error }}</p>
  </div>
  <div v-else>
    <p>読み込み中…</p>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  props: ['id'],
  data() {
    return {
      note: null,
      error: ''
    }
  },
  async created() {
    try {
      const res = await axios.get(`/api/notes/${this.id}`)
      this.note = res.data
    } catch (e) {
      this.error = 'ノートの読み込みに失敗しました'
    }
  },
  methods: {
    async remove() {
      if (!confirm('このノートを本当に削除しますか？')) return
      try {
        await axios.delete(`/api/notes/${this.id}`)
        this.$router.push('/')
      } catch {
        alert('削除に失敗しました')
      }
    }
  }
}
</script>

<style scoped>
button { margin-right: 8px; }
.error { color: red; }
</style>
