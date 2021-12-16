<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店信息" key="1">
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item label="酒店名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写酒店名"
                                v-decorator="['name', { rules: [{ required: true, message: '请输入酒店名' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ hotelInDutyInfo.name }}</span>
                    </a-form-item>
                    <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{hotelInDutyInfo.address}}</span>
                    </a-form-item>
                    <a-form-item label="商圈" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{hotelInDutyInfo.bizRegion}}</span>
                    </a-form-item>

                    <a-form-item label="星级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-select
                                :default-value="hotelInDutyInfo.hotelStar"
                                v-if="modify"
                                v-decorator="[  'hotelStar',
                                                { rules: [{ required: true, message: '请选择星级' }] }]"
                        >
                            <a-select-option value="Five">
                                五星级
                            </a-select-option>
                            <a-select-option value="Four">
                                四星级
                            </a-select-option>
                            <a-select-option value="Three">
                                三星级
                            </a-select-option>
                            <a-select-option value="Two">
                                二星级
                            </a-select-option>
                            <a-select-option value="One">
                                一星级
                            </a-select-option>
                        </a-select>
                        <span v-else>{{hotelInDutyInfo.hotelStar}}</span>
                    </a-form-item>
                    <a-form-item label="评分" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ hotelInDutyInfo.rate }}</span>
                    </a-form-item>
                    <a-form-item label="联系电话" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" >
                        <a-input
                                placeholder="请输入新联系电话"
                                v-decorator="['phoneNum', { rules: [{ required: true, message: '请输入新联系电话' }] }]"
                                v-if="modify"
                                oninput = "value=value.replace(/[^\d]/g,'')"
                                maxLength="11"
                        />
                        <span v-else>{{hotelInDutyInfo.phoneNum}}</span>
                    </a-form-item>
                    <a-form-item label="酒店介绍" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" >
                        <a-input
                                placeholder="请输入新酒店介绍"
                                v-decorator="['description', { rules: [{ required: true, message: '请输入新酒店介绍' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{hotelInDutyInfo.description}}</span>
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
            </a-tab-pane>
            <a-tab-pane tab="酒店管理" key="2">
                 <a-table
                    :columns="columns1"
                    :dataSource="hotelList"
                    bordered
                >
<!--                     只有该管理员管理的酒店才能进行操作-->
                    <span slot="action" slot-scope="record" >
                        <span v-if="userId!=record.managerId">您没有管理权限</span>
                        <a-button type="primary" size="small" @click="showCoupon(record)" v-show="userId==record.managerId">优惠策略</a-button>
                        <a-divider type="vertical" v-show="userId==record.managerId"></a-divider>
                        <a-button type="primary" size="small" @click="addRoom(record)" v-show="userId==record.managerId">录入房间</a-button>
                        <a-divider type="vertical" v-show="userId==record.managerId"></a-divider>
                    </span>
                </a-table>
                <a-input
                        v-decorator="['link',{ rules: [{required: true, message: '请输入图片链接', }] }]"
                        style="padding-right: 10px;width: 600px"
                        placeholder="请输入图片链接"
                        v-model="link"
                />
                <a-button type="primary" @click="addHotelPicture"> 上传图片 <a-icon type="upload"/></a-button>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="3">
                <a-table
                    :columns="columns2"
                    :dataSource="hotelInDutyOrders"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="checkInDate" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record" v-if="record.hotelId==hotelInDuty">
                        <a-button type="primary" size="small"  @click="checkOrder(record.id)">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该订单吗？该操作会删去该订单的所有记录，但不会影响信用，评价的记录"
                            @confirm="delete_order(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除订单</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical"></a-divider>
<!--                        执行订单-->
                        <a-popconfirm
                                title="你确定执行该笔订单吗？"
                                @confirm="confirmExecOrder(record.id)"
                                @cancel="cancelExecOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">执行订单</a-button>
                        </a-popconfirm>
<!--                        延时入住-->
                        <a-popconfirm
                                title="你确定为该订单办理延时入住吗？"
                                @confirm="confirmDelayOrder(record.id)"
                                @cancel="cancelDelayOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState =='异常订单'"
                        >
                            <a-button type="danger" size="small">延时入住</a-button>
                        </a-popconfirm>
                    </span>
                    <span v-else>您没有管理权限</span>
                </a-table>
            </a-tab-pane>
            
        </a-tabs>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <CheckOrderModal></CheckOrderModal>>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddRoomModal from './components/addRoomModal'
