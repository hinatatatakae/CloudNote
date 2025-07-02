<template>
  <div class="home">
    <header class="home-header">
      <h1>{{ $t('home.title') }}</h1>
      <button @click="createNote">{{ $t('home.create') }}</button>
    </header>

    <section class="note-list">
      <div
        v-for="note in notes"
        :key="note.id"
        class="note-card"
        @click="goToDetail(note.id)"
      >
        <h2>{{ note.title }}</h2>
        <p>{{ note.summary }}</p>
        <span class="tag" v-for="tag in note.tags" :key="tag">{{ tag }}</span>
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getNotes } from '@/services/note'
import { useRouter } from 'vue-router'

const router = useRouter()
const notes = ref([])

onMounted(async () => {
  const res = await getNotes()
  notes.value = res.data
})

function goToDetail(id) {
  router.push(`/notes/${id}`)
}

function createNote() {
  router.push('/notes/new')
}
</script>

<style scoped>
.note-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
  margin-bottom: 1rem;
  cursor: pointer;
}
.tag {
  margin-right: 5px;
  font-size: 0.8rem;
  color: #666;
}
</style>
