import { createApp } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min'
import './style.css'
import App from './App.vue'
import router from './router/router';

createApp(App).use(router).mount('#app')
