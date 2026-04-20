<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前积分" prop="points">
        <el-input
          v-model="queryParams.points"
          placeholder="请输入当前积分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否禁借(0正常 1禁借)" prop="isBanned">
        <el-input
          v-model="queryParams.isBanned"
          placeholder="请输入是否禁借(0正常 1禁借)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="禁借截止时间" prop="banUntil">
        <el-date-picker clearable
          v-model="queryParams.banUntil"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择禁借截止时间">
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
          v-hasPermi="['book:penalty:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['book:penalty:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['book:penalty:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['book:penalty:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="penaltyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="惩罚ID" align="center" prop="penaltyId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="当前积分" align="center" prop="points" />
      <el-table-column label="是否禁借(0正常 1禁借)" align="center" prop="isBanned" />
      <el-table-column label="禁借截止时间" align="center" prop="banUntil" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.banUntil, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['book:penalty:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['book:penalty:remove']">删除</el-button>
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

    <!-- 添加或修改用户积分惩罚对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="penaltyRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="用户ID" prop="userId">
              <el-input v-model="form.userId" placeholder="请输入用户ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="当前积分" prop="points">
              <el-input v-model="form.points" placeholder="请输入当前积分" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否禁借(0正常 1禁借)" prop="isBanned">
              <el-input v-model="form.isBanned" placeholder="请输入是否禁借(0正常 1禁借)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="禁借截止时间" prop="banUntil">
              <el-date-picker clearable
                v-model="form.banUntil"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择禁借截止时间">
              </el-date-picker>
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

<script setup name="Penalty">
import { listPenalty, getPenalty, delPenalty, addPenalty, updatePenalty } from "@/api/book/penalty"

const { proxy } = getCurrentInstance()

const penaltyList = ref([])
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
    userId: null,
    points: null,
    isBanned: null,
    banUntil: null
  },
  rules: {
    userId: [
      { required: true, message: "用户ID不能为空", trigger: "blur" }
    ],
    points: [
      { required: true, message: "当前积分不能为空", trigger: "blur" }
    ],
    isBanned: [
      { required: true, message: "是否禁借(0正常 1禁借)不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询用户积分惩罚列表 */
function getList() {
  loading.value = true
  listPenalty(queryParams.value).then(response => {
    penaltyList.value = response.rows
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
    penaltyId: null,
    userId: null,
    points: null,
    isBanned: null,
    banUntil: null
  }
  proxy.resetForm("penaltyRef")
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
  ids.value = selection.map(item => item.penaltyId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加用户积分惩罚"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _penaltyId = row.penaltyId || ids.value
  getPenalty(_penaltyId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改用户积分惩罚"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["penaltyRef"].validate(valid => {
    if (valid) {
      if (form.value.penaltyId != null) {
        updatePenalty(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addPenalty(form.value).then(() => {
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
  const _penaltyIds = row.penaltyId || ids.value
  proxy.$modal.confirm('是否确认删除用户积分惩罚编号为"' + _penaltyIds + '"的数据项？').then(function() {
    return delPenalty(_penaltyIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('book/penalty/export', {
    ...queryParams.value
  }, `penalty_${new Date().getTime()}.xlsx`)
}

getList()
</script>
