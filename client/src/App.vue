<template>
  <div id="app">
    <img src="./images/favicon-196x196.png" alt="" />
    <h1>Search for a charity</h1>
    <div v-if="numFound > 0">
      <h2>{{ numFound }} results found.</h2>
      <div v-if="results.length > 0">
        <div v-for="cause in results">
          {{ cause.name }}
        </div>
      </div>
    </div>
      <div v-else>
        <p>No results found.</p>
      </div>
    <form @submit.prevent="onSearch">
      <input type="text" v-model="query" />
    </form>
  </div>
</template>

<script>

import CauseService from './scripts/cause/CauseService';

const causeService = new CauseService();

export default {
  name: 'app',
  methods: {
    onSearch () {
      causeService.findByName(this.query).then((resp) => {
        console.log(resp)
        this.results = resp.docs;
        this.numFound = resp.numFound
      });
    }
  },
  data () {
    return {
      query: '',
      results: [],
      numFound: null
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
