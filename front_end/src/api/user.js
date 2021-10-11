import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function registerVIPAPI(data){
    return axios({
        url:`${api.userPre}/registerVIP`,
        method:'POST',
        data
    })
}
export function getVIPInfoByUserIdAPI(id){
    return axios({
        url:`${api.userPre}/${id}/getVIPInfoByUserId`,
        method:'GET'
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}
export function getUserCreditRecordsAPI(userId) {
    return axios({
        url: `${api.userPre}/${userId}/getUserCredits`,
        method: 'GET',
    })
}
export function chargeCreditAPI(data){
    return axios({
        url: `${api.userPre}/${data.id}/${data.money}/chargeCredit`,
        method: 'POST',
    })
}
export function addCommentAPI(data) {
    return axios({
        url: `${api.userPre}/addComment`,
        method: 'POST',
        data
    })
}
export function getCommentsByUserIdAPI(userId) {
    return axios({
        url: `${api.userPre}/${userId}/getCommentsByUserId`,
        method: 'GET',
    })
}
export function getCommentsByHotelIdAPI(hotelId) {
    return axios({
        url: `${api.userPre}/${hotelId}/getCommentsByHotelId`,
        method: 'GET',
    })
}