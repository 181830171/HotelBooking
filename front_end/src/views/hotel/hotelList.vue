<template>
    <div style="padding: 10px 60px;">
  <div class="hotelList">
      <a-tabs class="tabs" default-active-key="1">
          <a-tab-pane key="1" tab="酒店搜索">
              <div class="searchBox">
                  <baidu-map :center="centerPoint" :zoom="zoom" @ready="handler" style="margin-left:25px;width: 550px;height: 320px">
                      <bm-marker :position="centerPoint">
                          <bm-label
                              :content="pointerName"
                              :label-style="{color:'red',fontSize : '12px'}"
                              :offset="{width: -35, height: 30}"/>
                      </bm-marker>
                      <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
                      <!--          <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :isOpen="true"></bm-overview-map>-->
                  </baidu-map>
                  <a-form-model :loading="isloading" v-model="searchForm" :label-col="{span:7}" :wrapper-col="{span:16}">
                      <a-form-model-item ref="addr" label="区域范围" >
                          <a-cascader v-model="searchForm.region" :options="options" change-on-select placeholder="请选择区域" />
                      </a-form-model-item>
                      <a-form-model-item ref="addr" label="地址" >
                          <a-input v-model="searchForm.addr" placeholder="请输入详细地址" />
                      </a-form-model-item>
                      <a-form-model-item ref="name" label="酒店关键词">
                          <a-input v-model="searchForm.name" placeholder="请输入酒店关键词"
                          ></a-input>
                      </a-form-model-item>
                      <a-form-model-item label="入住时间">
                          <a-range-picker @change="(val)=>{console.log(val)}" :placeholder="['开始日期','结束日期']" />
                      </a-form-model-item>
                      <a-form-model-item :wrapper-col="{ span: 10, offset: 4 }">
                          <a-button icon="search" type="primary" @click="onSearch" :loading="isloading">
                              搜索
                          </a-button>
                      </a-form-model-item>
                  </a-form-model>
              </div>
          </a-tab-pane>
          <a-tab-pane key="2" tab="优惠活动" force-render>
              <a-carousel :autoplay="true">
                  <div>
                      <a-tooltip placement="top" effect="light">
                          <template slot="title">
                              <span>点击查看详情</span>
                          </template>
                          <img style="height: 200px;margin: auto" src="../../assets/double11.jpg" @click="showDrawer(coupons[0])">
                      </a-tooltip>
                  </div>
                  <div>
                      <a-tooltip placement="top" effect="light">
                          <template slot="title">
                              <span>点击查看详情</span>
                          </template>
                          <img style="height: 200px;margin:auto" src="../../assets/childrenDay.jpg" @click="showDrawer(coupons[1])">
                      </a-tooltip>
                  </div>
              </a-carousel>
          </a-tab-pane>
      </a-tabs>
      <a-button type="default" style="margin-left: 90%;bottom:40px" @click="showHotelInList()" size="large">
          列表查看<a-icon type="unordered-list" />
      </a-button>
    <a-layout style="position:relative;bottom:20px;border-radius: 10px">
        <a-layout-content style="min-width: 800px;">
          <a-spin :spinning="hotelListLoading">
              <div style="text-align: right">
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
    <a-modal
        title="活动详情"
        placement="right"
        :visible="visible1"
        cancelText="取消"
        okText="确认"
        @cancel="onClose"
        @ok="onClose"
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
    </a-modal>
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
      searchForm:{
          region:[],
          addr:'',
          name:'',
      },
        options:[
            {
                value: '南京市',
                label: '南京市',
                children: [
                    {
                        value: '鼓楼区',
                        label: '鼓楼区',
                    },{
                        value: '玄武区',
                        label: '玄武区',
                    },{
                        value: '秦淮区',
                        label: '秦淮区',
                    },{
                        value: '建邺区',
                        label: '建邺区',
                    },
                    {
                        value: '栖霞区',
                        label: '栖霞区',
                    },
                    {
                        value: '江宁区',
                        label: '江宁区',
                    }

                ],
            },
      ],
      // search:'',
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
      },
      myGeo:'',
      myMap:null,
      centerPoint:'南京',
      pointerName:'南京',
      zoom:12,
    }
  },

  async mounted() {
      await this.getUserInfo()
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
      if((this.isSearch || this.searchForm.name || this.searchForm.addr || this.searchForm.region) && this.hotelInDisplay.length!==0){
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
        'getUserInfo'

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
        const _this=this
        this.hotelInDisplay = []
        if(this.searchForm.addr!=='' || this.searchForm.region.length>0){
            const searchAddr=this.searchForm.region.join()+this.searchForm.addr
            this.myGeo.getPoint(searchAddr, (point1)=>{
                if(point1) {
                    this.centerPoint=point1
                    this.pointerName=searchAddr
                    this.zoom=15
                    console.log('point1', point1)
                    for (let i = 0; i < this.hotelList.length; i++) {
                        if (this.hotelList[i].name.indexOf(_this.searchForm.name) >= 0) {
                            this.myGeo.getPoint(this.hotelList[i].address, (point2) => {
                                if (point2) {
                                    const distance = (_this.myMap.getDistance(point1, point2) / 1000).toFixed(2)
                                    console.log(this.hotelList[i].address,point2,distance)
                                    if(distance<=3){
                                        _this.hotelInDisplay.push(_this.hotelList[i]);
                                    }
                                } else {
                                    alert('地址没有解析到结果！');
                                }
                            })
                        }
                    }
                    setTimeout(()=>{
                        if(this.hotelInDisplay.length===0){
                            this.$message.error('未找到符合的酒店')
                            this.hotelInDisplay = this.hotelList
                            this.isSearch=false
                        }else{
                            this.isSearch=true
                        }
                        this.currentPage=1;
                        this.isloading=false
                    },1000)
                }else {
                    alert('地址没有解析到结果！');
                }
            })
        }else if(this.searchForm.name!==''){
            this.hotelInDisplay=[]
            for(let i=0;i<this.hotelList.length;i++){
                if(this.hotelList[i].name.indexOf(this.searchForm.name)>=0){
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
            setTimeout(()=>{
                this.isloading=false
            },1000)
        }else{
            this.centerPoint='南京'
            this.pointerName='南京'
            this.zoom=12
            this.hotelInDisplay=this.hotelList
            this.isSearch=false
            setTimeout(()=>{
                this.isloading=false
            },1000)
        }

    },
      handler({BMap, map}) {
          this.myGeo=new BMap.Geocoder()
          this.myMap=map
      },
  }

}
</script>
<style scoped lang="less">

  .hotelList {
    text-align: center;
    padding: 50px 0;
    border-radius: 10px;
    .tabs {
        .searchBox {
            display: flex;
            justify-content: space-around;
            padding: 25px;
            margin: 0px 50px;
        }
        background-color: rgba(255, 255, 255, 0.9);
    }
      .emptyBox {
      height: 0;
      margin: 10px 10px
    }
    .card-wrapper{
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      flex-grow: 3;
      margin: 15px 10px;
      //min-height: 800px
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
  }
</style>
