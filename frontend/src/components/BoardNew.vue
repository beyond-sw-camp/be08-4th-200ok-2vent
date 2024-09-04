<template>
  <div class="container mt-5">
    <h1 class="mb-4 text-center">새 글 작성</h1>

    <!-- 고정된 이미지 -->
    <div class="fixed-image">
      <img src="../assets/images/광고용포스터.png" alt="Fixed Image">
    </div>

    <div class="form-container">
      <!-- 주의사항 -->
      <div class="notice-container">
        <div class="notice-item">
          <p class="notice-number">1.</p>
          <div class="notice-content">
            <strong>게시글 작성 방법</strong>
            <p>지역을 선택하세요.</p>
          </div>
        </div>
        <div class="notice-item">
          <p class="notice-number">2.</p>
          <div class="notice-content">
            <strong>게시글 작성 시 주의사항</strong>
            <p>욕설과 비방은 자제하고, 게시판에 맞는 글을 작성하세요.</p>
          </div>
        </div>
      </div>

      <form @submit.prevent="submitForm">
        <!-- 지역 선택 -->
        <div class="selection-container">
          <div class="form-group">
            <label for="area">지역 선택</label>
            <select id="area" v-model="area" class="form-select" required>
              <option value="" disabled>--지역선택--</option>
              <option v-for="region in regions" :key="region" :value="region">{{ region }}</option>
            </select>
          </div>
        </div>

        <!-- 제목 및 내용 입력 -->
        <div class="input-section">
          <div class="form-group">
            <label for="title">제목</label>
            <input type="text" v-model="title" id="title" class="form-control" required />
          </div>

          <div class="form-group">
            <label for="content">내용</label>
            <textarea v-model="content" id="content" class="form-control" rows="5" required></textarea>
          </div>
        </div>

        <!-- 버튼 -->
        <div class="d-flex justify-content-between">
          <router-link to="/boards" class="btn btn-secondary">취소</router-link>
          <button type="submit" class="btn btn-primary">작성 완료</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios';

const title = ref('')
const area = ref('')
const content = ref('')
const router = useRouter()

const regions = [
  "경기도", "강원도", "충청북도", "충청남도", "전라북도",
  "전라남도", "경상북도", "경상남도", "서울특별시",
  "부산광역시", "대구광역시", "인천광역시", "광주광역시",
  "대전광역시", "울산광역시", "세종특별자치시", "제주특별자치도"
]

const submitForm = async () => {
  try {
    if (!area.value) {
      alert("지역을 선택해주세요.");
      return;
    }

    const userInfo = JSON.parse(localStorage.getItem("userInfo")); // 사용자 정보 가져오기

    const newPost = {
      title: title.value,
      region: area.value,
      content: content.value,
      likes: 0,
      views: 0,
      date: new Date().toISOString().slice(0, 10),
      memberId: userInfo.no 
    }

    console.log(newPost); // 로그 추가

    await axios.post('http://localhost:8080/v1/api/post/register/' + userInfo.no, newPost);
    router.push('/boards')
  } catch (error) {
    console.error("게시물 등록 중 오류 발생:", error);
  }
}
</script>

<style scoped>
  .container {
    max-width: 800px;
  }
  
  .fixed-image {
    position: fixed;
    top: 250px;
    left: 40px;
    z-index: 10;
  }
  
  .fixed-image img {
    width: 300px;
    height: auto;
    max-width: 100%;
    object-fit: contain;
  }
  
  .form-container {
    max-width: 800px;
    margin: 40px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .notice-container {
    margin-bottom: 20px;
    padding: 15px;
    background-color: #e9f4f8;
    border-radius: 5px;
    border: 1px solid #d0e3ed;
  }
  
  .notice-item {
    display: flex;
    align-items: flex-start;
    margin-bottom: 10px;
  }
  
  .notice-number {
    font-size: 1.1rem;
    color: #2a9d8f;
    font-weight: bold;
    margin-right: 10px;
  }
  
  .notice-content {
    flex-grow: 1;
  }
  
  .notice-content strong {
    display: block;
    font-size: 1.1rem;
    color: #1d3557;
    margin-bottom: 5px;
  }
  
  .notice-content p {
    font-size: 0.9rem;
    color: #4a4a4a;
    margin: 0;
  }
  
  .input-section {
    margin-top: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #333;
  }
  
  input[type="text"],
  textarea,
  select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 1rem;
  }
  
  textarea {
    min-height: 150px;
    resize: vertical;
  }
  
  .btn-w {
    text-align: center;
    margin-top: 20px;
  }
  
  .btn-w button {
    background-color: #2a9d8f;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    font-size: 1rem;
    cursor: pointer;
  }
  
  .btn-w button:hover {
    background-color: #21867a;
  }
  
  .selection-container {
    margin-bottom: 20px;
  }
</style>
