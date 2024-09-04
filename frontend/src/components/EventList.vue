<template>
    <div class="container">
        <!-- 행사 출력 조건 -->
        <div class="title">
            <div class="event_count">
              <span>{{ props.events.length }}</span>   개의 행사가 검색되었습니다.
            </div>
        </div>

        <div class="content">
            <EventCard :events="events" @refresh-data="refreshData"/>
            <PageNation :startPage="startPage" :currentPage="currentPage" :endPage="endPage" @change-page="changePage"/>
          
        </div>
    
    </div>

</template>

<script setup>
import { ref } from 'vue';
import EventCard from './EventCard.vue';
import PageNation from '../components/PageNation.vue';
import { useRoute, useRouter } from 'vue-router';

    const currentRoute = useRoute();
    const router = useRouter();

    const props = defineProps(['events','startPage','currentPage','endPage']);
    const emit = defineEmits(['refresh-data']);

    const refreshData=()=>{
        emit('refresh-data',props.currentPage);
    }

    const changePage=(page)=>{
        emit('change-page',page);
    }
   
    
</script>

<style lang="scss" scoped>
    .title {
        display: flex;
        justify-content: space-between;
        height: 50px;
        // background-color: aqua;
    }

    .event_count{
        font-size: large;
        span {
            color:purple;
        }
    }
  

   
</style>