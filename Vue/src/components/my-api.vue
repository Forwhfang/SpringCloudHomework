<template>
	<div>
		<!-- 服务器API -->
	</div>
</template>

<script>
	// import axios from 'axios'
	export default {
		name: "myApi",
		data(){
			return {
				/* 服务器 Url */
				getAllBooksUrl: '',
				deleteBookByIdUrl: ''
			}
		},
		methods: {
			// sj--未完成 对接接口
			async getAllBooks(){
				/**
				 * 从服务器获取所有书籍信息
				 * @return: {Array} 书籍信息列表 item: {id, ...}
				 * */
				alert('my-api.vue/getAllBooks 未对接接口 目前使用的是测试数据')
				return null;
			},
			async deleteBookById(_id){
				/**
				 * 根据书籍id删除服务器书籍
				 * @param: {Number} _id 书籍id
				 * @return: {Object} 删除情况 {code, msg, ...}
				 * */
				alert('my-api.vue/deleteBookById 未对接接口')
				console.log(_id)
				return null;
			}
		}
	}
</script>

<style scoped>

</style>
