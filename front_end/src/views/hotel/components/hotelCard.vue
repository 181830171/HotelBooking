<template>
    <div class="hotelCard" style="margin:10px ;width: 48%;display:flex;border: 1px lightgray solid">
      <a-card class="hotelImg ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
        <img
          alt="example"
          :src="hotel.picture"
          slot="cover"
          referrerPolicy="no-referrer"
        />
      </a-card>
      <a-descriptions :title="hotel.name" size="default" style="margin: 10px" :column="1">
          <a-descriptions-item label="星级" >
              <a-rate style="font-size: 15px" :value="list.indexOf(hotel.hotelStar)+1" disabled allowHalf/>
          </a-descriptions-item>
          <a-descriptions-item label="评分">
              <p v-if="hotel.rate!=0">{{hotel.rate}}分</p>
              <p v-else>该酒店暂无评分</p>
          </a-descriptions-item>
          <a-descriptions-item label="地址">
              {{hotel.address}}
          </a-descriptions-item>
        <a-descriptions-item label="最低">
          <span style="font-size:1.5em;font-weight: bold;color: #ff6666">￥{{lowestPrice}}</span>/起
        </a-descriptions-item>
      </a-descriptions>
    </div>
</template>
<script>
import {mapActions, mapMutations} from "vuex";

export default {
  name:'',
  props: {
    hotel: {},
  },
  data() {
    return{
      list: ['One','Two','Three','Four','Five'],
      lowestPrice:''
    }
  },
  mounted() {
    this.loadingData()
  },
  methods: {
    ...mapActions([
        'getLowestPrice',
    ]),
    async loadingData(){
      this.getLowestPrice(this.hotel.id).then(res=>{
        this.lowestPrice=res
      })

    }
  }
}
</script>
<style scoped lang="less">
    .hotelImg {
        margin: 10px 25px;
        min-width: 180px;
        max-height: 350px;
        img {
          height: 250px;
        }
    }
</style>

<style lang="less">
.hotelImg{
  .ant-card-body{
    padding: 12px
  }
}

.hotelCard:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>
