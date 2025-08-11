<!-- src/views/Register.vue -->
<template>
  <section class="register-form">
    <h2>ユーザー登録</h2>

    <form @submit.prevent="submit">
      <div>
        <label>ユーザー名</label>
        <input v-model="form.username" required maxlength="20" />
      </div>
      <div>
        <label>メール</label>
        <input type="email" v-model="form.email" required />
      </div>
      <div>
        <label>パスワード</label>
        <input type="password" v-model="form.password" required minlength="6" />
      </div>

      <button type="submit">登録</button>
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
      error: ''
    }
  },
  methods: {
    async submit() {
      try {
        const res = await axios.post('/api/auth/register', this.form)
		// 成功時
		const auth = useAuth()
		auth.setUser(res.data)
		this.$router.push('/mypage')
      } catch (e) {
        this.error = e.response?.data?.message || '登録に失敗しました'
      }
    }
  }
}
</script>

<style scoped>
.register-form {
  max-width: 400px;
  margin: 40px auto;
  padding: 16px;
  border: 1px solid #ececec;
  border-radius: 4px;
}
.register-form div {
  margin-bottom: 12px;
}
.register-form label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
}
.register-form input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
.register-form button {
  background: #42b983;
  color: white;
  border: none;
  padding: 10px 16px;
  cursor: pointer;
}
.register-form .error {
  color: red;
  margin-top: 8px;
}
</style>
