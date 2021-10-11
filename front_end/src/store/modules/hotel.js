import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    deleteHotelAPI,
    updateHotelInfoAPI,
    addPictureAPI,
    getPicturesAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI,
    // getHotelOrdersAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'
import {
    getCommentsByHotelIdAPI
} from "@/api/user";

const hotel = {
    state: {
        hotelList: [
            
        ],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {

        },
        orderModalVisible: false,
       // evaluationVisible:false,
        currentOrderRoom: {

        },
        orderMatchCouponList: [

        ],
        hotelInListVisible: false,
        hotelComments:[

        ],
        currentPictureList:[

        ],
        // hotelInDuty: 0,
        // hotelInDutyInfo: [],
        //hotelInDutyOrders:[],

    },
    mutations: {
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_hotelInListVisible: function (state,data) {
            state.hotelInListVisible=data
        },
        clearOrderMatchCouponList: function (state) {
            state.orderMatchCouponList=[]

        },//add on 5.15 修改匹配优惠券时出现的bug，每次下完订单后都清空匹配的优惠券
        set_hotelComments:(state,data)=>{
            state.hotelComments=data
        },
        set_currentPictureList:(state,data)=>{
            state.currentPictureList=data
        },
        // set_hotelInDuty: function (state,data) {
        //     state.hotelInDuty=data
        // },
        // set_hotelInDutyInfo: function (state,data) {
        //     state.hotelInDutyInfo=data
        // },
        // set_hotelOrders:function (state,data) {
        //     state.hotelInDutyOrders=data
        //
        // }

    },

    actions: {
        getHotelList: async({commit, state}) => {
            const res = await getHotelsAPI()
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
            }
        },
        getHotelByIdTwo:async({commit, state},hotelId) => {
            const res = await getHotelByIdAPI({
                hotelId
            })
            if(res){
                commit('set_currentHotelId',hotelId)
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async({ state, commit }, data) => {
            const res = await reserveHotelAPI(data)
            console.log(res)
            if(res){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        getCommentsByHotelId: async ({state,commit})=>{
            const res =await getCommentsByHotelIdAPI(state.currentHotelId)
            if(res){
                commit('set_hotelComments',res)
            }else{
                message.error("获取评论失败")
            }
        },
        deleteHotel:async({ state, dispatch }, hotelId) => {
            const res = await deleteHotelAPI(hotelId)
            if(res) {
                dispatch('getHotelList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        // updateHotelInfo: async({ state, dispatch ,commit}, data) => {
        //     const params = {
        //         ...data,
        //     }
        //     const res = await updateHotelInfoAPI(params)
        //     if(res){
        //         message.success('修改成功')
        //         dispatch('getHotelList')
        //         for(let i=0;i<state.hotelList.length;i++){
        //             if(state.hotelList[i].id==state.hotelInDuty){
        //                 commit('set_hotelInDutyInfo',state.hotelList[i])
        //                 break
        //             }
        //         }
        //
        //     }
        // },
        addPicture: async({ state, commit }, data) => {
            const res = await addPictureAPI(data)
            console.log(res)
            if(res){
                message.success('添加成功')
                //commit('getPictures')
            }
        },
        getPictures: async ({state,commit})=>{
            const res =await getPicturesAPI(state.currentHotelId)
            if(res){
                commit('set_currentPictureList',res)
            }else{
                message.error("获取图片失败")
            }
        },
        // getHotelOrders: async ({state,commit},hotelId)=>{
        //     const res=await getHotelOrdersAPI(hotelId)
        //     if(res){
        //         commit('set_hotelOrders',res)
        //     }else{
        //         message.error("获取订单失败")
        //     }
        //
        // },
    }




}

export default hotel