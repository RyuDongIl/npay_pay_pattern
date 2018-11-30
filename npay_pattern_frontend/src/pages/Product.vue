<template>

</template>

<script>
export default {
  name: 'Product',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  created() {
    //do something after creating vue instance
    const object = {
      store: "GP",
      year: 2018,
      month: 6
    }
    this.$store.dispatch('getProductByMonth', object)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
