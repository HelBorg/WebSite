<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <div>
        <Menu/>
      </div>

<!--        <b-card title="Tasks">-->
        <b-container>
          <div>
            <b-table id="tasks"
                     title="Tasks"
                     :items="getTasks.tasks"
                     :fields="fields"
                     small
                     @row-clicked="goToTask"
            >
            </b-table>
          </div>

<!--          Pagination-->
          <div v-if="getTasks.pagesCount>1">
            <b-button variant="light"
                      @click="changePage(0)"
                      active="getTasks.currentPage!=0">
              First
            </b-button>
            <b-button variant="light"
                      @click="changePage(getTasks.currentPage - 1)"
                      active="getTasks.currentPage!=0">
              Prev
            </b-button>
            <b-button variant="light"
                      v-for="index in getTasks.pagesCount"
                      @click="changePage(index - 1)"
            >
              {{index}}
            </b-button>
            <b-button variant="light"
                      @click="changePage(getTasks.currentPage + 1)"
                      active="getTasks.currentPage!=getTasks.pageCount-1">
              Next
            </b-button>
            <b-button variant="light"
                      @click="changePage(getTasks.pagesCount - 1)"
                      active="getTasks.currentPage!=getTasks.pageCount-1">
              Last
            </b-button>
          </div>
        </b-container>
      {{getTasks.tasks}}


        <!--    Filter-->
<!--        <b-col>-->
<!--          <b-card title="Filter">-->
<!--          <div v-if="page.showFilter">-->
<!--            <b-form @submit="onSubmit" @reset="onReset">-->

<!--              <b-form-group id="input-group-1" label-cols-sm="3" label="Name" class="mb-0">-->
<!--                <b-input-group>-->
<!--                  <b-form-input v-model="editFilter.name" placeholder="Type to Search"></b-form-input>-->
<!--                  <b-input-group-append>-->
<!--                    <b-button :disabled="!editFilter.name" @click="editFilter.name = ''">Clear</b-button>-->
<!--                  </b-input-group-append>-->
<!--                </b-input-group>-->
<!--              </b-form-group>-->

<!--              <b-form-group id="input-group-2" label="Date of creation:" label-for="input-2">-->
<!--                <b-form aria-label="From:">-->
<!--                  From:-->
<!--                    <datepicker v-model="editFilter.date_from"-->
<!--                                style="float: right; margin-left: 50px"-->
<!--                                monday-first=true>-->
<!--                    </datepicker>-->
<!--                </b-form>-->
<!--                <b-form aria-label="To:">-->
<!--                  To:-->
<!--                    <datepicker v-model="editFilter.date_to"-->
<!--                                style="float: right; margin-bottom: 10px; margin-left: 50px"-->
<!--                                monday-first=true>-->
<!--                    </datepicker>-->
<!--                </b-form>-->
<!--              </b-form-group>-->


<!--              <b-form-group id="input-group-3" label="Skills:" label-for="input-3">-->
<!--                <b-container fluid v-for="(skill,index) in editFilter.skillsF">-->
<!--                  <b-row>-->
<!--                    <b-col>-->
<!--                      <b-form-select-->
<!--                        id="input-{{index}}"-->
<!--                        v-model="editFilter.selectedSkill">-->
<!--                        <option v-for="skill in getSkills.skills">-->
<!--                          {{skill.name}}-->
<!--                        </option>-->
<!--                      </b-form-select>-->
<!--                    </b-col>-->
<!--                    <b-col cols="3"> Value: {{skill.value}}-->
<!--                    </b-col>-->
<!--                    <b-col cols='1'>-->
<!--                      <b-button @click="deleteSkill(index)" variant="primary"> - </b-button>-->
<!--                    </b-col>-->
<!--                  </b-row>-->
<!--                  <b-row>-->
<!--                    <b-col>-->
<!--                      <b-form-input id="skill-range-{{skill.name}}" v-model="skill.value" type="range" min="0"-->
<!--                                    max="10"></b-form-input>-->
<!--                    </b-col>-->
<!--                    <b-col cols="1"></b-col>-->
<!--                  </b-row>-->
<!--                  <b-row>-->
<!--                    <h1>-->
<!--                    </h1>-->
<!--                  </b-row>-->
<!--                </b-container>-->
<!--              </b-form-group>-->


<!--              <b-form-group id="input-group-4" label="Author:" label-for="input-4">-->
<!--                <b-form-select multiple v-model="selected" :select-size="4" @click="select">-->
<!--                  <option v-for="user in getUsers.users"-->
<!--                          @click="select(user)"-->
<!--                          :aria-pressed.sync="user.state">-->
<!--                    {{user.name}}-->
<!--                  </option>-->
<!--                </b-form-select>-->
<!--              </b-form-group>-->

<!--              <b-button type="submit" variant="primary">Submit</b-button>-->
<!--              <b-button type="addSkill" variant="success" v-on:click="addSkill">Add Skill</b-button>-->
<!--              <b-button type="reset" variant="danger">Reset</b-button>-->
<!--            </b-form>-->
<!--          </div>-->
<!--          </b-card>-->
<!--        </b-col>-->


      <div><Filter/></div>
    </div>
  </div>
</template>

