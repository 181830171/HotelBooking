<template>
    <div class="common-user-list">
        <a-table
            :columns="columns"
            :dataSource="userList"
            bordered
        >
        </a-table>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    const columns=[
        {
            title:'用户名',
            dataIndex:'userName',
        },
        {
            title:'邮箱',
            dataIndex:'email',
        },
        {
            title:'手机号',
            dataIndex:'phoneNumber',
        },
        {
            title:'信用',
            dataIndex:'credit',
        }
    ]
    export default {
        name: "commonUserList",
        data(){
            return{
                columns,
            }
        },
        computed:{
            ...mapGetters([
                'userList',
            ])
        },
        async mounted(){
          await this.getUserList()
        },
        methods:{
            ...mapActions([
                'getUserList',
            ])
        }
    }
</script>

<style scoped>

</style>