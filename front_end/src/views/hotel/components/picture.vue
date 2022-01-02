<template>
    <a-carousel >
        <div class="carousel-item">
            <div class="my-picture-comp" >
                <img :src="pictures[0].linkStr">
                <img :src="pictures[1].linkStr">
                <img :src="pictures[2].linkStr">
                <img :src="pictures[3].linkStr">
            </div>
        </div>
        <div class="carousel-item" >
            <div class="my-picture-comp" >
                <img :src="pictures[4].linkStr">
                <img :src="pictures[5].linkStr">
            </div>
        </div>
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
.my-picture-comp{
    margin-left: auto;
    margin-right: auto;
    display: flex;
    width: fit-content;
    img{
        margin: 10px 10px;
        border: solid 1px lightgray;
        transform: scale(1);
    }
    img:hover{
        box-shadow: 5px 5px 10px rgba(0,0,0,.2);
        transform: scale(1.01);
        transition: transform 0.8s;
    }
}
    //div.carousel-item{
    //    display: flex;
    //    flex-direction: row;
    //    img{
    //        width: 33%;
    //    }
    //    //width:100%;
    //    //height: 400px;
    //    //position: relative;
    //}
    //img {
    //    width: 33%;
    //    /*margin-top: -25px; !* 高度的一半 *!*/
    //    /*margin-left: -25px; !* 宽度的一半 *!*/
    //}



</style>
