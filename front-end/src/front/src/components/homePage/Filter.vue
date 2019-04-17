<template>
  <div>
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Name:"
        label-for="input-1"
      >
        <b-form-input
          id="input-1"
          v-model="name"
          type="name"
          placeholder="Enter name"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Date:" label-for="input-2">
        <b-row>
          <b-col></b-col>
          <b-col></b-col>
        </b-row>
      </b-form-group>



      <b-form-group id="input-group-3" label="Skills:" label-for="input-3">
        <b-container fluid v-for="skill in skills">
          <b-row>
            <b-col>
              <b-form-input
                id="input-1"
                v-model="skill.name"
                type="name"
                placeholder="Enter skill"
              ></b-form-input>
            </b-col>
            <b-col cols="3"> Value:  {{skill.value}}
            </b-col>
            <b-col cols='1'>
              <b-button type="deleteSkill" variant="primary"> - </b-button>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-input id="skill-range-{{skill.name}}" v-model="skill.value" type="range" min="0" max="10"></b-form-input>
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
        <b-form-select multiple v-model="selected" :options="options"  :select-size="4"></b-form-select>
        <div class="mt-3">Selected: <strong>{{ selected }}</strong></div>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="addSkill" variant="success">Add Skill</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
  </div>
</template>

<script>
  import DatePick from 'vue-date-pick';
  import 'vue-date-pick/dist/vueDatePick.css';

  export default {
    components: {DatePick},
    data() {
      return {
        name: '',
        show: true,
        skills: [
          {name: '', value: '0'},
          {name: '', value: '0'}
        ],
        selected: ['author 1','author 2'], // Array reference
        options: [ 'author 1','author 2','author 3','author 4'],
        status: 'not_accepted'
      }
    },
    methods: {
      onSubmit(evt) {
        evt.preventDefault()
        alert(JSON.stringify(this.form))
      },
      onReset(evt) {
        evt.preventDefault()
        // Reset our form values
        this.filter.name = ''
        // Trick to reset/clear native browser form validation state
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      }
    }
  }
</script>

<style scoped>

</style>
