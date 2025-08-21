<template>
  <section class="mypage card">
    <h2 class="section-title">📂 {{ username }} さんのノート一覧</h2>
    <NoteForm @noteCreated="handleNoteCreated" />
    <NoteList :notes="notes" />
  </section>
</template>

<script>
import NoteForm from '@/components/NoteForm.vue'
import NoteList from '@/components/NoteList.vue'
import axios from 'axios'
import { useAuth } from '@/stores/auth'

export default {
  name: 'MyPage',
  components: { NoteForm, NoteList },
  data() {
    return {
      notes: [],
      username: ''
    }
  },
  async created() {
    const auth = useAuth()
    const user = auth.currentUser
    this.username = user.username || user.name
    await this.loadNotes()
  },
  methods: {
    async loadNotes() {
      const res = await axios.get('/notes/mine')
      this.notes = res.data
    },
    handleNoteCreated(newNote) {
      this.notes.unshift(newNote)
    }
  }
}
</script>

<style scoped>
.card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.06);
  padding: 24px;
  max-width: 960px;
  margin: 24px auto;
}
.section-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 1.4rem;
}
</style>
