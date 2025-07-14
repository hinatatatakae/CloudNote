<template>
  <div>
    <h2>ノート一覧</h2>
    <ul>
      <li v-for="note in notes" :key="note.id">{{ note.title }}</li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      notes: [],
    }
  },
  mounted() {
    axios.get('http://spring:8081/api/notes')
      .then(res => {
        this.notes = res.data
      })
      .catch(err => {
        console.error('API取得エラー:', err)
      })
  }
}
</script>
