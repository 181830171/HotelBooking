<template>
  <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img
      alt="example"
      :src="hotel.picture"
      slot="cover"
      referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta :title="hotel.name">
      <template slot="description">星级：
        <a-rate style="font-size: 15px" :value="list.indexOf(hotel.hotelStar)+1" disabled allowHalf/>
        <p v-if="hotel.rate!=0">评分： {{hotel.rate}}分</p>
        <p v-else>该酒店暂无评分</p>
      </template>
    </a-card-meta>
    </a-tooltip>
  </a-card>
</template>
<script>
export default {
  name:'',
  props: {
    hotel: {},
  },
  data() {
    return{
      list: ['One','Two','Three','Four','Five']
    }
  },
}
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 10px 10px;
        min-width: 180px;
        max-height: 350px;
        img {
          height: 250px;
        }
    }
</style>

<style lang="less">
.hotelCard{
  .ant-card-body{
    padding: 12px
  }
}

.ant-card-hoverable:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>