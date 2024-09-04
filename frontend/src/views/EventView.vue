<template>
    <div class="container">
        <!-- 내용 -->
        <div class="content_container">
            <!-- 필터 목록 -->
            <div class="filter_container">
                <!-- 필터 초기화 -->
                <div class="filter_header">
                    <div >필터</div>
                    <button id="filter_reset" @click="filterReset">필터 초기화</button>
                </div>
                <!-- 필터 >> 일시 -->
                <div class="filter_date">
                    <div class="filter_title">일시</div>
                    <div><input type="radio" name="date" value="ALL" v-model="selectDate"><label for="ALL">전체</label></div>
                    <div><input type="radio" name="date" value="TODAY" v-model="selectDate"><label for="TODAY">오늘</label></div>
                    <div><input type="radio" name="date" value="THIS_WEEK" v-model="selectDate"><label for="THIS_WEEK">이번주</label></div>
                    <div><input type="radio" name="date" value="THIS_MONTH" v-model="selectDate"><label for="THIS_MONTH">이번달</label></div>
                </div>
                <!-- 필터 >> 지역 -->
                <div>
                    <div class="filter_title">지역</div>
                    <select name="area" id="" v-model="selectRegion">
                        <option value="전체" :selected="selectRegion=='전체'">전체</option>
                        <option value="서울/경기/인천" :selected="selectRegion=='서울/경기/인천'">서울/경기/인천</option>
                        <option value="부산/울산/경남" :selected="selectRegion=='부산/울산/경남'">부산/울산/경남</option>
                        <option value="대구/경북" :selected="selectRegion=='대구/경북'">대구/경북</option>
                        <option value="충청/대전/세종" :selected="selectRegion=='충청/대전/세종'">충청/대전/세종</option>
                        <option value="전라/광주" :selected="selectRegion=='전라/광주'">전라/광주</option>
                        <option value="강원" :selected="selectRegion=='강원'">강원</option>
                        <option value="제주" :selected="selectRegion=='제주'">제주</option>
                    </select>
                </div>
                 <!-- 필터 >> 참여 방법 -->
                 <div>
                    <div class="filter_title">참여 방법</div>
                    <div><input type="radio" name="join" value="onoff" v-model="selectJoin"><label for="onoff">전체</label></div>
                    <div><input type="radio" name="join" value="online" v-model="selectJoin"><label for="online">온라인</label></div>
                    <div><input type="radio" name="join" value="offline" v-model="selectJoin"><label for="offline">오프라인</label></div>
                </div>
                <!-- 필터 >> 가격 -->
                <div>
                    <div class="filter_title">가격</div>
                    <div><input type="radio" name="price" value="freeAndPaid" v-model="selectPrice"><label for="freeAndPaid">전체</label></div>
                    <div><input type="radio" name="price" value="free" v-model="selectPrice"><label for="free">무료</label></div>
                    <div><input type="radio" name="price" value="paid" v-model="selectPrice"><label for="paid">유료</label></div>
                </div>
            </div>
            <!-- 이벤트 목록 -->
            <div class="event_container">
                <EventList :events="events" @refresh-data="refreshData" :startPage="startPage" :currentPage="currentPage" :endPage="endPage" @change-page="changePage"/>
               
                <!-- <PageNation :startPage="startPage" :currentPage="currentPage" :end-page="endPage" @change-page="changePage"/> -->
            </div> 

    

        </div>
    </div>
</template>

