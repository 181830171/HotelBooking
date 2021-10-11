<template>
    <a-modal
            :visible="checkOrderModalVisible"
            title="订单详情"
            cancelText="取消"
            okText="确认"
            @cancel="confirm"
            @ok="confirm"
            width=1300px

    >
        <a-table
                :columns="rows"
                :dataSource="userOrder"
                bordered
                style="width: 1200px"
        >
            <span slot="roomType" slot-scope="text">
                <span v-if="text == 'BigBed'">大床房</span>
                <span v-else-if="text == 'DoubleBed'">双床房</span>
                <span v-else-if="text == 'Family'">家庭房</span>
                <span v-else >{{ text }}</span>
            </span>
            <a-tag slot="haveChild" color="blue" slot-scope="text" >{{Number(text)==1?"有":"无"}}</a-tag>


            <a-tag slot="orderState" color="blue" slot-scope="text">
                {{ text }}
            </a-tag>
        </a-table>
    </a-modal>
</template>


<script>import {mapActions, mapGetters, mapMutations} from "vuex";
const rows=[
    {
        title: '订单号',
        dataIndex: 'id',
    },
    {
        title:'入住人姓名',
        dataIndex:'clientName',

    },
    {
        title:'联系电话',
        dataIndex:'phoneNumber',
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
        title:'创建时间',
        dataIndex:'createDate',
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
        title: '有无儿童',
        dataIndex: 'haveChild',
        scopedSlots: { customRender: 'haveChild'}
    },
    {
        title: '房价',
        dataIndex: 'price',
    },
    {
        title: '状态',
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },

]

export default {
    name: "checkOrderModal",
    data() {
        return {
            formItemLayout: {
                width:30,
                labelCol: {
                    xs: { span: 16},
                    sm: { span: 15 },
                },
                wrapperCol: {
                    xs: { span: 20 },
                    sm: { span: 16 },
                },
            },
            rows,
            checkedList: [],
        }
    },
    computed: {
        ...mapGetters([
            'checkOrderModalVisible',
            'userOrder',//add on 5.8
        ]),

    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'checkOrderModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_checkOrderModalVisible'
        ]),
        ...mapActions([
            'getOrderById',//add on 5.8

        ]),
        confirm() {
            this.set_checkOrderModalVisible(false)
        },



    }
    }
</script>

<style scoped>

</style>