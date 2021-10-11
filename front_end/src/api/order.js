import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function execOrderAPI(orderId){//执行订单
    return axios({
        url:`${api.orderPre}/${orderId}/execOrder`,
        method:'GET',
    })
}
export function getOrderByIdAPI(orderId) {//add on 5.8
    return axios({
        url: `${api.orderPre}/${orderId}/getOrderById`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {//撤销订单
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}
export function delayCheckInAPI(orderId) {//撤销订单
    return axios({
        url: `${api.orderPre}/${orderId}/delayCheckIn`,
        method: 'GET',
    })
}
export function endOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/endOrder`,
        method: 'GET',
    })
}
export function deleteOrderAPI(orderId) {//从数据库中删除订单
    return axios({
        url:`${api.orderPre}/${orderId}/deleteOrder`,
        method: 'DELETE',
    })

}
export function getHotelOrdersAPI(hotelId) {
    return axios({
        url: `${api.orderPre}/${hotelId}/allOrders`,
        method: 'GET',
    })
}
