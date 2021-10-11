const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  userOrder:state=>state.user.userOrder,//add on 5.8
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  checkOrderModalVisible:state=>state.user.checkOrderModalVisible,//add on 5.8
  hotelInListVisible:state=>state.hotel.hotelInListVisible,//add on 5.9
  userCreditRecord:state=>state.user.userCreditRecord,//信用记录
  creditRecordVisible:state=>state.user.creditRecordVisible,//信用记录
  evaluationVisible:state=>state.user.evaluationVisible,//评分模板
  comments:state=>state.user.comments,
  hotelComments:state=>state.hotel.hotelComments,
  currentPictureList:state=>state.hotel.currentPictureList,
  vipInfo:state=>state.user.vipInfo,
  hotelInDutyOrders:state=>state.hotelManager.hotelInDutyOrders,
  // searchHotel(state,name){
  //   for(let i=0;i<state.hotel.hotelList.length;i++){
  //
  //   }
  // },
  // vipRegisterVisible:state=>state.user.vipRegisterVisible,
  //admin
  managerList: state => state.admin.managerList,
  userList:state=>state.admin.userList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  //hotelManager
  hotelInDuty: state=>state.hotelManager.hotelInDuty,//5.28
  hotelInDutyInfo:state=>state.hotelManager.hotelInDutyInfo,
  orderList: state => state.hotelManager.orderList,
  addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  couponVisible: state => state.hotelManager.couponVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  couponList: state => state.hotelManager.couponList,
  }
  
  export default getters