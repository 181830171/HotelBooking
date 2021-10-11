<template >
    <a-modal
        :visible="evaluationVisible"
        title="请对本次入住体验做一个评价"
        cancelText="取消"
        okText="确认"
        @cancel="cancle"
        @ok="confirmEvaluate"
        :okButtonProps="okprop"
    >
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="描述相符" :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }" >
                <a-rate v-model="value[0]" :tooltips="tips" allow-half @change="onChangeOne"  />
            </a-form-item>
            <a-form-item label="服务态度" :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }">
                <a-rate v-model="value[1]" :tooltips="tips" allow-half @change="onChangeTwo"  />
            </a-form-item>
            <a-form-item label="卫生状况" :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }">
                <a-rate v-model="value[2]" :tooltips="tips" allow-half @change="onChangeThree"  />
            </a-form-item >
            <a-form-item label="请填写评价:" :label-col="{ span: 5 }" :wrapper-col="{ span: 9, offset: 1  }" >
                <a-input
                        type="textarea"
                        :row="4"
                        placeholder="从多个角度进行评价，可以帮助更多的顾客"
                        v-decorator="['userEvaluation', { rules: [{ required: true, message: '请填写评价:' }] }]"
                        :disabled="committed"
                />
            </a-form-item>

        </a-form>
    </a-modal>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    export default {
         name: 'evaluation',
        data() {
             return {
                 value: [3.0,3.0,3.0], //默认值
                 tips: ['不满意', '糟糕', '一般', '优秀', '非常满意'],  // 父组件传来星星的大小和分数
                 form: this.$form.createForm(this, { name: 'coordinated' }),
                 okprop: {
                     danger : true,
                     disabled : false,
                 }
             }
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'evaluation' });
        },
        computed: {

             ...mapGetters([
                 'evaluationVisible',
                 'currentHotelInfo',
                 'currentHotelId',
                 'userId',
                 'userOrder',
             ])
        },

        methods: {
            ...mapMutations([
                "set_evaluationVisible",//5.19
            ]),
            ...mapActions([
                "addComment"
            ]),
            onChangeOne(value) {

            },
            onChangeTwo(value) {

            },
            onChangeThree(value) {

            },

            cancle(){
                this.set_evaluationVisible(false)

            },
            confirmEvaluate(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            hotelId: this.currentHotelId,
                            hotelName: this.currentHotelInfo.name,
                            userId:  this.userId,
                            orderId: this.userOrder[0].id,
                            createDate: '',
                            descriptionScore: this.value[0],
                            serviceScore: this.value[1],
                            environmentScore: this.value[2],
                            userEvaluation: this.form.getFieldValue('userEvaluation')
                            //分别向后端传回酒店id，酒店名，用户id，描述相符评分，服务态度评分，卫生环境评分，整体评价。
                        }
                        this.addComment(data)
                    }
                });
            },

        }
    };
</script>
<style  scoped>

</style>
