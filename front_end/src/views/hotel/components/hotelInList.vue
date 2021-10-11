<template>
    <a-modal
            :visible="hotelInListVisible"
            title="酒店列表"
            cancelText="取消"
            okText="确认"
            @cancel="confirm"
            @ok="confirm"
            width=1100px
    >
        <a-input
                v-decorator="[
                        'search',
                        { rules: [{required: true, message: '请输入酒店名称', }] }
                    ]"
                style="padding-right: 10px;width: 200px;"
                placeholder="请输入酒店名称"
                v-model="search"
        />
        <a-button @click="handleSearch">搜索</a-button>

<!--        <a-select-->
<!--                v-decorator="[-->
<!--                    'type',-->
<!--                    { rules: [{ required: true, message: '请选择类型' }] }]"-->
<!--                @change="changeType"-->
<!--                style="width: 200px;margin-left: 40px"-->
<!--                placeholder="请选择星级"-->
<!--                :allowClear="selectClear"-->

<!--        >-->
<!--            <a-select-option value="1">全部</a-select-option>-->
<!--            <a-select-option value="2">五星级</a-select-option>-->
<!--            <a-select-option value="3">四星级</a-select-option>-->
<!--            <a-select-option value="4">三星级</a-select-option>-->
<!--            <a-select-option value="5">二星级</a-select-option>-->
<!--            <a-select-option value="6">一星级</a-select-option>-->
<!--        </a-select>-->
<!--        <a-input-->
<!--                v-decorator="[-->
<!--                        'searchRate',-->
<!--                        { rules: [{required: true, message: '请输入评分', }] }-->
<!--                    ]"-->
<!--                style="padding-right: 10px;width: 200px;margin-left: 60px"-->
<!--                placeholder="请输入最低评分"-->
<!--                v-model="searchRate"-->
<!--        />-->
<!--        <a-button @click="searchByHotelRate">搜索</a-button>-->

        <a-button @click="hotelLoading" style="margin-left: 60px">重置</a-button>
        <a-switch className="s" @change="searchOrdered" un-checked-children="曾经预定" checked-children="全部" style="margin-left: 30px" :checked="checkstate">
        </a-switch>
        <a-divider type="horizontal" :dashed="true" ></a-divider>
        <a-table
                :columns="rows"
                :dataSource="hotelSearchList"
                bordered
                style="width: 1000px"
        >
            <span slot="bizRegion" slot-scope="text">{{text}}</span>
            <span slot="rate" slot-scope="text">{{text}}</span>
            <span slot="action" slot-scope="record">
                        <a-button type="link" size="small" @click="checkHotel(record.id)">查看酒店</a-button>
            </span>
            <span slot="ordered" slot-scope="text">
<!--                <p v-if="record.name in usersHotel">已预定</p>-->
<!--                <p v-if="!record.name in usersHotel">未预定</p>-->
                <a-tag color="green" v-if="checkwhetherin(text.name)==true">曾经预定</a-tag>
                <a-tag color="cyan" v-if="checkwhetherin(text.name)==false">从未预定</a-tag>
            </span>

        </a-table>

    </a-modal>
</template>

