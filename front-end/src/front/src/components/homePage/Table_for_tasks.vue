<template>
  <div>
    <b-table id="tasks"
             :items="items"
             :fields="fields"
             :per-page="perPage"
             :current-page="currentPage"
             small>
      <template slot="show_details" slot-scope="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template slot="row-details" slot-scope="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Task rate:</b></b-col>
            <b-col>{{ row.item.task_rate }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Skills:</b></b-col>
          </b-row>
          <b-row class="mb-2" v-for="skill in row.item.skills">
            <b-col></b-col>
            <b-col>{{ skill.name }}</b-col>
            <b-col>{{ skill.rate }}</b-col>
          </b-row>
          <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
        </b-card>
      </template>
    </b-table>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="tasks"
      first-text="First"
      prev-text="Prev"
      next-text="Next"
      last-text="Last"
      size="sm"
    ></b-pagination>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "Table_for_tasks",
    data() {
      return {
        perPage: 1,
        currentPage: 1,
        tasks: [],
        name: '',
        status: '',

        fields: {
          name: {
            label: 'Name',
            sortable: true
          },
          status: {
            label: 'Status'
          },
          date_from: {
            label: 'Date of creating',
            sortable: true
          },
          date_to: {
            label: 'To',
            sortable: true
          },
          task_rate: {
            label: 'Rate',
            sortable: true
          },
          author: {
            label: 'Author',
            sortable: true
          },
          skills: {
            label: 'Skills'
          },
          assigned: {
            label: 'Assigned'
          }
        }
      }
    },
    computed: {
      rows() {
        return this.items.length
      }
    },
    methods: {
      getFilms() {
        console.log(this.year)
        axios.get('http://localhost:8080/api/tasks', {
          params: {
            title: this.title,
            year: this.year,
            country: this.country,
            genre: this.genre,
            releaseDate: this.releaseDate,
            budget: this.budget
          }
        }).then((response) => {
          console.log(response.data);
          if (response.data.empty) {
            this.info = true;
          } else {
            this.films = response.data;
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
