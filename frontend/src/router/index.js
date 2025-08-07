// src/router/index.js
import { createRouter, createWebHashHistory } from 'vue-router'
import Home       from '@/views/Home.vue'
import NoteDetail from '@/components/NoteDetail.vue'
import NoteEdit   from '@/components/NoteEdit.vue'

const routes = [
  { path: '/',               name: 'Home',       component: Home },
  { path: '/notes/:id',      name: 'NoteDetail', component: NoteDetail, props: true },
  { path: '/notes/:id/edit', name: 'NoteEdit',   component: NoteEdit,   props: true }
]

export default createRouter({
  history: createWebHashHistory(),  // ← 引数を消す
  routes
})

