import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/homePage/Home'
import SingUp from '../components/SingUp'
import Task from '../components/Task/Task'
import Login from '../components/Login'
import store from '../store'
import * as types from '../store/mutation-types'
import Tasks from "../components/tasks/Tasks"

const hasToken = (to, from, next) => {
  const token = localStorage.getItem('JWT')
  const username = localStorage.getItem('username')
  if (token) {
    store.commit(types.LOGIN_SUCCESS, { token, username })
    router.push('/home')
  } else {
    next()
  }
}

const requireAuth = (to, from, next) => {
  if (store.getters.isLoggedIn) {
    next()
  } else {
    router.replace('/')
  }
}

Vue.use(Router)

const router = new Router({
  mode: 'hash',
  routes: [
    {
      path: '/',
      alias: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: hasToken
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      beforeEnter: requireAuth
    },
    {
      path: '/tasks/:pageName',
      name: 'tasks',
      component: Tasks,
    },
    {
      path: '/task/:id',
      name: 'Task',
      component: Task,

    },
    {
      path: '/user',
      name: 'User',
    },
    {
      path: '/singup',
      name: 'singup',
      component: SingUp
    },
    {
      path: '*',
      redirect: '/login'
    }
  ]
})
export default router
