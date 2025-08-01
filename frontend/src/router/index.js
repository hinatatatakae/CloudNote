import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import NoteDetail from '@/views/NoteDetail.vue'
import NoteEdit   from '@/views/NoteEdit.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/notes',      redirect: '/' }, 
  { path: '/notes/:id', component: NoteDetail, props: true },
  { path: '/notes/:id/edit', component: NoteEdit, props: true }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
