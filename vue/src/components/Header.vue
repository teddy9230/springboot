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
    <el-dropdown style="width: 70px; cursor: pointer;">
      <span>林于哲</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>會員資料</el-dropdown-item>
        <el-dropdown-item>會員登出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Boolean
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  },
  watch: {
    currentPathName(newVal, oldVal) {
      console.log(newVal);
    }
  }
}
</script>

<style scoped>

</style>