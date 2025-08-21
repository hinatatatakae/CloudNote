<template>
  <section class="login-form card">
    <h2 class="form-title">🔐 ログイン</h2>

    <form @submit.prevent="submit">
      <div class="field">
        <label for="username">ユーザー名</label>
        <input
          id="username"
          v-model="form.username"
          required
          autocomplete="username"
        />
      </div>

      <div class="field">
        <label for="password">パスワード</label>
        <input
          id="password"
          type="password"
          v-model="form.password"
          required
          autocomplete="current-password"
        />
      </div>

      <button type="submit" :disabled="loading" class="btn-primary">
        <span v-if="loading">ログイン中…</span>
        <span v-else>ログイン</span>
      </button>

      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </section>
</template>

<script>
import axios from 'axios'
import { useAuth } from '@/stores/auth'

export default {
  name: 'Login',
  data() {
    return {
      form: { username: '', password: '' },
      loading: false,
      error: ''
    }
  },
  methods: {
    async submit() {
      this.loading = true
      this.error = ''
      try {
        const res = await axios.post(
          '/auth/login',
          { username: this.form.username, password: this.form.password },
          { withCredentials: true }
        )

        const auth = useAuth()
        auth.setUser(res.data)
        this.$router.push({ name: 'MyPage' })
      } catch (e) {
        if (e.response?.status === 401) {
          this.error = 'ユーザー名またはパスワードが正しくありません'
        } else {
          this.error = 'ログインに失敗しました'
        }
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.card {
  background: #fff;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.06);
  max-width: 420px;
  margin: 40px auto;
}
.form-title {
  text-align: center;
  margin-bottom: 20px;
  color: #35495e;
  font-size: 1.5rem;
}
.field {
  margin-bottom: 14px;
}
label {
  display: block;
  margin-bottom: 6px;
  font-weight: bold;
}
input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}
.error {
  margin-top: 12px;
  color: #d9534f;
  text-align: center;
}
.btn-primary {
  width: 100%;
  background: linear-gradient(135deg, #42b983, #3aa276);
  color: white;
  border: none;
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 6px;
  transition: background 0.25s ease;
}
.btn-primary:hover {
  background: linear-gradient(135deg, #3aa276, #319068);
}
.btn-primary:disabled {
  opacity: 0.6;
}
</style>
