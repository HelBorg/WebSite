<template>
  <div id="tasksList">
    <div>
      <Navbar/>
    </div>
    <div>
      <Menu/>
      <div style="float:right; width: 80%">
        <b-row>
          <b-col>
            <div>
              <b-dropdown id="dropdown-1"
                          class="m-md-2"
                          text="Number of tasks">
                <b-dropdown-item @click="changePerPage(5)">5</b-dropdown-item>
                <b-dropdown-item @click="changePerPage(10)">10</b-dropdown-item>
                <b-dropdown-item @click="changePerPage(50)">50</b-dropdown-item>
                <b-dropdown-item @click="changePerPage(100)">100</b-dropdown-item>
              </b-dropdown>
              <b-dropdown id="dropdown-2"
                          class="m-md-2"
                          text="Sort by">
                <b-dropdown-item @click="changeSort('createdTime')">Date of creation</b-dropdown-item>
                <b-dropdown-item @click="changeSort('deadline')">Deadline</b-dropdown-item>
                <b-dropdown-item @click="changeSort('taskId')">None</b-dropdown-item>
              </b-dropdown>
              <b-dropdown id="dropdown-3"
                          class="m-md-2"
                          text="Direction">
                <b-dropdown-item @click="changeSortDir('des')">From old to new</b-dropdown-item>
                <b-dropdown-item @click="changeSortDir('asc')">From new to old</b-dropdown-item>
              </b-dropdown>
            </div>
            <div v-if="this.show">
              <MyTable :tasks="getTasks.items"></MyTable>
            </div>
            <div>
              <MyPagination :currentPage="page.currentPage"
                            :pagesCount="getTasks.pagesCount"
                            @changePage="changePage"/>
            </div>
          </b-col>
          <div>{{page.user}}</div>

            <b-col cols="4"
                   v-if="page.get==='search'">
              <MyFilter :show="this.page.showFilter"
                        @filter="handleFilter"/>
            </b-col>
        </b-row>
      </div>
    </div>
  </div>
</template>
<script>
  import axios from 'axios';
  import Menu from "../Menu";
  import Navbar from "../Navbar";
  import MyFilter from "./MyFilter";
  import MyPagination from "./MyPagination";
  import MyTable from "./Table_for_tasks";

  export default {
    name: "Tasks",
    components: {MyPagination, Menu, Navbar, MyFilter, MyTable},
    data() {
      return {
        show: true,
        errors: [],
        page: {
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          showFilter: true,
          user_id: null,
          currentPage: 0,
          pageSize: 10,
          user: null
        },
        user_id: null,
        sort: 'taskId',
        sortDir: 'asc',
        getTasks: {},
        //Filter
        filter: {
          find_name: '',
          date_from: '',
          date_to: '',
          due_from: '',
          due_to: '',
          selectedUser: {name: ''},
          skillsF: []
        }
      }
    },
    methods: {
      retrieveTasks() {
        axios.get('http://localhost:80/api/v1/task', {
            params: {
              size: this.page.pageSize,
              page: this.page.currentPage,
              pageName: this.page.get,
              id: this.user_id,
              find_name: this.filter.find_name,
              sortDir: this.sortDir,
              sort: this.sort,
              date_from: this.filter.date_from,
              date_to: this.filter.date_to,
              due_from: this.filter.due_from,
              due_to: this.filter.due_to,
              skillsFilter: JSON.stringify(this.filter.skillsF)
                .replace("[", "")
                .replace("]", ""),
              author: this.filter.selectedUser.name
            },
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.getTasks = response.data;
          }
        }).catch(e => {
          this.errors.push(e);
        });
        console.log(this.getTasks);
      },
      // Исполльзовать, когда при обновлении таблицы хотим перейти на первую страницу
      refreshList() {
        this.page.currentPage = 0;
        this.retrieveTasks();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      },
      changePage(changeTo) {
        this.page.currentPage = changeTo;
        this.retrieveTasks();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        })
      },
      changePerPage(perPage) {
        this.page.pageSize = perPage;
        this.refreshList();
      },
      changeSort(sortBy) {
        this.sort = sortBy;
        this.refreshList();
      },
      changeSortDir(Dir) {
        this.sortDir = Dir;
        this.refreshList();
      },
      getUserId() {
        axios.get('http://localhost:80/api/v1/me', {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.user_id = response.data.id;
          }
          this.refreshList();
        }).catch(e => {
          this.errors.push(e);
          console.log(e);
        });
      },
      extractPageParam() {
        this.page.get = this.$route.params.pageName;
      },
      goToTask(record) {
        if (this.getTasks.items[0].id > -1) {
          this.$router.push({name: 'Task', params: {id: record.id}});
        }
      },
      handleFilter(filter) {
        this.filter = filter;
        this.refreshList();
      }
    },
    mounted() {
      this.getUserId();
      this.extractPageParam();
    }
  };
</script>
