<template>
    <a-modal
        :visible="orderModalVisible"
        title="预定详情"
        cancelText="取消"
        okText="下单"
        @cancel="cancelOrder"
        @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人姓名">
                <a-input
                    v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请填写入住人联系手机', }] }
                    ]"
                    maxLength=11
                />
            </a-form-item>

            <a-form-item v-bind="formItemLayout" label="入住日期">
                <a-range-picker
                    format="YYYY-MM-DD"
                    @change="changeDate"
                    v-decorator="[
                        'date', 
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]   
                        }
                    ]"
                    :placeholder="['入住日期','退房日期']"
                    :disabledDate="disabledDate"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数">
                <a-select
                    v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请选择入住人数' }] },
                    ]"
                    placeholder="请选择入住人数"
                    @change="changePeopleNum"
                >
                    <a-select-option :value="1">
                    1
                    </a-select-option>
                    <a-select-option :value="2">
                    2
                    </a-select-option>
                     <a-select-option :value="3">
                    3
                    </a-select-option>
                    <a-select-option :value="4">
                    4
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童">
                <a-radio-group
                    v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数">
                <a-select
                    v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请选择房间数' }] },
                    ]"
                    placeholder="请选择房间数"
                    @change="changeRoomNum"
                >
                    <a-select-option :value="1">
                    1
                    </a-select-option>
                    <a-select-option :value="2">
                    2
                    </a-select-option>
                     <a-select-option :value="3">
                    3
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间单价">
                <span>{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="总价">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-divider></a-divider>
            <h2 v-if="orderMatchCouponList.length>0">优惠</h2>
            <a-radio-group v-model="checkedList" :defaultValue="0" @change="countMoney">
                <a-table
                    style="margin-left: 35px"
                    :columns="columns"
                    :dataSource="orderMatchCouponList"
                    :showHeader="false"
                    bordered
                    v-if="orderMatchCouponList.length>0"
                >
                    <a-radio
                        slot="id"
                        slot-scope="record"
                        :value="record"
                    >
                    </a-radio>
                </a-table>
            </a-radio-group>
            <div v-if="vipInfo.viptype!=undefined">
                <div>
                <a-popover title="提示">
                    <template slot="content">
                        <p>会员优惠和地区优惠不可同时享用，自动选择最大优惠</p>
                    </template>
                <h2>VIP优惠</h2>
                </a-popover>
                </div>
                <a-radio-group v-model="vipDis" @change="countVipDis" class="vip-radio-group">
                    <a-radio :value="0">{{vipInfo.viptype}}等级优惠：{{vipInfo.vipDiscount}}折</a-radio><br/>
                    <a-radio :value="1" v-if="currentHotelInfo.bizRegion=='RegionA'">A区商圈优惠：{{regionDiscount[0]}}折</a-radio><br/>
                    <a-radio :value="2" v-if="currentHotelInfo.bizRegion=='RegionB'">B区商圈优惠：{{regionDiscount[1]}}折</a-radio><br/>
                    <a-radio :value="3" v-if="currentHotelInfo.bizRegion=='RegionC'">C区商圈优惠：{{regionDiscount[2]}}折</a-radio><br/>
                    <a-radio :value="4" v-if="currentHotelInfo.bizRegion=='RegionD'">D区商圈优惠：{{regionDiscount[3]}}折</a-radio><br/>
                    <a-radio :value="5" v-if="currentHotelInfo.bizRegion=='RegionE'">A区商圈优惠：{{regionDiscount[4]}}折</a-radio>
                </a-radio-group>
<!--                <div>-->
<!--                    <a-popover title="提示">-->
<!--                        <template slot="content">-->
<!--                            <p>若选择享受优惠会自动享受二者中折扣更低的优惠</p>-->
<!--                        </template>-->
<!--                        <a-button type="link" @click="enjoyVipDiscount" style="margin-left: 30px" :disabled="discounted">享受以下会员优惠</a-button>-->
<!--                        <a-button type="link" @click="revert" style="margin-bottom: 30px" :disabled="!discounted">重置</a-button>-->
<!--                    </a-popover>-->
<!--                </div>-->
<!--                <a-tag color="#108ee9" style="margin-left: 35px">{{vipInfo.viptype}}等级优惠：{{vipInfo.vipDiscount}}折</a-tag>-->
<!--                <a-tag color="#108ee9" style="margin-left: 35px" v-if="currentHotelInfo.bizRegion=='RegionA'">A区商圈优惠：{{regionDiscount[0]}}折</a-tag>-->
<!--                <a-tag color="#108ee9" style="margin-left: 35px" v-if="currentHotelInfo.bizRegion=='RegionB'">B区商圈优惠：{{regionDiscount[1]}}折</a-tag>-->
<!--                <a-tag color="#108ee9" style="margin-left: 35px" v-if="currentHotelInfo.bizRegion=='RegionC'">C区商圈优惠：{{regionDiscount[2]}}折</a-tag>-->
<!--                <a-tag color="#108ee9" style="margin-left: 35px" v-if="currentHotelInfo.bizRegion=='RegionD'">D区商圈优惠：{{regionDiscount[3]}}折</a-tag>-->
<!--                <a-tag color="#108ee9" style="margin-left: 35px" v-if="currentHotelInfo.bizRegion=='RegionE'">E区商圈优惠：{{regionDiscount[4]}}折</a-tag>-->
            </div>
            <a-divider></a-divider>
             <a-form-item v-bind="formItemLayout" style="margin-top: 30px" label="结算后总价">
                <span>￥{{ finalPrice }}</span>
<!--                 <a-button type="link" style="margin-left: 20px" @click="countMoney">结算</a-button>-->
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex'

    const moment = require('moment')

const columns = [
    {  
        title: '勾选',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'}
    },
    {
        title: '优惠类型',
        dataIndex: 'couponName',
        scopedSlots: {customRender: 'couponName'}
    },
    {
        title: '折扣',
        dataIndex: 'discount',
    },

    {
        title: '优惠简介',
        dataIndex: 'description',
        
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney',
    },
  ];
export default {
    name: 'orderModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
            totalPrice: '',
            columns,
            checkedList:'',
            finalPrice: '',
            regionDiscount:[0.9, 0.91, 0.92, 0.93, 0.94],
            discounted:true,
            vipDis:0,
            tempvipDis:0,
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'orderMatchCouponList',
            'vipInfo',
        ]),
        
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    mounted() {
        this.vipDis=this.getLowestDiscountOption()
        this.tempvipDis=this.vipDis
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            "clearOrderMatchCouponList"//add on 5.15
        ]),
        ...mapActions([
            'addOrder',
            'getOrderMatchCoupons',
        ]),
        cancelOrder() {
            this.set_orderModalVisible(false)
            this.clearOrderMatchCouponList()//add on 5.15
        },
        confirmOrder() {

        },
        changeDate(v) {
            if(this.totalPrice != ''){
                this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price)
            }
            this.countMoney()
        },
        changePeopleNum(v){

        },
        //提前算出最小优惠选项
        getLowestDiscountOption(){
            let temp=0
            if(this.currentHotelInfo.bizRegion==="RegionB"){
                temp=1
            }else if(this.currentHotelInfo.bizRegion==="RegionC"){
                temp=2
            }else if(this.currentHotelInfo.bizRegion=="RegionD"){
                temp=3
            }else if(this.currentHotelInfo.bizRegion=="RegionE"){
                temp=4
            }
            if(this.vipInfo.vipDiscount<this.regionDiscount){
                return 0
            }
            return temp+1
        },
        countVipDis(){
            //优惠价格写死
            const dislist=[]
            dislist.push(this.vipInfo.vipDiscount)
            const totaldislist = dislist.concat(this.regionDiscount)
            if(this.tempvipDis!=this.vipDis){
                this.finalPrice=Math.round(this.finalPrice/totaldislist[this.tempvipDis]*totaldislist[this.vipDis])
            }
            this.tempvipDis = this.vipDis
            console.log('改变了吗',this.finalPrice)
        },
        changeRoomNum(v) {
            this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]),'day')
            this.countMoney()
        },
        change() {
            this.finalPrice = this.totalPrice

            // this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id)!=-1).forEach(item => (this.finalPrice= this.finalPrice-item.discountMoney))
            for(let i=0;i<this.orderMatchCouponList.length;i++) {
                if (this.orderMatchCouponList[i].id == this.checkedList) {
                    if (this.orderMatchCouponList[i].discount != 0) {
                        this.finalPrice =Math.round(this.finalPrice * this.orderMatchCouponList[i].discount)
                    } else {
                        this.finalPrice =Math.round( this.finalPrice - this.orderMatchCouponList[i].discountMoney)
                    }
                    break;
                }
            }
        },
        countMoney(){
            this.change()
            if(this.discounted){
                let region_Discount=0.9;
                if(this.currentHotelInfo.bizRegion=='RegionB'){region_Discount=this.regionDiscount[1];}
                else if(this.currentHotelInfo.bizRegion=='RegionC'){region_Discount=this.regionDiscount[2];}
                else if(this.currentHotelInfo.bizRegion=='RegionD'){region_Discount=this.regionDiscount[3];}
                else if(this.currentHotelInfo.bizRegion=='RegionE'){region_Discount=this.regionDiscount[4];}
                let finalDiscount=this.vipInfo.vipDiscount;
                if(region_Discount<this.vipInfo.vipDiscount){
                    finalDiscount=region_Discount
                }
                this.finalPrice=Math.round(this.finalPrice*finalDiscount);
            }
        },
        disabledDate(current){//设置不可选日期//
            if(this.selectDate!=null){
                return current<this.selectDate;
            }else{
                return current<moment().endOf('day').subtract(1,'days');
            }
        },
        enjoyVipDiscount(){
            this.discounted=true;
            this.countMoney()
        },
        revert(){
            this.discounted=false;
            this.vipDis=''
            this.countMoney()
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        hotelId: this.currentHotelId,
                        hotelName: this.currentHotelInfo.name,
                        userId: Number(this.userId),
                        checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType,
                        roomNum: this.form.getFieldValue('roomNum'),
                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        clientName:this.form.getFieldValue('clientName'),//add on 5.9
                        phoneNumber:this.form.getFieldValue('phoneNumber'),//add on 5.9
                        createDate: '',
                        // price: this.checkedList.length > 0 ? this.finalPrice: this.totalPrice
                        price: this.finalPrice
                    }
                    this.addOrder(data)
                    this.clearOrderMatchCouponList()
                }
            });
        },
    },

    watch:{
        totalPrice(val) {
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.totalPrice,
                roomNum: this.form.getFieldValue('roomNum'),
                checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
            }
            this.getOrderMatchCoupons(data)
        }
    }
}
</script>
<style scoped lang="less">
    .vip-radio-group{
        position:relative;
        left:9%;
    }
</style>