<template>
  <div>
    <h1>📓 ノート一覧</h1>
    <ul>
      <li v-for="note in notes" :key="note.id">
        <h3>{{ note.title }}</h3>
        <p>{{ note.content }}</p>
        <small>🕒 {{ note.createdAt }}</small>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'NoteList',
  data() {
    return {
      notes: []
    };
  },
  created() {
    axios.get('/api/notes')
      .then(response => {
        this.notes = response.data;
      })
      .catch(error => {
        console.error('ノートの取得に失敗しました:', error);
      });
  }
};
</script>