import Coupon from './components/coupon'
import CheckOrderModal from "../user/checkOrderModal";
const moment = require('moment')
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
const columns2 = [
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
            {text: '已评价',value:'已评价'},{text:'延迟入住',value:'延迟入住'},{text:'已退房',value:'已退房'}],//新增异常订单
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }//add on 5.10 wang 状态栏过滤器
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name: 'manageHotel',
    data(){
        return {
            modify:false,
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
            link:'',
        }
    },
    components: {
        AddRoomModal,
        Coupon,
        CheckOrderModal,//add on 5.10 wang
    },
    computed: {
        ...mapGetters([
            'hotelList',
            'addRoomModalVisible',
            'activeHotelId',
            'couponVisible',
            'checkOrderModalVisible',//add on 5.10 wang
            'userId',
            'hotelInDuty',
            'hotelInDutyInfo',
            'hotelInDutyOrders',
        ]),
    },
    async mounted() {
        await this.getHotelList()
        await this.getHotelInDuty()
    },
    methods: {
        ...mapMutations([
            'set_addRoomModalVisible',
            'set_couponVisible',
            'set_activeHotelId',
            'set_checkOrderModalVisible',//add on 5.10 wang
            'set_hotelInDuty',
            'set_hotelInDutyInfo',
        ]),
        ...mapActions([
            'getHotelList',
            'getAllOrders',
            'getHotelCoupon',
            'getOrderById',
            'execOrder',//5.15
            'delayCheckIn',
            'deleteOrder',
            'updateHotelInfo',
            'addPicture',
            'getHotelOrders'

        ]),
        addHotelPicture(){
            if(this.link!='') {
                const data = {
                    hotelId: this.hotelInDuty,
                    linkStr: this.link,
                }
                this.addPicture(data)
            }else{
                alert("请输入图片链接")
            }
        },
        addRoom(record) {
            this.set_activeHotelId(record.id)
            this.set_addRoomModalVisible(true)
        },
        showCoupon(record) {
            this.set_activeHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
        delete_order(order){
            if(order.orderState=='已预订' || order.orderState=='已入住' ||order.orderState=='已退房' || order.orderState=='延时入住'){
                alert("客户还未入住/退房/评价，不能删除该订单")
            }else if(order.orderState=="异常订单" && moment.duration(moment()-moment(order.checkOutDate+" 00:00:00"))<0){//当前时间小于退房时间
                alert("客户延迟入住期限未过，不能删除该订单")
            }
            else{this.deleteOrder(order.id)}
        },
        checkOrder(orderId){
            this.set_checkOrderModalVisible(true) //add on 5.10 wang
            this.getOrderById(orderId)//add on 5.10 wang
        },
        confirmExecOrder(orderId){
            this.execOrder(orderId)//执行订单 5.15
            this.getHotelOrders(this.hotelInDuty)
        },
        cancelExecOrder(){//啥也不做 5.15

        },
        confirmDelayOrder(orderId){
            this.delayCheckIn(orderId)//延时入住 5.16
        },
        cancelDelayOrder(){

        },
        getHotelInDuty(){//设置该管理员负责的酒店id,info
            for(let i=0;i<this.hotelList.length;i++){
                if(this.hotelList[i].managerId==this.userId){
                    this.set_hotelInDuty(this.hotelList[i].id)
                    this.set_hotelInDutyInfo(this.hotelList[i])
                    this.getHotelOrders(this.hotelList[i].id)
                    break
                }
            }
        },
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        id: this.hotelInDutyInfo.id,
                        name: this.form.getFieldValue('name'),
                        phoneNum: this.form.getFieldValue('phoneNum'),
                        description: this.form.getFieldValue('description'),
                        hotelStar:this.form.getFieldValue('hotelStar'),
                    }
                    this.updateHotelInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'name': this.hotelInDutyInfo.name,
                    'phoneNum': this.hotelInDutyInfo.phoneNum,
                    'description':this.hotelInDutyInfo.description,
                    'hotelStar':this.hotelInDutyInfo.hotelStar,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>