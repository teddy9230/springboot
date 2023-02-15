<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #FFF; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <el-form :rules="rules" :model="user" ref="userForm">
        <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>會員登入</b></div>
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-user"
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">會員登入</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">會員註冊</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: "請輸入會員名稱", trigger: "blur"},
          {min: 3, max: 10, message: "長度 3 到 10 個字", trigger: "blur"},
        ],
        password: [
          {required: true, message: "請輸入會員密碼", trigger: "blur"},
          {min: 1, max: 20, message: "長度 1 到 10個字", trigger: "blur"},
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          alert('Valid Success!!');
          this.request.post("/user/login", this.user).then(res => {
            if (res.code === "200") {
              localStorage.setItem("user", JSON.stringify(res.data));
              this.$router.push('/');
              this.$message.success("會員登入成功!!");
            } else {
              this.$message.error(res.msg);
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC4668, #3F5EFB);
  overflow: hidden;
}
</style>