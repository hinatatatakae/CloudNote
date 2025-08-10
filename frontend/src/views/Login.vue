<!-- src/views/Login.vue -->
<template>
  <section class="login-form">
    <h2>ログイン</h2>

    <form @submit.prevent="submit">
      <div>
        <label>ユーザー名</label>
        <input v-model="form.username" required />
      </div>
      <div>
        <label>パスワード</label>
        <input type="password" v-model="form.password" required />
      </div>

      <button type="submit">ログイン</button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </section>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Login',
  data() {
    return {
      form: { username: '', password: '' },
      error: ''
    }
  },
  methods: {
    async submit() {
		try {
			const params = new URLSearchParams()
			params.append('username', this.form.username)
			params.append('password', this.form.password)

			await axios.post('/api/auth/login', params, {
				withCredentials: true,
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
		})

		this.$router.push({ name: 'Home' })
		} catch (e) {
		this.error = 'ログインに失敗しました'
	}
}

  }
}
</script>

<style scoped>
.login-form {
  max-width: 400px;
  margin: 40px auto;
  padding: 16px;
  border: 1px solid #ececec;
  border-radius: 4px;
}
.login-form div {
  margin-bottom: 12px;
}
.login-form label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
}
.login-form input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
.login-form button {
  background: #35495e;
  color: white;
  border: none;
  padding: 10px 16px;
  cursor: pointer;
}
.login-form .error {
  color: red;
  margin-top: 8px;
}
</style>
