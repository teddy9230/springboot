<template>
  <div style="line-height: 60px; display: flex;">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px;" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <!--        <el-breadcrumb-item :to="{path: '/'}">首頁</el-breadcrumb-item>-->
        <!--        <el-breadcrumb-item>會員管理</el-breadcrumb-item>-->

        <!--        <el-breadcrumb-item :to="item.path" v-for="item in paths">{{item.name}}</el-breadcrumb-item>-->

        <el-breadcrumb-item :to="'/'">首頁</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>

      </el-breadcrumb>
    </div>

    <el-dropdown style="width:150px; cursor: pointer; text-align: right">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0;">
          <router-link to="/person" style="text-decoration: none;">會員資料</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0;">
          <span style="text-decoration: none" @click="logout">會員退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user : Object
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  },
  data() {
    return {
      // user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  watch: {
    currentPathName(newVal, oldVal) {
      console.log(newVal);
    }
  },
  methods: {
    collapse(){
      this.$emit("asideCollapse");
    },
    logout() {
      this.$router.push("login/");
      localStorage.removeItem("user");
      this.$message.success("登出成功!!");
    }
  }
}
</script>

<style scoped>

</style>