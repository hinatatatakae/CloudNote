<template>
  <section class="note-detail">
    <button @click="$router.back()" class="btn-back">← 戻る</button>
    <h2>{{ note.title }}</h2>
    <p>{{ note.content }}</p>
    <footer>👤 {{ note.owner }}</footer>
  </section>
</template>

<script>
import axios from 'axios'
export default {
  name: 'NoteDetail',
  props: {
    id: {
      type: String,
      required: true
    }
  },
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
    } catch {
      this.error = 'ノート取得に失敗しました'
    }
  }
}

</script>

<style scoped>
.note-detail { max-width:600px; margin:40px auto; padding:0 16px; }
.btn-back { background:none; border:none; color:#42b983; cursor:pointer; margin-bottom:16px; }
</style>
