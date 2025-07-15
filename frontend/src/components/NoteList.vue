<template>
  <ul>
    <li v-for="note in notes" :key="note.id">
      <h3>{{ note.title }} <small>by {{ note.owner }}</small></h3>
      <p>{{ note.content }}</p>
    </li>
  </ul>
</template>

<script>
export default {
  name: 'NoteList',
  props: { notes: { type: Array, default: () => [] } }
}
</script>
