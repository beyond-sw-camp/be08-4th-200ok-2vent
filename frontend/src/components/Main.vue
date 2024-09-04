<template>
    <MainBanner/>
    <Events/>
</template>

<script setup>
    import MainBanner from './MainBanner.vue';
    import Events from './Events.vue'
    if(localStorage.getItem("isLoggedIn") == null) localStorage.setItem("isLoggedIn", false);   
</script>

<style lang="scss" scoped>

</style>
