<template>
  <div id="menu"
       style="float: left;width:29%"
       class="container-fluid ">
      <b-list-group title="Menu">
        <b-list-group-item :to="'tasks/mine'">
          Mine
        </b-list-group-item>
        <b-list-group-item :to="'tasks/candidates'">
          Candidates
        </b-list-group-item>
        <b-list-group-item :to="'tasks/in_work'">
          In work
        </b-list-group-item>
        <b-list-group-item @click="createNewTask">
            Create New
        </b-list-group-item>
        <b-list-group-item :to="'tasks/search'">
          Search
        </b-list-group-item>
        <b-list-group-item :to="'users'">Users</b-list-group-item>
      </b-list-group>
      <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
      </main>
    </div>
</template>

<script>
 import router from "../router";
  export default {
    name: "Menu",
data() {
      return {
        id:'',
      }
    },
    methods: {
      createNewTask() {
        fetch('/api/v1/task/new', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          }
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                console.log(data)
                router.push('/task/' + data.id);
                })
            }
          )
      }
    }
  }

</script>

<style scoped>

</style>
