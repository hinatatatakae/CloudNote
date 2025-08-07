<template>
  <section class="note-list card">
    <h2 class="section-title">📝 ノート一覧</h2>
    <p v-if="!notes.length" class="empty">まだノートがありません。</p>
    <ul v-else class="list">
      <li v-for="note in notes" :key="note.id" class="item">
        <router-link
          class="item-link"
          :to="{ name:'NoteDetail', params:{ id: note.id } }"
        >
          {{ note.title }}
        </router-link>

        <p class="item-summary">{{ truncate(note.content, 100) }}</p>
        <div class="item-meta">👤 {{ note.owner }}</div>
      </li>
    </ul>
  </section>
</template>

<script>
export default {
  name: 'NoteList',
  props: {
    notes: { type: Array, default: () => [] }
  },
  methods: {
    truncate(text, len) {
      return text.length > len ? text.slice(0, len) + '…' : text
    }
  }
}
</script>

<style scoped>
.card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  padding: 24px;
}
.section-title { text-align:center; margin-bottom:16px; font-size:1.25rem; }
.empty { text-align:center; color:#777; margin-top:24px; }
.list { list-style:none; padding:0; margin:0; }
.item { padding:16px 0; border-bottom:1px solid #ececec; }
.item:last-child { border-bottom:none; }
.item-link { color:#42b983; font-size:1.1rem; text-decoration:none; }
.item-link:hover { text-decoration:underline; }
.item-summary { color:#555; line-height:1.4; margin:4px 0 8px; }
.item-meta { font-size:0.85rem; color:#555; }
</style>