<script>
  import Menu from "../Menu";
  import Navbar from "../Navbar";
  import axios from 'axios';
  import Datepicker from 'vuejs-datepicker';

  export default {
    name: "Tasks",
    components: { Menu, Navbar, Datepicker},
    data() {
      return {
        page:{
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          showFilter: true,
          user_id: 1,
          find: null
        },
        errors:[],
        show: true,
        task:{
          id: null,
          name: null,
          date_from: null,
          status: null,
          deadline: null,
          rate: null,
          author: null,
          skills: [],
          assigned: []
        },
        getTasks: {
          tasks: [{name: 'Sorry, there is no tasks in here yet'}],
          hasPreviousPage: null,
          hasNextPage: null,
          pagesCount: null,
          sort: null,
          currentPage: 0,
          pageSize: 3,
          find: null
        },
        getSkills:{skills: [{name:'Nothing in here yet'}]},
        getUsers: {users: []},
        fields: {
          name: {
            key: 'name',
            label: 'Name',
            thClass: null,
            tdClass: null
          },
          status: {
            key: 'status',
            label: 'Status',
            thClass: null,
            tdClass: null
          },
          date_from: {
            key: 'date_from',
            label: 'Date of creating',
            thClass: null,
            tdClass: null
          },
          deadline: {
            key: 'deadline',
            label: 'To',
            thClass: null,
            tdClass: null
          },
          rate: {
            key: 'rate',
            label: 'Rate',
            thClass: null,
            tdClass: null
          },
          author: {
            key: 'author',
            label: 'Author',
            thClass: null,
            tdClass: null
          },
          skills: {
            key: 'skills',
            label: 'Skills',
            thClass: null,
            tdClass: null
          },
          assigned: {
            key: 'assigned',
            label: 'Assigned',
            thClass: null,
            tdClass: null
          }
        },


        // Filter
        editFilter: {
          name: '',
          date_from: '',
          date_to: '',
          newSkill:[],
          skillsF:
            [
              {name: '', value: '0'}
            ],
          skills:
            ['author 1'],
          newSelect: {id:0, name: ''},
          selected: [], // Array reference
          selectedSkill: []
        },
        status: 'not_accepted'
      }
    },
    methods: {
      retrieveTasks() {
        axios.get('http://localhost:80/api/v1/task',{
          params:
        {
          size: this.getTasks.pageSize,
          page: this.getTasks.currentPage,
          pageName: this.page.get,
          id: this.page.user_id
        }}
        )
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getTasks.tasks = []; //remove default msg from tasks
              for (let t in response.data.tasks) {
                this.task.id = response.data.tasks[t].id;
                this.task.name = response.data.tasks[t].name;
                this.task.status = response.data.tasks[t].status;
                this.task.date_from = null;
                this.task.deadline = response.data.tasks[t].deadline;
                this.task.rate = response.data.tasks[t].rate;
                this.task.author = response.data.tasks[t].author.name;
                for(let skill in response.data.tasks[t].taskSkills) {
                  this.task.skills.push(skill.name);
                }
                for (let assign in response.data.tasks[t].assignedUser) {
                  this.task.assigned.push(assign.name);
                }
                this.getTasks.tasks.push(this.task);
                this.task.skills = null;
                this.task.assigned = null;
              }
              this.getTasks.hasNextPage = response.data.hasNextPage;
              this.getTasks.hasPreviousPage = response.data.hasPreviousPage;
              this.getTasks.pagesCount = response.data.pagesCount;
              this.getTasks.sort = response.data.sort;
              this.getTasks.find = response.data.find;
            }
          })
        .catch(e => {
          this.errors.push(e)
      });
      },
      retrieveSkills() {
        axios.get('http://localhost:80/api/v1/skill')
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getSkills.skills = response.data;
            }})
          .catch(e => {
            this.errors.push(e)
          });
      },
      retrieveUsers() {
        axios.get('http://localhost:80/api/v1/person')
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getUsers.users = response.data;
            }})
          .catch(e => {
            this.errors.push(e)
          });
        for(let user in this.getUsers.users) {
          let state = false;
          user.push(state);
        }
      },
      refreshList() {
        this.retrieveTasks();
      },
      changePage(changeTo) {
        console.log(this.getTasks.currentPage);
        this.getTasks.currentPage = changeTo;
        this.refreshList();
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      },
      extractPageParam() {
        this.page.name = this.$route.params.pageName;
        switch(this.page.name) {
          case 'search':
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            this.page.get = 'tasks';
            break;
          case 'candidates':
            this.page.get = 'candidate';
            break;
          case 'mine':
            this.fields.author.thClass = 'd-none';
            this.fields.author.tdClass = 'd-none';
            this.page.get = 'author';
            break;
          case 'in_work':
            this.page.get = 'assigned';
            break;
          default: break;
        }
      },
      goToTask(record) {
        this.$router.push({name:'Task', params: {id: record.id}});
      },


      // Filter
      onSubmit(evt) {
        evt.preventDefault();
        alert(JSON.stringify(this.form));
        this.page.find = this.editFilter.name;

        this.refreshList();
      },

      onReset(evt) {
        evt.preventDefault()
        // Reset our form values
        this.editFilter.name = '';
        this.editFilter.date_from = '';
        this.editFilter.date_to = '';
        this.editFilter.skillsF = null;
        this.editFilter.selected = null;
        // Trick to reset/clear native browser form validation state
        this.page.showFilter = false;
        this.$nextTick(() => {
          this.page.showFilter = true;
        })
      },

      addSkill() {
        let name = '';
        this.editFilter.newSkill.push(name);
        let value = 0;
        this.editFilter.newSkill.push(value);
        this.editFilter.skillsF.push(this.editFilter.newSkill);
        this.editFilter.newSkill = [];
        this.page.showFilter = false;
        this.$nextTick(() => {
          this.page.showFilter = true;
        })
      },

      deleteSkill(index) {
        this.editFilter.skillsF.splice(index, 1);
      },

      select(user) {
        this.editFilter.selected.push(this.editFilter.newSelect);
      }
    },
    mounted() {
      this.extractPageParam();
      this.retrieveTasks();
      if(this.page.showFilter) {
        this.retrieveSkills();
        this.retrieveUsers();
      }
    }
  };
</script>

<style scoped>

</style>
