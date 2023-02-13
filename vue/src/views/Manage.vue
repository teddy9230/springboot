<template>
  <div>
    <el-container style="min-height: 100vh">
      <el-aside :width="SideWidth + 'px'"
                style="background-color: rgb(238, 241, 246); box-shadow: 2px 0px 6px rgb(0 21 41 / 35%);">
          <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #CCC;">
            <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse"/>
        </el-header>

        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: "home",
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      SideWidth: 200,
      logoTextShow: true,
    }
  },
  components: {
    Aside,
    Header
  },
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse

      if (this.isCollapse) {
        this.SideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        this.SideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    }
  },
};
</script>

<style>

</style>