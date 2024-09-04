<template>
    <div class="signup-container">
        <div class="signup-box">
            <h2>회원가입</h2>
            
            <label for="user-name">이름:</label>
            <input type="text" placeholder="Name" class="input-field" id="user-name" v-model="name">
            
            <label for="user-id">이메일(아이디):</label>
            <input type="text" placeholder="Emial(ID)" class="input-field" id="user-id" v-model="email">
            
            <label for="user-password">비밀번호:</label>
            <input type="password" placeholder="Password" class="input-field" id="user-password" v-model="password">

            <label for="user-region">지역:</label>
            <input type="text" placeholder="Region" class="input-field" id="user-region" v-model="location"> <br>
            
            <input type="submit" value="회원가입" class="btn" @click="signup">

            <div class="login">
                <a href="login">이미 계정이 있으신가요? 로그인</a>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';

    const name = ref('');
    const email = ref('');
    const password = ref('');
    const location = ref('');
    const router = useRouter();

    const signup = ()=>{

    if(name.value==""){
        alert('이름을 입력해주세요');
        return;
    }
    if(email.value==""){
        alert("이메일을 입력해주세요");
        return;
    }
    if(password.value==""){
        alert("비밀번호를 입력해주세요");
        return;
    }
    if(location.value==""){
        alert("거주중인 지역을 입력해주세요");
        return;
    }

  //  console.log('회원가입 진행...');
    loginApi();
 }

 const loginApi = async()=>{
    try{
        const res = await axios.post('http://localhost:8080/v1/api/member/register',{
            email:email.value,
            password:password.value,
            name:name.value,
            location:location.value
        })
        .then((res)=>{
            console.log("응답값 : ",res);
            if(res.status==201){
                alert("회원가입에 성공했습니다.");
                router.push("/login");

            }
           
        })
    }catch(err){
        console.log(err);
        alert('회원가입을 실패했습니다.');
    }
 }
</script>

<style scoped>
.signup-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #ffffff;
    padding: 90px;
}
.signup-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #f5f5f5;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0px 8px 25px rgba(0, 0, 0, 0.15);
    width: 400px; /* 로그인 창의 너비 설정 */
    transition: transform 0.3s ease;
}

.signup-box:hover {
    transform: translateY(-8px);
    box-shadow: 0px 12px 30px rgba(0, 0, 0, 0.2);
}

h2 {
    color: #007bb5;
    text-align: center;
    margin-bottom: 25px;
    font-size: 22px;
}

label {
    align-self: flex-start;
    margin-bottom: 8px;
    color: #007bb5;
    font-weight: bold;
}

.input-field {
    width: 100%; 
    max-width: 350px; 
    padding: 10px; 
    margin-bottom: 18px;
    border: 1px solid #007bb5;
    border-radius: 5px;
    box-shadow: inset 0px 1px 4px rgba(0, 0, 0, 0.1);
}

.btn {
    width: 100%;
    padding: 12px;
    background-color: #000000;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.15);
    transition: background 0.3s ease, box-shadow 0.3s ease;
}

.btn:hover {
    background-color: #6bc3ec;
    box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.2);
}

.login {
    margin-top: 15px;
}

.login a {
    color: #007bb5;
    text-decoration: none;
    font-weight: bold;
    transition: color 0.3s ease;
}

.login a:hover {
    color: #005f8b;
    text-decoration: underline;
}
</style>
