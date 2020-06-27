<template>
	<div>
		<!-- 服务器API -->
	</div>
</template>

<script>
	import axios from 'axios'
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
				return {
					code: 0,
					msg: 'success',
					data: [
						/* 测试数据 */
						{
							"id": "1",
							"imgUrl":"https://img1.doubanio.com/view/subject/s/public/s1070959.jpg",
							"name": "红楼梦",
							"star": 9.6,
							"quotes": "都云作者痴，谁解其中味？",
							"author":"[清] 曹雪芹 著",
							"translator":"",
							"pubdate":"1996-12",
							"press":"人民文学出版社"
						},{
							"id": "2",
							"imgUrl":"https://img3.doubanio.com/view/subject/s/public/s29053580.jpg",
							"name": "活着",
							"star": 9.4,
							"quotes": "生的苦难与伟大",
							"author":"余华",
							"translator":"",
							"pubdate":"2012-8-1",
							"press":"作家出版社"
						}
					]
				};
			},
			async deleteBookById(_id){
				/**
				 * 根据书籍id删除服务器书籍
				 * @param: {Number} _id 书籍id
				 * @return: {Object} 删除情况 {code, msg, ...}
				 * */
				alert('my-api.vue/deleteBookById 未对接接口')
				console.log(_id)
				return {
					code: 0,
					msg: 'success'
				};
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
