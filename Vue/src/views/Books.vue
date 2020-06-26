<template>
	<div class="books_root">
		<div class="books_root_head">
			<div class="books_root_head_title">个人书单</div>
			<div class="books_root_head_search">
				<input class="books-search_text" placeholder="书名、作者、出版信息" v-model="searchValue" @keyup.enter="search"/>
				<img class="books-search_icon" src="../assets/books/search.png" @click="search" title="搜索"/>
			</div>
		</div>
		<div class="books_root_body">
			<div v-for="(books_index, index) in booksShowing" :key="index">
				<div class="books_root_body_book">
					<img class="books-book_image" :src="books[books_index].imgSrc" :ref="`book_image_${index}`" @load="justifyImage(index, 100)"/>
					<div class="books-book_content">
						<div class="books-book-head">
							<div class="books-book-title">{{books[books_index].title}}</div>
							<img class="books-book-edit" src="../assets/books/edit.png" @click="bookEdit(books[books_index].id)" title="编辑"/>
							<img class="books-book-delete" src="../assets/books/delete.png" @click="bookDelete(books[books_index].id)" title="删除"/>
						</div>
						<div class="books-book-info">{{books[books_index].info}}</div>
						<my-rate :total-score="10" :rate="books[books_index].currScore/10" :size="15"></my-rate>
						<div class="books-book-quote">{{'—— ' + books[books_index].quote}}</div>
					</div>
				</div>
			</div>
			<div class="books_root_body_create">
				<img class="books-create_icon" src="../assets/books/create.png" @click="bookCreate" title="新建"/>
			</div>
		</div>
		<!-- 方法依赖 -->
		<my-api ref="my_api"></my-api>
	</div>
</template>

<script>
	import myRate from '../components/my-rate.vue';
	import myApi from '../components/my-api.vue';
	export default {
		name: "Books",
		components:{
			myRate,
			myApi
		},
		data() {
			return {
				searchValue: '',
				books: [
					/* 测试数据 */
					{
						id: 0,
						imgSrc: require('../assets/test/book1.jpg'),
						title: '红楼梦',
						info: '[清] 曹雪芹 著 / 人民文学出版社 / 1996-12 / 59.70元',
						currScore: 9.6,
						quote: '都云作者痴，谁解其中味？'
					},
					{
						id: 1,
						imgSrc: require('../assets/test/book2.jpg'),
						title: '活着',
						info: '余华 / 作家出版社 / 2012-8-1 / 20.00元',
						currScore: 9.4,
						quote: '生的苦难与伟大'
					},
					{
						id: 0,
						imgSrc: require('../assets/test/book1.jpg'),
						title: '红楼梦2',
						info: '[清] 曹雪芹 著 / 人民文学出版社 / 1996-12 / 59.70元',
						currScore: 9.6,
						quote: '都云作者痴，谁解其中味？'
					},
					{
						id: 1,
						imgSrc: require('../assets/test/book2.jpg'),
						title: '活着2',
						info: '余华 / 作家出版社 / 2012-8-1 / 20.00元',
						currScore: 9.4,
						quote: '生的苦难与伟大'
					}
				],
				booksShowing: []
			}
		},
		async mounted(){
			// sj--未完成 调用API初始化 this.books
			let booksRe = await this.$refs.my_api.getAllBooks();
			console.log(booksRe)
			//
			for(let i=0; i<this.books.length; i++) this.booksShowing.push(i);
		},
		methods:{
			search(){
				/**
				 * 搜索按钮
				 * */
				// 去除前后空格
				this.searchValue = this.searchValue.trim();
				if(this.searchValue.length === 0){
					this.booksShowing = [];
					for(let i=0; i<this.books.length; i++) this.booksShowing.push(i);
					return null;
				}
				this.booksShowing = [];
				this.books.forEach((book, index) =>{
					if(book.title.indexOf(this.searchValue) > -1 || book.info.indexOf(this.searchValue) > -1) this.booksShowing.push(index);
				})
			},
			bookCreate(){
				/**
				 * 书籍新增按钮
				 * */
				this.$router.push('/modification')
				this.$nextTick(() => {
					this.$root.Bus.$emit('createBook')
				})
			},
			bookEdit(_id){
				/**
				 * 书籍修改按钮
				 * @param: {Number} _id 书籍id
				 * */
				this.$router.push('/modification')
				this.$nextTick(() => {
					this.$root.Bus.$emit('editBook', this.books[_id])
				})
			},
			async bookDelete(_id){
				/**
				 * 书籍删除按钮
				 * @param: {Number} _id 书籍id
				 * */
				// sj--未完成 删除书籍
				console.log(_id)
				let deleteRe = await this.$refs.my_api.deleteBookById(_id);
				console.log(deleteRe)
			},
			justifyImage(_index, _width){
				/**
				 * 图片按设置宽度进行等比例放缩
				 * @param: {Number} _index v-for列表index
				 * @param: {Number} _width 图片要显示的宽度
				 * */
				let originWidth = this.$refs[`book_image_${_index}`][0].width;
				let originHeight = this.$refs[`book_image_${_index}`][0].height;
				let scale = _width/originWidth;
				this.$refs[`book_image_${_index}`][0].width = _width;
				this.$refs[`book_image_${_index}`][0].height = originHeight * scale;
			}
		}
	}
