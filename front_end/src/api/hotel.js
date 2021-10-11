import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function deleteHotelAPI(hotelId) {
    return axios({
        url:`${api.hotelPre}/${hotelId}/deleteHotel`,
        method: 'DELETE',
    })
}
export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.id}/hotelInfo/update`,
        method: 'POST',
        data
    })
}
export function addPictureAPI(data,bizRegion) {
    return axios({
        url:`${api.hotelPre}/addHotelPicture`,
        method:'POST',
        data,bizRegion
    })

}
export function getPicturesAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/getHotelPictures`,
        method: 'GET',
    })
}
