<!-- src/views/Register.vue -->
<template>
  <section class="register-form">
    <h2>ユーザー登録</h2>

    <form @submit.prevent="submit">
      <div>
        <label for="username">ユーザー名</label>
        <input
          id="username"
          v-model="form.username"
          required
          maxlength="20"
          autocomplete="username"
        />
      </div>

      <div>
        <label for="email">メール</label>
        <input
          id="email"
          type="email"
          v-model="form.email"
          required
          autocomplete="email"
        />
      </div>

      <div>
        <label for="password">パスワード</label>
        <input
          id="password"
          type="password"
          v-model="form.password"
          required
          minlength="6"
          autocomplete="new-password"
        />
      </div>

      <button type="submit" :disabled="loading">
        <span v-if="loading">登録中…</span>
        <span v-else>登録</span>
      </button>

      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </section>
</template>

<script>
import axios from 'axios'
import { useAuth } from '@/stores/auth'

// グローバルに withCredentials を有効化
axios.defaults.withCredentials = true

export default {
  name: 'Register',

  data() {
    return {
      // ← 必ず data() で初期化する
      form: {
        username: '',
        email: '',
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
		if (this.form.username.length < 3 || this.form.username.length > 20) {
			this.error = 'ユーザー名は3～20文字で入力してください'
			this.loading = false
			return
		}

		if (!/.+@.+\..+/.test(this.form.email)) {
			this.error = 'メール形式が正しくありません'
			this.loading = false
			return
		}

		if (this.form.password.length < 6) {
			this.error = 'パスワードは6文字以上で入力してください'
			this.loading = false
			return
		}

        // API 登録エンドポイントへ POST
        const res = await axios.post(
          '/auth/register',
          this.form,
          { withCredentials: true }
        )

        // 認証情報をストアに保存
        const auth = useAuth()
        auth.setUser(res.data)

        // マイページへ遷移
        this.$router.push({ name: 'MyPage' })
      } catch (e) {
        // サーバーからのメッセージ or 汎用エラー
        this.error = e.response?.data?.message || '登録に失敗しました'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.register-form {
  max-width: 400px;
  margin: 40px auto;
  padding: 24px;
  border: 1px solid #ececec;
  border-radius: 6px;
  background: #fafafa;
}

.register-form h2 {
  text-align: center;
  margin-bottom: 16px;
  color: #35495e;
}

.register-form div {
  margin-bottom: 14px;
}

.register-form label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
}

.register-form input {
  width: 100%;
  padding: 8px 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.register-form button {
  width: 100%;
  background: #42b983;
  color: white;
  border: none;
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
}

.register-form button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.register-form .error {
  margin-top: 12px;
  color: #d9534f;
  text-align: center;
}
</style>
