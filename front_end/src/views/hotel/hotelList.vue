<template>
    <div>
  <div class="hotelList">
      <a-carousel :autoplay="true">
          <div>
              <a-tooltip placement="top">
                  <template slot="title">
                      <span>点击查看详情</span>
                  </template>
                  <img style="height: 200px;margin: auto" src="../../assets/double11.jpg" @click="showDrawer(coupons[0])">
              </a-tooltip>
          </div>
          <div>
              <a-tooltip placement="top">
                  <template slot="title">
                      <span>点击查看详情</span>
                  </template>
                  <img style="height: 200px;margin:auto" src="../../assets/childrenDay.jpg" @click="showDrawer(coupons[1])">
              </a-tooltip>
          </div>
      </a-carousel>
      <a-input-search
        placeholder="输入想要搜索的酒店名称" style="width:250px;left:30%"
        size="large"
        @search="onSearch()"
        v-model="search"
        :loading="isloading"
      />
      <a-button type="default" style="margin-left: 90%;bottom:40px" @click="showHotelInList()" size="large">
          列表查看<a-icon type="unordered-list" />
      </a-button>
    <a-layout style="position:relative;bottom:20px">
        <a-layout-content style="min-width: 800px">
          <a-spin :spinning="hotelListLoading">
              <div class="searchbox" style="text-align: right">
                  <span style="width: 100%;max-width: 520px">
                      <span class="ant-input-wrapper">
                      </span>
                  </span>
              </div>
            <div class="card-wrapper">
                <HotelCard :hotel="item" v-for="item in hotelsInCurrentPage" :key="item.name" @click.native="jumpToDetails(item.id)"></HotelCard>
                <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                </div>
                <a-pagination
                  v-model="currentPage"
                  showQuickJumper
                  :total="hotelInDisplay.length"
                  :defaultPageSize="4"
                  @change="pageChange"></a-pagination>
<!--                //add on 5.10-->
                <hotelInList></hotelInList>
            </div>

          </a-spin>
      </a-layout-content>

    </a-layout>
<!--      <hotel-in-list></hotel-in-list>-->
  </div>
    <a-drawer
        title="活动详情"
        placement="right"
        :visible="visible1"
        @close="onClose"
        :width="500"
    >
        <a-descriptions
            :column="2"
            :title="coupon.couponName"
            size="middle">
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
            <a-descriptions-item label="活动范围">
                全网站酒店同享
            </a-descriptions-item>
        </a-descriptions>
    </a-drawer>
    </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import HotelInList from './components/hotelInList'
import { mapGetters, mapActions, mapMutations } from 'vuex'
import * as couponMethod from '@/api/coupon'


export default {
  name: 'home',
  components: {
      //add on 5.10
      HotelInList,
    HotelCard,
    // HotelInList
  },
  data(){
    return{
      currentPage:1,
      emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
      hotelInDisplay:[],
      isSearch:false,
      search:'',
      isloading:false,
      visible1:false,
      coupons:[],
      coupon:{
          couponName:'',
          description:'',
          targetMoney:'',
          startTime:'',
          endTime:'',
          discountMoney:'',
          discount:'',
      }
    }
  },

  async mounted() {
    await this.getHotelList()
    await this.getCoupons()
    this.hotelInDisplay=this.hotelList
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading',
      //add on 5.10
      'hotelInListVisible',

    ]),
    hotelsInCurrentPage(){
      //this.hotelInDisplay = this.hotelList.slice(this.currentPage*4-4,this.currentPage*4)
      if((this.isSearch || this.search) && this.hotelInDisplay.length!==0){
          return this.hotelInDisplay.slice(this.currentPage*4-4,this.currentPage*4)
      }
      return this.hotelList.slice(this.currentPage*4-4,this.currentPage*4)
    }
  },
  methods: {
    ...mapMutations([
      'set_hotelListParams',
      'set_hotelListLoading',
      //add on 5.10
      'set_hotelInListVisible',

    ]),
    ...mapActions([
      'getHotelList',

    ]),
      showDrawer(coupon) {
          this.visible1 = true;
          this.coupon=coupon;
      },
      onClose() {
          this.visible1 = false;
      },
      async getCoupons(){
        const res = await couponMethod.hotelAllCouponsAPI(-1)
          if(res){
              this.coupons=res
          }
      },
    pageChange(page, pageSize) {
      const data = {
        pageNo: page - 1
      }
      this.set_hotelListParams(data)
      this.set_hotelListLoading(true)
      this.getHotelList()
    },
    jumpToDetails(id){
      this.$router.push({ name: 'hotelDetail', params: { hotelId: id}})
    },
      //add on 5.10
    showHotelInList(){
      this.getHotelList()
      this.set_hotelInListVisible(true)
    },
    onSearch(){
        this.isloading = true
        if(this.search!==''){
            this.hotelInDisplay=[]
            for(let i=0;i<this.hotelList.length;i++){
                if(this.hotelList[i].name.indexOf(this.search)>=0){
                    // this.hotelList[i].lowestPrice=this.getPrice(this.hotelList[i].id)
                    this.hotelInDisplay.push(this.hotelList[i]);
                }
            }
            if(this.hotelInDisplay.length===0){
                this.$message.error('未找到符合的酒店')
                this.hotelInDisplay = this.hotelList
                this.isSearch=false
            }else{
                this.isSearch=true
            }
            this.currentPage=1;
        }else{
            this.hotelInDisplay=this.hotelList
            this.isSearch=false
        }
        setTimeout(()=>{
            this.isloading=false
        },1000)

    }
  }

}
</script>
<style scoped lang="less">
  .hotelList {
    text-align: center;
    padding: 50px 0;
    .emptyBox {
      height: 0;
      margin: 10px 10px
    }
    .card-wrapper{
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      flex-grow: 3;
      //min-height: 800px
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
  }
</style>
