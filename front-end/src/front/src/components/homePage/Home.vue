<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <b-row>
        <b-col>
          <Menu/>
        </b-col>
        <b-col>
            <div class="hero">
              <h1 class="display-3">Hello World</h1>
              <p class="lead">This is the homepage of our website</p>
            </div>
        </b-col>
      </b-row>
  </div>
  </div>
</template>

<script>
  import Navbar from "../Navbar";
  import Menu from "../Menu";

  export default {
    name: 'home',
    pageName: 'welcome',
    data() {
      return {}
    },
    components: {Menu, Navbar},
    methods: {
      getPage() {
        this.pageName = this.$router.params.pageName
      }
    }
  }
</script>

<style scoped>
  .hero {
    height: 90vh;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
  }

  .hero .lead {
    font-weight: 200;
    font-size: 1.5rem;
  }
</style>
