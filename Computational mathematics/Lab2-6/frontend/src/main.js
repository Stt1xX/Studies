import { createApp } from 'vue'
import App from './App.vue'
import PrimeVue from 'primevue/config';
import './assets/theme.css'
import router from "./routes/routes.js";

 createApp(App).use(router).use(PrimeVue).mount('#app')
