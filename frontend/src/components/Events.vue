<template>
    <div class="carousel-wrapper">
        <div class="list">
            ⭐ 지금 현재 사람들이 가장 많이 본 행사!
        </div>
        <Carousel :items-to-show="4.5" :wrap-around="false" :autoplay="false" transition="1000">
            <Slide v-for="event in listByView" :key="event.no"
                @mousedown="handleMouseDown" @mouseup="handleMouseUp" @mousemove="handleMouseMove">
                <div class="slide-item-container" >
                    <div>
                        <img :src="event.imageUrl" :alt="event.title" @click="handleClick(event.no)">
                        <img :src="wishExist(event.no) ? checkImg : unCheckImg" class="scrap-button" @click="onScrap(event.no)"></img>   
                    </div>
                    <div>
                        <div class="date">{{ event.eventDate }}</div>
                        <div class="title">{{ event.title }}</div>
                        <div class="etc_container">
                            <div class="price">{{ event.price > 0 ? `${event.price}원` : '무료' }}</div>
                            <div class="view_count">조회 {{ event.views }}</div>
                        </div>
                    </div>
                </div>
            </Slide>
        </Carousel>
    </div>

    <div class="carousel-wrapper">
        <div class="list2">
            💙 생각지도 못한 행사들이 우르르~! 궁금하다면 클릭!
        </div>
        <Carousel :items-to-show="4.5" :wrap-around="false" :autoplay="false" transition="1000">
            <Slide v-for="event in listByLike" :key="event.no"
                @mousedown="handleMouseDown" @mouseup="handleMouseUp" @mousemove="handleMouseMove">
                <div class="slide-item-container" >
                    <div>
                        <img :src="event.imageUrl" :alt="event.title" @click="handleClick(event.no)">
                        <img :src="this.wishExist(event.no) ? checkImg : unCheckImg" class="scrap-button" @click="onScrap(event.no)"></img>   
                    </div>
                    <div>
                        <div class="date">{{ event.eventDate }}</div>
                        <div class="title">{{ event.title }}</div>
                        <div class="etc_container">
                            <div class="price">{{ event.price > 0 ? `${event.price} 원` : '무료' }}</div>
                            <div class="view_count">조회 {{ event.views }}</div>
                        </div>
                    </div>
                </div>
            </Slide>
        </Carousel>
    </div>
</template>

<script>
import { computed, ref, onMounted } from 'vue';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';
import axios from 'axios';
import { useRouter } from 'vue-router';
import scrapUnCheck from '../assets/images/scrap_uncheck.png';
import scrapCheck from '../assets/images/scrap_check.png';

const baseUrl = 'http://localhost:8080/v1/api/event';

