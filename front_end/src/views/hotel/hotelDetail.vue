<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 250px">
                        <img
                            alt="example"
                            :src="currentHotelInfo.picture"
                            slot="cover"
                            referrerPolicy="no-referrer"
                            height="270px"
                            />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.bizRegion">
                            <span class="label">酒店商圈:</span>
                            <span class="value">{{ currentHotelInfo.bizRegion.charAt(6) }}区</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址：</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span> 
                            <span class="value">{{ currentHotelInfo.rate }}分</span>
                        </div>
                        <div class="items" v-else>
                            <span class="label">评分：</span>
                            <span class="value">该酒店暂未被评价过</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级：</span>
                            <a-rate style="font-size: 15px" :value="list.indexOf(currentHotelInfo.hotelStar)+1" disabled />
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span> 
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
<!--                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>-->
                        <div class="card-wrapper">
                            <RoomCard :roomInfo="room" v-for="room in currentHotelInfo.rooms" :key="room.index"></RoomCard>
                        </div>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">
                        <Picture :pictures="currentPictureList"></Picture>
                    </a-tab-pane>
                    <a-tab-pane tab="历史评价" key="3">
                        <CommentList :comment="Comment" v-for="Comment in hotelComments" :key="Comment.index"></CommentList>
                    </a-tab-pane>
                    <a-tab-pane tab="我的历史订单" key="4">
                        <userOrderList></userOrderList>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import CommentList from './components/commentsList'
import userOrderList from "./components/userOrderList";
import Picture from "./components/picture";
import RoomCard from "./components/roomCard";
export default {
    name: 'hotelDetail',
    components: {
        RoomCard,
        Picture,
        //RoomList,
        CommentList,
        userOrderList,
    },
    data() {

        return{
            emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
            list: ['One','Two','Three','Four','Five'],
        }
        }
    ,
    computed: {
        ...mapGetters([
            'currentHotelInfo',
            'hotelComments',
            'userOrderList',
            'currentPictureList',
            'userId',
        ])
    },
    mounted() {
        this.set_currentHotelId(Number(this.$route.params.hotelId))
        this.getHotelById()
        this.getCommentsByHotelId()
        this.getPictures()
        this.getVIPInfoByUserId(this.userId)
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        this.getCommentsByHotelId()
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
        ]),
        ...mapActions([
            'getHotelById',
            'getCommentsByHotelId',
            'getPictures',
            'getVIPInfoByUserId',
        ]),
    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
        .card-wrapper{
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            /*min-height: 800px*/
        }
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>