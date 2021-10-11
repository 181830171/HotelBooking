<template>
    <a-modal
        :visible="addRoomModalVisible"
        title="录入客房"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-popover title="提示">
                    <template slot="content">
                        <p>录入的房间名称或类型在该酒店必须是唯一的</p>
                    </template>
                    <a-input
                        v-decorator="[
                        'roomType',
                        { rules: [{ required: true, message: '请输入房间名称/类型' }] }]"
                    >
                    </a-input>
                </a-popover>
            </a-form-item>
            <a-form-item label="房间简介" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写房间简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请输入房间简介' }] }]"
                />
            </a-form-item>
            <a-form-item label="图片链接" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写房间图片链接"
                        v-decorator="['picture', { rules: [{ required: false, message: '请输入房间图片链接' }] }]"
                />
            </a-form-item>
            <a-form-item label="人数上限" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写最多可入住人数"
                        v-decorator="['peopleNum', { rules: [{ required: true, message: '请输入最多可入住人数' }] }]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="是否提供早餐">
                <a-radio-group
                        v-decorator="[
                        'breakfast',
                        { rules: [{required: true, message: '请选择是否提供早餐', }] }
                    ]"
                >
                    <a-radio :value="1">是</a-radio>
                    <a-radio :value="0">否</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item label="房间数量" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写房间数量"
                    v-decorator="['roomNum', { rules: [{ required: true, message: '请输入房间数量' }] }]"
                />
            </a-form-item>
            <a-form-item label="原始价格" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写原始价格"
                    v-decorator="['price', { rules: [{ required: true, message: '请输入原始价格' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addRoomModal',
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
            'addRoomModalVisible',
            'activeHotelId'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addRoomModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addRoomModalVisible',
            'set_addRoomParams'
        ]),
        ...mapActions([
            'addRoom'
        ]),
        cancel() {
            this.set_addRoomModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                let picture=this.form.getFieldValue('picture');
                if(picture==""){
                    picture="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png";
                }

                if (!err) {
                    const data = {
                        picture: picture,
                        description: this.form.getFieldValue('description'),
                        peopleNum: Number(this.form.getFieldValue('peopleNum')),
                        breakfast: Number(this.form.getFieldValue('breakfast')),
                        roomType: this.form.getFieldValue('roomType'),
                        price: Number(this.form.getFieldValue('price')),
                        total: Number(this.form.getFieldValue('roomNum')),
                        curNum: Number(this.form.getFieldValue('roomNum')),
                        hotelId: this.activeHotelId,
                    }
                    this.set_addRoomParams(data)
                    this.addRoom()
                }
            });
        },
    }
}
</script>