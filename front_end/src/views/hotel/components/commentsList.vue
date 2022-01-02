<template>
    <a-layout style="padding: 0px 20px">
        <a-comment :author="'用户Id: '+comment.userId"
                   :content="'评论内容: '+comment.userEvaluation"
                   :datetime="comment.createDate"
                   avatar="https://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/avatar.png">
            {{"描述相符"}}<a-rate :value="value[0]"  allow-half  disabled  />
            {{"服务态度"}}<a-rate :value="value[1]"  allow-half  disabled />
            {{"卫生环境"}}<a-rate :value="value[2]"  allow-half  disabled />
        </a-comment>
        <a-divider type="horizontal" ></a-divider>
    </a-layout>
</template>
<script>
export default {
    name: 'commentsList',
    props:{
      comment:{}
    },
    components: {

    },
    data() {
        return{
            value:[this.comment.descriptionScore,this.comment.serviceScore,this.comment.environmentScore]
        }
    },



}
</script>
