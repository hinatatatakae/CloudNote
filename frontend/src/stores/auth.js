// src/stores/auth.js
export function useAuth() {
  return {
    get currentUser() {
      return JSON.parse(localStorage.getItem('user') || 'null')
    },
    isAuthenticated() {
      return !!localStorage.getItem('user')
    },
    setUser(user) {
      localStorage.setItem('user', JSON.stringify(user))
    },
    clear() {
      localStorage.removeItem('user')
    }
  }
}
