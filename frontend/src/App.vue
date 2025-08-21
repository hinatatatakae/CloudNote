<template>
  <div id="app">
    <header class="site-header">
      <nav class="nav">
        <router-link to="/" class="nav-link">🏠 Home</router-link>
        <router-link to="/register" class="nav-link">🆕 登録</router-link>
        <router-link to="/login" class="nav-link">🔐 ログイン</router-link> 
      </nav>
    </header>

    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style scoped>
.site-header {
  background: linear-gradient(135deg, #42b983, #3aa276);
  padding: 12px 24px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}

.nav {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: bold;
  font-size: 1rem;
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.nav-link:hover {
  opacity: 0.85;
  transform: translateY(-1px);
}

.main-content {
  padding: 24px 16px;
  min-height: calc(100vh - 60px);
  background-color: #f7f9fa;
}
</style>
