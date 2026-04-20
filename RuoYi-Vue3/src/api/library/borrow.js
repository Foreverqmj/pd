import request from '@/utils/request'

// 查询借阅记录列表
export function listBorrow(query) {
  return request({
    url: '/library/borrow/list',
    method: 'get',
    params: query
  })
}

// 查询借阅记录详细
export function getBorrow(recordId) {
  return request({
    url: '/library/borrow/' + recordId,
    method: 'get'
  })
}

// 新增借阅记录
export function addBorrow(data) {
  return request({
    url: '/library/borrow',
    method: 'post',
    data: data
  })
}

// 修改借阅记录
export function updateBorrow(data) {
  return request({
    url: '/library/borrow',
    method: 'put',
    data: data
  })
}

// 删除借阅记录
export function delBorrow(recordId) {
  return request({
    url: '/library/borrow/' + recordId,
    method: 'delete'
  })
}
