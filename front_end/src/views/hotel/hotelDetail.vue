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
                    <a-tab-pane tab="优惠活动" key="2">
                        <a-collapse v-if="hotelCoupons.length>0" v-model="activeKey">
                            <a-collapse-panel v-for="(coupon,index) in hotelCoupons" :key="currentHotelInfo.id+' '+index" :header="coupon.couponName">
                                <a-descriptions title="活动详情" size="middle">
                                    <a-descriptions-item label="优惠介绍">
                                        {{coupon.description}}
                                    </a-descriptions-item>
                                    <a-descriptions-item label="优惠门槛">
                                        <span v-if="coupon.targetMoney===0">无门槛</span>
                                        <span v-else>消费金额满{{coupon.targetMoney}}</span>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="折扣/优惠">
                                        消费总额<span v-if="coupon.discount===0">减{{coupon.discountMoney}}元</span>
                                        <span v-else>打{{coupon.discount*10}}折</span>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="有效期" v-if="coupon.startTime">
                                        {{coupon.startTime.substring(0,10) + '~' + coupon.endTime.substring(0,10)}}
                                    </a-descriptions-item>
                                </a-descriptions>
                            </a-collapse-panel>
                        </a-collapse>
                        <a-empty v-else description="该酒店暂无优惠活动"/>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="3">
                        <div v-if="currentPictureList.length>0">
                            <Picture :pictures="currentPictureList"></Picture>
                        </div>
                        <a-empty v-else description="该酒店暂未上传图片"/>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店评价" key="4">
                        <div v-if="hotelComments.length>0">
                            <CommentList :comment="Comment" v-for="Comment in hotelComments" :key="Comment.index"></CommentList>
                        </div>
                        <a-empty v-else description="该酒店暂无评价"/>
                    </a-tab-pane>
                    <a-tab-pane tab="我的历史订单" key="5">
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
import * as CouponMethod from "@/api/coupon"
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
            hotelCoupons:[
                {
                    couponName:'',
                    description:'',
                }
            ],
            activeKey:'1',
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
        this.getCoupons()
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
        async getCoupons(){
            const res=await CouponMethod.hotelAllCouponsAPI(this.$route.params.hotelId)
            if(res){
                this.hotelCoupons=res
                console.log(res)
            }
        },

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
