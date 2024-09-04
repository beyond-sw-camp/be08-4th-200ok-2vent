<template>
  <div class="container">
    <div class="sidebar">
      <h2>마이페이지</h2>
      <ul>
        <li :class="{ active: activeTab === '관심 행사' }" @click="setActiveTab('관심 행사')">관심 행사</li>
        <li :class="{ active: activeTab === '내 게시물' }" @click="setActiveTab('내 게시물')">내 게시물</li>
        <li :class="{ active: activeTab === '내 정보' }" @click="setActiveTab('내 정보')">내 정보</li>
      </ul>
    </div>
    <div class="content">
      <h2>{{ activeTab }}</h2>

      <!-- 관심 행사 -->
      <div v-if="activeTab === '관심 행사'" class="event-list-container">
        <div  class="item_container" v-for="event in events" :key="event.no">
            <div>
                <div class="img_container" @click="handleClick(event.no)">
                    <img :src="event.imageUrl" :alt="event.title">  
                    <!-- <img :src="scrapImgUrl" class="scrap-button" @click="onScrap"></img> -->
                    <!-- <img :src="event.wish? checkImg:unCheckImg" class="scrap-button" @click="onScrap(event.id)"></img>    -->
                </div>     
            </div>         
            <div>
                <div class="date">{{ event.eventDate }}</div>
                <div class="title">{{ event.title }}</div>
                <div class="etc_container">
                    <div class="price">{{ event.price==0 ? '무료': event.price.toLocaleString() +" 원"}}</div>
                    <div class="view_count">조회 {{ event.views }}</div>
                </div>
            </div>
        
      </div>
      </div>

      <!-- 내 게시물 -->
      <div v-if="activeTab === '내 게시물'">
        <table>
          <thead>
            <tr>
              <th scope="col" style="width: 5%;">ID</th>
              <th scope="col" style="width: 12%;">지역</th>
              <th scope="col" style="width: 50%;">제목</th>
              <th scope="col" style="width: 8%;">좋아요</th>
              <th scope="col" style="width: 7%;">조회수</th>
              <th scope="col" style="width: 12%;">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(post, index) in posts" :key="post.no">
              <td>{{ index + 1 }}</td>
              <td>{{ post.region }}</td>
              <td class="text-start">
                <router-link :to="`/boards/${post.no}`" class="text-decoration-none" @click.prevent="incrementViews(post.id)">
                  {{ post.title }}
                </router-link>
              </td>
              <td>{{ post.likes }}</td>
              <td>{{ post.views }}</td>
              <td>{{ post.createDate.split('T')[0] }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <!-- 내 정보 수정 -->
      <div v-if="activeTab === '내 정보'">
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="name">이름</label>
            <input type="text" id="name" v-model="userInfo.name" />
          </div>
          <div class="form-group">
            <label for="email">이메일</label>
            <input type="email" id="email" v-model="userInfo.email" />
          </div>
          <!-- <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" v-model="userInfo.password" />
          </div> -->
          <div class="form-group">
            <label for="location">지역</label>
            <input type="text" id="location" v-model="userInfo.location" />
          </div>
          <!-- <button type="submit">수정 완료</button> -->
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import event1 from '../assets/event1.jpg';
import event2 from '../assets/event2.jpg';
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: "MyPage",
  data() {
    // localStorage에서 userInfo 가져오기
    const storedUserInfo = JSON.parse(localStorage.getItem("userInfo"));
    const router = useRouter();
    return {
      activeTab: '관심 행사', 
      userInfo: storedUserInfo || {  // localStorage에 정보가 없을 경우 기본값 설정
        name: '',
        email: '',
        password: '',
        location: '',
      },
      posts: [],  // 서버에서 받아올 게시물 목록
      events: [],
      router: router,
    };
  },
  methods: {
    setActiveTab(tabName) {
      this.activeTab = tabName;
    },

    submitForm() {
      console.log("사용자 정보:", this.userInfo);
      alert("정보가 성공적으로 수정되었습니다!");

      // 수정된 정보를 localStorage에 다시 저장
      localStorage.setItem("userInfo", JSON.stringify(this.userInfo));
    },

    handleClick(id) {
      this.router.push({ path: '/event', query: { id } });
    },

    async loadUserPosts() {
      try {
        // 서버에 해당 회원이 작성한 게시물을 요청
        const response = await axios.get(`http://localhost:8080/v1/api/member/list/post/${this.userInfo.no}`);
        this.posts = response.data;
      } catch (error) {
        console.error("게시물 로딩 중 오류 발생:", error);
      }
    },

    async loadUserWishes() {
      const response = await axios.get(`http://localhost:8080/v1/api/member/list/wish/${this.userInfo.no}`);
      this.events = response.data;
    }
  },

  mounted() {
    this.loadUserPosts();  // 컴포넌트가 로드될 때 사용자 게시물 로드
    this.loadUserWishes();
  },
};
</script>

