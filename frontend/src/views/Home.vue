<!-- src/views/Home.vue -->
<template>
  <div class="note-page">
    <header class="page-header">
      <h1>ノート管理</h1>
    </header>

    <div class="page-content">
      <!-- 左：作成ボタン -->
      <div class="create-section">
        <button
          v-if="!auth.isAuthenticated()"
          @click="$router.push('/login')"
          class="btn-create"
        >
          ノートを作成（ログインが必要）
        </button>
        <button
          v-else
          @click="$router.push('/mypage')"
          class="btn-create"
        >
          マイページへ移動
        </button>
      </div>

      <!-- 右：全ノート一覧 -->
      <NoteList :notes="notes" />
    </div>
  </div>
</template>

<script>
import { useAuth } from '@/stores/auth'
import NoteList from '@/components/NoteList.vue'
import axios from 'axios'

export default {
  name: 'Home',
  components: { NoteList },
  setup() {
    const auth = useAuth()
    return { auth }
  },
  data() {
    return { notes: [] }
  },
  async created() {
    try {
      const res = await axios.get('/notes')
      this.notes = res.data
    } catch (err) {
      console.error('ノート取得エラー:', err)
    }
  }
}
</script>

<style scoped>
.note-page {
  max-width: 960px;
  margin: 0 auto;
  padding: 24px 16px;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}

.page-header {
  text-align: center;
  margin-bottom: 32px;
}

.page-content {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 24px;
}

.create-section {
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-create {
  background: #42b983;
  color: white;
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.btn-create:hover {
  background: #369f73;
}

@media (max-width: 768px) {
  .page-content {
    grid-template-columns: 1fr;
  }
}
</style>
