// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Home       from '@/views/Home.vue'
import NoteDetail from '@/views/NoteDetail.vue'
import NoteEdit   from '@/views/NoteEdit.vue'

const routes = [
  { path: '/',               name: 'Home',       component: Home },
  { path: '/notes/:id',      name: 'NoteDetail', component: NoteDetail, props: true },
  { path: '/notes/:id/edit', name: 'NoteEdit',   component: NoteEdit,   props: true }
]

export default createRouter({
  history: createWebHistory(), // ← HashモードからHistoryモードへ
  routes
})
