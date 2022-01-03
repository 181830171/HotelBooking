<template>
    <div class="hotelCard" style="margin:10px ;width: 45%;height:300px;display:flex;border: 1px lightgray solid">
      <div class = "imgBox">
        <img
            alt="example"
            :src="hotel.picture"
        />
      </div>
      <div class="descriptions">
        <a-descriptions :title="hotel.name" size="default" style="margin: 10px;" :column="1">
          <a-descriptions-item label="星级">
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
    .hotelCard {
      position: relative;
      border-radius: 5px;
    }
    .imgBox {
      width: 100%;
      overflow: hidden;
      border-radius: 5px;
    }
    .imgBox img {
      width: 100%;
    }

    .hotelCard .imgBox img{
      transition: transform 1s;
    }
    .hotelCard:hover .imgBox img{
      transform: scale(1.2);
    }
    // 遮罩盒子
    .descriptions {
      position: absolute;
      top:10px;
      right:10px;
      left:10px;
      bottom:10px;
      background: rgba(0,0,0,0.4);
      transform: scale(1);
      transition: transform .7s;
      border-radius: 5px;
    }
    //.hotelCard:hover .descriptions {
    //  transform: scale(1);
    //}
    .descriptions ::v-deep .ant-descriptions-title {
      margin-bottom: 20px;
      color:rgba(255,255,255,0.85);
      font-weight: normal;
      font-size: 22px;
      line-height: 1.5;
    }
    .descriptions ::v-deep .ant-descriptions-item-label{
      color:#fff;
      font-size: 11px;
    }
    .descriptions ::v-deep .ant-descriptions-item-content{
      color:#fff;
      font-size: 11px;
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
