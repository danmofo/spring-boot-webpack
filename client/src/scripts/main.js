import Vue from 'vue';
import App from '../App.vue';

import CauseService from './cause/CauseService';

const causeService = new CauseService();

causeService.findById(1517).then((cause) => console.log(cause));
causeService.findByName("Starlight").then((resp) => console.log(resp));

causeService.find({
  q: 'Starlight',
  rows: 1
}).then((resp) => {
  console.log(resp)
})

new Vue({
  el: '#app',
  render: h => h(App)
})
