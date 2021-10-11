<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                    :columns="columns"
                    :dataSource="userOrderListInThisHotel"
            >
                <span slot="roomType" slot-scope="text">{{text=="Family"?"BigBed"?"家庭房":"大床房":"双床房"}}</span>
            </a-table>
        </div>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    const columns = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: { customRender: 'roomType' }
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: { customRender: 'checkInDate' }
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: { customRender: 'checkOutDate' }
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
        },
        {
            title: '状态',
            dataIndex: 'orderState',
        }]
    export default {
        name: "userOrderList",
        // props: {
        //     orderList: {
        //         type: Array
        //     }
        // },
        data(){
            return {
                columns,
                userOrderListInThisHotel:[],
            }
        },
        computed:{
            ...mapGetters([
                'userOrderList',
                'currentHotelInfo',
            ]),
            ...mapActions([
                'getUserOrders'
            ])
        },
        async mounted() {
          this.userOrderListLoading()
          await  this.getUserOrders()
        },
        methods:{
            userOrderListLoading(){
                console.log('I am running')
                this.userOrderListInThisHotel=[]
                for(let i=0;i<this.userOrderList.length;i++){
                    if(this.userOrderList[i].hotelName==this.currentHotelInfo.name){
                        this.userOrderListInThisHotel.push(this.userOrderList[i])
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>