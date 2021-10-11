<template>
    <div>
        <a-card hoverable style="width: 300px">
            <img
                    slot="cover"
                    alt="example"
                    :src="roomInfo.picture"
            />
            <template slot="actions" class="ant-card-actions">
    <!--            <a-icon key="setting" type="setting" />-->
    <!--            <a-icon key="edit" type="edit" />-->
    <!--            <a-icon key="ellipsis" type="ellipsis" />-->
                <a-button type="link">{{"价格："+roomInfo.price+"￥"}}</a-button>
                <a-button type="link" @click="order(roomInfo)"><a-icon type="edit"/>预定</a-button>
            </template>
            <a-card-meta :title="roomInfo.roomType" :description="roomInfo.description" >
                <a-avatar
                        slot="avatar"
                        src="https://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/guarantee.png"
                />
            </a-card-meta>
            <a-divider></a-divider>
            <div>
                <a-tag color="purple"><a-icon type="wifi"/> WIFI</a-tag>
                <a-tag color="green"><a-icon type="bell"/> 叫早</a-tag>
                <a-tag color="blue" v-if="Number(roomInfo.breakfast)==1"><a-icon type="coffee"/> 早餐</a-tag>
                <a-tag color="cyan"><a-icon type="user"/>{{" 可住"+roomInfo.peopleNum+"人"}}</a-tag>
            </div>
        </a-card>
        <OrderModal></OrderModal>
    </div>
</template>

<script>
    import OrderModal from "./orderModal";
    import {mapActions, mapGetters, mapMutations} from "vuex";
    export default {
        name: "roomCard",
        components: {OrderModal},
        props:{
            roomInfo:{}
        },
        ...mapGetters([
            'orderModalVisible',
            'vipInfo',
        ]),
        methods: {
            ...mapMutations([
                'set_orderModalVisible',
                'set_currentOrderRoom'
            ]),
            ...mapActions([

            ]),
            order(record) {
                this.set_currentOrderRoom(record)
                this.set_orderModalVisible(true)
            }
        }
    }
</script>

<style scoped>

</style>