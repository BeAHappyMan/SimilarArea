// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import './plugins/axios'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';             //全局引入element
import 'element-ui/lib/theme-chalk/index.css';    //全局引入element的样式
Vue.config.productionTip = false

/* eslint-disable no-new */
Vue.use(ElementUI);
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})
