<template>
  <div id="skills">
    <h5 class="lead">
      Necessary skills
    </h5>
    <div v-if="status === 'IN_DESIGN'" >
    <b-form-select v-model="skill_name" class="mb-3">
      <option :value="null">Please select skill name</option>
      <option value="java">Java</option>
      <option value="sql">SQL</option>
      <option value="spring">Spring</option>
    </b-form-select>

    <b-form-select v-model="skill_level" class="mb-3">
      <option :value="null">Please select skill level</option>
      <option value="bad">Bad</option>
      <option value="semi-good">semi-good</option>
      <option value="good">good</option>
      <option value="semi-profi">semi-profi</option>
      <option value="profi">profi</option>
    </b-form-select>
    <b-button @click="addTaskSkill" variant="success"> Add skill</b-button>
    <b-button @click="deleteTaskSkill" variant="danger"> Delete Selected</b-button>
    </div>
    <b-table selectable
             select-mode="single"
             selectedVariant="danger"
             hover
             :items="skills"
             @row-selected="rowSelected"
             class="mt-2"
    ></b-table>
  </div>
</template>

<script>
    export default {
      props: {
        skills: Object,
        status: Object
      },
      data() {
        return {
          skill_level: null,
          skill_name: null,
        }
      },
      methods: {
        addTaskSkill() {
          let self = this;
          if (self.skill_name != null && self.skill_level != null) {
            self.skills.push({
              skillName: self.skill_name,
              level: self.skill_level
            })

            fetch('/api/v1/task-skill/'+ self.$route.params.id, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('JWT')
              },
              body: JSON.stringify({
                skillName: {
                  name:self.skill_name
                },
                level: self.skill_level,
              })

            })
              .then(
                function (response) {
                  console.log(self.skills)
                  if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                      response.status);
                    return;
                  }
                  alert("Success!");
                  response.json().then(function (data) {
                    console.log(data)
                  })
                }
              )
          }
        },
        deleteTaskSkill() {
          let self=this;
          let index = self.skills.indexOf(self.selected[0]);

          fetch('/api/v1/task-skill/'+ self.skills[index].id, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('JWT')
            },
          })
            .then(
              function (response) {
                if (response.status !== 200) {
                  console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                  return;
                }
                alert("Success!");
                response.json().then(function (data) {
                  console.log(data)
                })
              }
            );
          self.skills.splice(index, 1);
        },
        rowSelected(skills) {
          this.selected = skills
        }
      }
    }
</script>

<style scoped>

</style>
