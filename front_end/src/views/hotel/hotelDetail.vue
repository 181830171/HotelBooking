<template>
<!--    <a-layout>-->
<!--        <a-layout-content>-->
            <div class="hotelDetailCard">
                <div class="hotel-info">
                    <div style="display: flex;justify-content: flex-start;width: 73%">
                        <a-card style="width: 250px">
                            <img
                                alt="example"
                                :src="currentHotelInfo.picture"
                                slot="cover"
                                referrerPolicy="no-referrer"
                                height="320px"
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
                                <span class="label">简介:</span>
                                <span class="value">{{ currentHotelInfo.description }}</span>
                            </div>
                        </div>
                    </div>
                    <baidu-map :center="point" :zoom="18" @ready="handler" style="margin-left:10px;width: 460px;height: 320px">
                        <bm-marker :position="point">
                            <bm-label
                                :content="hotelAddress"
                                :label-style="{color:'red',fontSize : '12px'}"
                                :offset="{width: -35, height: 30}"/>
                        </bm-marker>
                        <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
                        <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :isOpen="true"></bm-overview-map>
                    </baidu-map>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
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
                    <a-tab-pane key="4">
                        <div slot="tab">
                            酒店评价
                            <a-badge :count="hotelComments.length" :show-zero="true" :number-style="{ backgroundColor: '#87d068c8' }" />
                        </div>
                        <div v-if="hotelComments.length>0" style="border: 1px solid lightgray">
                            <CommentList :comment="Comment" v-for="Comment in hotelComments.slice(currentCommentPage*4-4,Math.min(currentCommentPage*4,hotelComments.length))" :key="Comment.id"></CommentList>
                            <a-pagination style="margin-top: 10px;float: right" v-model="currentCommentPage" :defaultPageSize="4" :total="hotelComments.length" show-less-items showQuickJumper />
                        </div>
                        <a-empty v-else description="该酒店暂无评价"/>
                    </a-tab-pane>
                    <a-tab-pane tab="我的历史订单" key="5">
                        <userOrderList></userOrderList>
                    </a-tab-pane>
                </a-tabs>
            </div>
<!--        </a-layout-content>-->
<!--    </a-layout>-->
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
// import myMap from './components/map'
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
        // myMap
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
            point: {lat:39.915,lng:116.404},
            myGeo:{},
            currentCommentPage:1,
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
        ]),
        hotelAddress(){
            return this.currentHotelInfo.address
        }
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
    watch:{
        hotelAddress:{
            handler(val) {
                this.myGeo.getPoint(val, (point) => {
                    if (point) {
                        console.log(point)
                        this.point = point
                    } else {
                        alert('您选择的地址没有解析到结果！');
                    }
                })
            },
            immediate:true
        }
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
        handler({BMap, map}) {
            this.myGeo=new BMap.Geocoder()
            this.myGeo.getPoint(this.currentHotelInfo.address, (point)=>{
                if(point){
                    console.log(point)
                    this.point=point
                }else{
                    alert('您选择的地址没有解析到结果！');
                }
            })
        }

    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding:25px;
        margin: 50px 60px;
        background-color: rgba(255,255,255,0.9);
        .card-wrapper{
            margin-bottom: 20px;
            display: flex;
            justify-content: space-evenly;
            align-content: space-evenly;
            flex-wrap: wrap;
            flex-grow: 1;
        }
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: space-around;

        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            width: 60%;
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
