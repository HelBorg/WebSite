<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <b-row>
        <b-col><Menu/></b-col>
        <b-col></b-col>
      </b-row>
    </div>
    <div>
      <router-view>

      </router-view>
    </div>
  </div>
</template>

<script>
  import Navbar from "./Navbar";
  import Menu from "./Menu";
  export default {
    name: 'home',
    components: {Menu, Navbar}
  }
</script>

<style scoped>

</style>
