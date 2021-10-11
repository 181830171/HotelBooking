<template>
    <a-modal
            :visible="creditRecordVisible"
            title="信用记录详情"
            cancelText="取消"
            okText="确认"
            @cancel="confirm"
            @ok="confirm"
            width=800px

    >
        <a-table
                :columns="rows"
                :dataSource="userCreditRecord"
                bordered
                style="width: 700px"
        >
        </a-table>
    </a-modal>
</template>

<script>import {mapActions, mapGetters, mapMutations} from "vuex";
    const rows=[
        {
            title: '订单号',
            dataIndex: 'orderId',
        },
        {
            title:'变更时间',
            dataIndex:'createDate',

        },
        {
            title:'动作',
            dataIndex:'actionType',
        },
        {
            title: '信用值变化',
            dataIndex: 'change',
            scopedSlots: { customRender: 'change' }
        },
        {
            title: '信用值结果',
            dataIndex: 'res',
            scopedSlots: { customRender: 'res' }
        },
    ]

    export default {
        name: "creditRecord",
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
            }
        },
        computed: {
            ...mapGetters([
                'creditRecordVisible',
                'userCreditRecord',
            ]),

        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'creditRecord' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_creditRecordVisible'
            ]),
            ...mapActions([

            ]),
            confirm() {
                this.set_creditRecordVisible(false)//
            },


        }
    }
</script>

<style scoped>

</style>