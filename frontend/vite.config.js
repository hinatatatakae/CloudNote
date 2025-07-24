import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'


export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // プロジェクトルート/src を @ で参照
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    cors: true,
    host: '0.0.0.0',
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://spring:8081', // or your service name
        changeOrigin: true,
        secure: false,
        rewrite: path => path.replace(/^\/api/, '/api')
      }
    }
  }
})