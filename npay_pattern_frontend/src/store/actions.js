import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://106.10.34.24:8080/',
  timeout: 30000
})

const STATUS_OK = 200
const STATUS_NOT_FOUND = 404

export const amountActions = {
  getAmountByYear ({ commit }, store) {
    instance.get(`/date/amount?store=${store}`).then(response => {
      if (response.data.status == STATUS_OK) {
        console.log(response)
        commit('amountByYear', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  },
  getAmountByMonth ({ commit }, payload) {
    instance.get(`/date/amount?store=${payload.store}&year=${payload.year}`).then(response => {
      if (response.data.status == STATUS_OK) {
        commit('amountByMonth', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  },
  getAmountByDay ({ commit }, payload) {
    instance.get(`/date/amount?store=${payload.store}&year=${payload.year}&month=${payload.month}`).then(response => {
      if (response.data.status == STATUS_OK) {
        commit('amountByDay', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  }
}

export const productActions = {
  getProductByYear ({ commit }, payload) {
    instance.get(`/date/product?store=${payload.store}&year=${payload.year}`).then(response => {
      if (response.data.status == STATUS_OK) {
        commit('productByYear', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  },
  getProductByMonth ({ commit }, payload) {
    instance.get(`/date/product?store=${payload.store}&year=${payload.year}&month=${payload.month}`).then(response => {
      if (response.data.status == STATUS_OK) {
        commit('productByMonth', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  },
  getProductByDay ({ commit }, payload) {
    instance.get(`/date/product?store=${payload.store}&year=${payload.year}&month=${payload.month}&day=${payload.day}`).then(response => {
      if (response.data.status == STATUS_OK) {
        commit('productByDay', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  }
}

export const homeActions = {
  getStore ({ commit }) {
    instance.get(`/`).then(response => {
      if (response.data.status == STATUS_OK) {
        console.log(response.data)
        commit('storeList', response.data.data)
      } else {
        alert(response.data.message)
      }
    }).catch(error => {
      alert(error.message)
    })
  }
}
