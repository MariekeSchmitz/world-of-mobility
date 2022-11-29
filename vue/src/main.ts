import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './TestCompVickyKevin2.vue'
import router from './router'
import GamingMap from '@/components/GamingMap.vue'
import EditorView from '@/views/editor/EditorView.vue'

const app = createApp(EditorView)

//app.use(createPinia())
//app.use(router)

app.mount('#app')
