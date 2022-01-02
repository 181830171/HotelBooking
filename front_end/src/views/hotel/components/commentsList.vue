<template>
    <a-layout style="padding: 0px 20px">
        <a-comment :author="'用户名: 匿名用户'"
                   :content="'评论内容: '+comment.userEvaluation"
                   :datetime="comment.createDate"
                   avatar="https://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/avatar.png">
            <template slot="actions">
                <span key="comment-basic-like">
                    <a-tooltip title="Like">
                        <a-icon type="like" :theme="action === 'liked' ? 'filled' : 'outlined'" @click="like"/>
                    </a-tooltip>
                    <span style="padding-left: 8px;cursor: auto">
                        {{ likes }}
                    </span>
                </span>
                <span key="comment-basic-dislike">
                    <a-tooltip title="Dislike">
                        <a-icon
                            type="dislike"
                            :theme="action === 'disliked' ? 'filled' : 'outlined'"
                            @click="dislike"
                        />
                    </a-tooltip>
                    <span style="padding-left: 8px;cursor: auto">
                      {{ dislikes }}
                    </span>
                </span>
            </template>
            <div class="rate-comp">
                <span>
            描述相符:<a-rate :value="value[0]" allow-half disabled/>
                </span>
                <span>
            服务态度:<a-rate :value="value[1]" allow-half disabled/>
                </span>
                <span>
            卫生环境:<a-rate :value="value[2]" allow-half disabled/>
                </span>
            </div>
        </a-comment>
        <a-divider type="horizontal"></a-divider>
    </a-layout>
</template>
<script>
export default {
    name: 'commentsList',
    props: {
        comment: {}
    },
    components: {},
    data() {
        return {
            value: [this.comment.descriptionScore, this.comment.serviceScore, this.comment.environmentScore],
            likes:Math.round(Math.random()*100),
            dislikes:Math.round(Math.random()*10),
            action:'none'
        }
    },
    methods:{
        like(){
            if(this.action==='disliked'){
                this.dislikes--
                this.likes++
                this.action='liked'
            }else if(this.action==='none'){
                this.likes++
                this.action='liked'
            }else{
                this.action='none'
                this.likes--
            }
        },
        dislike(){
            if(this.action==='liked'){
                this.likes--
                this.dislikes++
                this.action='disliked'
            }else if(this.action==='none'){
                this.dislikes++
                this.action='disliked'
            }else{
                this.action='none'
                this.dislikes--
            }
        }
    }


}
</script>
<style>
.rate-comp span {
    margin-right: 30px;
}
</style>
