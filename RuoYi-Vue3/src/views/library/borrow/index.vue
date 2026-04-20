<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图书ID" prop="bookId">
        <el-input
          v-model="queryParams.bookId"
          placeholder="请输入图书ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借阅人ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入借阅人ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借出时间" prop="borrowTime">
        <el-date-picker clearable
          v-model="queryParams.borrowTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择借出时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="应还时间" prop="dueTime">
        <el-date-picker clearable
          v-model="queryParams.dueTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择应还时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际归还时间" prop="returnTime">
        <el-date-picker clearable
          v-model="queryParams.returnTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择实际归还时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['library:borrow:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['library:borrow:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['library:borrow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['library:borrow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="borrowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="recordId" />
      <el-table-column label="图书ID" align="center" prop="bookId" />
      <el-table-column label="借阅人ID" align="center" prop="userId" />
      <el-table-column label="借出时间" align="center" prop="borrowTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.borrowTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应还时间" align="center" prop="dueTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.dueTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际归还时间" align="center" prop="returnTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.returnTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态(0借出中 1已归还)" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['library:borrow:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['library:borrow:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改借阅记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="borrowRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="图书ID" prop="bookId">
              <el-input v-model="form.bookId" placeholder="请输入图书ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="借阅人ID" prop="userId">
              <el-input v-model="form.userId" placeholder="请输入借阅人ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="借出时间" prop="borrowTime">
              <el-date-picker clearable
                v-model="form.borrowTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择借出时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应还时间" prop="dueTime">
              <el-date-picker clearable
                v-model="form.dueTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择应还时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="实际归还时间" prop="returnTime">
              <el-date-picker clearable
                v-model="form.returnTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择实际归还时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Borrow">
import { listBorrow, getBorrow, delBorrow, addBorrow, updateBorrow } from "@/api/library/borrow"

const { proxy } = getCurrentInstance()

const borrowList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    bookId: null,
    userId: null,
    borrowTime: null,
    dueTime: null,
    returnTime: null,
    status: null,
  },
  rules: {
    bookId: [
      { required: true, message: "图书ID不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "借阅人ID不能为空", trigger: "blur" }
    ],
    borrowTime: [
      { required: true, message: "借出时间不能为空", trigger: "blur" }
    ],
    dueTime: [
      { required: true, message: "应还时间不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态(0借出中 1已归还)不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询借阅记录列表 */
function getList() {
  loading.value = true
  listBorrow(queryParams.value).then(response => {
    borrowList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    recordId: null,
    bookId: null,
    userId: null,
    borrowTime: null,
    dueTime: null,
    returnTime: null,
    status: null,
    remark: null
  }
  proxy.resetForm("borrowRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.recordId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加借阅记录"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _recordId = row.recordId || ids.value
  getBorrow(_recordId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改借阅记录"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["borrowRef"].validate(valid => {
    if (valid) {
      if (form.value.recordId != null) {
        updateBorrow(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addBorrow(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _recordIds = row.recordId || ids.value
  proxy.$modal.confirm('是否确认删除借阅记录编号为"' + _recordIds + '"的数据项？').then(function() {
    return delBorrow(_recordIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('library/borrow/export', {
    ...queryParams.value
  }, `borrow_${new Date().getTime()}.xlsx`)
}

getList()
</script>
