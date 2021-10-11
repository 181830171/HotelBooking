<template>
   <div>
    <a-modal
        :visible="couponVisible"
        title="优惠策略"
        width="900px"
        :footer="null"
        @cancel="cancel"
    >
         <div style="width: 100%; text-align: right; margin:20px 0">
             <a-button type="primary" @click="addCoupon"><a-icon type="plus" />
添加优惠策略</a-button>
        </div>
        <a-table
            :columns="columns"
            :dataSource="couponList"
            bordered
        >
            <a-tag color="purple" slot="couponType" slot-scope="text">{{couponTypes[text-1]}}</a-tag>
            <span slot="couponName" slot-scope="text">
                {{text}}
            </span>
            <span slot="action" slot-scope="record">
                <a-popconfirm
                        title="确定想删除该优惠策略吗"
                        @confirm="deleteCouponById(record.id)"
                        okText="确定"
                        cancelText="取消"
                >
                    <a-button type="dashed">删除优惠策略</a-button>
                </a-popconfirm>
            </span>
        </a-table>
    </a-modal>
    <AddCoupon></AddCoupon>
   </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'

const columns = [
    {
        title: '优惠类型',
        dataIndex: 'couponType',
        scopedSlots: {customRender: 'couponType'}
    },
    {
        title: '优惠券名称',
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
        title:'使用门槛',
        dataIndex: 'targetMoney',
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
    // 这里定义列表头
  ];
export default {
    name: 'coupon',
    data() {
        return {
            columns,
            couponTypes: ['生日特惠','多间特惠','满减特惠','限时特惠','节日特惠','企业特惠']
        }
    },
    components: {
        AddCoupon,
    },
    computed: {
        ...mapGetters([
            'couponVisible',
            'couponList',
        ])
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        ...mapActions([
            'getHotelCoupon',
            'deleteCoupon',
        ]),
        cancel() {
            this.set_couponVisible(false)
        },
        addCoupon() {
            this.set_addCouponVisible(true),
            this.set_couponVisible(false)
        },
        deleteCouponById(couponId){
            this.deleteCoupon(couponId)
        }
    },
}
</script>
<style scoped>

</style>