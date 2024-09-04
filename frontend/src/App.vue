<template>
  <Header />

  <router-view />

</template>

<script setup>
  import Header from './components/Header.vue';
 // import { reactive, provide } from 'vue';

  // const loginState = reactive({ isLoggedIn: false });
  // provide('loginState', loginState);
</script>

<style lang="scss" scoped>

</style>