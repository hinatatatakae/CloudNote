<template>
  <header>
    <router-link to="/">Home</router-link>
    <router-link to="/register">登録</router-link>
    <router-link to="/login">ログイン</router-link> 
  </header>
  <router-view />
</template>

<script>
import Home from './views/Home.vue'

export default {
  name: 'App',
  components: { Home }
}
</script>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>

