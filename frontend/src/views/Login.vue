<!-- src/views/Login.vue -->
<template>
  <section class="login-form">
    <h2>ログイン</h2>

    <form @submit.prevent="submit">
      <div>
        <label for="username">ユーザー名</label>
        <input
          id="username"
          v-model="form.username"
          required
          autocomplete="username"
        />
      </div>

      <div>
        <label for="password">パスワード</label>
        <input
          id="password"
          type="password"
          v-model="form.password"
          required
          autocomplete="current-password"
        />
      </div>

      <button type="submit" :disabled="loading">
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
      form: {
        username: '',
        password: ''
      },
      loading: false,
      error: ''
    }
  },
  methods: {
    async submit() {
      this.loading = true
      this.error = ''
      try {
        // フォームの内容を x-www-form-urlencoded 形式に整形
        const params = new URLSearchParams()
        params.append('username', this.form.username)
        params.append('password', this.form.password)

        // 認証リクエスト
		const res = await axios.post(
			'/api/auth/login',
			{ username: this.form.username, password: this.form.password },
			{ withCredentials: true }
		)

        // 認証成功時：ユーザー情報をストアに保存
        const auth = useAuth()
        auth.setUser(res.data)

        // マイページへリダイレクト
        this.$router.push({ name: 'MyPage' })
      } catch (e) {
        // 失敗時はステータスに応じてエラーメッセージ表示
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
.login-form {
  max-width: 400px;
  margin: 40px auto;
  padding: 24px;
  border: 1px solid #ececec;
  border-radius: 6px;
  background: #fafafa;
}

.login-form h2 {
  margin-bottom: 16px;
  text-align: center;
  color: #35495e;
}

.login-form div {
  margin-bottom: 14px;
}

.login-form label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
}

.login-form input {
  width: 100%;
  padding: 8px 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login-form button {
  width: 100%;
  background: #35495e;
  color: white;
  border: none;
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
}

.login-form button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.login-form .error {
  margin-top: 12px;
  color: #d9534f;
  text-align: center;
}
</style>
