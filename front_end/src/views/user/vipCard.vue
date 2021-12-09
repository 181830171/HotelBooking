<template xmlns:white-space="">
    <div class="card-wrapper">
        <a-card hoverable style="width: 300px">
            <img
                    slot="cover"
                    alt="example"
                    src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
            />
            <template slot="actions" class="ant-card-actions">
                <a-button type="link"><a-icon type="tag" />会员等级: {{vipInfo.vipLevel}}</a-button>
                <a-button type="link" v-if="vipInfo.vipLevel<=6"><a-icon type="transaction" />会员折扣: {{vipInfo.vipDiscount}}</a-button>

            </template>
            <a-card-meta white-space:pre v-if="vipInfo.viptype=='普通会员'" :title="vipInfo.viptype+' 生日:'+vipInfo.birthday" description="可享受生日折扣，会员等级折扣，商圈折扣的折上折">
                <a-avatar
                        slot="avatar"
                        src="https://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/avatar.png"
                />
            </a-card-meta>
            <a-card-meta v-else :title="vipInfo.viptype+' 企业:'+vipInfo.enterpriseName" description="可享受企业折扣，会员等级折扣，商圈折扣的折上折" >
                <a-avatar
                        slot="avatar"
                        src="https://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/avatar.png"
                />

            </a-card-meta>
        </a-card>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "vipCard",
        computed: {
            ...mapGetters([
                'userId',
                'vipInfo',
            ])
        },
        async mounted() {
            await this.getVIPInfoByUserId(this.userId)
        },
        async beforeUpdate() {
          await this.getVIPInfoByUserId(this.userId)
        },
      methods: {
            ...mapMutations([]),
            ...mapActions([
                'getVIPInfoByUserId',
            ]),
        }
    }
</script>

<style scoped>
    .card-wrapper{
        display: flex;
        /*justify-content: space-around;*/
        justify-content: flex-start;
        flex-wrap: wrap;
        flex-grow: 3;
        /*min-height: 800px*/
    }
</style>
