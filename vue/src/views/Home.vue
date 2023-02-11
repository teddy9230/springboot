<template>
  <div>
    <el-container style="min-height: 100vh">
      <el-aside :width="SideWidth + 'px'"
                style="background-color: rgb(238, 241, 246); box-shadow: 2px 0px 6px rgb(0 21 41 / 35%);">
        <el-menu :default-openeds="['1', '3']"
                 style="min-height: 100%; overflow-x: hidden"
                 background-color="rgb(48, 65, 86)"
                 text-color="#FFF"
                 active-text-color="#ffd04b"
                 :collapse-transirion="false"
                 :collapse="isCollapse"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
            <b style="color: #FFF;" v-show="logoTextShow">後台管理系統</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">导航三</span>
            </template>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="font-size: 12px; border-botton:1px solid #CCC; line-height: 60px; display: flex;">
          <div style="flex: 1; font-size: 20px;">
            <span :class="collapseBtnClass" style="cursor: pointer;" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px; cursor: pointer;">
            <span>林于哲</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>會員資料</el-dropdown-item>
              <el-dropdown-item>會員登出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </el-header>

        <el-main>

          <div style="padding: 10px 0px;">
            <el-input style="width : 200px;" suffix-icon="el-icon-search" placeholder="請輸入搜尋名稱"
                      v-model="username"></el-input>
            <el-input style="width : 200px;" suffix-icon="el-icon-message" placeholder="請輸入搜尋信箱"
                      v-model="email"></el-input>
            <el-input style="width : 200px;" suffix-icon="el-icon-position" placeholder="請輸入搜尋地址"
                      v-model="address"></el-input>
            <el-button class="ml-5" type="primary" @click="load()">搜尋</el-button>
            <el-button class="ml-5" type="primary" @click="reset()">重置</el-button>
          </div>

          <div style="margin: 10px 0px;">
            <el-button type="primary" @click="handleAdd()">新增 <i class="el-icon-circle-plus-outline"></i></el-button>

            <el-popconfirm
                class="ml-5"
                confirm-button-text='刪除'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="您确定要刪除勾選的資料吗？"
                @confirm="batchDelete"
            >
              <el-button type="danger" slot="reference">多項刪除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>

            <el-button type="primary" class="ml-5">匯入 <i class="el-icon-bottom"></i></el-button>
            <el-button type="primary">匯出 <i class="el-icon-top"></i></el-button>
          </div>

          <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55px"></el-table-column>
            <el-table-column prop="id" label="使用者編號" width="80"></el-table-column>
            <el-table-column prop="username" label="使用者名稱" width="140"></el-table-column>
            <el-table-column prop="nickname" label="使用者暱稱" width="120"></el-table-column>
            <el-table-column prop="email" label="使用者信箱"></el-table-column>
            <el-table-column prop="phone" label="使用者電話"></el-table-column>
            <el-table-column prop="address" label="使用者地址"></el-table-column>

            <el-table-column>
              <template slot-scope="scope">
                <el-button type="success" @click="handleEdit(scope.row)">編輯 <i class="el-icon-edit"></i></el-button>

                <el-popconfirm
                    class="ml-5"
                    confirm-button-text='刪除'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定删除吗？"
                    @confirm="handleDelete(scope.row.id)"
                >
                  <el-button type="danger" slot="reference">刪除 <i class="el-icon-remove-outline"></i></el-button>
                </el-popconfirm>

              </template>
            </el-table-column>
          </el-table>


          <div style="padding: 10px 0;">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>

            <el-dialog title="使用者資訊" :visible.sync="dialogFormVisible" width="30%">
              <el-form label-width="120px" size="small">
                <el-form-item label="使用者名稱">
                  <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="使用者暱稱">
                  <el-input v-model="form.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="使用者信箱">
                  <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="使用者電話">
                  <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="使用者地址">
                  <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>

              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save()">确 定</el-button>
              </div>
            </el-dialog>

          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import request from "../utills/request";
// @ is an alias to /src

export default {
  name: "home",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      username: "",
      email: "",
      address: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      SideWidth: 200,
      logoTextShow: true,
      headerBg: "headerBg",
      dialogFormVisible: false,
      form: {},
      multipleSelection: []
    }
  },
  created() {
    this.load();
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
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    load() {
      request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address
        }
      }).then(res => {
        console.log("res = " + res);

        this.tableData = res.records;
        this.total = res.total;
      })
    },
    reset() {
      this.username = "";
      this.email = "";
      this.address = "";
      this.load();
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    },
    save() {
      request.post("/user", this.form).then(res => {
        console.log(res);

        if(res){
          this.$message.success("新增成功");
          this.dialogFormVisible = false;
          this.load();
        }else{
          this.$message.error("新增失敗");
        }

      })
    },
    handleEdit(row){
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleDelete(id){
      request.delete("/user/" + id).then(res => {
        console.log(res);

        if(res){
          this.$message.success("刪除成功");
          this.load();
        }else{
          this.$message.error("刪除失敗");
        }
      })
    },
    handleSelectionChange(val){
      console.log("val= " + val);
      this.multipleSelection = val;
    },
    batchDelete(){
      let ids = this.multipleSelection.map(v => v.id);

      request.post("/user/del/batch" , ids).then(res => {
        console.log(res);

        if(res){
          this.$message.success("多選取 刪除成功");
          this.load();
        }else{
          this.$message.error("多選取 刪除失敗");
        }
      })
    }
  },
};
</script>

<style>
.headerBg {
  background-color: #CCC !important;
}
</style>