<template>
  <div class="app">
    <b-map></b-map>
    <!-- <search class="keyWords"></search> -->
  </div>
</template>

<script>
import bMap from "./components/BMap.vue";
// import Search from "./components/Search.vue";
export default {
  name: "app",
  components: {
    bMap,
  },
};
</script>

<style>
.app {
  width: 100%;
  height: 705px;
  position: relative;
}

.keyWords {
  position: absolute;
  top: 50px;
  left: 20px;
}
</style>
