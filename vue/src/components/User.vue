<template>
  <div>
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

      <el-upload action="http://localhost:9090/user/import" style="display: inline-block;" :show-file-list="false"
                 accept="'xlsx'"
                 :on-success="handleExcelImportSuccess">
        <el-button type="primary" class="ml-5">匯入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>


      <el-button type="primary" @click="exp" class="ml-5">匯出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
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
          :page-sizes="[5, 10, 15, 20]"
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
  </div>
</template>

<script>
export default {
  name: "User.vue",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      email: "",
      address: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      headerBg: "headerBg"
    }
  },
  created() {
    this.load();
  },
  methods: {
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    load() {
      this.request.get("/user/page", {
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
      this.request.post("/user", this.form).then(res => {
        console.log(res);

        if (res) {
          this.$message.success("新增成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("新增失敗");
        }

      })
    },
    handleEdit(row) {
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleDelete(id) {
      this.request.delete("/user/" + id).then(res => {
        console.log(res);

        if (res) {
          this.$message.success("刪除成功");
          this.load();
        } else {
          this.$message.error("刪除失敗");
        }
      })
    },
    handleSelectionChange(val) {
      console.log("val= " + val);
      this.multipleSelection = val;
    },
    batchDelete() {
      let ids = this.multipleSelection.map(v => v.id);

      request.post("/user/del/batch", ids).then(res => {
        console.log(res);

        if (res) {
          this.$message.success("多選取 刪除成功");
          this.load();
        } else {
          this.$message.error("多選取 刪除失敗");
        }
      })
    },
    exp() {
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("上傳 xlsx 文件成功");
      this.load();
    }
  }
}
</script>

<style scoped>
.headerBg {
  background-color: #CCC !important;
}
</style>