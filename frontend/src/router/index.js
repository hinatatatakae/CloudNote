// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Home         from '@/views/Home.vue'
import Register     from '@/views/Register.vue'
import NoteDetail   from '@/views/NoteDetail.vue'
import NoteEdit     from '@/views/NoteEdit.vue'
import Login 　　　　from '@/views/Login.vue'
import MyPage 　　　from '@/views/MyPage.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/register', name: 'Register', component: Register },
  { path: '/login', name: 'Login', component: Login },
  { path: '/mypage', name: 'MyPage', component: MyPage },  // 🔹 追加
  { path: '/notes/:id', name: 'NoteDetail', component: NoteDetail, props: true },
  { path: '/notes/:id/edit', name: 'NoteEdit', component: NoteEdit, props: true }
]


export default createRouter({
  history: createWebHistory(),
  routes
})
