import Vue from 'vue'
import App from './App'
import router from './router/index'
// import axios from 'axios'
// import VueAxios from 'vue-axios'
import BootstrapVue from 'bootstrap-vue'
import store from './store'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"

Vue.use(BootstrapVue)
// Vue.config.productionTip = false
// Vue.config.devtools = true

new Vue({
  store,
  el: '#app',
  router,
  template: '<App/>',
  components: {
    App
  }
})
