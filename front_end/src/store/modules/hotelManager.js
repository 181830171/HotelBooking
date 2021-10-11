import {
    addRoomAPI,
    addHotelAPI,
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,
    execOrderAPI,//执行订单
    delayCheckInAPI,
    deleteOrderAPI,
    getHotelOrdersAPI,
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    deleteCouponAPI,
} from '@/api/coupon'
import {
    getHotelByIdAPI,
    updateHotelInfoAPI,
} from '@/api/hotel'
import { message } from 'ant-design-vue'

const hotelManager = {
    state: {
        orderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
            BizRegion:'',
            picture:'',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
            picture: '',
            peopleNum: 0,
            breakfast: 0,
            description: '',
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        activeHotelId: 0,
        couponList: [],
        hotelInDuty: 0,
        hotelInDutyInfo: [],
        hotelInDutyOrders:[],
    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_hotelInDuty: function (state,data) {
          state.hotelInDuty=data
        },
        set_hotelInDutyInfo: function (state,data) {
            state.hotelInDutyInfo=data
        },
        set_hotelOrders:function (state,data) {
            state.hotelInDutyOrders=data

        },
    },
    actions: {
        getAllOrders: async({ state, commit }) => {
            const res = await getAllOrdersAPI()
            if(res){
                commit('set_orderList', res)
            }
        },
        addHotel: async({ state, dispatch, commit },strBizRegion) => {
            const res = await addHotelAPI(state.addHotelParams, strBizRegion)
            if(res){
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'XiDan',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',
                    managerId:'',
                    BizRegion: '',
                    picture:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败，请检查管理员是否存在')
            }
        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res==null){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                    picture: '',
                    peopleNum: 0,
                    breakfast: 0,
                    description: '',
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList',res)
            }
        },
        addHotelCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible',false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        execOrder: async({ state, dispatch }, orderId) => {//执行订单 add on 5.15
            const res = await execOrderAPI(orderId)
            if(res) {
                dispatch('getAllOrders')
                dispatch('getHotelOrders',state.hotelInDuty)
                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
        },
        delayCheckIn: async({ state, dispatch }, orderId) => {//延时入住 add on 5.16
            const res = await delayCheckInAPI(orderId)
            if(res) {
                dispatch('getAllOrders')
                dispatch('getHotelOrders',state.hotelInDuty)
                message.success('办理延时成功')
            }else{
                message.error('办理延时失败')
            }
        },
        deleteOrder:async({ state, dispatch }, orderId) => {
            const res = await deleteOrderAPI(orderId)
            if(res) {
                dispatch('getAllOrders')
                dispatch('getHotelOrders',state.hotelInDuty)
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        deleteCoupon:async({ state, dispatch }, couponId) => {
            const res = await deleteCouponAPI(couponId)
            if(res) {
                dispatch('getHotelCoupon')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        getHotelOrders: async ({state,commit},hotelId)=>{
            const res=await getHotelOrdersAPI(hotelId)
            if(res){
                commit('set_hotelOrders',res)
            }else{
                message.error("获取订单失败")
            }

        },
        getHotelByIdTwo:async({commit, state},hotelId) => {
            const res = await getHotelByIdAPI({
                hotelId
            })
            if(res){
                commit('set_hotelInDutyInfo', res)
            }
        },
        updateHotelInfo: async({ state, dispatch ,commit}, data) => {
            const params = {
                ...data,
            }
            const res = await updateHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getHotelByIdTwo',state.hotelInDuty)

            }
        },

    }
}
export default hotelManager