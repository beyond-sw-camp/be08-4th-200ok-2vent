<template>
  <div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center">
      <h1>{{ post.title }}</h1>
      <!-- 게시물 삭제 버튼 -->
      <button v-if="post.memberNo === memberInfo.no" @click="deletePost" class="btn btn-danger">삭제</button>
    </div>
    
    <!-- 게시물 내용 -->
    <div class="post-content mt-4">
      <p>{{ post.content }}</p>
    </div>
    
    <!-- 게시물 정보 -->
    <div class="d-flex justify-content-between align-items-center">
      <span class="text-muted">{{ post.date }}</span>
      <span class="badge bg-primary">{{ post.views }} 조회수</span>
      <div>
        <button @click="increaseLikes" class="btn btn-success me-2">좋아요</button>
        <span class="badge bg-success">{{ post.likes }} 좋아요</span>
      </div>
    </div>

    <!-- 목록으로 돌아가기 버튼 -->
    <router-link to="/boards" class="btn btn-secondary mt-3">목록으로 돌아가기</router-link>

    <!-- 댓글 섹션 -->
    <div class="comments mt-5">
      <h5 class="mb-4">댓글</h5>
      
      <!-- 댓글 작성 칸 -->
      <div class="mb-4">
        <textarea v-model="newComment" class="form-control" rows="3" placeholder="댓글을 입력하세요"></textarea>
        <button @click="addComment" class="btn btn-primary mt-2">댓글 달기</button>
      </div>
      
      <!-- 댓글 목록 -->
      <div v-for="(comment, index) in comments" :key="index" class="comment mb-4 p-3 rounded">
        <div class="d-flex justify-content-between align-items-center">
          <p class="mb-1"><strong>{{ comment.memberName }}</strong></p> <!-- 회원 이름 표시 -->
          <button @click="toggleReply(index)" class="btn btn-sm btn-link">답글</button>
        </div>
        <p>{{ comment.text }}</p>
        
        <!-- 대댓글 작성 칸 -->
        <div v-if="comment.showReply" class="mb-3 ms-4">
          <textarea v-model="comment.replyText" class="form-control" rows="2" placeholder="답글을 입력하세요"></textarea>
          <button @click="addReply(index)" class="btn btn-secondary mt-2">답글 달기</button>
        </div>

        <!-- 대댓글 목록 -->
        <div v-for="(reply, replyIndex) in comment.replies" :key="replyIndex" class="reply ms-4 p-2 rounded">
          <div class="d-flex align-items-center">
            <span class="arrow me-2">↳</span>
            <p class="mb-1"><strong>익명</strong></p>
          </div>
          <p class="ms-4">{{ reply }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import axios from 'axios'

  const post = ref({})
  const route = useRoute();
  const router = useRouter();

  const comments = ref([]) 
  const newComment = ref('') 
  const memberInfo = JSON.parse(localStorage.getItem("userInfo"))

  const loadCommentsFromLocalStorage = () => {
    const storedComments = JSON.parse(localStorage.getItem(`comments_${route.params.id}`));
    if (storedComments) {
      comments.value = storedComments;
    }
  };

  const saveCommentsToLocalStorage = () => {
    localStorage.setItem(`comments_${route.params.id}`, JSON.stringify(comments.value));
  };

  const loadPost = async () => {
    try {
      const postId = route.params.id
      const response = await axios.get(`http://localhost:8080/v1/api/post/${postId}`)
      post.value = response.data

      const updatedResponse = await axios.put(`http://localhost:8080/v1/api/post/update/${postId}`, {
        views: post.value.views + 1
      })

      post.value.views = updatedResponse.data.views
    } catch (error) {
      console.error("게시물 로드 중 오류 발생:", error)
    }
  }

  const increaseLikes = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/v1/api/post/update/${route.params.id}`, { 
        likes: post.value.likes + 1 
      });

      post.value.likes = response.data.likes;
    } catch (error) {
      console.error("좋아요 증가 중 오류 발생:", error);
    }
  };

  const deletePost = async () => {
    if (confirm("정말로 이 게시물을 삭제하시겠습니까?")) {
      try {
        const postId = route.params.id;
        await axios.delete(`http://localhost:8080/v1/api/post/delete/${postId}`);

        alert("게시물이 삭제되었습니다.");
        router.push('/boards'); // 삭제 후 게시물 목록으로 이동
      } catch (error) {
        console.error("게시물 삭제 중 오류 발생:", error);
      }
    }
  };

  const addComment = async () => {
    if (newComment.value.trim()) {
      try {
        const postId = route.params.id;
        const commentData = {
          content: newComment.value,
          memberNo: memberInfo.no 
        };
        
        await axios.post(`http://localhost:8080/v1/api/comment/register/${postId}`, commentData);

        comments.value.push({
          text: newComment.value,
          memberName: memberInfo.name,
          replies: [],
          replyText: '',
          showReply: false,
        });

        saveCommentsToLocalStorage();

        newComment.value = '';
      } catch (error) {
        console.error("댓글 등록 중 오류 발생:", error);
      }
    }
  };

  const toggleReply = (index) => {
    comments.value[index].showReply = !comments.value[index].showReply
  }

  const addReply = (index) => {
    const replyText = comments.value[index].replyText.trim()
    if (replyText) {
      comments.value[index].replies.push(replyText)
      comments.value[index].replyText = ''
      comments.value[index].showReply = false

      saveCommentsToLocalStorage();
    }
  }

  onMounted(() => {
    loadPost()
    loadCommentsFromLocalStorage() 
  })
</script>

<style scoped>
  .post-content {
    margin-bottom: 20px;
    padding: 15px;
    background-color: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 5px;
  }

  .comments {
    border-top: 2px solid #dee2e6;
    padding-top: 20px;
  }

  .comment {
    background-color: #f8f9fa;
    border: 1px solid #dee2e6;
    padding-left: 10px;
    position: relative;
  }

  .reply {
    background-color: #e9ecef;
    border-left: 4px solid #adb5bd;
    margin-top: 10px;
  }

  textarea.form-control {
    resize: none;
  }

  .btn-link {
    font-size: 0.9rem;
    color: #007bff;
  }

  .btn-link:hover {
    color: #0056b3;
  }

  p {
    margin-bottom: 0.5rem;
  }

  .arrow {
    color: #6c757d;
    font-size: 1.2rem;
  }

  .comment::before {
    content: '';
    position: absolute;
    left: 10px;
    top: 0;
    bottom: 0;
    width: 3px;
    background-color: #dee2e6;
    border-radius: 5px;
  }

  .comment:first-child::before {
    display: none;
  }
</style>
