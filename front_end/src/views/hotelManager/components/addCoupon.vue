<template>
    <a-modal
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠卷类型" v-bind="formItemLayout">
                <a-select
                    v-decorator="[
                    'type',
                    { rules: [{ required: true, message: '请选择类型' }] }]"
                    @change="changeType"
                >
<!--                    <a-select-option value="1">生日特惠</a-select-option>-->
                    <a-select-option value="2">多间满减</a-select-option>
                    <a-select-option value="3">满减优惠</a-select-option>
                    <a-select-option value="4">限时优惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写券名"
                    v-decorator="['name', { rules: [{ required: true, message: '请输入券名' }] }]"
                 />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                    type="textarea"
                    :rows="4"
                    placeholder="请填写优惠简介"
                    v-decorator="['description', { rules: [{ required: true, message: '请填写优惠简介' }] }]"
                 />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有效日期" v-if="this.form.getFieldValue('type')==4">
                <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择有效时间' }]
                        }
                    ]"
                        :placeholder="['生效日期','失效日期']"
                        :disabledDate="disabledDate"
                />
            </a-form-item>
            <a-form-item label="优惠方式" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                                    'way',
                                    { rules: [{ required: true, message: '请选择优惠方式'}] }]"
                >
                    <a-select-option value="1">折扣</a-select-option>
                    <a-select-option value="2">满减</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="折扣" v-if="this.form.getFieldValue('way')==1" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写折扣力度(需为0-1之间的数字)"
                        v-decorator="['discount', { rules: [{ required: true, message: '请填写折扣力度(需为0-1之间的数字)' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标金额" v-if="this.form.getFieldValue('way')==2">
                <a-input
                    placeholder="请填写达标金额"
                    v-decorator="['targetMoney', { rules: [{ required: true, message: '请填写达标金额' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠金额" v-if="this.form.getFieldValue('way')==2">
                <a-input
                    placeholder="请填写优惠金额"
                    v-decorator="['discountMoney', { rules: [{ required: true, message: '请填写优惠金额' }] }]"
                 />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
export default {
    name: 'addCouponModal',
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
        }
    },
    computed: {
        ...mapGetters([
            'activeHotelId',
            'addCouponVisible',
        ])
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible'
        ]),
        ...mapActions([
            // addHotelCoupon：添加酒店策略接口
            'addHotelCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
        },
        changeType(v){

        },
        disabledDate(current){//设置不可选日期
            if(this.selectDate!=null){
                return current<this.selectDate;
            }else{
                return current<moment().endOf('day').subtract(1,'days');
            }
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {//两种情况，折扣或满减
                    let discount=0;
                    let targetMoney=0;
                    let discountMoney=0;
                    if(Number(this.form.getFieldValue('way')==1)){
                        discount=Number(this.form.getFieldValue('discount'));
                    }
                    else{
                        targetMoney=Number(this.form.getFieldValue('targetMoney'));
                        discountMoney=Number(this.form.getFieldValue('discountMoney'))
                    }
                    if (Number(this.form.getFieldValue('type')) == 4) {//两种情况，限时特惠需要传入生效时间，而其他优惠不用
                        const data = {
                            // 这里添加接口参数
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            targetMoney: targetMoney,
                            discountMoney: discountMoney,
                            hotelId: Number(this.activeHotelId),
                            startTime: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                            endTime: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                            status: 1,
                            discount: discount,
                        }
                        this.addHotelCoupon(data)
                    } else {
                        const data = {
                            // 这里添加接口参数
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            targetMoney: targetMoney,
                            discountMoney: discountMoney,
                            hotelId: Number(this.activeHotelId),
                            status: 1,
                            discount: discount,
                        }
                        this.addHotelCoupon(data)
                    }
                }

            });
        },
    }
}
</script>