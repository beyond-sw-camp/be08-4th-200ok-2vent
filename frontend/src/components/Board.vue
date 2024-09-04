<template>
  <div class="container-fluid mt-5">
    <h1 class="mb-4 text-center">자유 게시판</h1>

    <!-- 글쓰기 버튼 -->
    <div class="d-flex justify-content-end mb-3">
      <router-link v-if="store.isLoggedIn === 'true'" to="/boards/new" class="btn btn-primary">글쓰기</router-link>
    </div>

    <div class="row justify-content-center">
      <!-- 필터링 체크박스 -->
      <div class="col-md-3">
        <h5>지역 필터링</h5>
        <div v-for="city in cities" :key="city" class="form-check">
          <input class="form-check-input" type="checkbox" :id="city" :value="city" v-model="selectedCities">
          <label class="form-check-label" :for="city">
            {{ city }}
          </label>
        </div>
      </div>

      <!-- 테이블 -->
      <div class="col-md-9">
        <div class="d-flex justify-content-between mb-3">
          <div>
            <label for="postsPerPage" class="form-label">페이지당 게시물 수</label>
            <select id="postsPerPage" v-model="postsPerPage" class="form-select">
              <option value="10">10개</option>
              <option value="20">20개</option>
              <option value="30">30개</option>
            </select>
          </div>

          <div>
            <label for="sortKey" class="form-label">정렬 기준</label>
            <select id="sortKey" v-model="sortKey" class="form-select">
              <option value="id">기본 설정 (ID 내림차순)</option>
              <option value="likes">좋아요 수</option>
              <option value="views">조회수</option>
            </select>
          </div>
        </div>

        <table class="table table-hover table-bordered text-center">
          <thead class="table-dark">
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
            <tr v-for="post in filteredPosts" :key="post.id">
              <td>{{ post.id }}</td>
              <td>{{ post.area }}</td>
              <td class="text-start">
                <router-link :to="`/boards/${post.id}`" class="text-decoration-none" @click.prevent="incrementViews(post.id)">
                  {{ post.title }}
                </router-link>
              </td>
              <td>{{ post.likes }}</td>
              <td>{{ post.views }}</td>
              <td>{{ post.date }}</td>
            </tr>
          </tbody>
        </table>

        <!-- 페이지네이션 -->
        <PageNation :currentPage="currentPage" :startPage="startPage" :endPage="endPage"
          @change-page="handlePageChange"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { store } from '../data/store'; // store를 import
import PageNation from './PageNation.vue';

const cities = ["경기도", "강원도", "충청북도", "충청남도", "전라북도", 
          "전라남도", "경상북도", "경상남도", "서울특별시", 
          "부산광역시", "대구광역시", "인천광역시", "광주광역시", 
          "대전광역시", "울산광역시", "세종특별자치시", "제주특별자치도"];

const posts = ref([]);
const currentPage = ref(1); // 기본값 1로 지정
const postsPerPage = ref(10); // 한 페이지에 보여줄 데이터 개수
const sortKey = ref('id');
const selectedCities = ref([]);
const maxPage = ref(0);
const startPage = ref(0);  // 페이징된 페이지 중에 시작 페이지
const endPage = ref(0); // 페이징된 페이지 중 마지막 페이지
const pageLimit = 5;


const router = useRouter();

// 서버에서 게시물 목록 로드
const loadPosts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/v1/api/post/list', {
      params: {
        page: currentPage.value - 1, 
        size: postsPerPage.value,
      },
    });
 //  console.log(response.data);
    const result = response.data;
    

    currentPage.value = result.currentPageNumber + 1; // 현재 페이지 번호
    maxPage.value = parseInt(Math.ceil(result.totalDataCount/postsPerPage.value)); 
    startPage.value = (pageLimit * parseInt((currentPage.value - 1)/pageLimit)) + 1;
    endPage.value = startPage.value + pageLimit - 1;
    endPage.value = endPage.value > maxPage.value ? maxPage.value : endPage.value;


    posts.value = result.posts.map(post => ({
      id: post.no,
      area: post.region,
      title: post.title,
      likes: post.likes || 0,
      views: post.views,
      date: post.createDate.split('T')[0], 
    }));


  } catch (error) {
    console.error("게시물 로딩 중 오류 발생:", error);
  }
};

// 페이지 로드 시 게시물 목록 로드
onMounted(() => {
  loadPosts();
});

watch([postsPerPage, sortKey, selectedCities], () => {
  currentPage.value = 1;
  loadPosts();
});

const handlePageChange = (page) => {
  if(page>=1 && page<=maxPage.value){
    currentPage.value = page;
    loadPosts();
  }

};

// 조회수 증가
const incrementViews = async (postId) => {
  try {
    const post = posts.value.find(post => post.id === postId);
    await axios.put(`http://localhost:8080/v1/api/post/update/${postId}`, {
      views: post.views + 1
    });

    post.views += 1;

    router.push(`/boards/${postId}`);
  } catch (error) {
    console.error("조회수 증가 중 오류 발생:", error);
  }
};

const filteredPosts = computed(() => {
  let filtered = sortedPosts.value;
  if (selectedCities.value.length > 0) {
    filtered = filtered.filter(post => selectedCities.value.includes(post.area));
  }
  return filtered;
});

const sortedPosts = computed(() => {
  return [...posts.value].sort((a, b) => {
    if (sortKey.value === 'id') {
      return b.id - a.id;
    } else if (sortKey.value === 'likes') {
      return b.likes - a.likes;
    } else if (sortKey.value === 'views') {
      return b.views - a.views;
    }
  });
});
</script>

<style scoped>
.container-fluid {
  max-width: 1200px;
}

h5 {
  margin-bottom: 15px;
}

.table-hover tbody tr:hover {
  background-color: #f8f9fa;
}

.pagination .page-link {
  color: #007bff;
}

.pagination .page-link:hover {
  color: #0056b3;
}

.table-dark {
  background-color: #343a40;
  color: white;
}

.table-dark th {
  text-align: center;
}

.form-check-label {
  margin-left: 5px;
}
</style>
