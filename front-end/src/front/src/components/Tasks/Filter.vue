<template>
  <b-card title="Filter">
    <div v-if="show">
      <b-form @submit="onSubmit" @reset="onReset">

        <b-form-group id="input-group-1" label-cols-sm="3" label="Name" class="mb-0">
          <b-input-group>
            <b-form-input v-model="editFilter.name" placeholder="Type to Search"></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!editFilter.name" @click="editFilter.name = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>

        <b-form-group id="input-group-2" label="Date of creation:" label-for="input-2">
          <b-form aria-label="From:">
            From:
            <datepicker v-model="editFilter.date_from"
                        style="float: right; margin-left: 50px"
                        monday-first=true>
            </datepicker>
          </b-form>
          <b-form aria-label="To:">
            To:
            <datepicker v-model="editFilter.date_to"
                        style="float: right; margin-bottom: 10px; margin-left: 50px"
                        monday-first=true>
            </datepicker>
          </b-form>
        </b-form-group>


        <b-form-group id="input-group-3" label="Skills:" label-for="input-3">
          <b-container fluid v-for="(skill,index) in editFilter.skillsF">
            <b-row>
              <b-col>
                <b-form-select
                  id="input-{{index}}"
                  v-model="editFilter.selectedSkill">
                  <option v-for="skill in getSkills.skills">
                    {{skill.name}}
                  </option>
                </b-form-select>
              </b-col>
              <b-col cols="3"> Value: {{skill.value}}
              </b-col>
              <b-col cols='1'>
                <b-button @click="deleteSkill(index)" variant="primary"> -</b-button>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <b-form-input id="skill-range-{{skill.name}}" v-model="skill.value" type="range" min="0"
                              max="10"></b-form-input>
              </b-col>
              <b-col cols="1"></b-col>
            </b-row>
            <b-row>
              <h1>
              </h1>
            </b-row>
          </b-container>
        </b-form-group>


        <b-form-group id="input-group-4" label="Author:" label-for="input-4">
          <b-form-select multiple v-model="selected" :select-size="4" @click="select">
            <option v-for="user in getUsers.users"
                    @click="select(user)"
                    :aria-pressed.sync="user.state">
              {{user.name}}
            </option>
          </b-form-select>
        </b-form-group>

        <b-button type="submit" variant="primary">Submit</b-button>
        <b-button type="addSkill" variant="success" v-on:click="addSkill">Add Skill</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
    </div>
  </b-card>

</template>

<script>
  import axios from 'axios';
  import Datepicker from 'vuejs-datepicker';

  export default {
    name: "Filter",
    props: {
      show: Object
    },
    components: {Datepicker},
    data() {
      return {
        getSkills: {skills: [{name: 'Nothing in here yet'}]},
        getUsers: {users: []},
        name: 'hello world',
        date_from: '',
        date_to: '',
        newSkill: [],
        skillsF:
          [
            {name: '', value: '0'}
          ],
        skills:
          ['author 1'],
        newSelect: {id: 0, name: ''},
        selected: [], // Array reference
        selectedSkill: []
      }
    },
    methods: {
      retrieveSkills() {
        axios.get('http://localhost:80/api/v1/skill')
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getSkills.skills = response.data;
            }
          })
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
            }
          })
          .catch(e => {
            this.errors.push(e)
          });
        for (let user in this.getUsers.users) {
          let state = false;
          user.push(state);
        }
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
    }
  }
</script>

<style scoped>

</style>
