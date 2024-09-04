<template>
    <div class="event-details-container">
        <header class="event-header">
        </header>
        <!-- <section class="sidebar">
            <p>{{event.title }}</p>
            <p>경기/인천/서울</p>
            <h3>{{ event.title }}</h3>
            <img src="../assets/list/1.jpg"><br>
            <div>
                <button class="register-button">신청하기</button>
                <button class="like-button"
                    @click="toggle">
                    {{ userLiked.value ? '좋아요 ♡' : '좋아요 누르기 ♡' }}
                </button>
            </div>
        </section> -->
        <section class="event-content">
            <img :src="event.imageUrl" class="event-banner" alt="Event Banner">
            <div class="event-description">
                <h4>{{ `"${event.description}"` }}</h4>
            </div>
            <div class="event-title">
                <h1>{{ event.title }}</h1>
            </div>
            <div class="event-info">
                <div class="etc">
                    <div class="view_count">조회 {{ event.views }}</div>
                    <div class="view_likes">관심 {{ event.likes }}</div>
                </div>
                <div class="event-details">
                    <h2>행사 정보</h2>
                    <ul>
                        <li><strong>일시</strong> {{ event.eventDate }} </li>
                        <li><strong>비용</strong> {{ event.price == 0 ? '무료' : `${event.price}원` }}</li>
                        <li><strong>장소</strong> {{ event.location }}</li>
                        <li><strong>주소</strong> {{ event.address }} </li>
                        <!-- <li><strong>Organizer</strong> {{ event.organizer }}</li>
                            <li><strong>Speakers</strong> {{ event.speakers }}</li> -->
                    </ul>
                </div>

                <!-- <div v-for="(des, i) in event.description" :key="i">
                    <p class="event-description" >{{ des }}</p>
                </div> -->
            </div>
        </section>
        
        <footer class="event-footer">
        </footer>
    </div>
</template>
    
<script>
    import { defineComponent, ref, computed, onMounted } from 'vue';
    import { useRoute } from 'vue-router';
    import axios from 'axios';
    const baseUrl = 'http://localhost:8080/v1/api/event';
    
export default {
    name: 'EventDetails',
    components: {
        
    },
    setup() {
        const event = ref({});
        const message = ref('');

        const route = useRoute();
        const id = route.query.id;

        const userLiked = ref(true);
        const toggle = () => {
            if(userLiked.value){
                userLiked.value = false;
                // post api call
            }else{
                userLiked.value = true;
                // delete api call
            }
            console.log('Toggled, userLiked:', userLiked.value);
        }
    
        const getImageUrl = (url) => {
            return new URL(`${url}`, import.meta.url).href;
        };
        
        const getEventAPI = async() => {
            const result = await axios.get(`${baseUrl}/${id}`);
            console.log(result.data);
            event.value = result.data;
        };

        onMounted(() => {
            getEventAPI();
        });
    
        return {
            event,
            getImageUrl,
            id,
            event,
            route,
            userLiked,
            toggle,
        };
    }
};
</script>

<style>
.event-details-container {
    float: left;
    width: 100%;
    margin-top: 100px;
    padding: 20px;
    align-items: left;
    /* padding-right: 400px; */
    padding-left: 10%;
    height: auto;
}

.event-header {
    text-align: center;
    margin-bottom: 20px;
}

.event-title {
    font-size: 2rem;
    margin-top: 20px;
}

.event-meta {
    color: grey;
}

.event-date,
.event-location {
    margin: 5px 0;
}

.event-content {
    display: flex;
    flex-direction: column;
    height: auto;   
    width: 80%;
}

.event-banner {
    width: 100%;
    height: auto;
    object-fit: cover;
    margin-bottom: 20px;
}

.event-info {
    /* max-width: 800px; */
    text-align: center;
    padding-top: 20px;
}

.event-description {
    color: rgb(93, 15, 165);
    background-color: #E6E6FA; /* 연보라색 배경색 */
    padding: 20px; /* 내용 주변의 여백을 좀 더 여유 있게 */
    border-radius: 15px; /* 모서리 둥글게 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 약간의 그림자 추가 */
    margin: 5px 0; /* 상하 여백 추가 */
    display: flex; /* Flexbox 사용 */
    justify-content: center; /* 가로 방향 중앙 정렬 */
    align-items: center; /* 세로 방향 중앙 정렬 */
    text-align: center; /* 텍스트 가운데 정렬 */
    height: 50px; /* 필요한 높이 조정, 또는 사용자의 필요에 따라 조정 */
    h4{
        font-size: 20px;
        margin-top: 5px;
    };
}

.event-details {
    text-align: left;
}

.event-details h2 {
    font-size: 1.5rem;
}

.event-details ul {
    list-style: none;
    padding: 0;
}

.event-details li {
    margin-bottom: 10px;
    padding-top: 10px;
}

.event-details li strong {
    padding-right: 30px;
}

.event-footer {
    text-align: center;
    margin-top: 20px;
}

.register-button,
.like-button {
    display: inline-block;
    padding: 10px 20px;
    margin: 10px;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    font-size: 1rem;
    width: 44%;
}

.register-button {
    background-color: rebeccapurple;
}

.like-button {
    background-color: rgb(138, 236, 239);
    color: rgb(55, 118, 218);
}

.liked {
    background-color: crimson;
}
.sidebar {
    position: sticky;
    top: 100px;
    width: 30%; 
    background-color: white;
    padding: 20px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.3);
    float: right;
    margin-left: 100px;
}
.sidebar h {
    font-size: 25px;
    font-weight: bold;
}
.sidebar p {
    font-size: 13px;
    color: gray;
}

.etc {
    display: flex;
    justify-content: space-around;
    padding-bottom: 50px;
}
.view_likes {
    color: blueviolet;
}

</style>