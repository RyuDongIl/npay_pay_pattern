import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import * as Cookies from 'js-cookie'
import { homeGetters, amountGetters, productGetters } from './getters'
import { homeMutations, amountMutations, productMutations } from './mutations'
import { homeActions, amountActions, productActions } from './actions'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    store: null
  },
  getters: Object.assign({}, homeGetters, amountGetters, productGetters),
  mutations: Object.assign({}, homeMutations, amountMutations, productMutations),
  actions: Object.assign({}, homeActions, amountActions, productActions),
  plugins: [
    createPersistedState({
      storage: {
        getItem: key => Cookies.get(key),
        // Please see https://github.com/js-cookie/js-cookie#json, on how to handle JSON.
        setItem: (key, value) =>
          Cookies.set(key, value, { expires: 3, secure: false }), // secure같은 경우는 https일 경우에만 true로 만든다.
        removeItem: key => Cookies.remove(key)
      },
      reducer: state => ({
        store: state.store
      })
    })
  ]
})
