<template>
  <section class="register-form card">
    <h2 class="form-title">🆕 ユーザー登録</h2>
    <form @submit.prevent="submit">
      <div class="field">
        <label for="username">ユーザー名</label>
        <input id="username" v-model="form.username" required maxlength="20" autocomplete="username" />
      </div>
      <div class="field">
        <label for="email">メール</label>
        <input id="email" type="email" v-model="form.email" required autocomplete="email" />
      </div>
      <div class="field">
        <label for="password">パスワード</label>
        <input id="password" type="password" v-model="form.password" required minlength="6" autocomplete="new-password" />
      </div>
      <button type="submit" :disabled="loading" class="btn-primary">
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

export default {
  name: 'Register',
  data() {
    return {
      form: { username: '', email: '', password: '' },
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
        const res = await axios.post('/auth/register', this.form, { withCredentials: true })
        const auth = useAuth()
        auth.setUser(res.data)
        this.$router.push({ name: 'MyPage' })
      } catch (e) {
        this.error = e.response?.data?.message || '登録に失敗しました'
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
}
.btn-primary:disabled {
  opacity: 0.6;
}
</style>