export default {
    name: 'Events',
    components: {
        Carousel,
        Slide,
        Navigation,
    },
    setup() {
        const listByView = ref([]);
        const listByLike = ref([]);
        const listLike = ref([]);
        const router = useRouter();
        let isDragging = false;
        let startX = 0;
        let startY = 0;

        const scrapImgUrl = ref(scrapUnCheck);
        const unCheckImg = ref(scrapUnCheck);
        const checkImg = ref(scrapCheck);
        

        const requestAPI = async () => {
            const resultByView = await axios.get(`${baseUrl}/list/top/view`);
            listByView.value = resultByView.data;
            console.log(listByView.value);

            const resultByLike = await axios.get(`${baseUrl}/list/top/like`);
            listByLike.value = resultByLike.data;

            const isLoggedIn = localStorage.getItem("isLoggedIn");
            if(isLoggedIn === 'false') return;

            const userInfo = JSON.parse(localStorage.getItem("userInfo"));
            const resultLike = await axios.get(`http://localhost:8080/v1/api/member/list/wish/${userInfo.no}`);
            listLike.value = resultLike.data;
            console.log(listLike.value);
        };

        const handleMouseDown = (event) => {
            isDragging = false;
            startX = event.clientX;
            startY = event.clientY;
        };

        const handleMouseMove = (event) => {
            if (Math.abs(event.clientX - startX) > 5 || Math.abs(event.clientY - startY) > 5) {
                isDragging = true;
            }
        };

        const handleMouseUp = (event) => {
            if (isDragging) {
                event.preventDefault();
            }
        };

        const handleClick = (id) => {
            if (!isDragging) {
                router.push({ path: '/event', query: { id } });
            }
        };
        
        const onScrap =(eventNo)=>{
            //   scrapImgUrl.value = scrapCheck;
            // 로그인 여부 먼저 확인 
            // todo 스크랩 api 호출
            console.log('onScrap 클릭');
            const isLoggedIn = localStorage.getItem("isLoggedIn");
    
    
            if(isLoggedIn=="false"){
                alert("관심행사 등록은 로그인 후 이용해주세요");
                return;
            }
            // todo : 관심행사 등록 api 호출
            // api 재실행 
            const userInfo = localStorage.getItem("userInfo");
            const memberNo = JSON.parse(userInfo)['no'];

            console.log(`memberNo : ${memberNo}`);
            console.log(`eventNo : ${eventNo}`);

            scrapApi(memberNo,eventNo);
        }

        const scrapApi = async(memberNo,eventNo)=>{
            try{
                await axios.post('http://localhost:8080/v1/api/wish/register',{
                    memberNo,
                    eventNo
                }).then((res)=>{
                    console.log(res);
                    console.log(res.data);
                    if(res.data=="register"){
                        // emit('refresh-data');
                        requestAPI();
                        alert("관심행사로 등록되었습니다.");
                    }else if(res.data=="cancel"){
                        // emit('refresh-data');
                        requestAPI();
                        alert("관심행사 등록을 취소했습니다.")
                    }
                
                })
            }catch(err){
                console.log(err);
            }

        }

        const wishExist = (eventNo) => {
            const isLoggedIn = localStorage.getItem("isLoggedIn");
            if(isLoggedIn === 'false') return;
            const userInfo = JSON.parse(localStorage.getItem("userInfo"));
            for(let i = 0; i < listLike.value.length; i++){
                if(listLike.value[i].no === eventNo) return true;
            }
            return false;
        }

        onMounted(() => {
            requestAPI();
        });

        return {
            listByView,
            listByLike,
            handleClick,
            handleMouseDown,
            handleMouseMove,
            handleMouseUp,
            onScrap,
            scrapApi,
            scrapImgUrl,
            unCheckImg,
            checkImg,
            wishExist,
            listLike,
        };
    },
};
</script>

<style lang="scss">
.carousel-wrapper {
    align-items: flex-start;
    width: 100%;
    margin-top: 100px;
    margin-left: 50px;
}

.slide-item-container {
    position: relative;
    margin-bottom: 20px;
    padding: 10px;
    width: 100%;
    height: auto;
    cursor: pointer;
    display: inline-block;
    overflow: hidden; /* 이미지를 확대할 때 컨테이너를 벗어나지 않도록 함 */
    border-radius: 8px; /* 모서리 둥글게 */
    img {
        width: 100%;
        height: 70%;
        border-radius: 5px;
        object-fit: cover;
        display: block;
        height: auto;
        transition: transform 0.3s ease, box-shadow 0.3s ease; /* 변환 및 그림자 효과를 부드럽게 적용 */
    }
    img:hover {
        transform: scale(1.1); /* 마우스를 올리면 이미지가 1.1배 확대 */
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3); /* 확대 시 그림자 추가 */
    }
}

.date {
    font-size: small;
    margin-top: 8px;
    color: gray;
    text-align: left;
}

.etc_container {
    display: flex;
    justify-content: space-between;
    height: auto;
}

.title {
    font-weight: 500;
    text-align: left;
    height: 80px;
    margin-top: 3px;
}

.price {
    color: purple;
}

.view_count {
    color: gray;
}

.list, .list2 {
    margin-top: 90px;
    font-size: 1.5rem;
    color: rgb(36, 75, 167);
    font-weight: bold;
    text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); /* 텍스트 그림자 */
    font-family: 'Arial', sans-serif; /* 폰트 변경 */
    background-color: #f5faff; /* 배경색 추가 */
    padding: 10px 20px; /* 내부 여백 추가 */
    border-radius: 8px; /* 모서리 둥글게 */
    border: 1px solid #d3e2ff; /* 테두리 추가 */
    transition: transform 0.3s ease, color 0.3s ease;
}

.list:hover, .list2:hover {
    transform: translateY(-5px); /* 마우스 올렸을 때 텍스트 살짝 위로 */
    color: rgb(255, 99, 71); /* 텍스트 색상 변경 */
}

.scrap-button {
       position: absolute;
       top:5px;
       right:10px;
       width:35px !important;
       cursor: pointer;
     
    }

</style>
