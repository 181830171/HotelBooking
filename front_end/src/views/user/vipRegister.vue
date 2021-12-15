<template>
    <div class="vip-wrapper">
        <a-tabs>
            <a-tab-pane tab="注册会员" key="1" style="display: flex">
              <a-form :form="form" style="width:48%;margin-top: 30px" v-if="vipInfo.viptype==undefined">
                <a-form-item label="会员类型" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 1  }">
                  <a-select
                    style="width: 83.3%"
                          v-decorator="[
                              'type',
                              { rules: [{ required: true, message: '请选择会员类型'}] }]"
                          @chane="changeType"
                  >
                      <a-select-option value="1">普通会员</a-select-option>
                      <a-select-option value="2">企业会员</a-select-option>
                  </a-select>
                  <a-popover title="会员类型" placement="bottomLeft">
                    <template slot="content">
                      <p>合作企业的员工可以注册企业会员，详情请咨询贵公司</p>
                      <p>所有用户皆可注册普通会员，会员等级与信用值相关</p>
                    </template>
                    <a-icon type="question-circle" style="margin-left: 10px"/>
                  </a-popover>
                </a-form-item>
                <a-form-item label="生日" :label-col="{ span: 6 }" :wrapper-col="{ span: 10, offset: 1 }" v-if="this.form.getFieldValue('type')==1">
                  <a-date-picker
                          format="YYYY-MM-DD"
                          v-decorator="[
                              'birthday',
                              {rules: [{ required: true,message: '请选择生日'}] }
                          ]"
                  />
                </a-form-item>
                <a-form-item label="企业" :label-col="{ span: 6 }" :wrapper-col="{ span: 10, offset: 1 }" v-if="this.form.getFieldValue('type')==2">
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
              <VipCard ref="vipCard" v-else></VipCard>
              <a-divider type="vertical"></a-divider>
              <div style="max-width: 48%;margin-bottom: 20px">
                <a-card title="会员需知" :bordered="false" style="width: 100%">
                  <p>普通会员在生日当月预定，则打8折</p>
                  <p>企业会员预定即享受88折优惠</p>
                  <p>会员同时还可享受会员等级折扣或商圈折扣的折上折</p>
                  <p>信用值每增加500，会员等级增加一级</p>
                  <p>会员等级折扣为level1打95折扣，level2打9折。以此类推，每提高一级折扣提高0.05</p>
                  <p>会员等级折扣上限为7折</p>
                </a-card>
              </div>
            </a-tab-pane>
            <a-tab-pane tab="信用充值" key="2">
                <a-form :form="form" style="margin-top: 30px" :aria-colspan="17">
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="信用充值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                      <a-input
                        style="width: 80%"
                                placeholder="请输入充值额度"
                                v-decorator="['addCredit',{rules: [{ required: true, message: '请输入充值额度'}] }]"
                      >
                      </a-input>
                      <a-popover title="充值规则" placement="bottomLeft">
                        <template slot="content">
                          <p>距离入住时间少于一天退房会导致信用降低（降低数值等于订单金额）</p>
                          <p>充值信用可以提升信用值</p>
                          <p>输入充值的金额，系统将为你自动提升信用值，我们将在您入住时收取这部分金额</p>
                          <p>1￥等价于100信用值</p>
                        </template>
                        <a-icon type="question-circle" style="margin-left: 10px"/>
                      </a-popover>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
                        <a-button type="primary" @click="handleChargeCredit">
                            充值
                        </a-button>
                    </a-form-item>
                </a-form>

            </a-tab-pane>
        </a-tabs>
        <a-alert message="您已经注册过了,请刷新后查看详情" :closable="true" type="warning" :banner="true" v-show="temp"></a-alert>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
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
           async handleRegister(){
               await this.form.validateFields((err, values) =>{
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
               this.$forceUpdate()
           },
           async handleChargeCredit(){
                const data={
                    id: this.userId,
                    money: Number(this.form.getFieldValue('addCredit')),
                }
                await this.chargeCredit(data)
               await this.getUserInfo()
             this.$refs.vipCard.$forceUpdate()
           }

       }
    }

</script>

<style scoped lang="less">
    .vip-wrapper {
        padding: 25px;
        margin: 50px 60px;
        background-color: rgba(255,255,255,0.9);
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
