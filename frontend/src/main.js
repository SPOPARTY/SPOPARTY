// import './style.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'

// Vuetify
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
// import {aliases, mdi} from 'vuetify/iconsets/mdi-svg'
import {aliases, mdi} from 'vuetify/iconsets/mdi'
import {mdiAccount} from '@mdi/js'

import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia()

const vuetify = createVuetify({
    components,
    directives,
    icons : {
        defaultSet:'mdi',
        aliases: {
            ...aliases,
            account: mdiAccount,
        },
        sets: {
            mdi,
        },
    }
  })
  

app.use(router);
app.use(pinia);
app.use(vuetify);

app.mount('#app')
