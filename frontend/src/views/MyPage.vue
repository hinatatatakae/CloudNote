<!-- src/views/MyPage.vue -->
<template>
  <section>
    <h2>{{ username }} さんのノート一覧</h2>
    <NoteForm @created="handleNoteCreated" />
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
