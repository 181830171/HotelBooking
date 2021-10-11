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
        <a-divider type="vertical"></a-divider>
        <div>
            <a-card title="会员需知" :bordered="false" style="width: 350px">
                <p>普通会员在生日当月预定，则打8折</p>
                <p>企业会员预定即享受88折优惠</p>
                <p>会员同时还可享受会员等级折扣或商圈折扣的折上折</p>
                <p>信用值每增加500，会员等级增加一级</p>
                <p>会员等级折扣为level1打95折扣，level2打9折。以此类推，每提高一级折扣提高0.05</p>
                <p>会员等级折扣上限为7折</p>
            </a-card>
        </div>
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