import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin',
    userPre:'/api/user'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
    })
}
export function getUserListAPI(){
    return axios({
        url:`${api.adminPre}/getAllUsers`,
        method:'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function deleteUserAPI(id){//删除管理员账户
    return axios({
        url: `${api.userPre}/${id}/deleteUser`,
        method: 'DELETE'
    })

}