import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import fs from 'fs'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    https: {
      key: fs.readFileSync('./cert/key.pem'),
      cert: fs.readFileSync('./cert/cert.pem')
    },
    host: 'localhost',
    port: 5173,
	strictPort: true,
    hmr: {
      protocol: 'wss',
      host: 'localhost',
      port: 5173,
      clientPort: 5173
    },
    proxy: {
      '/api': {
        target: 'https://spring-1:8081',
        changeOrigin: true,
        secure: false,
        cookieDomainRewrite: 'localhost',
		cookiePathRewrite: { '^/api': '' },
      }
    }
  }
})
