<template>
    <div class="vip-wrapper">
        <a-tabs>
            <a-tab-pane tab="注册会员" key="1">
                <a-form :form="form" style="margin-top: 30px" v-if="vipInfo.viptype==undefined">
                    <a-form-item label="会员类型" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-select
                                v-decorator="[
                                    'type',
                                    { rules: [{ required: true, message: '请选择会员类型'}] }]"
                                @chane="changeType"
                        >
                            <a-select-option value="1">普通会员</a-select-option>
                            <a-select-option value="2">企业会员</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="生日" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="this.form.getFieldValue('type')==1">
                        <a-date-picker
                                format="YYYY-MM-DD"
                                v-decorator="[
                                    'birthday',
                                    {rules: [{ required: true,message: '请选择生日'}] }
                                ]"
                        />
                    </a-form-item>
                    <a-form-item label="企业" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="this.form.getFieldValue('type')==2">
                        <a-input
                                placeholder="请输入企业名称"
                                v-decorator="['company',{rules: [{ required: true, message: '请输入企业名称'}] }]"
                        >
                        </a-input>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
                        <a-button type="primary" @click="handleRegister">
                            确定
                        </a-button>
                    </a-form-item>
                </a-form>
                <VipCard v-else></VipCard>
            </a-tab-pane>
            <a-tab-pane tab="信用充值" key="2">
                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="信用充值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请输入充值额度"
                                v-decorator="['addCredit',{rules: [{ required: true, message: '请输入充值额度'}] }]"
                        >
                        </a-input>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
                        <a-button type="primary" @click="handleChargeCredit">
                            充值
                        </a-button>
                    </a-form-item>
                </a-form>

            </a-tab-pane>
        </a-tabs>
        <a-alert message="您已经注册过了" :closable="true" type="warning" :banner="true" v-show="temp"></a-alert>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import * as message from "ant-design-vue";
    import VipCard from './vipCard'
    const moment = require('moment')
    export default {
        name: "vipRegister",
        data(){
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
                form: this.$form.createForm(this, { name: 'coordinated' }),
                temp:false,
            }
        },
        components: {
            VipCard,
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'userId',
                'vipInfo',
            ])
        },
        async mounted() {
            await this.getUserInfo()//尝试解决刷新问题
            await this.getVIPInfoByUserId(this.userId)
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'vipRegister' });
        },
       methods: {
            ...mapMutations([

            ]),
           ...mapActions([
              'chargeCredit',
               'getUserInfo',
               'registerVIP',
               'getVIPInfoByUserId',
           ]),
           changeType(v){

           },
           handleRegister(){
               this.form.validateFields((err, values) =>{
                   if(!err){

                       if(Number(this.form.getFieldValue('type'))==1&&this.vipInfo.userId!=this.userId&&this.temp!=true){
                           const data={
                               userId:this.userId,
                               viptype:"普通会员",
                               vipName:String(this.userInfo.userName),
                               birthday: moment(this.form.getFieldValue('birthday')[0]).format('YYYY-MM-DD'),
                               enterpriseName:"",
                           }
                           this.registerVIP(data)
                           this.temp=true
                       }else if(Number(this.form.getFieldValue('type'))==2&&this.vipInfo.userId!=this.userId&&this.temp!=true){
                           const data={
                               userId:this.userId,
                               viptype: "企业会员",
                               vipName:String(this.userInfo.userName),
                               birthday:"",
                               enterpriseName: String(this.form.getFieldValue('company')),
                           }
                           this.registerVIP(data)
                           this.temp=true
                       }
                       this.getVIPInfoByUserId(this.userId)
                       this.temp=true
                   }
               });
           },
           handleChargeCredit(){
                const data={
                    id: this.userId,
                    money: Number(this.form.getFieldValue('addCredit')),
                }
                this.chargeCredit(data)
               this.getUserInfo()
           }

       }
    }

</script>

<style scoped lang="less">
    .vip-wrapper {
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
    .vip-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>