<template>
    <a-carousel >
<!--        <div class="carousel-item"><img src='http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/four.jpg'></div>-->
<!--        <div class="carousel-item"><img src='http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/one.jpg' ></div>-->
        <div class="carousel-item" v-for="picture in pictures" :key="picture.index"><img :src="picture.linkStr"></div>
<!--        <div class="carousel-item"><img src='http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/three.jpg' ></div>-->
<!--        <div class="carousel-item"><img src='http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/two.jpg' ></div>-->
    </a-carousel>

</template>

<script>
    export default {
        name: "picture",
        props:{
            pictures:{}
        }
    }
</script>

<style lang="less" rel="stylesheet/less" scoped>
    div{
        width:100%;
        height: 400px;
        position: relative;
    }
    img {
        width: 50%;
        height: 400px;
        position: absolute;
        left: 25%;
        /*margin-top: -25px; !* 高度的一半 *!*/
        /*margin-left: -25px; !* 宽度的一半 *!*/
    }



</style>