<script setup>
    import { computed, onMounted, ref, watch } from 'vue';
    import EventList from '../components/EventList.vue';
    import { useRoute,useRouter } from 'vue-router';
    import axios from 'axios';

    const route = useRoute();
    const router = useRouter();

    const events = ref([]);
    const selectDate = ref('ALL'); // 일시 
    const selectRegion = ref('전체'); // 지역 
    const selectJoin = ref(route.query.selectJoin||'onoff'); // 참여 방법 
    const selectPrice = ref(route.query.selectPrice||'freeAndPaid'); // 가격 
    const memberNo = ref(null);

     // 페이지네이션을 위한 데이터
   // const currentPage = ref(Number(route.query.page)||1); // 현재 페이지 번호
    // const page = ref(0);
    const currentPage = ref(1); // 현재 페이지 번호
    const startPage = ref(0);  // 페이징된 페이지 중에 시작 페이지
    const endPage = ref(0); // 페이징된 페이지 중 마지막 페이지
    const maxPage = ref(0); // 전체 페이지중 마지막 페이지
    const pageLimit = 5;  // 한 페이지에 보여지는 페이지 수
    const listLimit = 9; // 한 페이지에 표시될 리스트수


    onMounted(()=>{
        console.log(route.fullPath);
        fetchEventData();
    });

    watch(()=>route.query,(newQuery)=>{
        if(route.query.selectPrice!=null){
            selectPrice.value = route.query.selectPrice;
        }
        if(route.query.selectJoin!=null){
            selectJoin.value = route.query.selectJoin;
        }
    });
    watch([selectDate,selectRegion,selectJoin,selectPrice],()=>{
        // 필터 조건값이 바뀌면 api 재 요청
        currentPage.value = 1;
        fetchEventData();
    });

    const filterReset=()=>{
        alert('필터를 초기화합니다.');
        selectDate.value = 'ALL';
        selectRegion.value = '전체';
        selectJoin.value = 'onoff';
        selectPrice.value = 'freeAndPaid';
        currentPage.value = 1;
        
        fetchEventData();
    }

    const fetchEventData = ()=>{
        // todo : api 호출
        // console.log('api 호출 !')
        getLoginUserId();
        getEventsAPI(currentPage.value-1);

    }

    const getLoginUserId=()=>{
        const isLoggedIn = localStorage.getItem("isLoggedIn");
        if(isLoggedIn=="true"){
            const userInfo = localStorage.getItem("userInfo");
            memberNo.value = JSON.parse(userInfo)['no'];
        }else{
            memberNo.value = null;
        }

        // console.log(`userId : ${memberNo}`);

    }

    async function getEventsAPI(page){
        try{
            const response = await axios.get(`http://localhost:8080/v1/api/event`,{
                params:{
                    page:page,
                    size: 9,
                    date: selectDate.value,
                    region: selectRegion.value,
                    join: selectJoin.value,
                    price:selectPrice.value,
                    userId: memberNo.value,
                }
            });
             const res = response.data;
             currentPage.value = res.pageNumber+1;

            maxPage.value = parseInt(Math.ceil(res.totalElements/listLimit));
            startPage.value = (pageLimit * parseInt((page-1)/pageLimit)) + 1;
            endPage.value = startPage.value + pageLimit - 1;
            endPage.value = endPage.value > maxPage.value ? maxPage.value : endPage.value;
            
            events.value = res.contents; 

        }catch(error){
            console.log('API 요청 실패 : ',error);
        }
    }
    const refreshData = ()=>{
        // console.log('refreshData : ',currentPage.value);
        getLoginUserId();
        getEventsAPI(currentPage.value-1);
    }

     const changePage=(page)=>{
    //     console.log('페이지 이동');
        if(page>=1 && page<=maxPage.value){
            getLoginUserId();
            getEventsAPI(page-1);
        }
    }


</script>

<style lang="scss" scoped>

.container {
    margin: 0 auto;
    height: 100vh;
    padding: 0 1px;
}

.content_container {
    // background-color: bisque;
    display: flex;

}
.filter_container{
    // background-color: rgb(157, 202, 242);
    margin-top:20px;
    width: 20%;
    padding:0 12px 0 0;

    >div{
        margin-bottom: 25px;
    }
    select {
        width: 100%;
        height: 30px;
        margin-right:10px;
        border-radius: 5px;
        color: gray;
      &:focus {
        border: 1.5px solid purple;
        outline: none;
       
      }
    }

    input {
        margin-right:4px;
    }


    div{
        margin-top:4px;
    }

}

.event_container {
    margin-top:20px;
    width:80%;
    display: flex;
    flex-direction: column;

}

.filter_header{
    display: flex;
    justify-content: space-between;
    div{
        color: purple;
    }
}

.filter_date {
    display: flex;
    flex-direction: column;
   

}

.filter_title {
    font-weight: 600;
    margin-bottom: 5px;
}
#filter_reset{
    color:gray;
    border: none;
    background-color: transparent;
}

</style>