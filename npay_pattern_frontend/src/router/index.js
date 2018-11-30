import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import Amount from '@/pages/Amount'
import Product from '@/pages/Product'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/amount',
      name: 'Amount',
      component: Amount
    },
    {
      path: '/product',
      name: 'Product',
      component: Product
    }
  ]
})
