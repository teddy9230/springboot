<template>
  <el-menu :default-openeds="['1', '3']"
           style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#FFF"
           active-text-color="#ffd04b"
           :collapse-transirion="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
      <b style="color: #FFF;" v-show="logoTextShow">後台管理系統</b>
    </div>

    <el-menu-item index="/home">
      <i class="el-icon-house"></i>
      <template slot="title">
        <span slot="title">主頁</span>
      </template>
    </el-menu-item>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系統管理</span>
      </template>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <span slot="title">會員管理</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside.vue",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods: {}
}
</script>

<style scoped>

</style>