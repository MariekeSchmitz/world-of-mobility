import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './TestCompVickyKevin.vue'
import router from './router'
import GamingMap from '@/components/GamingMap.vue'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
