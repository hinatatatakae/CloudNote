// src/stores/auth.js
export function useAuth() {
  return {
    get currentUser() {
      // 例: { username: 'alice', token: '...' } を想定
      return JSON.parse(localStorage.getItem('user') || 'null')
    },
    setUser(user) {
      localStorage.setItem('user', JSON.stringify(user))
    },
    clear() {
      localStorage.removeItem('user')
    },
    isAuthenticated() {
      return !!localStorage.getItem('user')
    }
  }
}
