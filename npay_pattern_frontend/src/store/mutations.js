import { store } from '../store/store'

export const amountMutations = {
  amountByYear (state, payload) {
    console.log(payload)
  },
  amountByMonth (state, payload) {
    console.log(payload)
  },
  amountByDay (state, payload) {
    console.log(payload)
  }
}

export const productMutations = {
  productByYear (state, payload) {
    console.log(payload)
  },
  productByMonth (state, payload) {
    console.log(payload)
  },
  productByDay (state, payload) {
    console.log(payload)
  }
}

export const homeMutations = {
  storeList (state, payload) {
    console.log(payload)
  }
}
