import request from '@/utils/request'

// 查询用户积分惩罚列表
export function listPenalty(query) {
  return request({
    url: '/book/penalty/list',
    method: 'get',
    params: query
  })
}

// 查询用户积分惩罚详细
export function getPenalty(penaltyId) {
  return request({
    url: '/book/penalty/' + penaltyId,
    method: 'get'
  })
}

// 新增用户积分惩罚
export function addPenalty(data) {
  return request({
    url: '/book/penalty',
    method: 'post',
    data: data
  })
}

// 修改用户积分惩罚
export function updatePenalty(data) {
  return request({
    url: '/book/penalty',
    method: 'put',
    data: data
  })
}

// 删除用户积分惩罚
export function delPenalty(penaltyId) {
  return request({
    url: '/book/penalty/' + penaltyId,
    method: 'delete'
  })
}
