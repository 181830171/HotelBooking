<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>

                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                            v-if="modify"
                            oninput = "value=value.replace(/[^\d]/g,'')"
                            maxLength="11"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" >
                        <!--                        查看详细信用记录-->
                        <a-button type="primary" @click="checkCreditRecords(userInfo.id)"><a-icon type="search"/>
                            查看信用记录
                        </a-button>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                            placeholder="请输入新密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                            maxlength="11"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                     <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo"><a-icon type="edit" />修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
                <CreditRecord></CreditRecord>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2">
                <a-table
                    :columns="columns"
                    :dataSource="userOrderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-else-if="text == 'DoubleBed'">双床房</span>
                        <span v-else-if="text == 'Family'">家庭房</span>
                        <span v-else >{{ text }}</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="checkOrder(record.id)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record.id)"
                            @cancel="cancel"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState=='已入住'||record.orderState=='延迟入住'"></a-divider>
                        <a-popconfirm
                                title="你确定退房吗？"
                                @confirm="checkOut(record.id)"
                                @cancel="cancel"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已入住'||record.orderState=='延迟入住'"
                        >
                            <a-button type="default" size="small">退房</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState=='已退房'"></a-divider>
                        <a-button type="default" size="small" v-if="record.orderState=='已退房'" @click="writeEvaluation(record.id,record.hotelId)">评价</a-button>

                    </span>
                </a-table>
                <CheckOrderModal></CheckOrderModal>
                <Evaluation></Evaluation>
            </a-tab-pane>
            <a-tab-pane tab="我的评论" key="3">
                <UserComments :comment="Comment"  v-for="Comment in comments" :key="Comment.index">
                </UserComments>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import CreditRecord from "./creditRecord"
import CheckOrderModal from "./checkOrderModal";
import Evaluation from "../hotel/components/evaluation";
import UserComments from './userComments'
const moment = require('moment')
const columns = [
    {  
        title: '订单号',
        dataIndex: 'id',
    },
    {  
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        sorter:(a,b)=>moment.duration(moment(a.checkInDate+" 00:00:00")-moment(b.checkInDate+" 00:00:00")),
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
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' },{text:'异常订单',value:'异常订单'},
            {text: '已评价',value:'已评价'},{text:'延迟入住',value:'延迟入住'},{text:'已退房',value:'已退房'}],//新增异常订单，已评价订单，未完成订单
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
    
  ];
export default {
    name: 'info',
    data(){
        return {
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
        }
    },
    components: {
        Evaluation,
        CheckOrderModal,
        CreditRecord,
        UserComments
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'checkOrderModalVisible',//add on 5.8
            'creditRecordVisible',
            'evaluationVisible',
            'comments',
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
        await this.getCommentsByUserId()
        //await this.getOrderById()//add on 5.8
    },
    methods: {
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'getOrderById',//add on 5.8
            'updateUserInfo',
            'cancelOrder',
            'getUserCreditRecord',//5.18
             "endOrder",
            'getHotelByIdTwo',
            'getCommentsByUserId',
        ]),
        ...mapMutations([
            "set_checkOrderModalVisible",//add on 5.8
            "set_creditRecordVisible",//5.18
            "set_evaluationVisible",//5.19


        ]),
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password')
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                    'password': this.userInfo.password,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
        confirmCancelOrder(orderId){
            this.cancelOrder(orderId)
        },
        cancel() {

        },
        //add on 5.8
        checkOrder(orderId){
            this.getOrderById(orderId)
            this.set_checkOrderModalVisible(true)
        },
        checkCreditRecords(userId){
            this.getUserCreditRecord(userId)
            this.set_creditRecordVisible(true)
        },
        writeEvaluation(orderId,hotelId){
            this.getHotelByIdTwo(hotelId)
            this.getOrderById(orderId)
            this.set_evaluationVisible(true)
        },//评价
        checkOut(orderId){
            this.endOrder(orderId)//退房
        }
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>