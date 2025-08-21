<template>
  <div class="note-page">
    <header class="page-header">
      <h1>📚 ノート管理</h1>
      <p class="subtitle">あなたのアイデアやメモを、いつでもどこでも。</p>
    </header>

    <main class="page-content">
      <!-- 左：ボタン -->
      <div class="create-section">
        <button
          v-if="!auth.isAuthenticated()"
          @click="$router.push('/login')"
          class="btn-primary"
        >
          ✏️ ノートを作成（ログインが必要）
        </button>
        <button
          v-else
          @click="$router.push('/mypage')"
          class="btn-primary"
        >
          📂 マイページへ移動
        </button>
      </div>

      <!-- 右：ノート一覧 -->
      <NoteList :notes="notes" />
    </main>
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
  max-width: 1080px;
  margin: 0 auto;
  padding: 24px 16px;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-header h1 {
  font-size: 2rem;
  margin-bottom: 8px;
}

.subtitle {
  color: #666;
}

.page-content {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 32px;
}

.create-section {
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

.btn-primary {
  background: linear-gradient(135deg, #42b983, #3aa276);
  color: white;
  padding: 14px 28px;
  font-size: 1rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.25s ease;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}

.btn-primary:hover {
  background: linear-gradient(135deg, #3aa276, #319068);
}

@media (max-width: 768px) {
  .page-content {
    grid-template-columns: 1fr;
  }
}
</style>