</script>

<style scoped>
	.books_root{
		display: flex;
		flex-direction: column;
	}
	/* head */
	.books_root_head{
		display: flex;
		flex-direction: row;
		align-items: baseline;
		padding: 14px 0 34px 0;
		background-color: #f6f6f1;
	}
	.books_root_head_title{
		font-size: 38px;
		min-width: 160px;
		font-weight: bolder;
		font-family: 华文楷体;
		color: rgb(93, 70, 49);
		margin-left: 15%;
	}
	.books_root_head_search{
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-left: 4%;
		width: 60%;
	}
	.books-search_text{
		height: 30px;
		width: 66%;
		padding: 3px 40px 3px 20px;
		text-align: left;
		border-width: 0;
		border-radius: 10px;
		box-shadow: 3px 3px 5px rgba(97, 97, 97, 0.38);
		outline: none;
	}
	.books-search_icon{
		position: relative;
		left: -30px;
		width: 25px;
		height: 25px;
	}
	.books-search_icon:hover {
		transform: scale(1.2);
	}

	/* books_root_body */
	.books_root_body{
		display: flex;
		flex-direction: column;
		margin: 0 10%;
	}
	.books_root_body_book{
		display: flex;
		flex-direction: row;
		padding: 20px 0;
		border-bottom: rgba(132, 132, 132, 0.42) dotted 1px;
	}
	.books-book_image{
		margin-left: 14px;
		box-shadow: 3px 3px 6px #717171;
	}
	.books-book_content{
		display: flex;
		flex-direction: column;
		align-items: start;
		margin-left: 22px;
		width: 100%;
	}
	.books-book-head{
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		width: 100%;
	}
	.books-book-title{
		font-size: 18px;
		margin-top: 4px;
		font-weight: bold;
	}

	.books-book-edit{
		margin-left: auto;
		width: 25px;
		height: 25px;
	}
	.books-book-edit:hover {
		transform: scale(1.2);
	}

	.books-book-delete{
		margin-left: 20px;
		width: 25px;
		height: 25px;
	}
	.books-book-delete:hover {
		transform: scale(1.2);
	}
	.books_root_body_create{
		display: flex;
		position: sticky;
		bottom: 4%;
		margin: 20px -4% 0 auto;
		border-radius: 2px;
		background-color: white;
		opacity: 0.85;
	}
	.books-create_icon{
		width: 52px;
		height: 52px;
	}
	.books-create_icon:hover {
		transform: scale(1.2);
	}

	.books-book-info{
		margin: 16px 0;
		font-size: 14px;
		color: #7d7d7d;
	}
	.books-book-quote{
		margin-top: 16px;
		font-size: 14px;
	}
</style>
