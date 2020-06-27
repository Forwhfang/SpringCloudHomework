import Vue from 'vue'
import VueRouter from 'vue-router'
import Books from '../views/Books.vue'
import Modification from '../views/Modification.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    redirect: '/books'
  },
  /* books */
  {
    path: '/books',
    name: 'Books',
    component: Books
  },
  /* modification */
  {
    path: '/modification',
    name: 'Modification',
    component: Modification
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
