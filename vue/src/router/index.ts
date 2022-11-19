import { createRouter, createWebHistory } from 'vue-router'
import GamingMap from '@/components/GamingMap.vue'
import App from '@/TestApp.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: GamingMap
    },
    {
      path: '/app',
      name: 'App',
      component: App
    }
  ]
})

export default router
