<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理员账户信息" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加用户</a-button>
                </div>
                <a-table
                    :columns="columns"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteManager(record.id)"
                                okText="确定"
                                cancelText="取消"
                        >
                        <a-button type="danger" size="small">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="酒店管理" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="showCoupon"><a-icon type="search" />优惠策略</a-button>
                    <a-divider type="vertical"></a-divider>
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="delete_hotel(record.id)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="客户信息" key="3">
                <commonUserList></commonUserList>
            </a-tab-pane>
        </a-tabs>
        <Coupon></Coupon>
        <addHotelModal></addHotelModal>
        <AddManagerModal></AddManagerModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import addHotelModal from '../hotelManager/components/addHotelModal'
import commonUserList from "./components/commonUserList";
import Coupon from "../hotelManager/components/coupon";
const columns = [
    {
        title:"管理员Id",
        dataIndex:"id",
    },
    {  
        title: '邮箱',
        dataIndex: 'email',
    },
    {  
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '信用值',
        dataIndex: 'credit',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns1 = [
    {
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'manageUser',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns1,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        addHotelModal,
        AddManagerModal,
        commonUserList,
        Coupon,
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
            'hotelList',
            'addHotelModalVisible',
            'addCouponVisible',
        ])
    },
    async mounted() {
        await this.getManagerList()
        await this.getHotelList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'deleteUser',
            'getHotelList',
            'deleteHotel',
            'getHotelCoupon',
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_addHotelModalVisible',
            'set_couponVisible',
            'set_activeHotelId',

        ]),
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        deleteManager(userId){
            this.deleteUser(userId)
        },
        addHotel(){
            this.set_addHotelModalVisible(true)
        },
        delete_hotel(hotelId){
            this.deleteHotel(hotelId)
        },
        showCoupon() {
            this.set_activeHotelId(-1)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        //padding: 50px;
        padding: 25px;
        margin: 50px 60px;
        background-color:rgba(255,255,255,0.9);
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>