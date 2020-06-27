<template>
	<div>
		<!-- 服务器API -->
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name: "myApi",
		methods: {
			// sj--未完成 对接接口
			async getAllBooks(){
				/**
				 * 从服务器获取所有书籍信息
				 * @return: {Promise}  { code, msg, data {Array} }
				 * */
				let option = {
					url: 'http://127.0.0.1:8003/orderservice/findAll',
					method: 'get'
				}
				try {
					let apiRe = await axios(option);
					return apiRe.data;
				} catch {
					return {
						code: -1,
						msg: 'API获取书籍数据失败',
					}
				}
			},
			async deleteBookById(_id){
				/**
				 * 根据书籍id删除服务器书籍
				 * @param: {Number} _id 书籍id
				 * @return: {Promise} 删除情况 {code, msg}
				 * */
				let option = {
					url: `http://127.0.0.1:8003/orderservice/delete/${_id}`,
					method: 'delete'
				}
				try {
					let apiRe = await axios(option);
					return apiRe.data;
				} catch {
					return {
						code: -1,
						msg: 'API删除书籍数据失败',
					}
				}
			},
			async updateBook(book) {
				let option = {
					url: 'http://127.0.0.1:8003/orderservice/update',
					method: 'put',
					data: book
				}
				try {
					return await axios(option)
				} catch {
					return null
				}
			},
			async createBook(book) {
				let option = {
					url: 'http://127.0.0.1:8003/orderservice/add',
					method: 'post',
					data: book
				}
				try {
					return await axios(option)
				} catch {
					return null
				}
			},
		}
	}
</script>

<style scoped>

</style>