<script>import {mapActions, mapGetters, mapMutations} from "vuex";
const list=['One','Two','Three','Four','Five'];
const rows=[
    {
      title: '编号',
      dataIndex: 'id',
    },
    {
        title: '酒店名称',
        dataIndex: 'name',

    },
    {
        title: '酒店评分',
        sorter:(a,b)=>a.rate-b.rate,//按评分排序
        dataIndex: 'rate',
        filters: [{ text:"4分以上", value: 4 }, { text: '3分以上', value: 3 }, { text: '2分以上', value: 2}],
        onFilter: (value, record) => record.rate>value,
        scopedSlots: { customRender: 'rate' }
    },
    {
        title: '酒店星级',
        sorter:(a,b)=>list.indexOf(a.hotelStar)-list.indexOf(b.hotelStar),//按星级排序
        dataIndex: 'hotelStar',
        filters: [{ text: '五星级', value: 'Five' }, { text: '四星级', value: 'Four' }, { text: '三星级', value: 'Three' },{text:'二星级',value:'Two'},{text:'一星级',value:'One'}],
        onFilter: (value, record) => record.hotelStar.includes(value),
        scopedSlots: { customRender: 'hotelStar' }

    },
    {
        title: '附近商圈',
        filters: [{ text: 'RegionA', value: 'RegionA' }, { text: 'RegionB', value: 'RegionB' }, { text: 'RegionC', value: 'RegionC' },{text:'RegionD',value:'RegionD'},{text:'RegionE',value:'RegionE'}],
        onFilter: (value, record) => record.bizRegion.includes(value),
        dataIndex: 'bizRegion',
        scopedSlots:{customRender:'bizRegion'}
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' }
    },
    {
        title:'是否预定过',
        key:'ordered',
        scopedSlots:{customRender:'ordered'}
    }
]
    export default {
        name: "hotelInList",
        data(){
            return{
                formItemLayout: {
                    width:30,
                    labelCol: {
                        xs: { span: 16},
                        sm: { span: 15 },
                    },
                    wrapperCol: {
                        xs: { span: 20 },
                        sm: { span: 16 },
                    },
                },
                rows,
                hotelSearchList:[],
                search:"",
                selectClear:false,
                searchRate:0,
                checkstate:false,
            }

        },
        computed: {
            ...mapGetters([
                'hotelInListVisible',
                'hotelList',
                'hotelListLoading',
                'userOrderList',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'hotelInList' });
        },
        async mounted() {
            this.hotelLoading()
            await this.getUserOrders()
        },
        methods: {
            ...mapMutations([
               'set_hotelInListVisible'
            ]),
            ...mapActions([
                'getHotelList',
                'getUserOrders',
            ]),
            confirm() {
                this.set_hotelInListVisible(false)
            },
           checkHotel(hotelid){
               this.$router.push({ name: 'hotelDetail', params: { hotelId: hotelid}})
               this.set_hotelInListVisible(false)
           },
            handleSearch(){
                this.hotelSearchList=[]
                for(let i=0;i<this.hotelList.length;i++){
                    if(this.hotelList[i].name.indexOf(this.search)>=0){
                        this.hotelSearchList.push(this.hotelList[i]);
                    }
                }
            },
            hotelLoading(){
                console.log('hi')
                this.hotelSearchList=[]
                for(let i=0;i<this.hotelList.length;i++){
                    this.hotelSearchList.push(this.hotelList[i])
                }
                this.search=''
                this.selectClear=true
                this.searchRate=null
                this.checkstate=false

            },
            checkwhetherin(hotelname){
                console.log('i am in')
                console.log(this.userOrderList.length)
                for(let i=0;i<this.userOrderList.length;i++){
                    console.log('i am in here')
                    if(hotelname==this.userOrderList[i].hotelName){
                        console.log('true')
                        return true
                    }
                }
                return false

            },
            searchOrdered(){
                let temp=[]
                if(this.checkstate==false) {
                    if (this.hotelSearchList.length != 0) {
                        for (let i = 0; i < this.hotelSearchList.length; i++) {
                            if (this.checkwhetherin(this.hotelSearchList[i].name) == true) {
                                temp.push(this.hotelSearchList[i])
                            }
                        }
                        this.hotelSearchList = temp
                    } else {
                        for (let i = 0; i < this.hotelList.length; i++) {
                            if (this.checkwhetherin(this.hotelList[i].name) == true) {
                                temp.push(this.hotelList[i])
                            }
                        }
                        this.hotelSearchList = temp
                    }
                    this.checkstate=true
                }else{
                    this.hotelSearchList=this.hotelList
                    this.checkstate=false
                }

            },
            searchByHotelStar(hotelStar){
              var temp=[]
              for(let i=0;i<this.hotelSearchList.length;i++){
                  if(this.hotelSearchList[i].hotelStar==hotelStar){
                      temp.push(this.hotelSearchList[i])
                  }
              }
              this.hotelSearchList=temp
            },
            searchByHotelRate(){
                var temp=[]
                for(let i=0;i<this.hotelSearchList.length;i++){
                    if(this.hotelSearchList[i].rate>=this.searchRate){
                        temp.push(this.hotelSearchList[i])
                    }
                }
                this.hotelSearchList=temp
            },

            changeType(v){
                if(v=='1'){
                    if(this.search==""){
                        this.hotelSearchList=this.hotelList
                    }else{
                        this.handleSearch()
                    }
                }else if(v=='2'){
                    if(this.search==""){
                        this.hotelSearchList=this.hotelList
                        this.searchByHotelStar('Five')
                    }else{
                        this.handleSearch()
                        this.searchByHotelStar('Five')
                    }
                }else if(v=='3'){
                    if(this.search==""){
                        this.hotelSearchList=this.hotelList
                        this.searchByHotelStar('Four')
                    }else{
                        this.handleSearch()
                        this.searchByHotelStar('Four')
                    }
                }else if(v=='4'){
                    if(this.search==""){
                        this.hotelSearchList=this.hotelList
                        this.searchByHotelStar('Three')
                    }else{
                        this.handleSearch()
                        this.searchByHotelStar('Three')
                    }
                }else if(v=='5'){
                    if(this.search==""){
                        this.hotelSearchList=this.hotelList
                        this.searchByHotelStar('Two')
                    }else{
                        this.handleSearch()
                        this.searchByHotelStar('Two')
                    }
                }else{
                    if(this.search==""){
                        this.hotelSearchList=this.hotelList
                        this.searchByHotelStar('One')
                    }else{
                        this.handleSearch()
                        this.searchByHotelStar('One')
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>