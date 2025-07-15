<template>
  <div>
    <note-form @created="onCreated" />
    <note-list :notes="notes" />
  </div>
</template>

<script>
import NoteForm from '@/components/NoteForm.vue'
import NoteList from '@/components/NoteList.vue'
import axios from 'axios'

export default {
  components: { NoteForm, NoteList },
  data() {
    return { notes: [] }
  },
  async created() {
    const res = await axios.get('/api/notes')
    this.notes = res.data
  },
  methods: {
    onCreated(note) {
      this.notes.unshift(note)
    }
  }
}
</script>
