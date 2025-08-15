// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

// ベースURLを/apiに
axios.defaults.baseURL = '/api'
// withCredentialsを常に有効化
axios.defaults.withCredentials = true

const app = createApp(App)
app.use(router)
app.mount('#app')
