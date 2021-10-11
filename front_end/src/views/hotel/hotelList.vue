<template>
  <div class="hotelList">
      <a-button type="default" style="margin-left: 90%" @click="showHotelInList()">
          列表查看<a-icon type="search"></a-icon>
      </a-button>
    <a-layout>
        <a-layout-content style="min-width: 800px">
          <a-spin :spinning="hotelListLoading">
              <div class="searchbox" style="text-align: right">
                  <span style="width: 100%;max-width: 520px">
                      <span class="ant-input-wrapper">
                      </span>
                  </span>
              </div>
            <div class="card-wrapper">
                <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                </div>
                <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
<!--                //add on 5.10-->
                <hotelInList></hotelInList>
            </div>

          </a-spin>
      </a-layout-content>

    </a-layout>
<!--      <hotel-in-list></hotel-in-list>-->
  </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import HotelInList from './components/hotelInList'
import { mapGetters, mapActions, mapMutations } from 'vuex'


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
      emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
    }
  },
    async mounted() {
    await this.getHotelList()
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading',
      //add on 5.10
      'hotelInListVisible',

    ])
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
      min-height: 800px
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
  }
</style>