<style scoped>
.container {
  display: flex;
  padding-top: 50px;
  background-color: #ffffff; /* 전체 배경을 흰색으로 유지 */
  min-height: 100vh; /* 페이지 전체 높이 */
}

.sidebar {
  width: 20%;
  padding: 20px;
  background-color: transparent; /* 배경을 투명하게 설정 */
  /* 불필요한 네모 박스 및 그림자 제거 */
}

.sidebar h2 {
  font-size: 1.5rem;
  margin-bottom: 30px;
  font-family: 'Pretendard', sans-serif;
  font-weight: bold;
  color: #333;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  margin-bottom: 20px;
  padding: 10px;
  color: #555;
  cursor: pointer;
  font-family: 'Pretendard', sans-serif;
  border-radius: 8px;
  transition: color 0.3s ease, background-color 0.3s ease;
  background-color: transparent; /* 배경색 투명 */
}

.sidebar li:hover {
  background-color: #f7f9fc; /* 마우스 오버 시 색상 변경 */
}

.sidebar .active {
  color: #4f75e6;
  font-weight: bold;
}

.content {
  width: 80%;
  padding: 30px;
  font-family: 'Pretendard', sans-serif;
}

.content h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  font-family: 'Pretendard', sans-serif;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #4f75e6;
  padding-bottom: 10px;
}

.event-list {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.event-card {
  width: 220px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  padding: 15px;
  text-align: center;
  background-color: #fff;
  transition: transform 0.3s ease;
}

.event-card:hover {
  transform: translateY(-5px);
}

.event-card img {
  width: 100%;
  height: auto;
  border-radius: 10px;
  margin-bottom: 15px;
}

.event-card h3 {
  font-size: 1.2rem;
  margin: 0;
  font-family: 'Pretendard', sans-serif;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-family: 'Pretendard', sans-serif;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #4f75e6;
  outline: none;
}

button {
  background-color: #4f75e6;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  cursor: pointer;
  font-family: 'Pretendard', sans-serif;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #3e5bb5;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #e0e0e0;
  padding: 15px;
  text-align: left;
  font-family: 'Pretendard', sans-serif;
}

th {
  background-color: #f7f9fc;
  font-weight: bold;
  color: #555;
}

a {
  color: #4f75e6;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.event-list-container {
  margin-bottom: 20px;
  padding: 10px;
  height: auto;
  cursor: pointer;
  overflow: hidden; /* 이미지를 확대할 때 컨테이너를 벗어나지 않도록 함 */
  border-radius: 8px; /* 모서리 둥글게 */
  padding-left:0;
  padding-right: 0;
  display: grid;
  grid-template-columns: repeat(3, 1fr); 
  gap: 15px; /* 아이템 간의 간격 */
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
.item_container {
  position: relative;
  margin-bottom: 15px;
  img{
      width: 100%;
  }
}

.date{
  font-size: 12px;
  color: gray;
}

.etc_container {
  display: flex;
  justify-content: space-between;
}

.title {
  font-size: 15px;
  font-weight: 500;
}

.price {
  font-size: 15px;
  color: purple;
}

.view_count {
  color: gray;
}
</style>
